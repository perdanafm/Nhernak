package org.d3ifcool.nhernak.networking;

/**
 * Created by Johan Sutrisno on 24/04/2018.
 */

public class Price {
    /** Gender of the animal */
    private int mJenisKelamin;

    /** Age of the animal */
    private String mUmur;

    /** Price of the animal */
    private int mHarga;

    /**
     *
     * @param jenisKelamin is meaning to gender of the animal
     * @param umur is the age of the animal
     * @param harga is the price of the animal
     */
    public Price(int jenisKelamin, String umur, int harga) {
        mJenisKelamin = jenisKelamin;
        mUmur = umur;
        mHarga = harga;
    }

    public int getJenisKelamin() {
        return mJenisKelamin;
    }

    public String getUmur() {
        return mUmur;
    }

    public int getHarga() {
        return mHarga;
    }
}
