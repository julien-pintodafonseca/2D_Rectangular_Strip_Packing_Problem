package com.ensimag.Sorts;

import com.ensimag.Models.PieceWithCoords;

import java.util.Comparator;

/**
 * Created by solokwal on 2/21/20.
 */

public class SortByCoords implements Comparator<PieceWithCoords> {
    /**
     * Méthode permettant de comparer deux pièces par rapport à leurs coordonnées
     * @param p1 : première pièce à comparer
     * @param p2 : deuxième pièce à comparer
     * @return : 1 si Y de p1> y de p2
     *           1 si p1 et p2 ont le même Y mais que Xde p1 > X de p2
     *           0 si elles ont les mêmes coordonées
     *           -1 si aucun des cas précédents
     */
    public int compare(PieceWithCoords p1, PieceWithCoords p2) {
        if (p1.getY() > p2.getY()) {
            return 1;
        } else if (p1.getY() == p2.getY() && p1.getX() > p2.getX()) {
            return 1;
        }  else if (p1.getY() == p2.getY() && p1.getX() == p2.getX()) {
            return 0;
        } else {
            return -1;
        }
    }
}
