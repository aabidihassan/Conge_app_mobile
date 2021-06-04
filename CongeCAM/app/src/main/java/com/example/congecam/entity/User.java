package com.example.congecam.entity;

public class User {
    private int id, id_service;
    private String name, email;

    public User(int id, int id_service, String name, String email) {
        this.id = id;
        this.id_service = id_service;
        this.name = name;
        this.email = email;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setId_service(int id_service) {
        this.id_service = id_service;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public int getId_service() {
        return id_service;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
