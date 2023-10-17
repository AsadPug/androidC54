package com.example.projetpetitionnaire;

import java.io.Serializable;

public class Membre implements Serializable {
    private String nom;
    private String prenom;
    private String objectif;
    private int degre ;
    private int age ;


    public Membre(String nom, String prenom, String objectif, int degre, int age) {
        this.nom = nom;
        this.prenom = prenom;
        this.objectif = objectif;
        this.degre = degre;
        this.age = age;

    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getObjectif() {
        return objectif;
    }

    public int getDegre() {
        return degre;
    }

    public int getAge() {
        return age;
    }


    public static class Builder
    {
        private String nom;
        private String prenom;
        private String objectif;
        private int degre ;
        private int age ;



        public Builder setNom(String nom) {
            if (! nom.equals(""))
                this.nom = nom;
            return this;
        }

        public Builder setPrenom(String prenom) {
            if ( !prenom.equals(""))
            this.prenom = prenom;
            return this;
        }

        public Builder setObjectif(String objectif) {
            this.objectif = objectif;
            return this;
        }

        public Builder setAge(int age) {
            this.age = age;
            return this;
        }

        public Builder setDegre(int degre) {
            this.degre = degre;
            return this;
        }


        public Membre build ()
        {
            return new Membre( nom, prenom, objectif, degre, age);
        }
    }
}
