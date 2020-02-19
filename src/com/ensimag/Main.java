package com.ensimag;

import com.ensimag.Files.FileIn;
import com.ensimag.Models.Plate;
import com.ensimag.Models.Rectangle;

import java.util.Map;

public class Main {

    public static void main(String[] args) {
        FileIn myFileIn = new FileIn("entries.txt");
        myFileIn.loadEntries();

        //Map<Plate, Integer> plates = myFileIn.getPlates();
        Plate myPlate = new Plate(40, 55);
        Map<Rectangle, Integer> pieces = myFileIn.getPieces();

        myPlate.BL(pieces);

        System.out.println();
        for (Plate p : myFileIn.getPlates().keySet()) {
            System.out.println("Plaque H="+p.getH()+" / W="+p.getW()+" / Quantité="+myFileIn.getPlates().get(p));
        }
        for (Rectangle p : myFileIn.getPieces().keySet()) {
            System.out.println("Pièce H="+p.getH()+" / W="+p.getW()+" / Quantité="+myFileIn.getPieces().get(p));
        }
    }
}
