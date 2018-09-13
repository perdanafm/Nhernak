package org.d3ifcool.nhernak.activity;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import org.d3ifcool.nhernak.R;
import org.d3ifcool.nhernak.networking.Price;
import org.d3ifcool.nhernak.networking.PriceAdapter;
import org.d3ifcool.nhernak.networking.PriceLoader;

import java.util.ArrayList;
import java.util.List;

public class PriceActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Price>>{
    private final String BASE_URL = "http://vpn.farnetwork.net/api/data.json";
    private String mCategory;
    private PriceAdapter mAdapter;
    private TextView mEmptyStateTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_price);
        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);

        Intent intent = getIntent();
        mCategory = intent.getStringExtra("category");

        ListView listView = findViewById(R.id.listview_price);

        mAdapter = new PriceAdapter(this, new ArrayList<Price>());
        listView.setAdapter(mAdapter);

        getSupportLoaderManager().initLoader(1, null, this);

        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            getSupportLoaderManager().initLoader(1, null, this);
        } else {
            View progress = findViewById(R.id.progress);
            progress.setVisibility(View.GONE);
            View refreshButton = findViewById(R.id.refresh_button);
            refreshButton.setVisibility(View.VISIBLE);
            refreshButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onRestart();
                }
            });
            mEmptyStateTextView.setText(R.string.no_internet_connection);
        }

        setTitleBar(mCategory);
    }

    private void setTitleBar(String category) {
        if (category.equalsIgnoreCase("cemani")) setTitle(R.string.price_chicken);
        else if (category.equalsIgnoreCase("garut")) setTitle(R.string.price_sheep);
        else setTitle(R.string.price_cow);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Intent intent = new Intent(PriceActivity.this, PriceActivity.class);
        intent.putExtra("category", mCategory);
        startActivity(intent);
        finish();
    }

    @Override
    public Loader<List<Price>> onCreateLoader(int i, Bundle bundle) {
        return new PriceLoader(this, BASE_URL, mCategory);
    }

    @Override
    public void onLoadFinished(Loader<List<Price>> loader, List<Price> prices) {
        View progress = findViewById(R.id.progress);
        progress.setVisibility(View.GONE);
        mAdapter.addAll(prices);
    }

    @Override
    public void onLoaderReset(Loader<List<Price>> loader) {
        mAdapter.clear();
    }
}
