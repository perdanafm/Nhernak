package org.d3ifcool.nhernak.object;

/**
 * A class that represent user who use this app right now
 */

public class Peternak {

    private String mName,mJob,mEmail,mNoHp,mAlamat,mJenisKelamin,mId;
    private int mImageResourceId;

    /**
     *
     * @param vName Variable that represent the name of users that using this app now
     * @param vJob represent the job of the users ( Peternak )
     * @param vEmail represent the email of the users
     * @param vAlamat represent the address of the users
     * @param vImageResourceId represent the id image of the users
     */
    public Peternak(String vName, String vJob, String vEmail, String vNoHp, String vAlamat,
                    int vImageResourceId) {
        mName = vName;
        mJob = vJob;
        mEmail = vEmail;
        mNoHp = vNoHp;
        mAlamat = vAlamat;
        mImageResourceId = vImageResourceId;
    }

    public Peternak(String mName, String mJob, String mEmail, String mNoHp, String mAlamat, String mJenisKelamin, String mId, int mImageResourceId) {
        this.mName = mName;
        this.mJob = mJob;
        this.mEmail = mEmail;
        this.mNoHp = mNoHp;
        this.mAlamat = mAlamat;
        this.mJenisKelamin = mJenisKelamin;
        this.mId = mId;
        this.mImageResourceId = mImageResourceId;
    }

    public Peternak() {}

    public String getmName() {
        return mName;
    }

    public String getmJob() {
        return mJob;
    }

    public String getmEmail() {
        return mEmail;
    }

    public String getmAlamat() {
        return mAlamat;
    }

    public int getmImageResourceId() {
        return mImageResourceId;
    }

    public String getmNoHp() {
        return mNoHp;
    }

    public String getmId() {
        return mId;
    }

    public String getmJenisKelamin() {
        return mJenisKelamin;
    }

    public class PeternakContract{
        public final static int JK_LAKI = 0;
        public final static int JK_PEREMPUAN = 1;
        public final static String CHILD_NOHP = "mNoHp";
        public final static String CHILD_ALAMAT = "mAlamat";
        public final static String CHILD_NAMA = "mName";
        public final static String CHILD_JOB = "mJob";

    }
}
