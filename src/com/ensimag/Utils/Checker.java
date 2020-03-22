package com.ensimag.Utils;

import com.ensimag.Files.FileCheck;
import com.ensimag.Models.CutChecker;
import com.ensimag.Models.Piece;

import java.util.Map;

/**
 * Class Checker (Algorithme tâche 4)
 * @author Groupe6
 */
public class Checker {
    /**
     * Le fichier de résultats à vérifier
     */
    private FileCheck fileCheck;
    /**
     * La plaque en cours de vérification
     */
    private CutChecker plate;

    /**
     * Constructeur de Checker
     * @param _fileCheck : fichier de résultats à vérifier
     */
    public Checker(FileCheck _fileCheck) {
        this.fileCheck = _fileCheck;
        this.plate = new CutChecker(0, 0);
    }

    /**
     * Permet l'éxécution de l'algorithme
     */
    public void start() {
        boolean checked = true;
        int total_lost = 0;
        // Vérification des plaques une à une
        while (this.fileCheck.hasNextPlate() && checked) {
            this.plate = this.fileCheck.nextPlate();
            total_lost += this.plate.getLost();
            if (!this.checkX() || !this.checkY()) {
                checked = false;
            }
        }
        if (checked) { //si les pièces sont bien positionnées
            if (total_lost == this.fileCheck.getLost()) { //on vérifie les pertes
                System.out.println("Checker --> "+fileCheck.getFileName()+" | All is ok!");
            } else {
                System.out.println("Checker --> "+fileCheck.getFileName()+" | Erreur: les chutes sont incorrectes!");
            }
        } else {
            System.out.println("Checker --> "+fileCheck.getFileName()+" | Erreur: des pièces se superposent!");
        }
    }

    /**
     * Permet de vérifier que, au niveau des Y, les pièces ne dépassent pas les unes sur les autres
     * et que leur voisin de droite ne commence pas sur leur surface
     * @return true si les pièces ne se superposent pas, false sinon
     */
    private boolean checkX() {;
        int y;
        int y_index;
        Piece p;
        int y_next;

        for (int x : this.plate.getxPieces().keySet()) { // Pour chaque X
            for (Map.Entry<Integer, Piece> subList : this.plate.getxPieces().get(x).entrySet()) { // On parcourt l'ensemble des pièces ayant ce même X
                y = subList.getKey();
                p = subList.getValue();
                y_index = this.plate.getyList().indexOf(y);

                // On vérifie qu'elle ne se superpose pas avec la pièce suivante ayant le même X
                // Et qu'elle ne dépasse pas de la plaque
                if (y_index < this.plate.getyList().size() - 1) { // Si on n'est pas au dernier Y possible
                    y_next = this.plate.nextY(x, y);
                    if (y_next < 0) {
                        if (y + p.getH() > this.plate.getH()) { // Si elle dépasse de la plaque au niveau de la hauteur
                            return false;
                        }
                    } else if (y + p.getH() > y_next) { // Si la pièce se superpose sur la pièce suivante
                        return false;
                    }
                    if (!this.plate.suivX(x, y, p)) {  // Si la pièce a une pièce voisine à droite qui se superpose sur elle
                        //    ___________
                        //    | Voisine |
                        // ___|____     |
                        //|   |    |    |
                        //|   |____|____|
                        //|________|
                        return false;
                    }
                } else {
                    if (y + p.getH() > this.plate.getH()) { // Si elle dépasse de la plaque au niveau de la hauteur
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * Permet de vérifier que, au niveau des X, les pièces ne dépassent pas les unes sur les autres
     * @return true si les pièces ne se superposent pas, false sinon
     */
    private boolean checkY() {
        int x;
        int x_index;
        Piece p;
        int x_next;
        for (int y : this.plate.getyPieces().keySet()) { // Pour chaque Y
            for (Map.Entry<Integer, Piece> subList : this.plate.getyPieces().get(y).entrySet()) { // On parcourt l'ensemble des pièces ayant ce même Y
                x = subList.getKey();
                p = subList.getValue();
                x_index = this.plate.getxList().indexOf(x);

                // On vérifie qu'elle ne se superpose pas avec la pièce suivante ayant le même Y
                // Et qu'elle ne dépasse pas de la plaque
                if (x_index < this.plate.getxList().size() - 1) { // Si on n'est pas au dernier X possible
                    x_next = this.plate.nextX(x, y);
                    if (x_next < 0) {
                        if (x + p.getW() > this.plate.getW()) { // Si elle dépasse de la plaque au niveau de la largeur
                            return false;
                        }
                    } else if (x + p.getW() > x_next) {  // Si la pièce se superpose sur la pièce suivante
                        return false;
                    }
                } else {
                    if (x + p.getW() > this.plate.getW()) { // Si elle dépasse de la plaque au niveau de la largeur
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
