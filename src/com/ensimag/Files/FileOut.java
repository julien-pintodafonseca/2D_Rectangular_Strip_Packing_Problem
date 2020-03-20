package com.ensimag.Files;

import java.io.FileWriter;
import java.util.List;

/**
 * Created by pintodaj on 2/19/20.
 */
public class FileOut {
    private String fileName;
    private List<String> results;

    public FileOut(String _fileName, List<String> _results) {
        this.fileName = _fileName;
        this.results = _results;
    }

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
