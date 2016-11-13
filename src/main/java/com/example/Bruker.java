package com.example;

import org.springframework.data.annotation.Id;


public class Bruker {

    @Id
    public String id;
    public String brukernavn;
    public String passord;
    public String rolle;


    public Bruker(String brukernavn, String passord, String rolle) {
        this.brukernavn = brukernavn;
        this.passord = passord;
        this.rolle = rolle;
    }

    public String getRolle() {
        return rolle;
    }

    public void setRolle(String rolle) {
        this.rolle = rolle;
    }

    public String getBrukernavn() {
        return brukernavn;
    }

    public void setBrukernavn(String brukernavn) {
        this.brukernavn = brukernavn;
    }

    public String getPassord() {
        return passord;
    }

    public void setPassord(String passord) {
        this.passord = passord;
    }
}
