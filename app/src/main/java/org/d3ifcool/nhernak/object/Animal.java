package org.d3ifcool.nhernak.object;

import java.io.Serializable;

/**
 * A class for accomodate the list of Animal that we serve the information
 */

public class Animal implements Serializable {
    private String mAnimal;
    private int mImageResource;
    private String mBodyAnimal;

    /**
     *
     * @param vAnimal Name for represent the animal
     * @param vImageResource Id image that representthe animal
     */
    public Animal(String vAnimal, int vImageResource, String vBodyAnimal) {
        mAnimal = vAnimal;
        mImageResource = vImageResource;
        mBodyAnimal = vBodyAnimal;
    }

    public String getmAnimal() {
        return mAnimal;
    }

    public int getmImageResource() {
        return mImageResource;
    }

    public String getmBodyAnimal() {
        return mBodyAnimal;
    }


}
