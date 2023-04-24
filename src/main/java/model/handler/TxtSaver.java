package model.handler;

import model.service.Savable;

import java.io.*;

public class TxtSaver extends FileSaver {

    /**
     * Class TxtSaver
     *
     * @param object the object of a class implemented the Savable interface to be saved or loaded
     * @param name   string value of the file's name (recommended to use latin alphabet and no spaces)
     */
    public TxtSaver(Savable object, String name) {
        super(object, name);
    }


    /**
     * Writes the full fata of the object given and saves the file
     */
    @Override
    public boolean saveFile(File file) {
        if (this.object != null) {
            try {
                FileWriter fileWriter = new FileWriter(file, false);
                fileWriter.write(this.object.fullData());
                fileWriter.close();
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
            }
        }
        return false;
    }
}
