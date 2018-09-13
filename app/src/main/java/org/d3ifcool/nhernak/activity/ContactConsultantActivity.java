package org.d3ifcool.nhernak.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.d3ifcool.nhernak.R;
import org.d3ifcool.nhernak.adapter.ConsultantAdapter;
import org.d3ifcool.nhernak.fragment.ArticleFragment;
import org.d3ifcool.nhernak.object.Consultant;
import org.d3ifcool.nhernak.object.Peternak;

import java.util.ArrayList;
import java.util.List;

public class ContactConsultantActivity extends AppCompatActivity {

    //View Object
    ListView listViewConsultant;

    //Database Reference to Firebase
    DatabaseReference databaseReference;

    //List to store consultant data from database
    ArrayList<Peternak> consultantList;

    Peternak peternak;

    //Firebase Auth
    FirebaseAuth mAuth;

    ValueEventListener listener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            consultantList.clear();
            for (DataSnapshot consultantSnapshot : dataSnapshot.getChildren()){
                //Getting consultant
                peternak = consultantSnapshot.getValue(Peternak.class);

                //Adding Consultant to the list
                consultantList.add(peternak);
            }

            //Make an adapter that containt the consultant
            ConsultantAdapter adapter = new ConsultantAdapter(ContactConsultantActivity.this,consultantList);
            listViewConsultant.setAdapter(adapter);
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_consultant);

        //Get reference of database
        databaseReference = FirebaseDatabase.getInstance().getReference("peternak");
        //Keep sync even when in other activity
        databaseReference.keepSynced(true);
        //List to store all thhe new article data
        consultantList = new ArrayList<>();

        mAuth = FirebaseAuth.getInstance();


        listViewConsultant = (ListView) findViewById(R.id.listview_contact);
        listViewConsultant.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String idKonsultan = consultantList.get(i).getmId();
                String namaKonsultan = consultantList.get(i).getmName();
                Intent intent = new Intent(ContactConsultantActivity.this, ChatActivity.class);
                intent.putExtra("ID_KONSULTAN",idKonsultan);
                intent.putExtra("NAMA_KONSULTAN",namaKonsultan);
                startActivity(intent);
            }
        });

        listViewConsultant.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                final String id = consultantList.get(i).getmId();
                AlertDialog.Builder builder = new AlertDialog.Builder(ContactConsultantActivity.this);
                builder.setTitle("Delete chat history");
                builder.setMessage("Hapus chat history dengan konsultan ini ?");
                builder.setCancelable(true);
                builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deleteHistory(id);
                    }
                });
                builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();

                return true;
            }
        });
    }


    public void deleteHistory (String idKonsultan){
        DatabaseReference databaseArtistDel = FirebaseDatabase.getInstance()
                .getReference("messages").child(mAuth.getCurrentUser().getUid()+"_"+idKonsultan);
        databaseArtistDel.removeValue();

        Toast.makeText(this,"Chat telah dihapus, silahkan cek",Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onStart() {
        super.onStart();
        databaseReference.addValueEventListener(listener);

    }

    @Override
    protected void onStop() {
        super.onStop();
        databaseReference.removeEventListener(listener);

    }
}
