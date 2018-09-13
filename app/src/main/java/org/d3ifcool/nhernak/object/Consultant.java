package org.d3ifcool.nhernak.object;

/**
 * A class for accomodate the list of consultant that have been registered in our app
 */

public class Consultant {
    private String mId;
    private String mName;
    private String mJenisKelamin;
    private String mNoHp;
    private String mEmail;
    private String mRating;
    private String mAlamat;
    private String mPendidikan;
    private String mPrestasi;
    private String mSpecialization;
    private String mPrice;

    /**
     *
     * @param mName Variable that represent name of the consultants
     * @param mSpecialization Variable that represent the specialization of the consultants
     * @param mPrice Variable that represent the price of consultant
     * @param mJenisKelamin Variable that represent the gender of the consultant
     */

    public Consultant(String mId, String mName, String mJenisKelamin, String mNoHp, String mEmail,
                      String mRating, String mAlamat, String mPendidikan,
                      String mPrestasi, String mSpecialization, String mPrice) {

        this.mId = mId;
        this.mName = mName;
        this.mJenisKelamin = mJenisKelamin;
        this.mNoHp = mNoHp;
        this.mEmail = mEmail;
        this.mRating = mRating;
        this.mAlamat = mAlamat;
        this.mPendidikan = mPendidikan;
        this.mPrestasi = mPrestasi;
        this.mSpecialization = mSpecialization;
        this.mPrice = mPrice;
    }

    public Consultant(String mName, String mSpecialization,String mPrice, String mJenisKelamin) {
        this.mName = mName;
        this.mJenisKelamin = mJenisKelamin;
        this.mSpecialization = mSpecialization;
        this.mPrice = mPrice;
    }

    public Consultant() {}

    public String getmName() {
        return mName;
    }

    public String getmSpecialization() {
        return mSpecialization;
    }

    public String getmPrice() {
        return mPrice;
    }

    public String getmJenisKelamin() {
        return mJenisKelamin;
    }

    public String getmId() {
        return mId;
    }

    public String getmNoHp() {
        return mNoHp;
    }

    public String getmEmail() {
        return mEmail;
    }

    public String getmRating() {
        return mRating;
    }

    public String getmAlamat() {
        return mAlamat;
    }

    public String getmPendidikan() {
        return mPendidikan;
    }

    public String getmPrestasi() {
        return mPrestasi;
    }
}
