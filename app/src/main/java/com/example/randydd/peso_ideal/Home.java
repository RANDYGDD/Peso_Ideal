package com.example.randydd.peso_ideal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Home extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportActionBar().setTitle(R.string.opciones);


        //Agregamos los Listener a las diferentes opciones
        findViewById(R.id.calcular).setOnClickListener(this);
        findViewById(R.id.informacion).setOnClickListener(this);
        findViewById(R.id.acerca_de).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        //Verificamos a la opcion a la cual el usuario ha presionado
        switch (v.getId()){
            case R.id.calcular:
                Intent calcular=new Intent(Home.this,MainActivity.class);
                startActivity(calcular);
                break;
            case R.id.informacion:
                Intent informacion=new Intent(Home.this,informacion.class);
                startActivity(informacion);
                break;

            case R.id.acerca_de:
                Intent acerca_de=new Intent(Home.this,acerca_de.class);
                startActivity(acerca_de);
                break;
        }



    }
}
