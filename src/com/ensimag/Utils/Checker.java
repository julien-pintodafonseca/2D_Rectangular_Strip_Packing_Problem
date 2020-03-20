package com.ensimag.Utils;

import com.ensimag.Files.FileCheck;
import com.ensimag.Models.CutPlate;
import com.ensimag.Models.Plate;

import java.util.Map;

public class Checker {
    private FileCheck fileCheck;
    private CutPlate plate;

    public Checker(FileCheck _fileCheck) {
        this.fileCheck = _fileCheck;
        this.plate = new CutPlate(0, 0);
    }

    public void start() {
        boolean checked = true;
        int total_lost = 0;
        while (this.fileCheck.hasNextPlate() && checked) {
            this.plate = this.fileCheck.nextPlate();
            total_lost += this.plate.getLost();
            if (!this.checkX() || !this.checkY()) {
                checked = false;
            }
        }
        if (checked) { //si les pièces sont bien positionnées
            if (total_lost == this.fileCheck.getLost()) { //on vérifie les pertes
                System.out.println("All is ok!");
            } else {
                System.out.println("Erreur: les chutes sont incorrectes!");
            }
        } else {
            System.out.println("Erreur: des pièces se superposent!");
        }
    }

    private boolean checkX() {;
        int y;
        int y_index;
        Plate p;
        int y_next;

        for (int x : this.plate.getyPieces().keySet()) {
            for (Map.Entry<Integer, Plate> subList : this.plate.getxPieces().get(x).entrySet()) {
                y = subList.getKey();
                p = subList.getValue();
                y_index = this.plate.getyList().indexOf(y);

                if (y_index < this.plate.getyList().size() - 1) {
                    y_next = this.plate.nextY(x, y);
                    if (y_next < 0) {
                        if (y + p.getH() > this.plate.getH()) {
                            return false;
                        }
                    } else if (y + p.getH() > y_next) {
                        return false;
                    }
                    if (!this.plate.suivX(x, y, p)) {
                        return false;
                    }
                } else {
                    if (y + p.getH() > this.plate.getH()) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private boolean checkY() {
        int x;
        int x_index;
        Plate p;
        int x_next;
        for (int y : this.plate.getyPieces().keySet()) {
            for (Map.Entry<Integer, Plate> subList : this.plate.getyPieces().get(y).entrySet()) {
                x = subList.getKey();
                p = subList.getValue();
                x_index = this.plate.getxList().indexOf(x);

                if (x_index < this.plate.getxList().size() - 1) {
                    x_next = this.plate.nextX(x, y);
                    if (x_next < 0) {
                        if (x + p.getW() > this.plate.getW()) {
                            return false;
                        }
                    } else if (x + p.getW() > x_next) {
                        return false;
                    }
                } else {
                    if (x + p.getW() > this.plate.getW()) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
