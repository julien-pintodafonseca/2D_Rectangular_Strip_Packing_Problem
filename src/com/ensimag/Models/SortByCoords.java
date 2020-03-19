package com.ensimag.Models;

import java.util.Comparator;

/**
 * Created by solokwal on 2/21/20.
 */

public class SortByCoords implements Comparator<PieceWithCoords> {
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
