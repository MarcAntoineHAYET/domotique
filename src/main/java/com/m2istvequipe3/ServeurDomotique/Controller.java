package com.m2istvequipe3.ServeurDomotique;

import com.m2istvequipe3.ServeurDomotique.Equipement.Donnees;
import com.m2istvequipe3.ServeurDomotique.Equipement.Donnees_telephone;
import com.m2istvequipe3.ServeurDomotique.Equipement.Equipement;
import com.m2istvequipe3.ServeurDomotique.Equipement.Equipement_Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@RestController
public class Controller {

    @Autowired
    Equipement_Repository equipement_repository;

    /**
     * renvoie l'etat d'un equipement
     * pour la prise, 0 si eteind, 1 si allumer
     * pour le telephone, renvoie 2 si rien, 1 si tout va bien, 0 si probleme
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/renvoie_actions/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public int action(@PathVariable("id") String id) {
        System.out.println("Passe dans renvoie action id=" + id);
        try {
            System.out.println("valeur detat envoyer " +  equipement_repository.getEquipement(id).getEtat());
            return equipement_repository.getEquipement(id).getEtat();
        } catch (Exception e) {
            System.out.println("Erreur dans renvoie action");
        }
        return 0;
    }

    /**
     * change l'etat d'un equipement
     * voir revoie_action pour valeur
     *
     * @param etat
     * @param id
     */
    @GetMapping(value = "/changer_etat/{id}/{etat}")
    public void action(@PathVariable("etat") int etat, @PathVariable("id") int id) {
        System.out.println("Passe dans changer d etat etat=" + etat);
        try {
            Equipement equipement = equipement_repository.getEquipement(String.valueOf(id));
            equipement.setEtat(etat);
            equipement_repository.save(equipement);
        } catch (Exception e) {
            System.out.println("Erreur dans changer etat");
        }

    }

    @PostMapping(value = "/creation_equipement", produces = {MediaType.APPLICATION_JSON_VALUE})
    public String creation_equipement(@RequestBody Equipement equipement) {
        equipement_repository.save(equipement);

        return "creation reussi";

    }

    /**
     * creer des donnees pour le telephone
     *
     * @param info_equipement
     * @return
     */
    @Async
    @PostMapping(value = "/donnees_telephone", produces = {MediaType.APPLICATION_JSON_VALUE})
    public String getDonneesTelephone(@RequestBody String info_equipement) {
        System.out.println("Passe dans donnees telephonne info=" + info_equipement.toString());

        String valeurs[] = info_equipement.split("&");

        Donnees_telephone donnees_telephone = new Donnees_telephone();

        try {
            donnees_telephone.setValeur1(valeurs[1].split("=")[1]);
            donnees_telephone.setValeur2(valeurs[2].split("=")[1]);
            donnees_telephone.setValeur3(valeurs[3].split("=")[1]);
            donnees_telephone.setValeur4(valeurs[4].split("=")[1]);
        } catch (Exception e) {
        }

        Equipement equipement = new Equipement();
        try {
            equipement = equipement_repository.getEquipement(valeurs[0].split("=")[1]);
            equipement.getDonnees_telephone().add(donnees_telephone);
            equipement_repository.save(equipement);
        } catch (Exception e) {
            System.out.println("Erreur dans donnees telephone");
        }

        return equipement.toString();

    }

    /**
     * Creer des donnees pour les capteurs
     *
     * @param equipement
     * @return
     */
    @Async
    @PostMapping(value = "/donnees_capteur", produces = {MediaType.APPLICATION_JSON_VALUE})
    public String donnees_capteur(@RequestBody Equipement equipement) {
        System.out.println("Passe dans le donnees capteur equipement=" + equipement);
        Equipement equipement1 = new Equipement();
        try {
            equipement1 = equipement_repository.getEquipement(equipement.getNodeid());
            equipement1.getDonnees().add(equipement.getDonnees().get(0));
            equipement_repository.save(equipement1);
        } catch (Exception e) {
            System.out.println("Erreur dans donnees capteur");
        }

        return equipement.toString();
    }

