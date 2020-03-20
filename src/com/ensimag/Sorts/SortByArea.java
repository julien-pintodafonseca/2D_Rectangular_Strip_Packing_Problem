package com.ensimag.Sorts;

import com.ensimag.Models.Piece;

import java.util.Comparator;

public class SortByArea implements Comparator<Piece> {
    public int compare(Piece r1, Piece r2) {
        if (getArea(r1) != getArea(r2)) {
            return getArea(r2) - getArea(r1);
        } else
            return 0;
    }

    private int getArea(Piece r) {
        return r.getH()*r.getW();
    }
}
