package com.example.user.booklistingapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class BookAdapter extends ArrayAdapter<Book> {

    TextView bookTitle, bookAuthor, bookDescription;
    ImageView bookImage;
    RatingBar bookRating;

    public BookAdapter(Context context, List<Book> books){
        super(context, 0, books);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItem = convertView;
        if(listItem==null)
            listItem = LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent,false);

        Book book = getItem(position);

        bookImage = listItem.findViewById(R.id.book_img);
        Picasso.get().load(book.getmImageUrl()).fit().into(bookImage);

        bookTitle = listItem.findViewById(R.id.book_title);
        bookTitle.setText(book.getmTitle());

        bookAuthor = listItem.findViewById(R.id.book_author);
        bookAuthor.setText(book.getmAuthor());

        bookRating = listItem.findViewById(R.id.book_rating);
        bookRating.setRating((float)book.getmRating());

        bookDescription = listItem.findViewById(R.id.book_disc);
        bookDescription.setText(book.getmDescription());

        return listItem;
    }
}