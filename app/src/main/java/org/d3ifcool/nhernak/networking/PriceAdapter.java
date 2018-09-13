package org.d3ifcool.nhernak.networking;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.d3ifcool.nhernak.R;

import java.util.List;

/**
 * Created by Johan Sutrisno on 24/04/2018.
 */

public class PriceAdapter extends ArrayAdapter<Price> {
    public PriceAdapter(@NonNull Context context, @NonNull List<Price> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listViewItem = convertView;

        if (listViewItem==null) {
            listViewItem = LayoutInflater.from(getContext()).inflate(R.layout.list_price, parent, false);
        }

        Price currentPrice = getItem(position);

        TextView ageTx = listViewItem.findViewById(R.id.age);
        ageTx.setText(currentPrice.getUmur());

        TextView genderTx = listViewItem.findViewById(R.id.gender);
        genderTx.setText(setGender(currentPrice.getJenisKelamin()));
        TextView priceTx = listViewItem.findViewById(R.id.price);
        priceTx.setText(String.valueOf(currentPrice.getHarga()));

        return listViewItem;

    }

    private String setGender(int gender) {
        if (gender == 1) {
            return "Jantan";
        } else if (gender == 2) {
            return "Betina";
        } else {
            return "Semua gender";
        }
    }
}
