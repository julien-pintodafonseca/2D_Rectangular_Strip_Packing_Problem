package com.ensimag.Models;

/**
 * Created by houdee on 2/17/20.
 */
public class Piece {
    private int h; // height
    private int w; // width

    public Piece(int _h, int _w) {
        this.h = _h;
        this.w = _w;
    }

    public int getH() {
        return this.h;
    }

    public int getW() {
        return this.w;
    }

    public void setH(int _h) {
        this.h = _h;
    }
}
