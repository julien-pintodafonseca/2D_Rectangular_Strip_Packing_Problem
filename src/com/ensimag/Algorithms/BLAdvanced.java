package com.ensimag.Algorithms;

import com.ensimag.Files.FileIn;
import com.ensimag.Files.FileOut;
import com.ensimag.Models.*;

import java.util.*;

/**
 * Created by pintodaj on 3/5/20.
 */
public class BLAdvanced {
    private FileIn fileIn;

    public BLAdvanced(FileIn _fileIn) {
        this.fileIn = _fileIn;
    }

    public void start() {
        Map<Plate, Integer> plates = fileIn.getPlates();
        Map<Rectangle, Integer> pieces = fileIn.getPieces();
        Cut resultBLForOnePlate;
        int lost = 0;
        List<String> results = new ArrayList<>();

        int plateNumber = 0;
        for (Plate pType : plates.keySet()) {
            int nbPlatesAtStart = plates.get(pType);
            for (int i=0; i<nbPlatesAtStart; i++) {
                Plate plate = new Plate(pType.getH(), pType.getW());

                resultBLForOnePlate = BLForOnePlate(plate, pieces);
                resultBLForOnePlate.getInfo().sort(new SortByCoords());
                if (i == nbPlatesAtStart - 1) {
                    results.addAll(resultBLForOnePlate.toString(pieces, plateNumber, lost));
                } else {
                    results.addAll(resultBLForOnePlate.toStringLimited(pieces, plateNumber));
                    lost += resultBLForOnePlate.getLost();
                }
                plateNumber++;
            }
        }

        System.out.println("BLAdvanced Algorithm: entries.txt --> resultsBLAdvanced.txt | State: Success!");
        FileOut fileOut = new FileOut("resultsBLAdvanced.txt", results);
        fileOut.writeFile();
    }

    private Cut BLForOnePlate(Plate plate, Map<Rectangle, Integer> pieces) {
        int lineH = 0;
        int lineW = 0;
        boolean plateIsUsed = false; // vrai si la plaque a été utilisé (même partiellement), faux sinon
        boolean newLine = true;
        boolean end = false; // vrai si l'on ne peut plus placer de pièces ou que l'on a placé toutes les pièces
        Cut piecesDecoupes = new Cut();

        while (plate.getHRest() > 0 && !end) {
            end = true;
            Iterator it = pieces.keySet().iterator();
            Rectangle p;
            while (it.hasNext()) {
                p = (Rectangle) it.next();
                if (p.getH() <= plate.getHRest() && p.getW() <= plate.getLineWRest(lineW) && pieces.get(p) > 0) {
                    end = false;
                    if (newLine) {
                        if (!plateIsUsed) {
                            plateIsUsed = true;
                            //System.out.println();
                            //System.out.println("+++ NOUVELLE PLAQUE ["+plate.getH()+"/"+plate.getW()+"]+++");
                        }
                        newLine = false;
                        plate.setHRest(plate.getHRest() - p.getH());
                        piecesDecoupes.addInfo(new PlateWithCoords(p.getH(), p.getW(), lineW, lineH));
                        //piecesDecoupes.addLost(getSize(p));
                        lineW += p.getW();
                        pieces.put(p, pieces.get(p) - 1);
                        //System.out.println("Nouvelle ligne, une pièce a été placée ! ["+p.getH()+"/"+p.getW()+"]");
                    }
                    while (plate.getLineWRest(lineW) >= p.getW() && pieces.get(p) > 0) {
                        // On découpe des pièces
                        //System.out.println("La pièce ["+p.getH()+"/"+p.getW()+"] a été découpée " +
                        piecesDecoupes.addInfo(new PlateWithCoords(p.getH(), p.getW(), lineW, lineH));
                        //piecesDecoupes.addLost(getSize(p));
                        pieces.put(p, pieces.get(p) - 1);
                        piecesDecoupes.fusion(stacking(new PlateWithCoords(lineH-p.getH(), p.getW(), lineW, lineH), pieces, p, it));
                        lineW += p.getW();
                    }
                }
            }
            if (!end) {
                piecesDecoupes.addLost(lineW*plate.getHRest());
                newLine = true;
                lineW = 0;
                lineH = plate.getH() - plate.getHRest();
            }
        }

        return piecesDecoupes;
    }

    private Cut stacking(PlateWithCoords subPlate, Map<Rectangle, Integer> pieces, Rectangle current, Iterator it) {
        Cut piecesDecoupes = new Cut();
        if (subPlate.getH() == 0) {
            return piecesDecoupes;
        } else if (subPlate.getH() >= current.getH() && subPlate.getW() >= current.getW() && pieces.get(current) > 0) {
            piecesDecoupes.addInfo(new PlateWithCoords(current.getH(), current.getW(), subPlate.getX(), subPlate.getY()));
            pieces.put(current, pieces.get(current) - 1);
            piecesDecoupes.addLost((subPlate.getW()-current.getW())*current.getH());
            piecesDecoupes.fusion(stacking(new PlateWithCoords( subPlate.getH()-current.getH(),
                    current.getW(), subPlate.getX(), subPlate.getY()+current.getH()),
                    pieces, current, it));
            return piecesDecoupes;
        } else if (it.hasNext()) {
            current = (Rectangle) it.next();
            return stacking(subPlate, pieces, current, it);
        } else {
            piecesDecoupes.addLost(subPlate.getW()*subPlate.getH());
            return piecesDecoupes;
        }
    }

    private int getSize(Rectangle o) {
        return o.getH()*o.getW();
    }
}
