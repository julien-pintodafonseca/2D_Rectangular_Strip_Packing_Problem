package com.ensimag.Models;

/**
 * Created by houdee on 2/17/20.
 */
public class Piece {
    private int h; // height
    private int w; // width

    /**
     * Constructeur
     * @param _h :  hauteur de la pièce
     * @param _w : largeur de la pièce
     */
    public Piece(int _h, int _w) {
        this.h = _h;
        this.w = _w;
    }

    /**
     * Getter de l'attribut hauteur
     * @return un int qui est la hauteur d'une pièce
     */
    public int getH() {
        return this.h;
    }

    /**
     * Getter de l'attribut largeur
     * @return un int qui est la largeur d'une pièce
     */
    public int getW() {
        return this.w;
    }

    /**
     * Setter de l'attribut hateur
     * @param _h : la nouvelle valeur de la hauteur de la pièce
     */
    public void setH(int _h) {
        this.h = _h;
    }
}
