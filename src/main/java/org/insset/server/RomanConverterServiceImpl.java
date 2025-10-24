/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.insset.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import org.insset.client.service.RomanConverterService;

/**
 *
 * @author user
 */
@SuppressWarnings("serial")
public class RomanConverterServiceImpl extends RemoteServiceServlet implements
        RomanConverterService {

@Override
    public String convertDateYears(String nbr) throws IllegalArgumentException {
        if (nbr == null || nbr.trim().isEmpty()) {
            throw new IllegalArgumentException("La date ne peut pas être vide.");
        }

        String[] parties = nbr.split("/");
        if (parties.length != 3) {
            throw new IllegalArgumentException("Le format doit être JJ/MM/AAAA (ex: 15/03/1910).");
        }

        try {
            int jour = Integer.parseInt(parties[0]);
            int mois = Integer.parseInt(parties[1]);
            int annee = Integer.parseInt(parties[2]);

            if (jour < 1 || jour > 31 || mois < 1 || mois > 12 || annee < 1 || annee > 3999) {
                throw new IllegalArgumentException("Date invalide : hors limites autorisées.");
            }

            String jourRomain = convertArabeToRoman(jour);
            String moisRomain = convertArabeToRoman(mois);
            String anneeRomaine = convertArabeToRoman(annee);

            return jourRomain + "/" + moisRomain + "/" + anneeRomaine;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("La date doit contenir uniquement des chiffres séparés par '/'.");
        }
    }

    /**
     * Convertit un nombre romain en entier.
     */
    @Override
    public Integer convertRomanToArabe(String nbr) throws IllegalArgumentException {
        if (nbr == null || nbr.trim().isEmpty()) {
            throw new IllegalArgumentException("Le nombre romain ne peut pas être vide.");
        }

        String roman = nbr.toUpperCase().trim();
        if (!roman.matches("[IVXLCDM]+")) {
            throw new IllegalArgumentException("Caractères non valides dans le nombre romain : " + nbr);
        }

        int total = 0;
        for (int i = 0; i < roman.length(); i++) {
            int current = romanCharToInt(roman.charAt(i));
            if (i + 1 < roman.length()) {
                int next = romanCharToInt(roman.charAt(i + 1));
                if (current < next) {
                    total -= current;
                } else {
                    total += current;
                }
            } else {
                total += current;
            }
        }

        return total;
    }

    /**
     * Convertit un entier (1–3999) en nombre romain.
     */
    @Override
    public String convertArabeToRoman(Integer nbr) throws IllegalArgumentException {
        if (nbr == null || nbr <= 0 || nbr > 3999) {
            throw new IllegalArgumentException("Le nombre doit être compris entre 1 et 3999.");
        }

        int[] valeurs = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] symboles = {
            "M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"
        };

        StringBuilder resultat = new StringBuilder();

        for (int i = 0; i < valeurs.length; i++) {
            while (nbr >= valeurs[i]) {
                resultat.append(symboles[i]);
                nbr -= valeurs[i];
            }
        }

        return resultat.toString();
    }

    /**
     * Méthode utilitaire pour convertir un caractère romain en entier.
     */
    private int romanCharToInt(char c) {
        switch (c) {
            case 'I': return 1;
            case 'V': return 5;
            case 'X': return 10;
            case 'L': return 50;
            case 'C': return 100;
            case 'D': return 500;
            case 'M': return 1000;
            default:
                throw new IllegalArgumentException("Caractère romain invalide : " + c);
        }
    }
}
