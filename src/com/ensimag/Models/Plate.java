package com.ensimag.Models;

/**
 * Class Plate
 * @author Groupe6
 */
public class Plate extends Piece {
    /**
     * Hauteur restante de la plaque
     */
    private int hRest;

    /**
     * Constructeur de Plate
     * @param _h : hauteur de la plaque
     * @param _w : largeur de la plaque
     */
    public Plate(int _h, int _w) {
        super(_h, _w);
        this.hRest = _h;
    }

    /**
     * Getter de la hauteur restante de la plaque
     * @return l'attribut hRest
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
