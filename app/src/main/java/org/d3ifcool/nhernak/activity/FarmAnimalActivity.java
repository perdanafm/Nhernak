package org.d3ifcool.nhernak.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.d3ifcool.nhernak.R;
import org.d3ifcool.nhernak.adapter.AnimalAdapter;
import org.d3ifcool.nhernak.object.Animal;

import java.util.ArrayList;

public class FarmAnimalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farm_animal);

        final ArrayList<Animal> data = new ArrayList<>();
        data.add(new Animal("Garut Sheep", R.drawable.img_domba,getString(R.string.body_garut_sheep)));
        data.add(new Animal("Limosin Cow", R.drawable.img_sapi,getString(R.string.body_sapi_limo)));
        data.add(new Animal("Cemani Chicken", R.drawable.img_ayam,getString(R.string.body_chicken_cemani)));

        ListView listView = (ListView) findViewById(R.id.listview_animal);
        listView.setAdapter(new AnimalAdapter(this, data));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(FarmAnimalActivity.this, DetailFarmAnimalActivity.class);
                intent.putExtra("hewan", data.get(i));
                startActivity(intent);
            }
        });
    }


}
