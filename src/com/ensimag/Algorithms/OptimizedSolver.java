package com.ensimag.Algorithms;

import com.ensimag.Files.FileIn;
import com.ensimag.Files.FileOut;
import com.ensimag.Models.*;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by solokwal on 2/20/20.
 */
public class OptimizedSolver {
    private FileIn fileIn;
    private Map<Rectangle, Integer> pieces;
    
    public OptimizedSolver(FileIn _fileIn) {
        this.fileIn = _fileIn;
    }

    public void start() {
        Plate plate = fileIn.getPlates().keySet().iterator().next();
        PlateWithCoords plate2 = new PlateWithCoords(plate, 0, 0);
        this.pieces = fileIn.getPieces();

        Cut result = this.cutCutCut(plate2);
        List<PlateWithCoords> info = result.getInfo();
        info.sort(new SortByCoords());
        result.setInfo(info);

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
        boolean stop = false;
        if (plate.getH() == 0 || plate.getW() == 0) {
            stop = true;
        }
        while (!enoughPlace && !toward && it.hasNext() && !stop) {
            p = it.next();
            enoughPlace = false;
            toward = false;
            if (p.getH() <= plate.getH() && p.getW() <= plate.getW() && pieces.get(p) > 0) {
                enoughPlace = true;
            }
            if (p.getH() <= plate.getW() && p.getW() <= plate.getH() && pieces.get(p) > 0 && p.getH() != p.getW()){
                toward = true;
            }
        }

        if (!enoughPlace && !toward) {
            return new Cut(plate.getH()*plate.getW(), null);
        } else {
            this.pieces.put(p, this.pieces.get(p) - 1);
            Cut subPlateEnough;
            Cut subPlateToward;
            if (enoughPlace && toward) {
                subPlateEnough = new Cut(bestSoluce(plate, p.getH(), p.getW()));
                subPlateToward = new Cut(bestSoluce(plate, p.getW(), p.getH()));
                if (subPlateToward.getLost() > subPlateEnough.getLost()) {
                    return subPlateEnough;
                } else {
                    return subPlateToward;
                }
            } else if (enoughPlace) {
                subPlateEnough = new Cut(bestSoluce(plate, p.getH(), p.getW()));
                return subPlateEnough;
            } else {
                subPlateToward = new Cut(bestSoluce(plate, p.getW(), p.getH()));
                return subPlateToward;
            }
        }
    }

    private Cut bestSoluce(PlateWithCoords plate, int pH, int pW) {
        Cut subPlate1a = new Cut(this.cutCutCut(new PlateWithCoords(plate.getH()-pH, pW, plate.getX(),plate.getY() + pH)));
        Cut subPlate1b = new Cut(this.cutCutCut(new PlateWithCoords(plate.getH(), plate.getW()-pW, plate.getX() + pW, plate.getY())));
        Cut subPlate2a = new Cut(this.cutCutCut(new PlateWithCoords(plate.getH()-pH, plate.getW(), plate.getX(), plate.getY() + pH)));
        Cut subPlate2b = new Cut(this.cutCutCut(new PlateWithCoords(pH, plate.getW()-pW, plate.getX() + pW, plate.getY())));
        if (subPlate1a.getLost()+subPlate1b.getLost() < subPlate2a.getLost()+subPlate2b.getLost()) {
            subPlate1a.fusion(subPlate1b);
            subPlate1a.addInfo(new PlateWithCoords(pH, pW, plate.getX(), plate.getY()));
            return subPlate1a;
        } else {
            subPlate2a.fusion(subPlate2b);
            subPlate2a.addInfo(new PlateWithCoords(pH, pW, plate.getX(), plate.getY()));
            return subPlate2a;
        }
    }
}
