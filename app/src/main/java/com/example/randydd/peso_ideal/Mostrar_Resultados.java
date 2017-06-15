package com.example.randydd.peso_ideal;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class Mostrar_Resultados extends AppCompatActivity implements View.OnClickListener {

    TextView text_Intervalo1;
    TextView text_Intervalo2;
    TextView text_Dianostico;
    TextView text_Unidad;
    TextView text_Complexion;
    TextView text_peso_actual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar__resultados);

        Bundle datos = getIntent().getExtras();

        String intervalos;
        String diagnostico;
        String complexion;
        double peso_actual;
        int unidad;
        int inter[] = new int[2];
        String[] inter2 ;


        intervalos = datos.getString("intervalos");
        diagnostico = datos.getString("diagnostico");
        complexion = datos.getString("complexion");
        unidad = datos.getInt("unidad");
        peso_actual=datos.getDouble("peso");


        text_Intervalo1 = (TextView) findViewById(R.id.text_intervalo_menor);
       text_Intervalo2=(TextView) findViewById(R.id.text_intervalo_mayor);
        text_Unidad = (TextView) findViewById(R.id.unidad_mediad);

        text_Complexion=(TextView)findViewById(R.id.mostrar_complexion);
        text_Dianostico=(TextView)findViewById(R.id.text_dianostico);
        text_peso_actual=(TextView)findViewById(R.id.text_peso_actual);


        findViewById(R.id.info_peso_ideal).setOnClickListener(this);
        findViewById(R.id.info_complexion).setOnClickListener(this);
        findViewById(R.id.info_IMC).setOnClickListener(this);

        if (unidad == 1) {
            inter = Conversion(intervalos);

            text_Intervalo1.setText(inter[0]+"");
            text_Intervalo2.setText(inter[1]+"");

            peso_actual=Math.round(peso_actual);

            if(peso_actual>=inter[0] && peso_actual<=inter[1]){
                text_peso_actual.setText(R.string.inter_recomendado);
            }else{
                text_peso_actual.setText(R.string.inter_no_recomendado);
            }

             text_Unidad.setText(R.string.lb);

        } else {
            double [] rango=new double[2];

            inter2 = intervalos.split("-");

            rango[0]=Double.parseDouble(inter2[0]);
            rango[1]=Double.parseDouble(inter2[1]);

            text_Intervalo1.setText(inter2[0]);
            text_Intervalo2.setText(inter2[1]);
            text_Unidad.setText(R.string.kg);

            if(peso_actual>=rango[0] &&peso_actual<=rango[1]){
                text_peso_actual.setText(R.string.inter_recomendado);
            }else{
                text_peso_actual.setText(R.string.inter_no_recomendado);
            }
        }


        text_Complexion.setText(complexion);
        text_Dianostico.setText(diagnostico);


    }


    public int[] Conversion(String intervalos) {

        int inter[] = new int[2];
        double intervalo1;
        double intervalo2;

        String[] valores = intervalos.split("-");


        intervalo1 = Double.parseDouble(valores[0]);
        intervalo2 = Double.parseDouble(valores[1]);

        intervalo1 *= 2.204623;
        intervalo2 *= 2.204623;


        inter[0] = (int) Math.round(intervalo1);
        inter[1] = (int) Math.round(intervalo2);


        return inter;

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.info_peso_ideal:
                AlertDialog alerta=new AlertDialog.Builder(this)
                        .setTitle(R.string.peso_ideal2)
                        .setMessage(R.string.peso_ideal)
                        .setPositiveButton(android.R.string.yes,new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })

                        .setNeutralButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }

                        }).create();
                alerta.show();
                break;

            case R.id.info_complexion:
                AlertDialog alerta2=new AlertDialog.Builder(this)
                        .setTitle(R.string.Complexion)
                        .setMessage(R.string.info_complexion)
                        .setPositiveButton(android.R.string.yes,new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })

                        .setNeutralButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }

                        }).create();
                alerta2.show();

                break;

            case R.id.info_IMC:
                AlertDialog alerta3=new AlertDialog.Builder(this)
                        .setTitle(R.string.IMC)
                        .setMessage(R.string.info_imc)
                        .setPositiveButton(android.R.string.yes,new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })

                        .setNeutralButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }

                        }).create();
                alerta3.show();

                break;

            default:
                break;
        }

    }
}