    /**
     * Recoit une reponse de la skill amazon
     * Si la reponse contient true, tout va bien
     * Sinon probleme
     *
     * @param reponse
     * @return
     */
    @PostMapping(value = "/reponse_skill_amazon")
    public String getEtatSante(@RequestBody String reponse) {
        System.out.println("Passe dans reponse skill amazon reponse=" + reponse);
        try {
            Equipement equipement = equipement_repository.getEquipement("11");
            Equipement equipement1 = equipement_repository.getEquipement("7");

            if (reponse.contains("true")) {
                equipement.setEtat(1);
                equipement1.setEtat(1);
            } else {
                equipement.setEtat(0);
                equipement1.setEtat(0);
            }
            equipement_repository.save(equipement);
            return reponse;
        } catch (Exception e) {
            System.out.println("Erreur dans reponse skill amazon");
        }
        return "";
    }

    /**
     * Renvoie la temperature
     *
     * @return
     */
    @GetMapping(value = "/temperature")
    public String temperature() {
        System.out.println("Passe dans temperature");
        try {
            return equipement_repository.resultat();
        } catch (Exception e) {
            System.out.println("Erreur dans temperature");
        }
        return "";
    }

    /**
     * Renvoie la luminosite
     *
     * @return
     */
    @GetMapping(value = "/luminance")
    public String luminance() {
        System.out.println("Passe dans Luminance");
        try {
            return equipement_repository.Luminance();
        } catch (Exception e) {
            System.out.println("Erreur dans luminance");
        }
        return "";

    }

    /**
     * Route appele par le telephone pour savoir si tout va bien
     *
     * @return
     */
    @GetMapping(value = "/tout_va_bien/{id}")
    public String tout_va_bien(@PathVariable String id) {
        //System.out.println("Passe dans tout_va_bien");

        if (id.equals("telephone")) {
            try {
                return equipement_repository.getEquipement("11").getEtat() + "";
            } catch (Exception e) {
                System.out.println("Erreur dans tout va bien telephone");
            }
        }
        if (id.equals("luminance")) {
            try {
                return equipement_repository.getEquipement("7").getEtat() + "";
            } catch (Exception e) {
                System.out.println("Erreur dans tout va bien luminance");
            }
        }
        return "";

    }

    /**
     * Le telephone reset la valeur pour savoir si tous va bien
     */
    @GetMapping(value = "/tout_va_bien_reset/{id}")
    public void tout_va_bien_reset(@PathVariable String id) {
        System.out.println("Passe tout_va_bien reset");
        if (id.equals("telephone")) {
            try {
                Equipement equipement = equipement_repository.getEquipement("11");
                equipement.setEtat(2);
                equipement_repository.save(equipement);
            } catch (Exception e) {
                System.out.println("Erreur dans tout_va_bien_reset telephone");
            }
        }
        if (id.equals("luminance")) {
            try {
                Equipement equipement = equipement_repository.getEquipement("7");
                equipement.setEtat(2);
                equipement_repository.save(equipement);
            } catch (Exception e) {
                System.out.println("Erreur dans tout_va_bien_reset luminance");
            }
        }
    }


    @Bean
    public CommandLineRunner demo() {
        return (args) -> {
/*
            RestTemplate restTemplate = new RestTemplate();

            HttpEntity<String> request = new HttpEntity<>("Test RestTemplate");
                      String foo = restTemplate.postForObject("http://192.168.43.99:8080/domotique/speak", request, String.class);

            Executor executor = Executors.newSingleThreadExecutor();
            executor.execute(() -> {
                int i = 0;
                while (i < 60) {
                    try {
                        Thread.sleep(1000);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("valeur de i " + i);
                    i++;
                }
            });
*/
            /*
            Equipement equipement = new Equipement();
            equipement.setEtat(1);
            equipement.setNodeid("10"); //Motion
            equipement_repository.save(equipement);

            equipement = new Equipement();
            equipement.setEtat(1);
            equipement.setNodeid("11");//Accelerometer
            equipement_repository.save(equipement);

            equipement = new Equipement();
            equipement.setEtat(1);
            equipement.setNodeid("12");//Locations
            equipement_repository.save(equipement);
*/


            Equipement equipement = new Equipement();
            equipement.setEtat(1);
            equipement.setNodeid("13");//Batterie
            equipement_repository.save(equipement);

        };
    }


}
