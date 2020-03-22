package com.ensimag.Sorts;

import com.ensimag.Models.PieceWithCoords;

import java.util.Comparator;

/**
 * Class SortByCoords
 * @author Groupe6
 */
public class SortByCoords implements Comparator<PieceWithCoords> {
    /**
     * Méthode permettant de comparer deux pièces par rapport à leurs coordonnées,
     * elle permet le trie par ordre croissant des pièces en fonction de leur Y puis de leur X
     * @param p1 : première pièce, à comparer avec p2
     * @param p2 : deuxième pièce, à comparer avec p1
     * @return : 1 si Y de p1 supérieur à y de p2
     *           1 si p1 et p2 ont le même Y mais que X de p1 supérieur à X de p2
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
