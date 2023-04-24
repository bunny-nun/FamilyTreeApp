package model.handler;

import model.service.Savable;

import java.io.File;


public abstract class FileLoader extends FileHandler {

    /**
     * Class FileHandler
     *
     * @param object an object of a class implemented the Savable interface to be saved or loaded
     * @param name   a string value of the file's name (recommended to use latin alphabet and no spaces)
     */
    public FileLoader(Savable object, String name) {
        super(object, name);
    }

    /**
     * Reads the given file and returns the object of a class implemented the Savable interface
     *
     * @return the object of a class implemented the Savable interface
     */
    abstract Savable readFile(File file);
}
