package com.ecommerce.microcommerce_IPI.dao;

import com.ecommerce.microcommerce_IPI.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface ProductDao extends JpaRepository<Product, Integer> {
    //Find by Id
    Product findById(int id);

    //Find great price than
    List<Product> findByPrixGreaterThan(int prixLimit);

    //Find by name
    List<Product> findByNomLike(String research);

    //request
    @Query("SELECT p FROM Product p WHERE p.prix > :prixLimit")
    public List<Product> chercherUnProduitCher(@Param("prixLimit")int prixLimit);

    //Order by Name
    public List<Product> findByOrderByNomAsc();
}
