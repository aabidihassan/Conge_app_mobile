package com.example.congecam.entity;

public class Conge {
    private int id_conge, type_vac;
    private String name, referance, date_debut, date_fin;

    public Conge(int id_conge, int type_vac, String name, String referance, String date_debut, String date_fin) {
        this.id_conge = id_conge;
        this.type_vac = type_vac;
        this.name = name;
        this.referance = referance;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
    }

    public void setId_conge(int id_conge) {
        this.id_conge = id_conge;
    }

    public void setType_vac(int type_vac) {
        this.type_vac = type_vac;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setReferance(String referance) {
        this.referance = referance;
    }

    public void setDate_debut(String date_debut) {
        this.date_debut = date_debut;
    }

    public void setDate_fin(String date_fin) {
        this.date_fin = date_fin;
    }

    public int getId_conge() {
        return id_conge;
    }

    public int getType_vac() {
        return type_vac;
    }

    public String getName() {
        return name;
    }

    public String getReferance() {
        return referance;
    }

    public String getDate_debut() {
        return date_debut;
    }

    public String getDate_fin() {
        return date_fin;
    }
}
