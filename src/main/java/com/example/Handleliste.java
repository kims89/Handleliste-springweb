package com.example;


import org.springframework.data.annotation.Id;

public class Handleliste {

    public String getId() {
        return id;
    }

    public void setVare(String vare) {
        this.vare = vare;
    }

    public String getVare() {
        return vare;
    }

    public int getSortering() {
        return sortering;
    }

    public void setSortering(int sortering) {
        sortering = sortering;
    }


    @Id
    public String id;

    public String vare;

    public int sortering;


  public Handleliste() {}
//
   public Handleliste(int Sortering, String Vare) {
        this.vare = Vare;
       this.sortering = Sortering;
 }

    @Override
    public String toString() {
        return String.format(
                "Handleliste[sortering=%s, id=%s, Vare='%s']",
                sortering, id, vare);
    }

}