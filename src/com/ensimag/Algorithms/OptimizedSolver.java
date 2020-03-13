package com.ensimag.Algorithms;

import com.ensimag.Files.FileIn;
import com.ensimag.Files.FileOut;
import com.ensimag.Models.*;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by solokwal on 2/20/20.
 */
public class OptimizedSolver {
    private FileIn fileIn;
    private Map<Rectangle, Integer> pieces;
    
    public  OptimizedSolver(FileIn _fileIn) {
        this.fileIn = _fileIn;
    }

    public void start() {
        Plate plate = fileIn.getPlates().keySet().iterator().next();
        PlateWithCoords plate2 = new PlateWithCoords(plate, 0, 0);
        this.pieces = fileIn.getPieces();
        Cut result = this.cutCutCut(plate2);

        result.getInfo().sort(new SortByCoords());
        List<String> endResults = result.toString(this.pieces, 0, 0);

        System.out.println("OptimizedSolver Algorithm: entries.txt --> resultsOptimizedSolver.txt | State: Success!");
        FileOut fileOut = new FileOut("resultsOptimizedSolver.txt", endResults);
        fileOut.writeFile();
    }

    private Cut cutCutCut(PlateWithCoords plate) {
        Iterator<Rectangle> it = this.pieces.keySet().iterator();
        Rectangle p = new Rectangle(0,0);
        boolean toward = false;
        boolean enoughPlace = false;

        while (!enoughPlace && it.hasNext()) {
            p = it.next();
            if (p.getH() <=plate.getH() && p.getW() <= plate.getW() && pieces.get(p) > 0) {
                enoughPlace=true;
            }
            if (p.getH() <= plate.getW() && p.getW() <= plate.getH() && pieces.get(p) > 0){
                toward = true;
            }
            if (p.getH() == p.getW()) {
                toward = false;
            }
        }

        if (!enoughPlace && !toward){
            return new Cut(plate.getH()*plate.getW(), null);
        } else{
            Cut subPlateToward = new Cut();
            Cut subPlateEnough = new Cut();
            this.pieces.put(p, this.pieces.get(p) - 1);
            if (toward) {
                subPlateToward = new Cut(bestSoluce(plate, p.getW(), p.getH()));
            }
            if (enoughPlace) {
                subPlateEnough = new Cut(bestSoluce(plate, p.getH(), p.getW()));
            }
            if (enoughPlace && toward) {
                if (subPlateToward.getLost() > subPlateEnough.getLost()) {
                    return subPlateEnough;
                }
                else {
                    return subPlateToward;
                }
            }
            else if (enoughPlace) {
                return subPlateEnough;
            }
            else {
                return subPlateToward;
            }

        }
    }

    private Cut bestSoluce(PlateWithCoords plate, int pH, int pW) {
        Cut subPlate1 = new Cut(this.cutCutCut(new PlateWithCoords(plate.getH()-pH, pW, plate.getX(), plate.getY() + pH)));
        Cut subPlate2 = new Cut(this.cutCutCut(new PlateWithCoords(plate.getH(), plate.getW()-pW, plate.getX() + pW, plate.getY())));
        Cut subPlate3 = new Cut(this.cutCutCut(new PlateWithCoords(plate.getH()-pH, plate.getW(), plate.getX(), plate.getY() + pH)));
        Cut subPlate4 = new Cut(this.cutCutCut(new PlateWithCoords(pH, plate.getW()-pW, plate.getX() + pW, plate.getY())));
        if (subPlate1.getLost()+subPlate2.getLost() < subPlate3.getLost()+subPlate4.getLost()) {
            subPlate1.fusion(subPlate2);
            subPlate1.addInfo(new PlateWithCoords(pH, pW, plate.getX(), plate.getY()));
            return subPlate1;
        }
        else {
            subPlate3.fusion(subPlate4);
            subPlate3.addInfo(new PlateWithCoords(pH, pW, plate.getX(), plate.getY()));
            return subPlate3;
        }
    }
}
