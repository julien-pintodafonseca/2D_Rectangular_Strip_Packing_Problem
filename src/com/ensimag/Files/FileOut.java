package com.ensimag.Files;

import java.io.FileWriter;
import java.util.List;

/**
 * Class FileOut (fichier de sortie / de résultats)
 * @author Groupe6
 */
public class FileOut {
    /**
     * Nom du fichier de sortie
     */
    private String fileName;
    private List<String> results;

    /**
     * Constructeur
     * @param _fileName : nom de fichier dans lequel on doit écrire
     * @param _results : résultats à écrire dans le fichier
     */
    public FileOut(String _fileName, List<String> _results) {
        this.fileName = _fileName;
        this.results = _results;
    }

    /**
     * Méthode qui permet d'écrire dans le fichier les données de l'attribut results
     */
    public void writeFile() {
        try {
            FileWriter writer = new FileWriter(fileName);
            for(String str: results) {
                writer.write(str + System.lineSeparator());
            }
            writer.close();
        } catch(Exception e) {
            System.out.println("There is a problem when writing FileOut");
        }
    }
}
