package com.m2istvequipe3.ServeurDomotique.Equipement;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Donnees_telephone {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private int id;

    private String valeur1;
    private String valeur2;
    private String valeur3;
    private String valeur4;

    public Donnees_telephone() {
    }

    public String getValeur1() {
        return valeur1;
    }

    public void setValeur1(String valeur1) {
        this.valeur1 = valeur1;
    }

    public String getValeur2() {
        return valeur2;
    }

    public void setValeur2(String valeur2) {
        this.valeur2 = valeur2;
    }

    public String getValeur3() {
        return valeur3;
    }

    public void setValeur3(String valeur3) {
        this.valeur3 = valeur3;
    }

    public String getValeur4() {
        return valeur4;
    }

    public void setValeur4(String valeur4) {
        this.valeur4 = valeur4;
    }

    @Override
    public String toString() {
        return "Donnees_telephone{" +
                "valeur1='" + valeur1 + '\'' +
                ", valeur2='" + valeur2 + '\'' +
                ", valeur3='" + valeur3 + '\'' +
                ", valeur4='" + valeur4 + '\'' +
                '}';
    }
}
