package org.d3ifcool.nhernak.fragment;



import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.d3ifcool.nhernak.R;
import org.d3ifcool.nhernak.activity.ViewArticleActivity;
import org.d3ifcool.nhernak.adapter.ArticleAdapter;
import org.d3ifcool.nhernak.object.Article;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Johan Sutrisno on 03/03/2018.
 */

public class ArticleFragment extends Fragment {

    //view object
    ListView listViewArticle;

    //database
    DatabaseReference databaseReference;

    //list to store all the article from firebase database
    List<Article> articleList;

    Article article;

    // Listener for initiate listview on this fragment
    ValueEventListener listener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            articleList.clear();
            for (DataSnapshot articleSnapshot : dataSnapshot.getChildren()){
                //getting article
                article = articleSnapshot.getValue(Article.class);

                //adding artist to the list
                articleList.add(article);
            }
            //make an Adapter that containt the article
            ArticleAdapter adapter = new ArticleAdapter(ArticleFragment.this.getActivity(),articleList);
            listViewArticle.setAdapter(adapter);

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        //getting the reference of database node
        databaseReference = FirebaseDatabase.getInstance().getReference("article");
        //Keep Sync even when in other activity
        databaseReference.keepSynced(true);

        //List to store all the article data
        articleList = new ArrayList<>();

        View view = inflater.inflate(R.layout.fragment_article, container, false);
        listViewArticle = view.findViewById(R.id.list_article);
        listViewArticle.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                article = articleList.get(i);
                Intent intent = new Intent(ArticleFragment.this.getActivity(), ViewArticleActivity.class);
                intent.putExtra("ID_ARTICLE",article.getmIdArticle());
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        databaseReference.addValueEventListener(listener);
    }

    @Override
    public void onStop() {
        super.onStop();
        databaseReference.removeEventListener(listener);
    }
}
