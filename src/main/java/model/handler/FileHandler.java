package model.handler;

import model.service.Savable;

public abstract class FileHandler {
    Savable object;
    String name;

    /**
     * Class FileHandler
     *
     * @param object an object of a class implemented the Savable interface to be saved or loaded
     * @param name   a string value of the file's name (recommended to use latin alphabet and no spaces)
     */
    public FileHandler(Savable object, String name) {
        this.object = object;
        this.name = name;
    }
}
