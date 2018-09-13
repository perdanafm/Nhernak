package org.d3ifcool.nhernak.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.d3ifcool.nhernak.R;
import org.d3ifcool.nhernak.object.Animal;

import java.util.List;

/**
 * Adapter to connect to the listview
 */

public class AnimalAdapter extends ArrayAdapter <Animal> {
    public AnimalAdapter(@NonNull Context context, @NonNull List<Animal> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listContactView = convertView;
        if (listContactView == null){
            listContactView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_animal, parent, false);
        }

        Animal currentAnimal = getItem(position);

        TextView animalNameTextView = (TextView) listContactView.findViewById(R.id.animalname_tx);
        ImageView iconAnimal = (ImageView) listContactView.findViewById(R.id.icon_imageview);

        animalNameTextView.setText(currentAnimal.getmAnimal());
        if (currentAnimal.getmAnimal().equalsIgnoreCase("Garut Sheep")){
            iconAnimal.setImageResource(R.drawable.ic_sheep);
        }else if (currentAnimal.getmAnimal().equalsIgnoreCase("Limosin Cow")){
            iconAnimal.setImageResource(R.drawable.ic_cow);
        }else iconAnimal.setImageResource(R.drawable.ic_chicken);

        return listContactView;
    }
}
