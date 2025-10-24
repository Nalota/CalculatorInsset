/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.insset.server;

/**
 *
 * @author insset
 */
public class PourcentageServiceImpl {

     /**
     

    Calcule le prix original avant remise.*

        @param pourcentage le pourcentage de remise (ex : 20 pour 20%)
         
            @param prixRemise le prix après remise

@return le prix d'origine avant remise@throws IllegalArgumentException si les valeurs sont invalides*/
    public Double calculRemise(Integer pourcentage,Double prixRemise) throws IllegalArgumentException {// Vérifications de base
   
        if (pourcentage == null || prixRemise == null) {
        throw new IllegalArgumentException("Les valeurs ne peuvent pas être nulles.");
    }
    if (pourcentage < 0 || pourcentage >= 100) {
        throw new IllegalArgumentException("Le pourcentage doit être compris entre 0 et 99.");}
    if (prixRemise <= 0) {
        throw new IllegalArgumentException("Le prix remisé doit être supérieur à 0.");}
     double prixOriginal = prixRemise / (1 - (pourcentage / 100.0));
        // On arrondit à l'entier le plus proche
        return (double) Math.round(prixOriginal);
    }
}
