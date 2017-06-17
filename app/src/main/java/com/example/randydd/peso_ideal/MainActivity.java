package com.example.randydd.peso_ideal;

import android.content.DialogInterface;
import android.content.Intent;
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
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

   //Declaraciones de Objetos*/
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

        /*Componentes*/
        SexoGroup=(RadioGroup)findViewById(R.id.genero);

        UnidadMedida=(RadioGroup)findViewById(R.id.unidades);

        Peso=(EditText) findViewById(R.id.Peso);

        Altura=(EditText) findViewById(R.id.Altura);

        TextoPeso=(TextView)findViewById(R.id.TextPeso);

        TextoAltura=(TextView)findViewById(R.id.TextAltura);

        TextoPulgada=(TextView)findViewById(R.id.TextPulgada);

        complexion=(Spinner)findViewById(R.id.complexion);
        pulgadas=(Spinner)findViewById(R.id.pulgada);

        Ayuda=(ImageButton)findViewById(R.id.Ayuda);

        CircunferenciaMuneca=(EditText)findViewById(R.id.CircufrenciaMuneca);

        verificar=(Button)findViewById(R.id.verificar);

        //Llenardo combobox*/
        LlenadoSpiner();

        //Asignado listener
        verificar.setOnClickListener(this);
        Ayuda.setOnClickListener(this);


        //Verificacion de las unidades selecionadas*/

        UnidadMedida.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {

                if(checkedId==R.id.unidad_mediad_1){
                    //Accion que ocurre si selecionamos KG/CM
                    TextoPeso.setText("KG");
                    TextoAltura.setText("CM");

                    Peso.setFilters(new InputFilter[]{new InputFilter.LengthFilter(3)});
                    Altura.setFilters(new InputFilter[]{new InputFilter.LengthFilter(3)});

                    pulgadas.setVisibility(View.GONE);
                    TextoPulgada.setVisibility(View.GONE);
                }else{

                    //Accion que ocurre si seleccionamos LB/FT
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

        //Verificacion del componente al cual se le ha hecho click
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

        //LLenado del Spinner con las complexiones
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.complexion, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        complexion.setAdapter(adapter);


        //Llenado del Spinner con las pulgadas de los pies
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.pulgadas, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pulgadas.setAdapter(adapter2);





    }




    public  void Comprobar_Datos(){
        //Declaracion de variables para comprobar datos
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
        int Circuenferencia=0;

            //Verificando si los campos estan llenos con los datos necesarios
        if(unidades!=null && sexo!=null && Peso.getText().length()>0 && Altura.getText().length()>0){

            String Sexo=sexo.getText().toString();

            //Verificamos las unidades de medidas que ha seleccionado el usuario
            if(UnidadMedida.getCheckedRadioButtonId()==R.id.unidad_medida_2){

                //Parseamos los repectivos campos
                pies=Double.parseDouble(Altura.getText().toString());
                pulgada=Double.parseDouble(pulgadas.getSelectedItem().toString());
                libras=Double.parseDouble(Peso.getText().toString());

                informacion[0]=true;


            }else{
                //Parseamos los repectivos campos
                centimetro=Double.parseDouble(Altura.getText().toString());
                kilogramos=Double.parseDouble(Peso.getText().toString());

                informacion[0]=false;
            }

            //Verificamos si el spinner complexion esta activo lo que significa que el usuario se sabe la complexion
            if(complexion.isEnabled()){
                NombreComplexion=complexion.getSelectedItem().toString();
                informacion[1]=true;
            }else{
                //Verificamos si el campo de texto complexion tiene texto, Lo que significa que se va a calcular la complexion
                if(CircunferenciaMuneca.getText().length()>0){
                    Circuenferencia=Integer.parseInt(CircunferenciaMuneca.getText().toString());
                    informacion[1]=false;

                }else{
                    //Si intentamos mandar la informacion sin completar el campo complexion
                     NoBascio=true;
                }

            }

            if (NoBascio){

                //Mensaje para que se introduzcan todos los datos*/
                Toast.makeText(MainActivity.this,R.string.mensaje_data_vacio,Toast.LENGTH_SHORT).show();

            }else{

                /*Aqui vemos cuales opciones el usuario tiene marcada como si se sabe la complexion o hay que calcularla,
                * o si selecionó Libra o Kilogramos y llamamos a la Sobrecarga del metodo correspondiente
                * */

                if(informacion[0]==true && informacion[1]==true){

                        TrabajarDatos(pies,pulgada,libras,NombreComplexion,Sexo);

                }

                if(informacion[0]==true && informacion[1]==false){

                    if(Circuenferencia==0) {
                        Toast.makeText(MainActivity.this, "La circunferencia debe ser mayor a 0", Toast.LENGTH_SHORT).show();
                    }else{
                        TrabajarDatos(pies,pulgada,libras,Circuenferencia,Sexo);
                    }

                }


                if(informacion[0]==false && informacion[1]==false){
                    if(Circuenferencia==0) {
                        Toast.makeText(MainActivity.this, "La circunferencia debe ser mayor a 0", Toast.LENGTH_SHORT).show();
                    }else{

                        TrabajarDatos(kilogramos,centimetro,Circuenferencia,Sexo);
                    }


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
        //Alerta para mostrar la ayuda para saber la complexion
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

        String Intervalos;
        String diagnostico;
        String verificar;

        //Convertimos los pies y la pulgadas a metros para proceder a calcular
        double metros=Double.parseDouble(convertir(pies,pulgadas));

        //Verificamos si los metros son los minimos que estan en la tabla de la complexion
        verificar=Verificar_data(metros);

        if(verificar.length()>2){

            Toast.makeText(MainActivity.this,verificar,Toast.LENGTH_LONG).show();

        }else{

            //LLamamos al metodo LeerTxt que es donde se busca los intervalos
            Intervalos=leerTxt(metros,NombreComplexion,sexo);

            //Verificamos el IMC(Indice de masa corporal)
            diagnostico=indice_masa_coproral(libras,metros);

            //Hacemos Una instacia de un Intent para luego llamar a mostar resultado
            Intent mostrar_data= new Intent(MainActivity.this,Mostrar_Resultados.class);

            //Pasamos algunos datos importante al activity Mostar_resultados
            mostrar_data.putExtra("intervalos",Intervalos);
            mostrar_data.putExtra("diagnostico",diagnostico);
            mostrar_data.putExtra("complexion",NombreComplexion);
            mostrar_data.putExtra("unidad",1);
            mostrar_data.putExtra("peso",libras);

            //Iniciamos el activity Mostrar_resultado
            startActivity(mostrar_data);
        }



    }

    public  void TrabajarDatos(double pies,double pulgadas,double libras,int Circunferencia,String sexo){
        String NombreComplexion;
        String diagnostico;
        String Intervalos;
        String verificar;

        //Convertimos los pies y la pulgadas a metros para proceder a calcular
        double metros=Double.parseDouble(convertir(pies,pulgadas));

        //Verificamos si los metros son los minimos que estan en la tabla de la complexion
        verificar=Verificar_data(metros);

        if(verificar.length()>2){

            Toast.makeText(MainActivity.this,verificar,Toast.LENGTH_LONG).show();

        }else{
            //Calculamos la complexion segun los datos obtenidos del usuario
            NombreComplexion=SaberCompexion(metros,Circunferencia,sexo);

            //LLamamos al metodo LeerTxt que es donde se busca los intervalos
            Intervalos=leerTxt(metros,NombreComplexion,sexo);

            //Verificamos el IMC(Indice de masa corporal)
            diagnostico=indice_masa_coproral(libras,metros);

            //Hacemos Una instacia de un Intent para luego llamar a mostar resultado
            Intent mostrar_data= new Intent(MainActivity.this,Mostrar_Resultados.class);

            //Pasamos algunos datos importante al activity Mostar_resultados
            mostrar_data.putExtra("intervalos",Intervalos);
            mostrar_data.putExtra("diagnostico",diagnostico);
            mostrar_data.putExtra("complexion",NombreComplexion);
            mostrar_data.putExtra("unidad",1);
            mostrar_data.putExtra("peso",libras);

            //Iniciamos el activity Mostrar_resultado
            startActivity(mostrar_data);
        }

    }


    public  void TrabajarDatos(double kilogramos,double centimetros,int Circunferencia,String sexo){
        String NombreComplexion;
        String diagnostico;
        String Intervalos;
        String verificar;

        //Convertimos los centimetros a metros para proceder a calcular
        double metros=Double.parseDouble(convertir(centimetros));

        //Verificamos si los metros son los minimos que estan en la tabla de la complexion
        verificar=Verificar_data(metros);

        if(verificar.length()>2){
            Toast.makeText(MainActivity.this,verificar,Toast.LENGTH_LONG).show();
        }else {


            //Calculamos la complexion segun los datos obtenidos del usuario
            NombreComplexion = SaberCompexion(metros, Circunferencia, sexo);

            //LLamamos al metodo LeerTxt que es donde se busca los intervalos
            Intervalos = leerTxt(metros, NombreComplexion, sexo);


            //Verificamos el IMC(Indice de masa corporal)
            diagnostico = indice_masa_coproral(kilogramos, metros);

            //Hacemos Una instacia de un Intent para luego llamar a mostar resultado
            Intent mostrar_data = new Intent(MainActivity.this, Mostrar_Resultados.class);

            //Pasamos algunos datos importante al activity Mostar_resultados
            mostrar_data.putExtra("intervalos", Intervalos);
            mostrar_data.putExtra("diagnostico", diagnostico);
            mostrar_data.putExtra("complexion", NombreComplexion);
            mostrar_data.putExtra("unidad", 2);
            mostrar_data.putExtra("peso",kilogramos);

            //Iniciamos el activity Mostrar_resultado
            startActivity(mostrar_data);
        }
    }


    public  void TrabajarDatos(double kilogramos,double centimetros,String NombreComplexion,String sexo){
        String Intervalos;
        String diagnostico;
        String verificar;

        //Convertimos los centimetros a metros para proceder a calcular
        double metros=Double.parseDouble(convertir(centimetros));

        //Verificamos si los metros son los minimos que estan en la tabla de la complexion
         verificar=Verificar_data(metros);

        if(verificar.length()>2){
            Toast.makeText(MainActivity.this,verificar,Toast.LENGTH_LONG).show();
        }else {
            //LLamamos al metodo LeerTxt que es donde se busca los intervalos
            Intervalos = leerTxt(metros, NombreComplexion, sexo);

            //Verificamos el IMC(Indice de masa corporal)
            diagnostico = indice_masa_coproral(kilogramos, metros);


            //Hacemos Una instacia de un Intent para luego llamar a mostar resultado
            Intent mostrar_data = new Intent(MainActivity.this, Mostrar_Resultados.class);

            //Pasamos algunos datos importante al activity Mostar_resultados
            mostrar_data.putExtra("intervalos", Intervalos);
            mostrar_data.putExtra("diagnostico", diagnostico);
            mostrar_data.putExtra("complexion", NombreComplexion);
            mostrar_data.putExtra("unidad", 2);
            mostrar_data.putExtra("peso",kilogramos);

            //Iniciamos el activity Mostrar_resultado
            startActivity(mostrar_data);
        }


    }






    public String  convertir(double pies,double pulgadas ){

        //Convertimos de pies y pulgadas a metros
        pies=pies + (pulgadas * 0.083);
        double metros=(pies*0.3048)+0.033;

        //Pasamos los metros a String y procedesmos a sacar los decimales que necesitamos
        String convertir=Double.toString(metros);

        convertir=convertir.charAt(0) +""+ convertir.charAt(1)+convertir.charAt(2)+convertir.charAt(3);

        return convertir;
    }

    public String convertir(double centimetros)
    {
        //Convertimos de centimetros a metros
        double metros=(centimetros * 0.0100);

        //Covertimos a cadena para prodecer a agregar un decimal faltante
        String convertir=Double.toString(metros);

        if(convertir.length()==3){
            //Agregamos el decimal faltante si la logitud es igual a 3
            convertir=convertir+"0";
        }

        //Sacamos los decimales necesarios para el calculo
        convertir=convertir.charAt(0) +""+ convertir.charAt(1)+convertir.charAt(2)+convertir.charAt(3);

        return convertir;
    }

    public String indice_masa_coproral(double peso,double estatura){
        String diagnostico="";
        float IMC;

        //Verificamos las unidades;
        if(UnidadMedida.getCheckedRadioButtonId()==R.id.unidad_medida_2){
            //Pasamos el peso en libra a kilogramos
            peso=peso*0.453592;
        }

        //Caculamos el IMC segun la formula peso entre estatura al cuadrado
        IMC=(float)((peso/(estatura*estatura)));

        //Verificamos el valor obtenido y su diagnostico
        if(IMC<18.5){
            diagnostico="Peso insuficiente";
        }
        if(IMC>=18.5  && IMC<25){

            diagnostico=" Peso normal (normopeso, el comúnmente conocido como peso ideal)";

        }

        if(IMC>=25 && IMC<27){

             diagnostico="Puede haber sobrepeso grado I";
        }

        if (IMC>=27 && IMC <30){
            diagnostico="Sobrepeso tipo I (preobesidad)";
        }

        if(IMC>=30 && IMC<35){
            diagnostico="Obesidad tipo I (leve)";
        }

        if(IMC>=35 && IMC<40){
            diagnostico="Obesidad tipo II (moderada)";
        }

        if(IMC>=40 && IMC<50){
            diagnostico="Obesidad tipo III (mórbida)";
        }

        if(IMC>50){
            diagnostico="Obesidad extrema";
        }

        return  diagnostico;

    }

    public String SaberCompexion(double metros,int Circunferencia,String sexo){

        //Pasamos de metros a centimetros
        int centiemtros=(int)(metros*100);
        String data="";
        //Calculamos la complexion que es igual a nuestra altura en centimetros divido nuestra circunferencia


        float divicion=centiemtros/Circunferencia;

        //Verificamos el sexo del usuario
        if(SexoGroup.getCheckedRadioButtonId()==R.id.hombre){

            //Procedemos a saber su complexion segun los datos obtenidos
            if(divicion>10.4){
                data="Pequeña";
            }

            if(divicion>9.6 && divicion<=10.4){
                data="Mediana";
            }

            if(divicion<9.6){
                data="Grande";
            }


        }else{

             if(divicion>11){
                 data="Pequeña";
             }

             if (divicion>=10 && divicion<=11){
                 data="Mediana";
             }

             if (divicion<10){
                 data="Grande";
             }

        }


        return data;


    }



    public String leerTxt(double estatura, String NombreComplexion,String sexo){

        String data="";
        String peque="";
        String media="";
        String grand="";
        InputStream buffer;
        try{

            //Leemos el archico que corresponde

            if(SexoGroup.getCheckedRadioButtonId()==R.id.hombre){
                buffer=this.getResources().openRawResource(R.raw.complexion_hombre);
            }else{
                buffer=this.getResources().openRawResource(R.raw.complexion_mujer);
            }

            BufferedReader bf=new BufferedReader(new InputStreamReader(buffer));
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
                    double nAltura=Double.parseDouble(altura);
                    //Preguntamos si es la altura que andamos buscandp

                    if(nAltura==estatura){
                        //Buscando la posicion de la complexion pequeña

                            int pequeno = texto.indexOf(";");
                            int pequeno2 = texto.indexOf(";", pequeno + 1);
                            peque = texto.substring(pequeno + 1, pequeno2);

                           // String[] interpeque = peque.split("-");



                        //Buscado la posicion de la complexion mediana

                        int mediano=texto.indexOf(";",pequeno2+1);
                        int mediano2=texto.indexOf(";",mediano+1);
                        media=texto.substring(mediano+1,mediano2);






                        //Buscando la posicion de la complexion grande

                        int grande=texto.indexOf(";",mediano2+1);
                        int grande2=texto.indexOf(";",grande+1);
                        grand=texto.substring(grande+1,grande2);


                        char h=NombreComplexion.charAt(0);

                        //tomaos los intervalos de la complexion y lo pasamos a la variable data que luego retornamos
                        if(h=='P'){
                            data=data+peque;
                        }

                        if(h=='M'){
                            data=data+media;
                        }

                        if(h=='G'){
                           data=data+grand;
                        }

                     break;
                    }



                }

                //Varables Que cuenta el numeros de lienas
                i++;

            }

        }catch(Exception e){

        }

        return data;

    }






    public  String Verificar_data(double metros){
        String verificacion="";

        //Metedo para verificar que los metros sean en el rango en el cual estan en la tabla
           if(SexoGroup.getCheckedRadioButtonId()==R.id.hombre){

                  if(metros<1.50){
                      verificacion="Altura muy pequeña  para ser calculada, diríjase donde un nutriólogo.";
                  }

                  if(metros>1.94){
                        verificacion="Altura muy grande para ser calculada, diríjase donde un nutriólogo.";
                  }


           }else{
               if(metros<1.42){
                   verificacion="Altura muy pequeña  para ser calculada, diríjase donde un nutriólogo.";
               }

               if(metros>1.94){
                   verificacion="Altura muy grande para ser calculada, diríjase donde un nutriólogo.";
               }

           }

           return  verificacion;


    }




}



