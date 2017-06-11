package com.example.randydd.peso_ideal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{


    private RadioGroup SexoGroup;
    private RadioGroup UnidadMedida;
    private EditText Peso;
    private  EditText Altura;
    private  TextView TextoPeso;
    private TextView TextoAltura;
    private Spinner complexion;
    private ImageButton Ayuda;
    private EditText CircunferenciaMuneca;
    private Button verificar;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SexoGroup=(RadioGroup)findViewById(R.id.genero);

        UnidadMedida=(RadioGroup)findViewById(R.id.unidades);

        Peso=(EditText) findViewById(R.id.Peso);

        Altura=(EditText) findViewById(R.id.Altura);

        TextoPeso=(TextView)findViewById(R.id.TextPeso);

        TextoAltura=(TextView)findViewById(R.id.TestAltura);

        complexion=(Spinner)findViewById(R.id.complexion);
        LlenadoSpiner();


        Ayuda=(ImageButton)findViewById(R.id.Ayuda);

        CircunferenciaMuneca=(EditText)findViewById(R.id.CircufrenciaMuneca);

        verificar=(Button)findViewById(R.id.verificar);
        verificar.setOnClickListener(this);
        Ayuda.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

           switch (v.getId()){

               case R.id.verificar:
                      Comprobar_Datos();
                   break;

               case R.id.Ayuda:
                        Ayuda();
                   break;
           }




    }


    public  void LlenadoSpiner(){
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.complexion, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        complexion.setAdapter(adapter);

    }




    public  void Comprobar_Datos(){

        RadioButton sexo = (RadioButton) findViewById(SexoGroup.getCheckedRadioButtonId());
        RadioButton unidades=(RadioButton)findViewById(UnidadMedida.getCheckedRadioButtonId());


        if(unidades!=null || sexo!=null || Peso.getText().toString().length()>0 || Altura.getText().toString().length()>0 ||
                complexion.getSelectedItem().toString().length()>0){

            String Sexo=sexo.getText().toString();
            String Unidades=unidades.getText().toString();
            double peso=Double.parseDouble(Peso.getText().toString());
            double altura=Double.parseDouble(Altura.getText().toString());
            String Tcomplexion=complexion.getSelectedItem().toString();
            // double circuferencia=Double.parseDouble(CircunferenciaMuneca.getText().toString());

            Toast.makeText(MainActivity.this,"Esta toso bien",Toast.LENGTH_SHORT).show();


        }else{
            Toast.makeText(MainActivity.this,R.string.mensaje_data_vacio,Toast.LENGTH_SHORT).show();
        }


    }


    public  void Ayuda(){
        Toast.makeText(MainActivity.this,"Necesitas ayuda",Toast.LENGTH_SHORT).show();
        complexion.setEnabled(false);
        CircunferenciaMuneca.setVisibility(View.VISIBLE);



    }





}
