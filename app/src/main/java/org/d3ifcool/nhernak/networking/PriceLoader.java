package org.d3ifcool.nhernak.networking;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Johan Sutrisno on 24/04/2018.
 */

public class PriceLoader extends AsyncTaskLoader<List<Price>> {
    private String mUrl;
    private String mCategory;

    public PriceLoader(Context context, String url, String category) {
        super(context);
        mUrl = url;
        mCategory = category;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Price> loadInBackground() {
        if (mUrl == null) return null;

        return fetchNameData(mUrl);
    }

    private List<Price> fetchNameData(String stringUrl) {
        URL url = createUrl(stringUrl);

        try {
            String json = makeHttpRequest(url);
            return parseJson(json, mCategory);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    private URL createUrl(String stringUrl) {
        URL url = null;

        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    private String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        if (url == null) return jsonResponse;

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream) ;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) urlConnection.disconnect();
            if (inputStream != null) inputStream.close();
        }

        return jsonResponse;
    }

    private String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();

        if (inputStream != null) {
            InputStreamReader streamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(streamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }

        return output.toString();
    }

    private ArrayList<Price> parseJson(String json, String category) {
        ArrayList<Price> result = new ArrayList<>();

        try {
            JSONObject root = new JSONObject(json);
            JSONArray priceCategory = root.getJSONArray(category);

            for (int i = 0; i<priceCategory.length(); i++) {
                JSONObject currentPrice = priceCategory.getJSONObject(i);

                String age = currentPrice.getString("umur");
                int gender = currentPrice.getInt("jeniskelamin");
                int price = currentPrice.getInt("harga");

                result.add(new Price(gender, age, price));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }
}
