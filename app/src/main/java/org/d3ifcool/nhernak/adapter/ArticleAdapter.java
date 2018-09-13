package org.d3ifcool.nhernak.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import org.d3ifcool.nhernak.R;
import org.d3ifcool.nhernak.fragment.ArticleFragment;
import org.d3ifcool.nhernak.object.Article;

import java.util.List;

/**
 * Adapter to connect to the listview
 */


public class ArticleAdapter extends ArrayAdapter<Article> {

    private Activity context;
    private List<Article> articleList;

    public ArticleAdapter(Activity context, List<Article> articleList) {
        super(context, R.layout.list_article, articleList);
        this.context = context;
        this.articleList = articleList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.list_article,null,false);

        TextView textViewJudul = (TextView) listViewItem.findViewById(R.id.title_article);
        TextView textViewCategory = (TextView) listViewItem.findViewById(R.id.category_article);
        ImageView imgArticle = (ImageView) listViewItem.findViewById(R.id.image_article);

        Article article = articleList.get(position);

        textViewJudul.setText(article.getmTitleArticle());
        textViewCategory.setText(article.getmCategory());
        if (article.getmCategory().equalsIgnoreCase("Sapi")){
            imgArticle.setImageResource(R.drawable.ic_cow);
        }
        else if (article.getmCategory().equalsIgnoreCase("Domba")){
            imgArticle.setImageResource(R.drawable.ic_sheep);
        }
        else if (article.getmCategory().equalsIgnoreCase("Ayam")){
            imgArticle.setImageResource(R.drawable.ic_chicken);
        }

        return listViewItem;
    }
}
