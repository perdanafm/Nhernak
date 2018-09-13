package org.d3ifcool.nhernak.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import org.d3ifcool.nhernak.R;
import org.d3ifcool.nhernak.object.Animal;

public class DetailFarmAnimalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_farm_animal);

        Animal hewan = (Animal) getIntent().getExtras().getSerializable("hewan");

        TextView detailJudul = (TextView) findViewById(R.id.detail_title);
        TextView detailDesc = (TextView) findViewById(R.id.detail_desc);
        ImageView detailImg = (ImageView) findViewById(R.id.detail_img);

        detailJudul.setText(hewan.getmAnimal());
        detailDesc.setText(hewan.getmBodyAnimal());
        detailImg.setImageResource(hewan.getmImageResource());

    }
}
