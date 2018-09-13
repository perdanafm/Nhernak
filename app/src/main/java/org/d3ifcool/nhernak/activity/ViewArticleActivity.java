package org.d3ifcool.nhernak.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.d3ifcool.nhernak.R;
import org.d3ifcool.nhernak.adapter.ArticleAdapter;
import org.d3ifcool.nhernak.fragment.ArticleFragment;
import org.d3ifcool.nhernak.object.Article;

import java.util.ArrayList;
import java.util.List;

public class ViewArticleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_article);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = getIntent();
        String idArticle = intent.getStringExtra("ID_ARTICLE");

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("article").child(idArticle);
        final TextView textJudul = (TextView) findViewById(R.id.articleTitle);
        final TextView textBody = (TextView) findViewById(R.id.articleBody);
//        final List<Article> articleData = new ArrayList<>();

        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //getting article
                Article article = dataSnapshot.getValue(Article.class);
                textJudul.setText(article.getmTitleArticle());
                textBody.setText(article.getmBody());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        databaseReference.addValueEventListener(listener);
    }
}
