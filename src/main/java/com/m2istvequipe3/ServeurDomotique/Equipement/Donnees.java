package com.m2istvequipe3.ServeurDomotique.Equipement;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Donnees {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private int id;

    private String instance;
    private String type;
    private String valueBefore;
    private String valueAfter;
    private String etat;
    private String date;

    public Donnees() {
    }

    public String getInstance() {
        return instance;
    }

    public void setInstance(String instance) {
        this.instance = instance;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValueBefore() {
        return valueBefore;
    }

    public void setValueBefore(String valueBefore) {
        this.valueBefore = valueBefore;
    }

    public String getValueAfter() {
        return valueAfter;
    }

    public void setValueAfter(String valueAfter) {
        this.valueAfter = valueAfter;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Donnees{" +
                "instance='" + instance + '\'' +
                ", type='" + type + '\'' +
                ", valueBefore='" + valueBefore + '\'' +
                ", valueAfter='" + valueAfter + '\'' +
                ", etat='" + etat + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
