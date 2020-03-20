package com.ensimag.Sorts;

import com.ensimag.Models.Rectangle;

import java.util.Comparator;

public class SortByArea implements Comparator<Rectangle> {
    public int compare(Rectangle r1, Rectangle r2) {
        if (getArea(r1) != getArea(r2)) {
            return getArea(r2) - getArea(r1);
        } else
            return 0;
    }

    private int getArea(Rectangle r) {
        return r.getH()*r.getW();
    }
}
