package com.company;

/**
 * Created by Sandjiv on 11/11/2015.
 */
public class ItemCourse {


    int idItem, chainId;
    String nom;
    String prix;

    String URL;
    Boolean isTaken;


    public ItemCourse() {

    }

    public ItemCourse(ItemCourse item) {
        nom = item.getNom();
        URL = item.getURL();
        prix = item.getPrix();
        chainId = item.getChainId();
        idItem = item.getIdItem();
        isTaken = item.getTaken();
    }


    public int getIdItem() {
        return idItem;
    }

    public void setIdItem(int idItem) {
        this.idItem = idItem;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrix() {
        return prix;
    }

    public void setPrix(String prix) {
        this.prix = prix;
    }

    public Boolean getTaken() {
        return isTaken;
    }

    public void setTaken(Boolean taken) {
        isTaken = taken;
    }


    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public int getChainId() {
        return chainId;
    }

    public void setChainId(int chainId) {
        this.chainId = chainId;
    }
}


