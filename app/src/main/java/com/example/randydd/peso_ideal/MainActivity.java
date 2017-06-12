package com.example.randydd.peso_ideal;

import android.content.DialogInterface;
import android.support.annotation.IdRes;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
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

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{


    private RadioGroup SexoGroup;
    private RadioGroup UnidadMedida;
    private EditText Peso;
    private  EditText Altura;
    private Spinner pulgadas;
    private  TextView TextoPeso;
    private TextView TextoAltura;
    private TextView TextoPulgada;
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

        TextoAltura=(TextView)findViewById(R.id.TextAltura);

        TextoPulgada=(TextView)findViewById(R.id.TextPulgada);

        complexion=(Spinner)findViewById(R.id.complexion);
        pulgadas=(Spinner)findViewById(R.id.pulgada);
        LlenadoSpiner();


        Ayuda=(ImageButton)findViewById(R.id.Ayuda);

        CircunferenciaMuneca=(EditText)findViewById(R.id.CircufrenciaMuneca);

        verificar=(Button)findViewById(R.id.verificar);
        verificar.setOnClickListener(this);
        Ayuda.setOnClickListener(this);


        UnidadMedida.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {

                if(checkedId==R.id.unidad_mediad_1){
                    TextoPeso.setText("KG");
                    TextoAltura.setText("CM");

                    Peso.setFilters(new InputFilter[]{new InputFilter.LengthFilter(3)});
                    Altura.setFilters(new InputFilter[]{new InputFilter.LengthFilter(3)});

                    pulgadas.setVisibility(View.GONE);
                    TextoPulgada.setVisibility(View.GONE);
                }else{

                    Peso.setFilters(new InputFilter[]{new InputFilter.LengthFilter(3)});
                    Altura.setFilters(new InputFilter[]{new InputFilter.LengthFilter(1)});
                   ///pulgadas.setFilters(new InputFilter[]{new InputFilter.LengthFilter(2)});

                    TextoPeso.setText("LB");
                    TextoAltura.setText("FT");
                    TextoPulgada.setText("IN");

                    pulgadas.setVisibility(View.VISIBLE);
                    TextoPulgada.setVisibility(View.VISIBLE);


                }

            }
        });

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


        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.pulgadas, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pulgadas.setAdapter(adapter2);





    }




    public  void Comprobar_Datos(){

        RadioButton sexo = (RadioButton) findViewById(SexoGroup.getCheckedRadioButtonId());
        RadioButton unidades=(RadioButton)findViewById(UnidadMedida.getCheckedRadioButtonId());
        boolean NoBascio=false;

        boolean informacion[]=new boolean[3];

        double pies=0;
        double libras=0;
        double pulgada=0;

        double centimetro=0;
        double kilogramos=0;

        String NombreComplexion="";
        double Circuenferencia=0;


        if(unidades!=null && sexo!=null && Peso.getText().length()>0 && Altura.getText().length()>0){

            String Sexo=sexo.getText().toString();
            String Unidades=unidades.getText().toString();

            if(UnidadMedida.getCheckedRadioButtonId()==R.id.unidad_medida_2){

                pies=Double.parseDouble(Altura.getText().toString());
                pulgada=Double.parseDouble(pulgadas.getSelectedItem().toString());
                libras=Double.parseDouble(Peso.getText().toString());

                informacion[0]=true;


            }else{
                centimetro=Double.parseDouble(Altura.getText().toString());
                kilogramos=Double.parseDouble(Peso.getText().toString());

                informacion[0]=false;
            }

            if(complexion.isEnabled()){
                NombreComplexion=complexion.getSelectedItem().toString();
                informacion[1]=true;
            }else{
                if(CircunferenciaMuneca.getText().length()>0){
                    Circuenferencia=Double.parseDouble(CircunferenciaMuneca.getText().toString());
                    informacion[1]=false;

                }else{
                     NoBascio=true;
                }

            }

            if (NoBascio){
                Toast.makeText(MainActivity.this,R.string.mensaje_data_vacio,Toast.LENGTH_SHORT).show();

            }else{
                Toast.makeText(MainActivity.this,"Esta Todo bien",Toast.LENGTH_SHORT).show();



                if(informacion[0]==true && informacion[1]==true){

                    TrabajarDatos(pies,pulgada,libras,NombreComplexion,Sexo);

                }

                if(informacion[0]==true && informacion[1]==false){
                    TrabajarDatos(pies,pulgada,libras,Circuenferencia,Sexo);
                }


                if(informacion[0]==false && informacion[1]==false){

                    TrabajarDatos(kilogramos,centimetro,Circuenferencia,Sexo);

                }

                if(informacion[0]==false && informacion[1]==true){

                    TrabajarDatos(kilogramos,centimetro,NombreComplexion,Sexo);
                }
            }


        }else{
            Toast.makeText(MainActivity.this,R.string.mensaje_data_vacio,Toast.LENGTH_SHORT).show();
        }






    }


    public  void Ayuda(){


        AlertDialog alerta=new AlertDialog.Builder(this)
                .setTitle(R.string.title)
                .setMessage(R.string.message)
                .setPositiveButton(android.R.string.yes,new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        complexion.setEnabled(false);
                        CircunferenciaMuneca.setVisibility(View.VISIBLE);
                        findViewById(R.id.TextoCM).setVisibility(View.VISIBLE);
                        CircunferenciaMuneca.requestFocus();
                        Toast.makeText(MainActivity.this,R.string.Yes,Toast.LENGTH_LONG).show();
                    }

                })

                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this,R.string.NO,Toast.LENGTH_LONG).show();

                    }
                })

                .setNeutralButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }

                }).create();

        alerta.show();

    }



    public  void TrabajarDatos(double pies,double pulgadas,double libras,String NombreComplexion,String sexo){

        pies=pies + (pulgadas * 0.083);
        double metros=(pies*0.3048)+0.033;

        String convertir=Double.toString(metros);

        convertir=convertir.charAt(0) +""+ convertir.charAt(1)+convertir.charAt(2)+convertir.charAt(3);

        metros=Double.parseDouble(convertir);

        ///convertir=toString(convertir.charAt(1)+convertir.charAt(2)+convertir.charAt(3)+convertir.charAt(4)+convertir.charAt(5));

        Toast.makeText(MainActivity.this,metros+"",Toast.LENGTH_SHORT).show();

    }

    public  void TrabajarDatos(double pies,double pulgadas,double libras,double Circunferencia,String sexo){

    }


    public  void TrabajarDatos(double kilogramos,double centimetros,double Circunferencia,String sexo){


    }


    public  void TrabajarDatos(double kilogramos,double centimetros,String NombreComplexion,String sexo){

    }







    public  String Buscar_complexion(double cirferencia,double altura){


     return "hola";

    }

    /*Seguir aqui*/

    public void  leerTxt(String  estatura,String complexion,String sexo){

        try{
            //Leemos el archico que corresponde
            InputStream is=this.getResources().openRawResource(R.raw.complexion_hombre);
            BufferedReader bf=new BufferedReader(new InputStreamReader(is));
            String altura="";
            String texto;
            String bfRead;
            int i=0;

            //Etramos al ciclo para recorrer los registro del archivo
            while ((bfRead=bf.readLine())!=null) {

                //Recorremos despues de la linea numero 0
                if(i>0){
                    //Lepasamos la liena leida a la variable texto
                    texto=bfRead;

                    //Sacamos la altura de la linea que estamos
                    altura=bfRead.substring(0,4);

                    //Parseamos la altura
                    double n=Double.parseDouble(altura);

                    //Preguntamos si es la altura que andamos buscandp

                    if(n==1.94){
                        //Buscando la posicion de la complexion pequeña
                        int pequeno=texto.indexOf(";");
                        int pequeno2=texto.indexOf(";",pequeno+1);
                        String peque=texto.substring(pequeno+1,pequeno2);

                        String [] interpeque=peque.split("-");

                        System.out.println("Intervalo pequeño: "+interpeque[0] +"  "+ interpeque[1]);

                        //Buscado la posicion de la complexion mediana

                        int mediano=texto.indexOf(";",pequeno2+1);
                        int mediano2=texto.indexOf(";",mediano+1);
                        String media=texto.substring(mediano+1,mediano2);

                        String[]intermediano=media.split("-");

                        System.out.println("Intervalo mediano: "+ intermediano[0]+" "+intermediano[1]);



                        //Buscando la posicion de la complexion grande

                        int grande=texto.indexOf(";",mediano2+1);
                        int grande2=texto.indexOf(";",grande+1);
                        String grand=texto.substring(grande+1,grande2);

                        String[] intergrande=grand.split("-");

                        System.out.println("Intervalo Grande: "+ intergrande[0]+ " "+intergrande[1]);

                        double probar=Double.parseDouble(intergrande[0]);

                        break;
                    }



                }

                //Varables Que cuenta el numeros de lienas
                i++;

            }

        }catch(Exception e){
            System.out.println("No se encontro el archivo: " + e.getMessage());
        }



    }



}



