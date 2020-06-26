package com.ecommerce.microcommerce_IPI.model;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Min;

@Entity
//@JsonFilter("monFiltreDynamique")
public class Product {
    @Id
    @GeneratedValue
    private int id;

    @Length(min=3, max=20, message = "Nom trop long ou trop court")
    private String nom;

    @Min(value =1)
    private int prix;
    //information que nous ne souhaitons pas exposer
   // @JsonIgnore
    private int prixAchat;

    //default
    public Product() {
    }

    //testing
    public Product(int id, String nom, int prix, int prixAchat) {
        this.id = id;
        this.nom = nom;
        this.prix = prix;
        this.prixAchat = prixAchat;
    }

    //Get ID
    public int getId() {
        return id;
    }

    //Set ID
    public void setId(int id) {
        this.id=id;
    }

    //Get Nom
    public String getNom() {
        return nom;
    }

    //Set Nom
    public void setNom(String nom) {
        this.nom=nom;
    }

    //Get Prix
    public int getPrix() {
        return prix;
    }

    //Set Prix
    public void setPrix(int prix) {
        this.prix=prix;
    }

    //Get Prix Achat
    public int getPrixAchat() {
        return prixAchat;
    }

    //Set Prix Achat
    public void setPrixAchat(int prixAchat) {
        this.prixAchat = prixAchat;
    }

    //Methode String
    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prix=" + prix +
                '}';
    }
}
