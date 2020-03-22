package com.ensimag.Models;

/**
 * Created by houdee on 2/17/20.
 * Objet plaque qui hérite de la classe pièce
 */
public class Plate extends Piece {
    private int hRest;

    /**
     * Constructeur
     * @param _h : hateur de la plaque
     * @param _w : largeur de la plaque
     */
    public Plate(int _h, int _w) {
        super(_h, _w);
        this.hRest = _h;
    }

    /**
     * Getter de la hauteur restante
     * @return un int qui est l'attribut hRest
     */
    public int getHRest() {
        return this.hRest;
    }

    /**
     * Setter de la hauteur restante
     * @param _hRest : nouvelle valeur de l'attribut hRest
     */
    public void setHRest(int _hRest) {
        this.hRest = _hRest;
    }
}
