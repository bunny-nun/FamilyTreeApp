package model.handler;

import model.service.Savable;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class OutputSaver extends FileSaver {

    /**
     * Class OutputLoader
     *
     * @param object the object of a class implemented the Savable interface to be saved or loaded
     * @param name   a string value of the file's name (recommended to use latin alphabet and no spaces)
     */
    public OutputSaver(Savable object, String name) {
        super(object, name);
    }

    /**
     * Writes the full fata of the object given as this OutputLoader's object to an OutputStream and saves the file
     * in format .out with given name
     */
    @Override
    public boolean saveFile(File file) {
        if (this.object != null) {
            try {
                ObjectOutputStream fileSaver = new ObjectOutputStream(new FileOutputStream(file));
                fileSaver.writeObject(this.object);
                fileSaver.close();
                return true;
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        return false;
    }
}
