package com.example.alejo.saladapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{


    String codigobus = ""; //Comando recibido por parte del servidor
    String ruta = ""; //Cadena que le envio al servidor
    String placa = "";
    String Nconductor = "";
    String cadena = "";
    String name="";
    String user="";
    String email="";
    Uri photoUrl;
    TextView tvUser;
    TextView tvMail;
    ImageView ivPhoto;
    //PUERTO
    private static final int SERVERPORT = 59999;
    //HOST
    private static final String ADDRESS = "175.17.0.121";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);


        cadena ="ini"+";";
        ConexionServidor conserv = new ConexionServidor();
        conserv.execute(cadena);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            name = user.getDisplayName();
            email = user.getEmail();
            photoUrl = user.getPhotoUrl();

        } else {
            logOut();
        }
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        displayScreen(R.id.nav_camera);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        tvUser = (TextView)findViewById(R.id.tvName);
        tvMail = (TextView)findViewById(R.id.tvMail);
        ivPhoto = (ImageView)findViewById(R.id.ivPhoto);
        tvUser.setText(name);
        tvMail.setText(email);
        Picasso.with(getApplicationContext()).load(photoUrl).into(ivPhoto);
        return true;
    }

    private void displayScreen(int id){
        Fragment fragment = null;

        if (id == R.id.nav_camera) {
            fragment = new ProfileFragment();
            Bundle bundle = new Bundle();
            bundle.putString("code",codigobus);
            bundle.putString("route",ruta);
            bundle.putString("placa",placa);
            bundle.putString("ncond",Nconductor);
            fragment.setArguments(bundle);
        } else if(id == R.id.nav_map){
            fragment = new MapFragment();

        }else if (id == R.id.nav_logout) {
            logOut();
        }
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        displayScreen(id);
        return true;
    }



    private void logOut() {
        Intent intent = new Intent(MainActivity.this,LoginActivity.class);
        FirebaseAuth.getInstance().signOut();
        startActivity(intent);
        finish();
    }







    /**
     * Clase para interactuar con el servidor
     * */
    class ConexionServidor extends AsyncTask<String,Void,String>
    {


        /**
         * Se conecta al servidor y trata resultado
         * */
        @Override
        protected String doInBackground(String... values)
        {

            try {
                //Se conecta al servidor
                InetAddress serverAddr = InetAddress.getByName(ADDRESS);
                Log.i("I/TCP Client", "Connecting...");
                Socket socket = new Socket(serverAddr,SERVERPORT); //ip servidor, puerto
                Log.i("I/TCP Client", "Connected to server");

                //envia peticion de cliente
                Log.i("I/TCP Client", "Send data to server");
                PrintStream output = new PrintStream(socket.getOutputStream());
                String request = values[0];
                output.println(request);
                output.flush();

                //recibe respuesta del servidor y formatea a String
                Log.i("I/TCP Client", "Received data to server");
                InputStream stream = socket.getInputStream();
                byte[] lenBytes = new byte[256];
                stream.read(lenBytes,0,256);
                String received = new String(lenBytes,"UTF-8").trim();
                Log.i("I/TCP Client", "Received " + received);
                Log.i("I/TCP Client", "");
                //cierra conexion
                socket.close();
                return received;
            }catch (UnknownHostException ex) {
                Log.e("E/TCP Client", "" + ex.getMessage());
                return ex.getMessage();
            } catch (IOException ex) {
                Log.e("E/TCP Client", "" + ex.getMessage());
                return ex.getMessage();
            }
        }

        /**
         * Oculta ventana emergente y muestra resultado en pantalla
         * */
        @Override
        protected void onPostExecute(String value)
        {
            String resultado = value; //resultado recibido desde el servidor

            if (resultado.equals("Connection refused"))
            {

                Toast.makeText(getApplicationContext(), "Conexion rechazada", Toast.LENGTH_SHORT).show();
            }
            else
            {
                StringTokenizer tokens = new StringTokenizer(value, ";"); //Separacion de cadena con punto y coma
                codigobus= tokens.nextToken(); // codigo de busContiene el comando recibido desde el servidor
                ruta=tokens.nextToken(); // ruta
                placa=tokens.nextToken(); // placa
                Nconductor=tokens.nextToken(); //Nombre del conductor
                Bundle bundle = new Bundle();
                bundle.putString("code",codigobus);
                bundle.putString("route",ruta);
                bundle.putString("placa",placa);
                bundle.putString("ncond",Nconductor);
                displayScreen(R.id.nav_camera);

            }
        }



    }

}
