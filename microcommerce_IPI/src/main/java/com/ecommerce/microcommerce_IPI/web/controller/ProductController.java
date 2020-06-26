package com.ecommerce.microcommerce_IPI.web.controller;

import com.ecommerce.microcommerce_IPI.dao.ProductDao;
import com.ecommerce.microcommerce_IPI.model.Product;
import com.ecommerce.microcommerce_IPI.web.exceptions.ProduitGratuitException;
import com.ecommerce.microcommerce_IPI.web.exceptions.ProduitIntrouvableException;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(value = "API pour es operations CRUD sur les produits.")
@RestController
public class ProductController {
    @Autowired
    private ProductDao productDao;

    //Logger
    private static final Logger logger =
            LoggerFactory. getLogger (ProductController.class);

    @Autowired
    private HttpServletRequest requestContext ;

    //Récupérer la liste des produits
    @RequestMapping(value = "/Produits", method = RequestMethod.GET)
    public MappingJacksonValue listeProduits() {
        logger.info("Début d'appel au service Produit pour la requête : " +
                requestContext.getHeader("req-id"));

        Iterable<Product> produits = productDao.findAll();

        SimpleBeanPropertyFilter monFiltre =
                SimpleBeanPropertyFilter.serializeAllExcept("prixAchat");
        FilterProvider listDeNosFiltres = new
                SimpleFilterProvider().addFilter("monFiltreDynamique", monFiltre);

        MappingJacksonValue produitsFiltres = new
                MappingJacksonValue(produits);
        produitsFiltres.setFilters(listDeNosFiltres);

        return produitsFiltres;
    }

    //Récupérer un produit par son Id
    @ApiOperation(value = "Recupere un produit grâce à son ID à condition que celui-ci soit en stock!")
    @GetMapping(value = "/Produits/{id}")
    public Product afficherUnProduit(@PathVariable int id) {
        Product produit = productDao.findById(id);
        if(produit == null) {
            throw new ProduitIntrouvableException("Le produit avec l'id " + id + " est INTROuvABLE.");
        }
        return produit;
    }

    //Greater
    @GetMapping(value = "test-prix/produits/{prixLimit}")
    public List<Product> testeDeRequetes(@PathVariable int prixLimit) {
        return productDao.findByPrixGreaterThan(400);
    }

    //Name
    @GetMapping(value = "test-nom/produits/{recherche}")
    public List<Product> testeDeRequetes(@PathVariable String
                                                 recherche) {
        return productDao.findByNomLike("%" + recherche + "%");
    }


    //ajouter un produit
    @PostMapping(value = "/Produits")
    public ResponseEntity<Void> ajouterProduit(@Valid @RequestBody Product
                                                       product) {
        if(product.getPrix() == 0) {
            throw new ProduitGratuitException("On ne peut pas rajouter de produit gratuit. Veuillez mettre un prix.");
        }
        Product productAdded = productDao.save(product);

        if (productAdded == null)
            return ResponseEntity.noContent().build();
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(productAdded.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    //deleting
    @DeleteMapping (value = "/Produits/{id}")
    public void supprimerProduit(@PathVariable int id) {
        productDao.delete(productDao.findById(id));
    }

    @PutMapping (value = "/Produits")
    public void updateProduit(@RequestBody Product product) {
        productDao.save(product);
    }

    @GetMapping(value="/Produits/AdminProduits")
    public Map<String,Integer> calculerMargeProduit() {
        List<Product> products = productDao.findAll();
        Map<String,Integer> maMap = new HashMap<>();
        for(Product p: products) {
            maMap.put(p.toString(),p.getPrix() - p.getPrixAchat());
        }
        return maMap;
    }

    @GetMapping(value="/Produits/Trier")
    public List<Product> trierProduitsParOrdreAlphabetique() {
        return productDao.findByOrderByNomAsc();
    }
}
