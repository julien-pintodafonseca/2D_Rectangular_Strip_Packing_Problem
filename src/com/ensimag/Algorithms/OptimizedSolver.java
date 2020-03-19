package com.ensimag.Algorithms;

import com.ensimag.Files.FileIn;
import com.ensimag.Files.FileOut;
import com.ensimag.Models.*;

import java.util.*;

/**
 * Created by solokwal on 2/20/20.
 */
public class OptimizedSolver {
    private FileIn fileIn;
    
    public OptimizedSolver(FileIn _fileIn) {
        this.fileIn = _fileIn;
    }

    public void start() {
        Plate plate = fileIn.getPlates().keySet().iterator().next();
        PieceWithCoords plateWithCoords = new PieceWithCoords(plate, 0, 0);
        Map<Rectangle, Integer> pieces = fileIn.getPieces();

        Cut2 result = this.cutCutCut(plateWithCoords, pieces);
        List<PieceWithCoords> info = result.getInfo();
        info.sort(new SortByCoords());
        result.setInfo(info);

        List<String> endResults = result.toString(0);
        endResults.addAll(result.toStringEnd(result.getPieces(), result.getLost()));

        System.out.println("OptimizedSolver Algorithm: entries.txt --> resultsOptimizedSolver.txt | State: Success!");
        FileOut fileOut = new FileOut("resultsOptimizedSolver.txt", endResults);
        fileOut.writeFile();
    }

    private Cut2 cutCutCut(PieceWithCoords plate, Map<Rectangle, Integer> pieces) {
        Iterator<Rectangle> it = pieces.keySet().iterator();
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
            if (pieces.get(p) > 0 && p.getH() <= plate.getH() && p.getW() <= plate.getW()) {
                enoughPlace = true;
            }
            if (pieces.get(p) > 0 && p.getH() <= plate.getW() && p.getW() <= plate.getH() && p.getH() != p.getW()){
                toward = true;
            }
        }

        if (!enoughPlace && !toward) {
            return new Cut2(plate.getH()*plate.getW(), new ArrayList<>(), pieces);
        } else {
            pieces.put(p, pieces.get(p) - 1);
            Cut2 subPlateEnough;
            Cut2 subPlateToward;
            if (enoughPlace && toward) {
                subPlateEnough = new Cut2(bestSoluce(plate, p.getH(), p.getW(), pieces));
                subPlateToward = new Cut2(bestSoluce(plate, p.getW(), p.getH(), pieces));
                if (subPlateToward.getLost() > subPlateEnough.getLost()) {
                    return subPlateEnough;
                } else {
                    return subPlateToward;
                }
            } else if (enoughPlace) {
                subPlateEnough = new Cut2(bestSoluce(plate, p.getH(), p.getW(), pieces));
                return subPlateEnough;
            } else {
                subPlateToward = new Cut2(bestSoluce(plate, p.getW(), p.getH(), pieces));
                return subPlateToward;
            }
        }
    }

    private Cut2 bestSoluce(PieceWithCoords plate, int pH, int pW, Map<Rectangle, Integer> pieces) {
        Map<Rectangle, Integer> piecesCopy = new HashMap<>(pieces);
        if (plate.getH()-pH == 0) {
            Cut2 subPlate1b = new Cut2(this.cutCutCut(new PieceWithCoords(plate.getH(), plate.getW()-pW, plate.getX() + pW, plate.getY()), pieces));
            subPlate1b.addInfo(new PieceWithCoords(pH, pW, plate.getX(), plate.getY()));
            return subPlate1b;
        }
        if (plate.getW()-pW == 0) {
            Cut2 subPlate1a = new Cut2(this.cutCutCut(new PieceWithCoords(plate.getH()-pH, pW, plate.getX(),plate.getY() + pH), pieces));
            subPlate1a.addInfo(new PieceWithCoords(pH, pW, plate.getX(), plate.getY()));
            return subPlate1a;
        }
        Cut2 subPlate1a;
        Cut2 subPlate1b;
        if ((plate.getH()-pH)*pW >= plate.getH()*(plate.getW()-pW)) {
            subPlate1a = new Cut2(this.cutCutCut(new PieceWithCoords(plate.getH()-pH, pW, plate.getX(),plate.getY() + pH), pieces));
            subPlate1b = new Cut2(this.cutCutCut(new PieceWithCoords(plate.getH(), plate.getW()-pW, plate.getX() + pW, plate.getY()), subPlate1a.getPieces()));
        } else {
            subPlate1a = new Cut2(this.cutCutCut(new PieceWithCoords(plate.getH(), plate.getW()-pW, plate.getX() + pW, plate.getY()), pieces));
            subPlate1b = new Cut2(this.cutCutCut(new PieceWithCoords(plate.getH()-pH, pW, plate.getX(),plate.getY() + pH), subPlate1a.getPieces()));
        }
        Cut2 subPlate2a;
        Cut2 subPlate2b;
        if ((plate.getH()-pH)*plate.getW() >= pH*(plate.getW()-pW)) {
            subPlate2a = new Cut2(this.cutCutCut(new PieceWithCoords(plate.getH()-pH, plate.getW(), plate.getX(), plate.getY() + pH), piecesCopy));
            subPlate2b = new Cut2(this.cutCutCut(new PieceWithCoords(pH, plate.getW()-pW, plate.getX() + pW, plate.getY()), subPlate2a.getPieces()));
        } else {
            subPlate2a = new Cut2(this.cutCutCut(new PieceWithCoords(pH, plate.getW()-pW, plate.getX() + pW, plate.getY()), piecesCopy));
            subPlate2b = new Cut2(this.cutCutCut(new PieceWithCoords(plate.getH()-pH, plate.getW(), plate.getX(), plate.getY() + pH), subPlate2a.getPieces()));
        }


        if (subPlate1a.getLost()+subPlate1b.getLost() <= subPlate2a.getLost()+subPlate2b.getLost()) {
            subPlate1b.fusion(subPlate1a.convertToCut());
            subPlate1b.addInfo(new PieceWithCoords(pH, pW, plate.getX(), plate.getY()));
            return subPlate1b;
        } else {
            subPlate2b.fusion(subPlate2a.convertToCut());
            subPlate2b.addInfo(new PieceWithCoords(pH, pW, plate.getX(), plate.getY()));
            return subPlate2b;
        }
    }
}
