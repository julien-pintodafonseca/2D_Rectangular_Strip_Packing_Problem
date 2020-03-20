package com.ensimag.Utils;

import com.ensimag.Files.FileCheck;
import com.ensimag.Models.CutPlate;
import com.ensimag.Models.Plate;

import java.util.Iterator;
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
                System.out.println("All is ok !");
            } else {
                System.out.println("Erreur : les chutes sont incorrectes !");
            }
        } else {
            System.out.println("Erreur : des pièces se superposent !");
        }
    }

    public boolean checkX() {
        Map.Entry<Integer, Plate> subList;
        Map.Entry<Integer, Plate> subList_next;
        int y;
        int y_next;
        Plate p;
        Plate p_next;
        for (int x : this.plate.getxPieces().keySet()) {
            Iterator<Map.Entry<Integer, Plate>> it = this.plate.getxPieces().get(x).entrySet().iterator();
            if (it.hasNext()) {
                subList = it.next();
                y = subList.getKey();
                p = subList.getValue();
                while (it.hasNext()) {
                    subList_next = it.next();
                    y_next = subList_next.getKey();
                    p_next = subList_next.getValue();

                    if (y + p.getH() > y_next) {
                        return false;
                    }

                    if (this.plate.nextX(x, y, p)) {
                        return false;
                    }

                    y = y_next;
                    p = p_next;
                }

                if (y + p.getH() > this.plate.getH()) {
                    return false;
                }

            }
        }
        return true;
    }

    public boolean checkY() {
        Map.Entry<Integer, Plate> subList;
        Map.Entry<Integer, Plate> subList_next;
        int x;
        int x_next;
        Plate p;
        Plate p_next;
        for (int y : this.plate.getyPieces().keySet()) {
            Iterator<Map.Entry<Integer, Plate>> it = this.plate.getyPieces().get(y).entrySet().iterator();
            if (it.hasNext()) {
                subList = it.next();
                x = subList.getKey();
                p = subList.getValue();
                while (it.hasNext()) {
                    subList_next = it.next();
                    x_next = subList_next.getKey();
                    p_next = subList_next.getValue();

                    if (x + p.getW() > x_next) {
                        return false;
                    }

                    x = x_next;
                    p = p_next;
                }

                if (x + p.getW() > this.plate.getH()) {
                    return false;
                }

            }
        }
        return true;
    }
}
