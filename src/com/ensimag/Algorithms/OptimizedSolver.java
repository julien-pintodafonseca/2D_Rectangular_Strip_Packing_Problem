package com.ensimag.Algorithms;

import com.ensimag.Files.FileIn;
import com.ensimag.Files.FileOut;
import com.ensimag.Models.CutOptimized;
import com.ensimag.Models.PieceWithCoords;
import com.ensimag.Models.Plate;
import com.ensimag.Models.Piece;

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
        Plate plate = fileIn.getPlateTypes().get(0);
        PieceWithCoords plateWithCoords = new PieceWithCoords(plate, 0, 0);
        Map<Piece, Integer> pieces = fileIn.getPiecesMap();

        CutOptimized result = this.cutCutCut(plateWithCoords, pieces);
        List<String> endResults = result.toString(0);
        endResults.addAll(result.toStringEnd(result.getPieces(), result.getLost()));

        System.out.println("OptimizedSolver Algorithm: entries.txt --> resultsOptimizedSolver.txt | State: Success!");
        FileOut fileOut = new FileOut("resultsOptimizedSolver.txt", endResults);
        fileOut.writeFile();
    }

    private CutOptimized cutCutCut(PieceWithCoords plate, Map<Piece, Integer> pieces) {
        Iterator<Piece> it = fileIn.getPieceTypes().iterator();
        Piece p = new Piece(0,0);
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
            return new CutOptimized(plate.getH()*plate.getW(), new ArrayList<>(), pieces);
        } else {
            pieces.put(p, pieces.get(p) - 1);
            CutOptimized subPlateEnough;
            CutOptimized subPlateToward;
            if (enoughPlace && toward) {
                subPlateEnough = new CutOptimized(bestSoluce(plate, p.getH(), p.getW(), pieces));
                subPlateToward = new CutOptimized(bestSoluce(plate, p.getW(), p.getH(), pieces));
                if (subPlateToward.getLost() > subPlateEnough.getLost()) {
                    return subPlateEnough;
                } else {
                    return subPlateToward;
                }
            } else if (enoughPlace) {
                subPlateEnough = new CutOptimized(bestSoluce(plate, p.getH(), p.getW(), pieces));
                return subPlateEnough;
            } else {
                subPlateToward = new CutOptimized(bestSoluce(plate, p.getW(), p.getH(), pieces));
                return subPlateToward;
            }
        }
    }

    private CutOptimized bestSoluce(PieceWithCoords plate, int pH, int pW, Map<Piece, Integer> pieces) {
        Map<Piece, Integer> piecesCopy = new HashMap<>(pieces);
        if (plate.getH()-pH == 0) {
            CutOptimized subPlate1b = new CutOptimized(this.cutCutCut(new PieceWithCoords(plate.getH(), plate.getW()-pW, plate.getX() + pW, plate.getY()), pieces));
            subPlate1b.addInfo(new PieceWithCoords(pH, pW, plate.getX(), plate.getY()));
            return subPlate1b;
        }
        if (plate.getW()-pW == 0) {
            CutOptimized subPlate1a = new CutOptimized(this.cutCutCut(new PieceWithCoords(plate.getH()-pH, pW, plate.getX(),plate.getY() + pH), pieces));
            subPlate1a.addInfo(new PieceWithCoords(pH, pW, plate.getX(), plate.getY()));
            return subPlate1a;
        }
        CutOptimized subPlate1a;
        CutOptimized subPlate1b;
        if ((plate.getH()-pH)*pW >= plate.getH()*(plate.getW()-pW)) {
            subPlate1a = new CutOptimized(this.cutCutCut(new PieceWithCoords(plate.getH()-pH, pW, plate.getX(),plate.getY() + pH), pieces));
            subPlate1b = new CutOptimized(this.cutCutCut(new PieceWithCoords(plate.getH(), plate.getW()-pW, plate.getX() + pW, plate.getY()), subPlate1a.getPieces()));
        } else {
            subPlate1a = new CutOptimized(this.cutCutCut(new PieceWithCoords(plate.getH(), plate.getW()-pW, plate.getX() + pW, plate.getY()), pieces));
            subPlate1b = new CutOptimized(this.cutCutCut(new PieceWithCoords(plate.getH()-pH, pW, plate.getX(),plate.getY() + pH), subPlate1a.getPieces()));
        }
        CutOptimized subPlate2a;
        CutOptimized subPlate2b;
        if ((plate.getH()-pH)*plate.getW() >= pH*(plate.getW()-pW)) {
            subPlate2a = new CutOptimized(this.cutCutCut(new PieceWithCoords(plate.getH()-pH, plate.getW(), plate.getX(), plate.getY() + pH), piecesCopy));
            subPlate2b = new CutOptimized(this.cutCutCut(new PieceWithCoords(pH, plate.getW()-pW, plate.getX() + pW, plate.getY()), subPlate2a.getPieces()));
        } else {
            subPlate2a = new CutOptimized(this.cutCutCut(new PieceWithCoords(pH, plate.getW()-pW, plate.getX() + pW, plate.getY()), piecesCopy));
            subPlate2b = new CutOptimized(this.cutCutCut(new PieceWithCoords(plate.getH()-pH, plate.getW(), plate.getX(), plate.getY() + pH), subPlate2a.getPieces()));
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
