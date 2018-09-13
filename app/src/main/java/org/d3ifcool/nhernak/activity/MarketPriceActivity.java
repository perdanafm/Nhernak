package org.d3ifcool.nhernak.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import org.d3ifcool.nhernak.R;

public class MarketPriceActivity extends AppCompatActivity {

    private String mCategory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market_price);

        LinearLayout ayam = (LinearLayout) findViewById(R.id.ayam);
        LinearLayout domba = (LinearLayout) findViewById(R.id.domba);
        LinearLayout sapi = (LinearLayout) findViewById(R.id.sapi);

        ayam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCategory = "cemani";
                Intent intent = new Intent(MarketPriceActivity.this, PriceActivity.class);
                intent.putExtra("category", mCategory);
                startActivity(intent);
            }
        });
        domba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCategory = "garut";
                Intent intent = new Intent(MarketPriceActivity.this, PriceActivity.class);
                intent.putExtra("category", mCategory);
                startActivity(intent);
            }
        });
        sapi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCategory = "limosin";
                Intent intent = new Intent(MarketPriceActivity.this, PriceActivity.class);
                intent.putExtra("category", mCategory);
                startActivity(intent);
            }
        });
    }
}
