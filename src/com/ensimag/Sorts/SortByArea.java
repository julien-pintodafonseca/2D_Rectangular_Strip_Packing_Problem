package com.ensimag.Sorts;

import com.ensimag.Models.Piece;

import java.util.Comparator;

public class SortByArea implements Comparator<Piece> {
    /**
     * Méthode qui permet de comparer deux pièces en fonction de leur aire
     * @param r1 : première pièce à comparer
     * @param r2 : deuxième pièce à comparer
     * @return 0 si elles ont la même  aire si non la différence entre des deux aires
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
