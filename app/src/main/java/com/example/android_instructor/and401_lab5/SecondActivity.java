package com.example.android_instructor.and401_lab5;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SecondActivity extends AppCompatActivity {
    private static final String LOG = SecondActivity.class.getSimpleName();

    private Context context;
    private EditText direccionEditText;
    private Button mostrarButton;

    private Button ejecutar1;
    private Button ejecutar2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        this.context = this;

        direccionEditText = (EditText)findViewById(R.id.direccionEditText);
        mostrarButton = (Button)findViewById(R.id.mostrarButton);
        ejecutar1 = (Button)findViewById(R.id.ejecutar1);
        ejecutar2 = (Button)findViewById(R.id.ejecutar2);

        mostrarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("url",direccionEditText.getText().toString());

                setResult(RESULT_OK,intent);
                finish();
            }
        });

        ejecutar1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //accionLarga();
                new AccionLarga().execute();
            }
        });

        ejecutar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,"Ejecutar 2",Toast.LENGTH_SHORT).show();
            }
        });
    }

    /*private void accionLarga(){
        //Iniciamos el proceso sin saber exactamente cuando terminara
        Runnable proceso = new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                    Log.d(LOG,"Finalizado");

                    //Volvemos al Thread de UI
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            direccionEditText.setText("http://www.google.com");
                        }
                    });

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        new Thread(proceso).start();
    }*/

    private void accionLarga(){
        //Iniciamos el proceso sin saber exactamente cuando terminara
        Thread hilo = new Thread(){
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                    Log.d(LOG,"Finalizado");

                    //Volvemos al Thread de UI
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            direccionEditText.setText("http://www.google.com");
                        }
                    });

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        hilo.start();
    }

    private ProgressDialog progressDialog;
    class AccionLarga extends AsyncTask<Void,Integer,Integer>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(context);
            progressDialog.setMessage("Cargando por favor espere");
            progressDialog.setIndeterminate(false);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.setProgress(0);
            progressDialog.setMax(10);
            progressDialog.setCancelable(true);
            progressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancelar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    cancel(true);
                }
            });
            progressDialog.show();
        }

        @Override
        protected Integer doInBackground(Void... voids) {
            int i = 0;
            try {
                for (i=1;i<=10;i++){
                    Thread.sleep(1000);
                    publishProgress(i);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return i;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);

            Log.d(LOG,""+integer);
            progressDialog.dismiss();
            //Toast.makeText(context,"Finalizado",Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressDialog.setProgress(values[0]);
        }

        @Override
        protected void onCancelled(Integer integer) {
            super.onCancelled(integer);
            progressDialog.dismiss();
        }
    }
}
