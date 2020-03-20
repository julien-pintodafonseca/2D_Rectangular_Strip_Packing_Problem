package com.ensimag.Algorithms;

import com.ensimag.Files.FileIn;
import com.ensimag.Files.FileOut;
import com.ensimag.Models.Cut;
import com.ensimag.Models.PieceWithCoords;
import com.ensimag.Models.Plate;
import com.ensimag.Models.Piece;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class BL2in1 {
    private FileIn fileIn;
    private List<String> results;

    public BL2in1(FileIn _fileIn) {
        this.fileIn = _fileIn;
        this.results = new ArrayList<>();
    }

    public void start(boolean type) {
        Map<Plate, Integer> plates = fileIn.getPlatesMap();
        Map<Piece, Integer> pieces = fileIn.getPiecesMap();
        Cut resultBLForOnePlate;
        int lost = 0;
        boolean end = false;

        int plateNumber = 0;
        for (Plate pType : fileIn.getPlateTypes()) {
            int nbPlatesAtStart = plates.get(pType);
            for (int i=0; i<nbPlatesAtStart; i++) {
                Plate plate = new Plate(pType.getH(), pType.getW());

                if (!end) {
                    resultBLForOnePlate = BLForOnePlate(plate, pieces, type);
                    if (resultBLForOnePlate.getInfo().size() == 0) {
                        end = true;
                    }
                } else {
                    resultBLForOnePlate = new Cut();
                }

                results.addAll(resultBLForOnePlate.toString(plateNumber));
                lost += resultBLForOnePlate.getLost();
                plateNumber++;
            }
            end = false;
        }
        results.addAll((new Cut()).toStringEnd(pieces, lost));

        FileOut fileOut;
        if (type) {
            System.out.println("BLAdvanced Algorithm : entries.txt --> resultsBLAdvanced.txt | State: Success!");
            fileOut = new FileOut("resultsBLAdvanced.txt", results);
        } else {
            System.out.println("BL Algorithm : entries.txt --> resultsBL.txt | State: Success!");
            fileOut = new FileOut("resultsBL.txt", results);
        }
        fileOut.writeFile();
    }

    private Cut BLForOnePlate(Plate plate, Map<Piece, Integer> pieces, boolean type) {
        boolean newLine = true;
        boolean end = false; // vrai si l'on ne peut plus placer de pièces ou que l'on a placé toutes les pièces
        Cut piecesDecoupes = new Cut();

        int lineH = 0; // hauteur de la ligne courante
        int lineW = plate.getW(); // largeur restante de la ligne courante
        int LS = 0;

        while (plate.getHRest() > 0 && !end) {
            end = true;
            Iterator it = fileIn.getPieceTypes().iterator();
            Piece p;
            while (it.hasNext()) {
                p = (Piece) it.next();
                if (pieces.get(p) == 0) {
                    it.remove();
                } else {
                    if (newLine) {
                        if (p.getH() <= plate.getHRest() && p.getW() <= plate.getW()) {
                            newLine = false;
                            end = false;
                            //add piece
                            pieces.put(p, pieces.get(p) - 1);
                            piecesDecoupes.addInfo(new PieceWithCoords(p, 0, LS));
                            plate.setHRest(plate.getHRest() - p.getH());
                            lineH = p.getH();
                            lineW = plate.getW() - p.getW();
                        }
                    }
                    if (!newLine) {
                        while (p.getH() <= lineH && p.getW() <= lineW && pieces.get(p) > 0) {
                            //add piece
                            pieces.put(p, pieces.get(p) - 1);
                            piecesDecoupes.addInfo(new PieceWithCoords(p, plate.getW() - lineW, LS));
                            if (type) {
                                piecesDecoupes.fusion(
                                        stacking(new PieceWithCoords(lineH - p.getH(), p.getW(),
                                                plate.getW() - lineW, LS + p.getH()), pieces)
                                );//appel à la méthode stacking
                            } else {
                                piecesDecoupes.addLost((lineH - p.getH()) * p.getW());
                            }

                            lineW -= p.getW();
                        }
                    }
                }
            }

            if (!end) {
                piecesDecoupes.addLost(lineW*lineH);
                newLine = true;
                LS = plate.getH() - plate.getHRest();
            }
        }
        piecesDecoupes.addLost(plate.getHRest()*plate.getW());
        if (piecesDecoupes.getLost() == plate.getW()*plate.getH()) {
            piecesDecoupes.addLost(-piecesDecoupes.getLost());
        }
        return piecesDecoupes;
    }

    private Cut stacking(PieceWithCoords subPlate, Map<Piece, Integer> pieces) {
        Cut piecesDecoupes = new Cut();
        if (subPlate.getH() == 0) {
            return piecesDecoupes;
        } else {
            for (Piece p : fileIn.getPieceTypes()) {
                while (subPlate.getH() >= p.getH() && subPlate.getW() >= p.getW() && pieces.get(p) > 0) {
                    piecesDecoupes.addInfo(new PieceWithCoords(p.getH(), p.getW(), subPlate.getX(), subPlate.getY()));
                    pieces.put(p, pieces.get(p) - 1);
                    piecesDecoupes.addLost((subPlate.getW() - p.getW()) * p.getH());
                    subPlate.setH(subPlate.getH() - p.getH());
                    subPlate.setY(subPlate.getY() + p.getH());
                }
            }
            piecesDecoupes.addLost(subPlate.getW()*subPlate.getH());
            return piecesDecoupes;
        }
    }
}
