package com.ensimag.Files;

import com.ensimag.Models.CutPlate;
import com.ensimag.Models.Plate;
import com.ensimag.Models.Rectangle;
import org.w3c.dom.css.Rect;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

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
                    st2 = st;

                    // on parcourt les lignes supports (LS) et les pièces découpées dans la plaque
                    while (st2.contains("LS") || st2.contains(",") || st2.contains("Pas utilisée")) {
                        // on traite la ligne suivante:
                        st2 = br.readLine();

                        if (st2.contains(",")) {
                            // on enregistre chaque pièce découpée dans la plaque
                            String[] spt = st.split(", ");
                            for (String s : spt) {
                                tk = new StringTokenizer(s);
                                int h = Integer.parseInt(tk.nextToken()); // on obtient "h"
                                int w = Integer.parseInt(tk.nextToken()); // on obtient "w"
                                int nbPieces = Integer.parseInt(tk.nextToken()); // on obtient "nbPieces"

                                // TODO:
                                //plate.addPiece(???); // on enregistre "nbPieces"de dimension "h", "w"découpées dans la plaque
                            }
                        }

                        // on passe à la ligne suivante:
                        st2 = br.readLine();
                        if (st2.contains("LS") || st2.contains(",") || st2.contains("Pas utilisée")) {
                            st = st2; // on ne traite que les lignes correspondantes à la plaque actuelle
                        }
                    }
                    cuttedPlates.add(plate); // on enregistre la plaque contenant les pièces découpées
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
            System.out.println("There is a problem with EntriesFile");
            e.printStackTrace();
        }
    }
}
