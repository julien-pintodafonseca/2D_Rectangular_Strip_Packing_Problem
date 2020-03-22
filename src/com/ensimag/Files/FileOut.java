package com.ensimag.Files;

import java.io.FileWriter;
import java.util.List;

/**
 * Class FileOut (fichier de sortie / de résultats)
 * @author Groupe6
 */
public class FileOut {
    /**
     * Nom du fichier de sortie dans lequel on doit écrire
     */
    private String fileName;
    /**
     * Contenu à écrire dans notre fichier de sortie, sous forme d'une liste de String
     */
    private List<String> results;

    /**
     * Constructeur de FileOut
     * @param _fileName : nom du fichier de sortie dans lequel on doit écrire
     * @param _results : contenu à écrire dans notre fichier de sortie
     */
    public FileOut(String _fileName, List<String> _results) {
        this.fileName = _fileName;
        this.results = _results;
    }

    /**
     * Permet d'écrire les résultats dans le fichier de sortie
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
