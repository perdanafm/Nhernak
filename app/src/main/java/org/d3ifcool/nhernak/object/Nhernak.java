package org.d3ifcool.nhernak.object;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Faishal on 24/04/2018.
 */

public class Nhernak extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

    }
}
