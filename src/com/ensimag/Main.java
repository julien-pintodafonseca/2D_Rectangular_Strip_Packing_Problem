package com.ensimag;

import com.ensimag.Algorithms.BL2in1;
import com.ensimag.Algorithms.OptimizedSolver;
import com.ensimag.Files.FileCheck;
import com.ensimag.Files.FileIn;
import com.ensimag.Models.Plate;
import com.ensimag.Models.Piece;
import com.ensimag.Utils.Checker;

/**
 * Main
 * @author Groupe6
 */
public class Main {

    public static void main(String[] args) {
        FileIn myFileIn = new FileIn("entries.txt");
        printInventory(myFileIn);

        BL2in1 myBL = new BL2in1(myFileIn);
        BL2in1 myBLAdvanced = new BL2in1(myFileIn);
        OptimizedSolver myOptimizedSolver = new OptimizedSolver(myFileIn);

        myBL.start(false); //Algorithme BL tâche 1
        myBLAdvanced.start(true); //Algorithme BL avancé tâche 2
        myOptimizedSolver.start(); //Algorithme optimisé tâche 3

        FileCheck myFileCheck1 = new FileCheck("resultsBL.txt", myFileIn.getPlatesList());
        FileCheck myFileCheck2 = new FileCheck("resultsBLAdvanced.txt", myFileIn.getPlatesList());
        FileCheck myFileCheck3 = new FileCheck("resultsOptimizedSolver.txt", myFileIn.getPlatesList());
        Checker myChecker1 = new Checker(myFileCheck1);
        Checker myChecker2 = new Checker(myFileCheck2);
        Checker myChecker3 = new Checker(myFileCheck3);
        myChecker1.start();
        myChecker2.start();
        myChecker3.start();
    }

    /**
     * Permet d'écrire l'inventaire dans la console (la liste des plaques et pièces, leur dimension et leur quantité)
     * @param myFileIn : fichier d'entrée
     */
    private static void printInventory(FileIn myFileIn) {
        System.out.println("Inventory:");
        System.out.println("---------");
        for (Plate p : myFileIn.getPlatesMap().keySet()) {
            System.out.println("PlaqueType: " + p.getH() + "x" + p.getW() + " | Quantité: " + myFileIn.getPlatesMap().get(p));
        }
        for (Piece p : myFileIn.getPiecesMap().keySet()) {
            System.out.println("PièceType: " + p.getH() + "x" + p.getW() + " | Quantité: " + myFileIn.getPiecesMap().get(p));
        }
        System.out.println();
    }
}
