package com.shia.practice126;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class DetailBookActivity extends AppCompatActivity {

    public static String ID = "id";
    int id;
    Bundle bundle;
    TextView book_name, description, name_author, published, genre, txt_price_detail;
    ImageView imgDetail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_book);

        id = Integer.parseInt(getIntent().getStringExtra(ID));
        Toast.makeText(this, "" + id, Toast.LENGTH_SHORT).show();
        bundle = getIntent().getExtras();

        book_name = findViewById(R.id.book_name);
        description = findViewById(R.id.description);
        name_author = findViewById(R.id.name_author);
        published = findViewById(R.id.published);
        genre = findViewById(R.id.genre);
        txt_price_detail = findViewById(R.id.txt_price_detail);
        imgDetail = findViewById(R.id.imgDetail);

        book_name.setText(bundle.getString("name"));
        description.setText(new StringBuilder("Description : ") + bundle.getString("description"));
        name_author.setText(new StringBuilder("Author : ") + bundle.getString("author"));
        published.setText(new StringBuilder("Published at : ") + bundle.getString("published"));
        genre.setText(new StringBuilder("Genre : ") + bundle.getString("genre"));
        txt_price_detail.setText(bundle.getString("txt_price_detail"));
        Picasso.get().load(bundle.getString("link_img")).into(imgDetail);
    }
}