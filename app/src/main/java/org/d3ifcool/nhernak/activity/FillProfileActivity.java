package org.d3ifcool.nhernak.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.flags.IFlagProvider;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

import org.d3ifcool.nhernak.R;
import org.d3ifcool.nhernak.object.Peternak;

public class FillProfileActivity extends AppCompatActivity {

    //View Variable
    EditText etNamaLengkap,etAlamat,etHewanTernak,etNoTelp;
    Spinner spJenisKelamin;
    Button btnSave;

    //Firebase Auth Variable
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;

    //Firebase Database
    DatabaseReference mUserDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_profile);

        etAlamat = (EditText) findViewById(R.id.et_alamat);
        etNamaLengkap = (EditText) findViewById(R.id.et_namalengkap);
        etHewanTernak = (EditText) findViewById(R.id.et_hewanTernak);
        etNoTelp = (EditText) findViewById(R.id.et_noTelp);
        spJenisKelamin = (Spinner) findViewById(R.id.sp_gender);
        btnSave = (Button) findViewById(R.id.btn_save);

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null){
                    finish();
                    Intent moveToHome = new Intent(FillProfileActivity.this, MainActivity.class);
                    moveToHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(moveToHome);
                }
            }
        };

        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("peternak")
                .child(mAuth.getCurrentUser().getUid());

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveUserProfile();
            }
        });
    }

    private void saveUserProfile() {

        String name,job,email,nohp,alamat,id,jenisKelamin;
        int idImageRes;

        name = etNamaLengkap.getText().toString().trim();
        job = etHewanTernak.getText().toString().trim();
        email = mAuth.getCurrentUser().getEmail();
        nohp = etNoTelp.getText().toString().trim();
        alamat = etAlamat.getText().toString().trim();

        id = mAuth.getCurrentUser().getUid();
        jenisKelamin = spJenisKelamin.getSelectedItem().toString();
        if (jenisKelamin.equalsIgnoreCase("laki-laki")){
            idImageRes = Peternak.PeternakContract.JK_LAKI;
        }else idImageRes = Peternak.PeternakContract.JK_PEREMPUAN;

        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(job) && !TextUtils.isEmpty(email)
                && !TextUtils.isEmpty(nohp) && !TextUtils.isEmpty(alamat)){

            Peternak peternak = new Peternak(name,job,email,nohp,alamat,jenisKelamin,
                    id,idImageRes);
            mUserDatabase.setValue(peternak);

            String deviceToken = FirebaseInstanceId.getInstance().getToken();
            Log.d("deviceToken",deviceToken);
            mUserDatabase.child("device_token").setValue(deviceToken).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(FillProfileActivity.this,"Authentication Success",Toast.LENGTH_SHORT).show();
                    Intent moveToHome = new Intent(FillProfileActivity.this,MainActivity.class);
                    moveToHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    Toast.makeText(FillProfileActivity.this,"Berhasil menyimpan profile",
                            Toast.LENGTH_LONG).show();
                    finish();
                    startActivity(moveToHome);
                }
            });
        }else {
            Toast.makeText(FillProfileActivity.this,"Tidak boleh ada yang kosong",
                    Toast.LENGTH_SHORT).show();
        }

    }
}
