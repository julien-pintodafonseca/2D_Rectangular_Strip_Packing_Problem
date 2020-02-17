package com.ensimag.Models;

/**
 * Created by houdee on 2/17/20.
 */
public class Rectangle {
    private int h; // hauteur
    private int l; // largeur

    public Rectangle(int _h, int _l) {
        this.h = _h;
        this.l = _l;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

    public int getL() {
        return l;
    }

    public void setL(int l) {
        this.l = l;
    }
}
