package model.handler;

import model.service.Savable;

import java.io.File;


public abstract class FileSaver extends FileHandler {

    /**
     * Class FileHandler
     *
     * @param object an object of a class implemented the Savable interface to be saved or loaded
     * @param name   a string value of the file's name (recommended to use latin alphabet and no spaces)
     */
    public FileSaver(Savable object, String name) {
        super(object, name);
    }

    /**
     * Saves data in given file
     */
    abstract boolean saveFile(File file);
}
