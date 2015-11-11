package com.company;

import java.util.ArrayList;

/**
 * Created by Sandjiv on 11/11/2015.
 */
public class ListeCourse {
    String nom;

    public int getIdListe() {
        return idListe;
    }

    public void setIdListe(int idListe) {
        this.idListe = idListe;
    }

    int idListe;
    public  ListeCourse(){

    }
    public ListeCourse(ListeCourse liste) {
        nom = liste.getNom();
        budget = liste.getBudget();
        Date = liste.getDate();
        idListe = liste.idListe;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }

    String budget;
    String Date;

}
