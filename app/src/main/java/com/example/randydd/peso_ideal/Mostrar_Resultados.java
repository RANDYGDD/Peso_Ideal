package com.example.randydd.peso_ideal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Mostrar_Resultados extends AppCompatActivity {

    TextView Intervalos;
    TextView Dianostico;
    TextView Unidad;
    TextView Complexion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar__resultados);

        Intervalos=(TextView)findViewById(R.id.intervalos);
        Dianostico=(TextView)findViewById(R.id.Dianostico);
        Unidad=(TextView)findViewById(R.id.unidad);
        Complexion=(TextView)findViewById(R.id.complexion);

        Bundle datos=getIntent().getExtras();

        String intervalos;
        String disnostico;
        String complexion;
        int unidad;

        intervalos=datos.getString("intervalos");
        disnostico=datos.getString("diagnostico");
        complexion=datos.getString("complexion");
        unidad=datos.getInt("unidad");


        Intervalos.setText("El intervalo es: " + intervalos);
        Dianostico.setText("El diagnostico es: "+disnostico);
        Unidad.setText("La unidad es: "+ unidad);
        Complexion.setText("La complexion es: " + complexion);


    }
}
