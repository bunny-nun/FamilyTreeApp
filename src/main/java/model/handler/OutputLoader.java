package model.handler;

import model.service.Savable;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class OutputLoader extends FileLoader {

    /**
     * Class OutputLoader
     *
     * @param object the object of a class implemented the Savable interface to be saved or loaded
     * @param name   a string value of the file's name (recommended to use latin alphabet and no spaces)
     */
    public OutputLoader(Savable object, String name) {
        super(object, name);
    }

    /**
     * Reads the given file in format .out and returns the object of a class implemented the Savable interface
     *
     * @return the object of a class implemented the Savable interface
     */
    @Override
    public Savable readFile(File file) {
        try {
            ObjectInputStream fileLoader = new ObjectInputStream(new FileInputStream(file));
            this.object = (Savable) fileLoader.readObject();
            fileLoader.close();
            return this.object;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
