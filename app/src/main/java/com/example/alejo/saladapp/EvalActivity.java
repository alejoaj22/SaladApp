package com.example.alejo.saladapp;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.StringTokenizer;

public class EvalActivity extends AppCompatActivity {

    Button bSend;
    String okay="";
    String string="";
    ImageView h1,h2,h3,h4,h5;
    int score;
    //PUERTO
    private static final int SERVERPORT = 59999;
    //HOST
    private static final String ADDRESS = "175.17.0.121";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eval);
        bSend = (Button)findViewById(R.id.bSend);

        score = getIntent().getIntExtra("score",3);
        h1 = (ImageView)findViewById(R.id.h1);
        h2 = (ImageView)findViewById(R.id.h2);
        h3 = (ImageView)findViewById(R.id.h3);
        h4 = (ImageView)findViewById(R.id.h4);
        h5 = (ImageView)findViewById(R.id.h5);
        switch (score){
            case 1:
                h1.setImageResource(R.drawable.ic_starb);
                h2.setImageResource(R.drawable.ic_stare);
                h3.setImageResource(R.drawable.ic_stare);
                h4.setImageResource(R.drawable.ic_stare);
                h5.setImageResource(R.drawable.ic_stare);
                break;
            case 2:
                h1.setImageResource(R.drawable.ic_starb);
                h2.setImageResource(R.drawable.ic_starb);
                h3.setImageResource(R.drawable.ic_stare);
                h4.setImageResource(R.drawable.ic_stare);
                h5.setImageResource(R.drawable.ic_stare);
                break;
            case 3:
                h1.setImageResource(R.drawable.ic_starb);
                h2.setImageResource(R.drawable.ic_starb);
                h3.setImageResource(R.drawable.ic_starb);
                h4.setImageResource(R.drawable.ic_stare);
                h5.setImageResource(R.drawable.ic_stare);
                break;
            case 4:
                h1.setImageResource(R.drawable.ic_starb);
                h2.setImageResource(R.drawable.ic_starb);
                h3.setImageResource(R.drawable.ic_starb);
                h4.setImageResource(R.drawable.ic_starb);
                h5.setImageResource(R.drawable.ic_stare);
                break;
            case 5:
                h1.setImageResource(R.drawable.ic_starb);
                h2.setImageResource(R.drawable.ic_starb);
                h3.setImageResource(R.drawable.ic_starb);
                h4.setImageResource(R.drawable.ic_starb);
                h5.setImageResource(R.drawable.ic_starb);
                break;
            default:
                h1.setImageResource(R.drawable.ic_stare);
                h2.setImageResource(R.drawable.ic_stare);
                h3.setImageResource(R.drawable.ic_stare);
                h4.setImageResource(R.drawable.ic_stare);
                h5.setImageResource(R.drawable.ic_stare);
        }
        bSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                string ="rate"+";"+String.valueOf(score);
                ConexionServidor conserv = new ConexionServidor();
                conserv.execute(string);
                Log.d("Score: ",String.valueOf(score));
                AlertDialog.Builder builder = new AlertDialog.Builder(EvalActivity.this);
                builder.setTitle("Información").setIcon(getResources().getDrawable(
                        android.R.drawable.ic_dialog_info));

                builder.setMessage("Muchas gracias por calificar a nuestro conductor. ¡Has ganado 200 puntos!");

                builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Display the message!

                        finish();
                    }
                });

                AlertDialog diag = builder.create();
                diag.show();


            }
        }); //Final clicklistener
    }

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
                okay = tokens.nextToken();
                Toast.makeText(getApplicationContext(), value, Toast.LENGTH_SHORT).show();

            }
        }
    }


}
