package org.d3ifcool.nhernak.fragment;



import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.d3ifcool.nhernak.R;
import org.d3ifcool.nhernak.activity.FillProfileActivity;
import org.d3ifcool.nhernak.activity.LoginActivity;
import org.d3ifcool.nhernak.activity.MainActivity;
import org.d3ifcool.nhernak.activity.RegisterActivity;
import org.d3ifcool.nhernak.adapter.ArticleAdapter;
import org.d3ifcool.nhernak.object.Article;
import org.d3ifcool.nhernak.object.Peternak;

/**
 * Created by Johan Sutrisno on 13/03/2018.
 */

public class ProfileFragment extends Fragment {

    Peternak peternak;
    DatabaseReference databaseReference;

    FirebaseAuth mAuth;

    ValueEventListener listener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            peternak = dataSnapshot.getValue(Peternak.class);
            if (getView() != null) {
                setup(getView());
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();

        databaseReference = FirebaseDatabase.getInstance().getReference("peternak")
                .child(mAuth.getCurrentUser().getUid());

        //Keep Sync even when in other activity
        databaseReference.keepSynced(true);

        databaseReference.addValueEventListener(listener);

        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//        setup(view);
    }

    private void setup(View view) {
        ImageView image = (ImageView) view.findViewById(R.id.image_profile);
        TextView name = (TextView) view.findViewById(R.id.name_profile);
        TextView job = (TextView) view.findViewById(R.id.job_profile);
        TextView email = (TextView) view.findViewById(R.id.text_email);
        TextView contact = (TextView) view.findViewById(R.id.text_contact);
        TextView alamat = (TextView) view.findViewById(R.id.text_location);
        RelativeLayout pAddress = (RelativeLayout) view.findViewById(R.id.profile_address);
        RelativeLayout pNumber = (RelativeLayout) view.findViewById(R.id.profile_number);
        RelativeLayout pEmail = (RelativeLayout) view.findViewById(R.id.profile_email);
        LinearLayout pLogout = (LinearLayout) view.findViewById(R.id.profile_logout);

        if (peternak.getmImageResourceId()== Peternak.PeternakContract.JK_LAKI){
            image.setImageResource(R.drawable.ic_profilemanblueyoung);
        }else image.setImageResource(R.drawable.ic_profilegirlblueyoung);

        name.setText(peternak.getmName());
        job.setText(peternak.getmJob());
        email.setText(peternak.getmEmail());
        contact.setText(peternak.getmNoHp());
        alamat.setText(peternak.getmAlamat());

        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showUpdateDialog(Peternak.PeternakContract.CHILD_NAMA,peternak.getmName());
            }
        });

        job.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showUpdateDialog(Peternak.PeternakContract.CHILD_JOB,peternak.getmJob());
            }
        });

        pNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showUpdateDialog(Peternak.PeternakContract.CHILD_NOHP,peternak.getmNoHp());
            }
        });
        pAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showUpdateDialog(Peternak.PeternakContract.CHILD_ALAMAT,peternak.getmAlamat());
            }
        });
        pLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                LoginManager.getInstance().logOut();
                Toast.makeText(ProfileFragment.this.getActivity(),"Anda berhasil log out",Toast.LENGTH_SHORT).show();
                getActivity().finish();
                Intent moveToHome = new Intent(ProfileFragment.this.getActivity(),LoginActivity.class);
                moveToHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(moveToHome);
            }
        });
    }

    private void showUpdateDialog(final String child, String currentStatus) {
        String nama = "Nama";
        String job = "Hewan Ternak";
        String noTelp = "Nomor Telepon";
        String location = "Alamat";

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.update_profile_dialog,null);

        dialogBuilder.setView(dialogView);

        final EditText editTextName = (EditText) dialogView.findViewById(R.id.editTextName);
        editTextName.setText(currentStatus);

        final Button buttonUpdate = (Button) dialogView.findViewById(R.id.buttonUpdate);
        switch (child){
            case Peternak.PeternakContract.CHILD_NAMA:
                dialogBuilder.setTitle("Update Nama");
                break;
            case Peternak.PeternakContract.CHILD_JOB:
                dialogBuilder.setTitle("Update Hewan Ternak");
                break;
            case Peternak.PeternakContract.CHILD_NOHP:
                dialogBuilder.setTitle("Update Nomor Telepon");
                break;
            case Peternak.PeternakContract.CHILD_ALAMAT:
                dialogBuilder.setTitle("Update Alamat");
                break;
        }
        dialogBuilder.setTitle("Update Profile");

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        final String regexStr = "^[0-9]*$";

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String value = editTextName.getText().toString().trim();
                if (child.equals(Peternak.PeternakContract.CHILD_ALAMAT)){
                    if (TextUtils.isEmpty(value)){
                        editTextName.setError("Alamat tidak boleh kosong");
                        return;
                    }
                    updateProfile(child,value);
                    alertDialog.dismiss();
                }else if(child.equals(Peternak.PeternakContract.CHILD_NOHP)){
                    if (!value.matches(regexStr)){
                        editTextName.setError("Tidak boleh karakter, Harus nomor");
                        return;
                    }else if (TextUtils.isEmpty(value)){
                        editTextName.setError("Nomor HP tidak boleh kosong");
                        return;
                    }
                    updateProfile(child,value);
                    alertDialog.dismiss();
                }else if (child.equals(Peternak.PeternakContract.CHILD_NAMA)){
                    if (TextUtils.isEmpty(value)){
                        editTextName.setError("Nama tidak boleh kosong");
                        return;
                    }
                    updateProfile(child,value);
                    alertDialog.dismiss();
                }else if (child.equals(Peternak.PeternakContract.CHILD_JOB)){
                    if (TextUtils.isEmpty(value)){
                        editTextName.setError("Hewan ternak tidak boleh kosong");
                        return;
                    }
                    updateProfile(child,value);
                    alertDialog.dismiss();
                }

            }
        });

    }

    private boolean updateProfile(String child, String value) {
        DatabaseReference databaseReferenceUpdt = FirebaseDatabase.getInstance()
                .getReference("peternak").child(peternak.getmId());

        databaseReferenceUpdt.child(child).setValue(value);

        Toast.makeText(getActivity(),"Profile Updated Successfully", Toast.LENGTH_SHORT).show();

        return true;
    }

    @Override
    public void onStart() {
        super.onStart();
        databaseReference.addValueEventListener(listener);
    }

    @Override
    public void onStop() {
        super.onStop();
        databaseReference.removeEventListener(listener);
    }
}
