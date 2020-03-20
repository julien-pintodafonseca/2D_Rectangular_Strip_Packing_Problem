package com.ensimag.Files;

import com.ensimag.Models.CutPlate;
import com.ensimag.Models.PieceWithCoords;
import com.ensimag.Models.Plate;
import com.ensimag.Models.Piece;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class FileCheck {
    private String fileName;
    private List<CutPlate> cuttedPlates;
    private List<Piece> leftPieces;
    private int lost;
    private List<Plate> platesList;
    private int plateNumber;

    public FileCheck(String _fileName, List<Plate> _platesList) {
        this.fileName = _fileName;
        this.cuttedPlates = new ArrayList<>();
        this.leftPieces = new ArrayList<>();
        this.lost = -1;
        this.plateNumber = -1;
        this.platesList = new ArrayList<>(_platesList);
        this.loadEntries();
    }

    public int getLost() {
        return this.lost;
    }

    public String getFileName() {
        return fileName;
    }

    public boolean hasNextPlate() {
        return (this.plateNumber + 1 < this.cuttedPlates.size());
    }

    public CutPlate nextPlate() {
        this.plateNumber += 1;
        return this.cuttedPlates.get(this.plateNumber);
    }

    private void loadEntries() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String st, st2;
            StringTokenizer tk;
            int current_plate = 0;
            while ((st = br.readLine()) != null) {
                if (st.contains("Plaque")) {
                    CutPlate plate = new CutPlate(this.platesList.get(current_plate).getH(), this.platesList.get(current_plate).getW());
                    plate.setLost(plate.getH()*plate.getW());
                    current_plate += 1;
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
                                plate.addPiece(new PieceWithCoords(h, w, x, y));
                                plate.addLost(-h*w);
                            }
                        }

                        // on passe à la ligne suivante:
                        st2 = br.readLine();
                        if (st2.contains("LS") || st2.contains(",") || st2.contains("Pas utilisée")) {
                            st = st2; // on ne traite que les lignes correspondantes à la plaque actuelle
                        }
                    }
                    plate.sort();
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
                            leftPieces.add(new Piece(h, w)); // on enregistre la pièce de dimension "h", "w"
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
