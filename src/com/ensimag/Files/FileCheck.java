package com.ensimag.Files;

import com.ensimag.Models.CutPlate;
import com.ensimag.Models.Plate;
import com.ensimag.Models.PlateWithCoords;
import com.ensimag.Models.Rectangle;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Map;
import java.util.StringTokenizer;

public class FileCheck {
    private String fileName;
    private ArrayList<CutPlate> cuttedPlates;
    private ArrayList<Rectangle> leftPieces;
    private int lost;

    public FileCheck(String _fileName) {
        this.fileName = _fileName;
        this.cuttedPlates = new ArrayList<>();
        this.leftPieces = new ArrayList<>();
        this.lost = -1;
    }

    public boolean hasNextPlate() {
        return true;
    }

    public CutPlate nextPlate() {
        return null;
    }

    public int getLost() {
        return this.lost;
    }

    public void loadEntries() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String st, st2;
            StringTokenizer tk;
            while ((st = br.readLine()) != null) {
                if (st.contains("Plaque")) {
                    CutPlate plate = new CutPlate();
                    int y = -1;

                    // on traite la ligne suivante:
                    st = br.readLine();
                    st2 = st;

                    // on parcourt les lignes supports (LS) et les pièces découpées dans la plaque
                    while (st2.contains("LS") || st2.contains(",") || st2.contains("Pas utilisée")) {

                        if (st2.contains("LS")) {
                            y = Integer.parseInt(st2.split("=")[1]);
                        } else if (st2.contains(",")) {
                            // on enregistre chaque pièce découpée dans la plaque
                            String[] spt = st2.split(", ");
                            for (String s : spt) {
                                tk = new StringTokenizer(s);
                                int x = Integer.parseInt(tk.nextToken()); // on obtient "x"
                                int h = Integer.parseInt(tk.nextToken()); // on obtient "h"
                                int w = Integer.parseInt(tk.nextToken()); // on obtient "w"

                                // on enregistre la pièce de coordonnées "x", "y" et de dimension "h", "w"
                                plate.addPiece(new PlateWithCoords(h, w, x, y));
                                //System.out.println(y+" "+x+" "+h+" "+w); //TODO: a supprimer
                            }
                        }

                        // on passe à la ligne suivante:
                        st2 = br.readLine();
                        if (st2.contains("LS") || st2.contains(",") || st2.contains("Pas utilisée")) {
                            st = st2; // on ne traite que les lignes correspondantes à la plaque actuelle
                        }
                    }

                    cuttedPlates.add(plate); // on enregistre la plaque contenant les pièces découpées

                    //TODO: a supprimer
                    System.out.println("----------");
                    for (Integer x : plate.xPieces.keySet()) {
                        Map<Integer, Plate> sub = plate.xPieces.get(x);
                        for (Integer bigy : plate.yPieces.keySet()) {
                            System.out.println("y:"+bigy);
                            System.out.println("x:"+x);
                            System.out.println(x + " " + bigy + " " + sub.get(bigy).getH() + " " + sub.get(bigy).getW());
                        }
                    }

                } else if (st.contains("Pièces restantes à couper")) {
                    // on traite la ligne suivante:
                    st = br.readLine();

                    if (!st.contains("Aucune")) {
                        // on enregistre chaque pièce restante à couper
                        String[] spt = st.split(", ");
                        for (String s : spt) {
                            tk = new StringTokenizer(s);
                            int h = Integer.parseInt(tk.nextToken()); // on obtient "h"
                            int w = Integer.parseInt(tk.nextToken()); // on obtient "w"
                            leftPieces.add(new Rectangle(h, w)); // on enregistre la pièce de dimension "h", "w"
                        }
                    }
                } else if (st.contains("Chutes")) {
                    // on traite la ligne suivante:
                    st = br.readLine();

                    tk = new StringTokenizer(st);
                    this.lost = Integer.parseInt(tk.nextToken()); // on enregistre "lost"
                }
                //System.out.println(st);
            }
        } catch (Exception e) {
            System.out.println("There is a problem when reading FileCheck");
            e.printStackTrace();
        }
    }
}
