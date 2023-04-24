package model.service;

import java.io.Serializable;

public interface Savable extends Serializable {

    /**
     * Returns the name of this object
     *
     * @return the string value of this object's name
     */
    String getName();

    /**
     * Returns the full data of this object in string format
     *
     * @return the string value of this object's fields assigned
     */
    String fullData();
}
