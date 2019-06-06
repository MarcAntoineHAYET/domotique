package com.m2istvequipe3.ServeurDomotique.Equipement;


import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Equipement {
    @Id
    private String nodeid;

    private int etat;

    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    private List<Donnees> donnees = new ArrayList<>();

    @OneToMany(cascade = {CascadeType.ALL})
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Donnees_telephone> donnees_telephone = new ArrayList<>();

    public Equipement() {
    }

    public String getNodeid() {
        return nodeid;
    }

    public void setNodeid(String nodeid) {
        this.nodeid = nodeid;
    }

    public int getEtat() {
        return etat;
    }

    public void setEtat(int etat) {
        this.etat = etat;
    }

    public List<Donnees> getDonnees() {
        return donnees;
    }

    public void setDonnees(List<Donnees> donnees) {
        this.donnees = donnees;
    }

    public List<Donnees_telephone> getDonnees_telephone() {
        return donnees_telephone;
    }

    public void setDonnees_telephone(List<Donnees_telephone> donnees_telephone) {
        this.donnees_telephone = donnees_telephone;
    }

    @Override
    public String toString() {
        return "Equipement{" +
                "nodeid='" + nodeid + '\'' +
                ", etat=" + etat +
                ", donnees=" + donnees +
                ", donnees_telephone=" + donnees_telephone +
                '}';
    }
}
