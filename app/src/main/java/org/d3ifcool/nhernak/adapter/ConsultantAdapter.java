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
import org.d3ifcool.nhernak.object.Consultant;
import org.d3ifcool.nhernak.object.Peternak;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter to connect to the listview
 */


public class ConsultantAdapter extends ArrayAdapter<Peternak>{

    public ConsultantAdapter(@NonNull Context context, @NonNull ArrayList<Peternak> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listContactView = convertView;
        if (listContactView == null){
            listContactView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_contact, parent, false);
        }

        Peternak currentConsultant = getItem(position);

        TextView nameTextView = (TextView) listContactView.findViewById(R.id.name_tx);
        nameTextView.setText(currentConsultant.getmName());
        TextView specializationTextView = (TextView) listContactView.findViewById(R.id.specialization_tx);
        specializationTextView.setText(currentConsultant.getmJob());

        TextView priceTextView = (TextView) listContactView.findViewById(R.id.alamat_konsultan);
//        if (currentConsultant.getmPrice().equalsIgnoreCase("0") ){
            priceTextView.setText(currentConsultant.getmAlamat());
//        }else priceTextView.setText(R.string.rupiah + currentConsultant.getmPrice());

        ImageView profileImageView = (ImageView) listContactView.findViewById(R.id.image_contact_img);
        if (currentConsultant.getmJenisKelamin().equalsIgnoreCase("Laki-laki")){
            profileImageView.setImageResource(R.drawable.ic_profilemanblueyoung);
        }else profileImageView.setImageResource(R.drawable.ic_profilegirlblueyoung);

        return listContactView;
    }
}
