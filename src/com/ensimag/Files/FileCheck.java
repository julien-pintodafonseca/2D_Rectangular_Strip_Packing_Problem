package com.ensimag.Files;

import com.ensimag.Models.CutChecker;
import com.ensimag.Models.PieceWithCoords;
import com.ensimag.Models.Plate;
import com.ensimag.Models.Piece;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

/**
 * Fichier de sortie/de résultats à vérifier
 * @author Groupe6
 */
public class FileCheck {
    /**
     * Nom du fichier de sortie / de résultats à vérifier
     */
    private String fileName;
    /**
     * Liste de plan de découpes comprenant les pièces découpées pour chaque plaque
     */
    private List<CutChecker> cuttedPlates;
    /**
     * Liste des pièces restantes (pièces non découpées)
     */
    private List<Piece> leftPieces;
    /**
     * Valeur des pertes
     */
    private int lost;
    /**
     * Liste des différentes plaques utilisées
     */
    private List<Plate> platesList;
    /**
     * Numéro de plaque
     */
    private int plateNumber;

    /**
     * Constructeur de FileCheck
     * @param _fileName : nom du fichier à vérifier
     * @param _platesList : liste des différentes plaques utilisées (à importer depuis un fichier d'entrée)
     */
    public FileCheck(String _fileName, List<Plate> _platesList) {
        this.fileName = _fileName;
        this.cuttedPlates = new ArrayList<>();
        this.leftPieces = new ArrayList<>();
        this.lost = -1;
        this.plateNumber = -1;
        this.platesList = new ArrayList<>(_platesList);
        this.loadEntries();
    }

    /**
     * Getter de la valeur des pertes
     * @return l'attribut lost
     */
    public int getLost() {
        return this.lost;
    }

    /**
     * Getter du nom du fichier à vérifier
     * @return l'attribut fileName
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Permet de savoir s'il y a une plaque suivante
     * @return true s'il existe une plaque suivante, false sinon
     */
    public boolean hasNextPlate() {
        return (this.plateNumber + 1 < this.cuttedPlates.size());
    }

    /**
     * Permet de récupérer la plaque suivante
     * @return la plaque suivante
     */
    public CutChecker nextPlate() {
        this.plateNumber += 1;
        return this.cuttedPlates.get(this.plateNumber);
    }

    /**
     * Permet de lire et de charger les données du fichier (plaques, pièces et positions, chutes) dans les attributs correspondants
     */
    private void loadEntries() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            BufferedReader br2 = new BufferedReader(new FileReader(fileName));
            String st;
            StringTokenizer tk;
            int current_plate = 0;
            while ((st = br.readLine()) != null) {
                if (!st.contains("Plaque") || st.contains("Plaque 0")) {
                    br2.readLine();
                }
                if (st.contains("Plaque")) {
                    CutChecker plate = new CutChecker(this.platesList.get(current_plate).getH(), this.platesList.get(current_plate).getW());
                    current_plate += 1;
                    int y = -1;

                    // on traite la ligne suivante:
                    st = br2.readLine();
                    br.readLine();
                    // Si le pièce est utilisée
                    // On ajoute son aire dans les chutes pour soustraire ensuite l'aire de chacune des pièces positionnées (ligne 94)
                    if (!st.contains("Pas utilisée")) {
                        plate.setLost(plate.getH()*plate.getW());
                    }
                    // on parcourt les lignes supports (LS) et les pièces découpées dans la plaque
                    while (st.contains("LS") || st.contains(",") || st.contains("Pas utilisée")) {
                        if (st.contains("LS")) {
                            y = Integer.parseInt(st.split("=")[1]);
                        } else if (st.contains(",")) {
                            // on enregistre chaque pièce découpée dans la plaque
                            String[] spt = st.split(", ");
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
                        st = br2.readLine();
                        if (st.contains("LS") || st.contains(",") || st.contains("Pas utilisée")) {
                            br.readLine(); // on ne traite que les lignes correspondantes à la plaque actuelle
                        }
                    }
                    plate.sort();
                    cuttedPlates.add(plate); // on enregistre la plaque contenant les pièces découpées
                } else if (st.contains("Pièces restantes à couper")) {
                    // on traite la ligne suivante:
                    st = br.readLine();
                    br2.readLine();

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
                    br2.readLine();

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
