package com.ensimag.Sorts;

import com.ensimag.Models.Piece;

import java.util.Comparator;

/**
 * Trie deux pièces en fonction de leur aire, par ordre croissant
 * @author Groupe6
 */
public class SortByArea implements Comparator<Piece> {
    /**
     * Méthode qui permet de comparer deux pièces en fonction de leur aire,
     * elle permet le trie par ordre croissant de l'aire des pièces comparées
     * @param r1 : première pièce, à comparer avec r1
     * @param r2 : deuxième pièce, à comparer avec r2
     * @return 0 si elles ont la même aire, la différence entre l'aide de r2 et r1 sinon
     */
    public int compare(Piece r1, Piece r2) {
        if (getArea(r1) != getArea(r2)) {
            return getArea(r2) - getArea(r1);
        } else
            return 0;
    }

    /**
     * Méthode permettant de calculer l'aire d'une pièce
     * @param r : la pièce dont on veut calculer l'aire
     * @return l'aire de la pièce
     */
    private int getArea(Piece r) {
        return r.getH()*r.getW();
    }
}
