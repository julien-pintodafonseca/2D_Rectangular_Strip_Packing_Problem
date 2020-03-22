package com.ensimag.Models;

import com.ensimag.Sorts.SortByCoords;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Class Cut (plan de découpe pour l'algorithme BL2in1 : BL et BLAdvanced)
 * @author Groupe6
 */
public class Cut {
    private int lost;
    private List<PieceWithCoords> info;

    /**
     * Constructeur de Cut
     */
    public Cut() {
        this.lost = 0;
        this.info = new ArrayList<>();
    }

    /**
     * Constructeur de Cut
     * @param clone : objet Cut à cloner
     */
    public Cut(Cut clone) {
        this.lost = clone.getLost();
        this.info = new ArrayList<>();
        this.info.addAll(clone.getInfo());
    }

    /**
     * Constructeur de Cut
     * @param _lost : valeur des pertes
     * @param _info : liste de PieceWithCoords (pièces découpées avec leurs coordonnées sur la plaque)
     */
    public Cut(int _lost, List<PieceWithCoords> _info) {
        this.lost = _lost;
        this.info = new ArrayList<>();
        if (_info != null) {
            this.info.addAll(_info);
        }
    }

    /**
     * Getter de la valeur des pertes
     * @return l'attribut lost
     */
    public int getLost() {
        return this.lost;
    }

    /**
     * Getter de la liste de PieceWithCoords (pièces découpées avec leurs coordonnées sur la plaque)
     * @return l'attribut info
     */
    public List<PieceWithCoords> getInfo() {
        return this.info;
    }

    /**
     *  Permet d'ajouter des pertes à la valeur des pertes actuelles
     * @param yes : valeur à ajouter aux pertes actuelles
     */
    public void addLost(int yes) { this.lost += yes; }

    /**
     * Permet d'ajouter une nouvelle PlateWithCoord à la liste de PieceWithCoords (pièces découpées avec leurs coordonnées sur la plaque)
     * @param newInfo : nouvelle PlateWithCoord à ajouter
     */
    public void addInfo(PieceWithCoords newInfo) {
        this.info.add(newInfo);
    }

    /**
     * Permet d'écrire, sous forme d'une liste de String, les pièces qui n'ont pas été utilisées ainsi que les pertes
     * @param pieces : pièces non utilisées
     * @param totalLost : valeur des pertes de toutes les plaques
     * @return une liste de string énoncant les pièces non utilisées ainsi que la valeur des pertes totales (seconde partie du fichier de résultat)
     */
    public List<String> toStringEnd(Map<Piece, Integer> pieces, int totalLost){
        List<String> endResult = new ArrayList<>();
        endResult.add("Pièces restantes à couper :");
        String information = "";
        for (Piece p : pieces.keySet()) {
            if (pieces.get(p) > 0) {
                for (int i = 0; i < pieces.get(p); i++) {
                    information = information + p.getH() + " " + p.getW() + ", ";
                }
            }
        }
        if (information.equals("")) {
            information = "Aucune.";
        }
        endResult.add(information);
        endResult.add("Chutes :");
        endResult.add(String.valueOf(totalLost));
        return endResult;
    }

    /**
     * Permet d'écrire, sous forme d'une liste de String, les découpes effectuées sur la plaque
     * @param plateNumber : le numéro de la plaque
     * @return une liste de string énoncant le positionnement de toutes les pièces découpées dans la plaque
     */
    public List<String> toString(int plateNumber){
        List<String> endResult = new ArrayList<>();
        if (this.getInfo().size() == 0) {
            endResult.add("Plaque " + plateNumber + " :");
            endResult.add("Pas utilisée.");
        } else {
            String information = "Plaque " + plateNumber + " :";
            int yCurrent = -1;
            this.sortInfo();
            for (PieceWithCoords plate : this.info) {
                if (plate.getY() > yCurrent) {
                    endResult.add(information);
                    endResult.add("LS=" + plate.getY());
                    yCurrent = plate.getY();
                    information = "";
                }
                information = information + plate.getX() + " " + plate.getH() + " " + plate.getW() + ", ";
            }
            endResult.add(information);
        }
        return endResult;
    }

    /**
     * Permet de fusionner deux objet Cut
     * @param toFusion : objet Cut à fusionner
     */
    public void fusion(Cut toFusion) {
        if (toFusion.getInfo().size() > 0) {
            this.info.addAll(toFusion.getInfo());
        }
        this.lost = this.lost + toFusion.getLost();
    }

    /**
     * Permet de trier la liste de PieceWithCoords (pièces découpées avec leurs coordonnées sur la plaque),
     * en fonction des connées Y puis X, par ordre croissant
     */
    private void sortInfo() {
        this.info.sort(new SortByCoords());
    }
}
