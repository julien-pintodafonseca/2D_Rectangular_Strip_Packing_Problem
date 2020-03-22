package com.ensimag.Models;

/**
 * Class Piece
 */
public class Piece {
    private int h; // height
    private int w; // width

    /**
     * Constructeur de Piece
     * @param _h :  hauteur de la pièce
     * @param _w : largeur de la pièce
     */
    public Piece(int _h, int _w) {
        this.h = _h;
        this.w = _w;
    }

    /**
     * Getter de la hauteur de la pièce
     * @return l'attribut h
     */
    public int getH() {
        return this.h;
    }

    /**
     * Getter de la largeur de la pièce
     * @return l'attribut w
     */
    public int getW() {
        return this.w;
    }

    /**
     * Setter de la hauteur de la pièce
     * @param _h : nouvelle valeur l'attribut h
     */
    public void setH(int _h) {
        this.h = _h;
    }
}
