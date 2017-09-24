package com.example.alejo.saladapp;


import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.StringTokenizer;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {


    ImageView s1;
    ImageView s2;
    ImageView s3;
    ImageView s4;
    ImageView s5;
    ImageView profilePhoto;
    TextView tvPlaca;
    TextView tvUser;
    TextView tvCode;
    TextView tvRoute;

    int score;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Información del viaje");
        profilePhoto = (ImageView)view.findViewById(R.id.Cimagen);
        score = 0;
        s1 = (ImageView)view.findViewById(R.id.h1);
        s2 = (ImageView)view.findViewById(R.id.h2);
        s3 = (ImageView)view.findViewById(R.id.h3);
        s4 = (ImageView)view.findViewById(R.id.h4);
        s5 = (ImageView)view.findViewById(R.id.h5);
        if(!getArguments().getString("code").equals("")){
            Picasso.with(getContext()).load("https://scontent.feoh3-1.fna.fbcdn.net/v/t34.0-12/22014569_10155681998641445_795024491_n.png?oh=1537595fb913fb4e09d011742b724cb4&oe=59C9CAC0").into(profilePhoto);
            s1.setVisibility(View.VISIBLE);
            s2.setVisibility(View.VISIBLE);
            s3.setVisibility(View.VISIBLE);
            s4.setVisibility(View.VISIBLE);
            s5.setVisibility(View.VISIBLE);
        }
        else{
            s1.setVisibility(View.INVISIBLE);
            s2.setVisibility(View.INVISIBLE);
            s3.setVisibility(View.INVISIBLE);
            s4.setVisibility(View.INVISIBLE);
            s5.setVisibility(View.INVISIBLE);
        }
        tvCode = (TextView)view.findViewById(R.id.Ncodigobus);
        tvUser = (TextView)view.findViewById(R.id.Nconductor);
        tvPlaca = (TextView) view.findViewById(R.id.Nplaca);
        tvRoute = (TextView) view.findViewById(R.id.Nruta);

        tvCode.setText(getArguments().getString("code"));
        tvUser.setText(getArguments().getString("ncond"));
        tvPlaca.setText(getArguments().getString("placa"));
        tvRoute.setText(getArguments().getString("route"));


        s1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                s1.setVisibility(View.VISIBLE);
                s2.setVisibility(View.VISIBLE);
                s3.setVisibility(View.VISIBLE);
                s4.setVisibility(View.VISIBLE);
                s5.setVisibility(View.VISIBLE);
                s1.setImageResource(R.drawable.ic_starb);
                s2.setImageResource(R.drawable.ic_stare);
                s3.setImageResource(R.drawable.ic_stare);
                s4.setImageResource(R.drawable.ic_stare);
                s5.setImageResource(R.drawable.ic_stare);
                score = 1;
                Intent intent = new Intent(getContext(),EvalActivity.class);
                intent.putExtra("score",score);
                startActivity(intent);
            }
        });
        s2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                s1.setVisibility(View.VISIBLE);
                s2.setVisibility(View.VISIBLE);
                s3.setVisibility(View.VISIBLE);
                s4.setVisibility(View.VISIBLE);
                s5.setVisibility(View.VISIBLE);
                s1.setImageResource(R.drawable.ic_starb);
                s2.setImageResource(R.drawable.ic_starb);
                s3.setImageResource(R.drawable.ic_stare);
                s4.setImageResource(R.drawable.ic_stare);
                s5.setImageResource(R.drawable.ic_stare);
                score = 2;
                Intent intent = new Intent(getContext(),EvalActivity.class);
                intent.putExtra("score",score);
                startActivity(intent);
            }
        });
        s3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                s1.setVisibility(View.VISIBLE);
                s2.setVisibility(View.VISIBLE);
                s3.setVisibility(View.VISIBLE);
                s4.setVisibility(View.VISIBLE);
                s5.setVisibility(View.VISIBLE);
                s1.setImageResource(R.drawable.ic_starb);
                s2.setImageResource(R.drawable.ic_starb);
                s3.setImageResource(R.drawable.ic_starb);
                s4.setImageResource(R.drawable.ic_stare);
                s5.setImageResource(R.drawable.ic_stare);
                score = 3;
                Intent intent = new Intent(getContext(),EvalActivity.class);
                intent.putExtra("score",score);
                startActivity(intent);
            }
        });
        s4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                s1.setVisibility(View.VISIBLE);
                s2.setVisibility(View.VISIBLE);
                s3.setVisibility(View.VISIBLE);
                s4.setVisibility(View.VISIBLE);
                s5.setVisibility(View.VISIBLE);
                s1.setImageResource(R.drawable.ic_starb);
                s2.setImageResource(R.drawable.ic_starb);
                s3.setImageResource(R.drawable.ic_starb);
                s4.setImageResource(R.drawable.ic_starb);
                s5.setImageResource(R.drawable.ic_stare);
                score = 4;
                Intent intent = new Intent(getContext(),EvalActivity.class);
                intent.putExtra("score",score);
                startActivity(intent);
            }
        });
        s5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                s1.setVisibility(View.VISIBLE);
                s2.setVisibility(View.VISIBLE);
                s3.setVisibility(View.VISIBLE);
                s4.setVisibility(View.VISIBLE);
                s5.setVisibility(View.VISIBLE);
                s1.setImageResource(R.drawable.ic_starb);
                s2.setImageResource(R.drawable.ic_starb);
                s3.setImageResource(R.drawable.ic_starb);
                s4.setImageResource(R.drawable.ic_starb);
                s5.setImageResource(R.drawable.ic_starb);
                score = 5;
                Intent intent = new Intent(getContext(),EvalActivity.class);
                intent.putExtra("score",score);
                startActivity(intent);
            }
        });
    }



}
