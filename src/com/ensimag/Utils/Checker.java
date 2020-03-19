package com.ensimag.Utils;

import com.ensimag.Files.FileCheck;
import com.ensimag.Models.CutPlate;
import com.ensimag.Models.Plate;

import java.util.HashMap;
import java.util.Map;

public class Checker {
    private FileCheck fileCheck;
    private CutPlate plate;
    private Map<Integer, Object> xPieces;
    private Map<Integer, Object> yPieces;

    public Checker(FileCheck _fileCheck) {
        this.fileCheck = _fileCheck;
        this.xPieces = new HashMap<>();
        this.yPieces = new HashMap<>();
        this.plate = new CutPlate();
    }

    public void start() {
        boolean checked = true;
        while (this.fileCheck.hasNextPlate() && checked) {
            this.plate = this.fileCheck.nextPlate();
            if (!this.checkX() || !this.checkY()) {
                checked = false;
            }
        }
        if (checked) {
            System.out.println("All is ok !");
        } else {
            System.out.println("NOK !");
        }
    }

    public boolean checkX() {
        Map.Entry<Integer, Plate> subList;
        Map.Entry<Integer, Plate> subList_next;
        int y;
        int y_next;
        Plate p;
        Plate p_next;
//        for (int x : plate.getxPieces().keySet()) {
//            Iterator<Map.Entry<Integer, Plate>> it = (Map.Entry<Integer, Plate>) plate.getxPieces().entrySet().iterator();
//            if (it.hasNext()) {
//                subList = it.next();
//                while (it.hasNext()) {
//                    y = subList.getKey();
//                    p = subList.getValue();
//
//                    subList_next = it.next();
//                    y_next = subList_next.getKey();
//                    p = subList_next.getValue();
//
//                    if (y + p.getH() == y_next) {
//                        if (p_next.getW() < p.getW()) {
//                            //trouver si il y a un élément sur la ligne y en x + p_next.getW() <= X  < x + p.getW()
//                        }
//                        System.out.print("ok");
//                    }
//                }
//
//                subList = subList_next;
//            }
//        }
        return true;
    }

    public boolean checkY() {
        return true;
    }
}
