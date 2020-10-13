package com.example.newsproject;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class ImageDialogFragment extends DialogFragment {


    public ImageDialogFragment() {
        // Required empty public constructor
    }
String imageUrl;

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       view=inflater.inflate(R.layout.fragment_image_dialod, container, false);
        ImageView imageView=view.findViewById(R.id.image_dialog);
        Glide.with(this).load(imageUrl).into(imageView);
       return view;
    }
}