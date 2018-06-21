package com.dragdrop_demo;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by k.key on 22/04/2018.
 */

public class DataHolder {


    private static List<Zpago> _ArrayList_factura=null;
    private static String data_searched=null;
    private static String type_table="";
    private static String data_table="";
    private static String data_ciclo="";
    private static boolean getStatusCiclos=false;
    private static List<String> Zciclos;
    private static Activity activity;
    private static String data_dates="";



    /// for register
    private static String registerGen="";

    public static String getData_ciclo()
    {
        return data_ciclo;
    }

    public static void setData_ciclo(String data_ciclo)
    {
        DataHolder.data_ciclo = data_ciclo;
    }
    public static String getData()
    {
        return data_searched;
    }
    public static void setData(String data)
    {
        DataHolder.data_searched = data;
    }
    public static List<Zpago> get_ArrayList_factura()
    {
        DB_simple_operations operator_db=new  DB_simple_operations(activity);
        DataHolder.set_ArrayList_factura(operator_db.put_list_data_pagos());
        return _ArrayList_factura;
    }
    public static void set_ArrayList_factura(List<Zpago> _ArrayList_factura)
    {
        DataHolder._ArrayList_factura = _ArrayList_factura;
    }
    public static String getData_searched()
    {
        return data_searched;

    }

    public static void setData_searched(String data_searched) {
        DataHolder.data_searched = data_searched;
    }

    public static String getData_table() {
        return data_table;
    }

    public static void savePagos(String type_table,String ciclo,String data_table)
    {
        DataHolder.type_table=type_table;
        DataHolder.data_table = data_table;
        DB_simple_operations operator_db=new  DB_simple_operations(activity);
        String fecFacturainit=data_table.substring(50);

        if (type_table.equals("GS1") && data_table.length()>50 && checkdatevsreadlecture(getData_dates(),fecFacturainit) )
        {
            System.out.println("Debug lectura GS1"+data_table);
            String numeroFactura=data_table.substring(20,32);
            String valFactura=data_table.substring(36,48);
            String fecFactura=data_table.substring(50);
            System.out.println("Debug duplicate"+SearchDucplicate(numeroFactura,numeroFactura,"ZPagos"));
            if (!SearchDucplicate(numeroFactura,"numeroFactura","ZPagos"))
            {
                int id=operator_db.get_last_index("Zpagos","id"); //it plu
                // db.execSQL("Create table Zpagos(id int primary key, numeroFactura text, valor text , fecha text,ciclo text,tipo text, fechaRegistro text, usuario text)");
                 ContentValues contenedor=new ContentValues();
                //for table Ciclos
                contenedor.put("id",id);
                contenedor.put("numeroFactura",numeroFactura);
                contenedor.put("valor",valFactura);
                contenedor.put("fecha",fecFactura);
                contenedor.put("ciclo",ciclo);
                contenedor.put("tipo",type_table);
                contenedor.put("fechaRegistro", String.valueOf(Calendar.getInstance().getTime()));
                operator_db.add_row_table_db(contenedor,"Zpagos");
            }
            else
            {

                    Toast.makeText(activity, "Numero de factura duplicado", Toast.LENGTH_SHORT).show();

            }

        }
        else if( type_table.equals("Interno o Factura") && data_table.length()<13)
        {
            System.out.println("Debug lectura"+data_table);
            String numeroFactura=data_table;

            System.out.println("Debug duplicate"+SearchDucplicate(numeroFactura,numeroFactura,"ZPagos"));
            if (!SearchDucplicate(numeroFactura,"numeroFactura","ZPagos"))
            {
                int id=operator_db.get_last_index("Zpagos","id"); //it plu
                // db.execSQL("Create table Zpagos(id int primary key, numeroFactura text, valor text , fecha text,ciclo text, fechaRegistro text, usuario text)");
                ContentValues contenedor=new ContentValues();
                //for table Ciclos
                contenedor.put("id",id);
                contenedor.put("numeroFactura",numeroFactura);
                contenedor.put("valor","0");
                contenedor.put("fecha","");
                contenedor.put("ciclo",ciclo);
                contenedor.put("tipo",type_table);
                contenedor.put("fechaRegistro", String.valueOf(Calendar.getInstance().getTime()));
                operator_db.add_row_table_db(contenedor,"Zpagos");
            }
            else
            {
                Toast.makeText(activity, "Numero de factura duplicado", Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
            System.out.println("Debug lectura"+data_table);
            System.out.println("Debug lectura invalida");
            if ( !checkdatevsreadlecture(getData_dates(),fecFacturainit))
            {
                Toast.makeText(activity, "la fecha de la lectura es posterior a la fecha del lote", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(activity, "lectura invalida con el tipo seleccionado", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public static String getType_table()
    {
        return type_table;
    }

    public static void setType_table(String type_table)
    {
        DataHolder.type_table = type_table;
    }

    public static boolean isGetStatusCiclos()
    {

        return getStatusCiclos;
    }

    public static void setGetStatusCiclos(boolean getStatusCiclos) {
        DataHolder.getStatusCiclos = getStatusCiclos;
    }

    public static List<String> getZciclos()
    {
        return Zciclos;
    }

    public static void setZciclos(List<String> zciclos)
    {
        Zciclos = zciclos;
    }

    public static Activity getActivity()
    {
        return activity;
    }

    public static void setActivity(Activity activity)
    {
        DataHolder.activity = activity;
    }


    public static Boolean SearchDucplicate(String dato,String column,String tabla)
    {
        DB_simple_operations operator_db=new  DB_simple_operations(activity);
        Boolean answer=operator_db.columnDucplicateBool(dato,column,tabla);
        return answer;
    }

    public static String info_report()
    {
        //get data
        List<Zpago> data=get_ArrayList_factura();
        int total_val=0;
        String dataResult="";
        dataResult=""+data.size()+" ,";
        for (int i=0;i<data.size();i++)
        {
            total_val=total_val+Integer.parseInt(data.get(i).getValor());
            System.out.println("debug total"+total_val);
        }
        dataResult=dataResult+""+total_val+" \n";
        for (int i=0;i<data.size();i++)
        {
            dataResult=dataResult+""+data.get(i).toStringReport();
        }
        System.out.println("debug data report"+dataResult);
        return dataResult;
    }
    public static void generate_report()
    {
        try {
            File nuevaCarpeta = new File(Environment.getExternalStorageDirectory(), "Zpagos");
            if (!nuevaCarpeta.exists()) {
                nuevaCarpeta.mkdir();
            }
            try {

                Calendar c = Calendar.getInstance();
                String name="Z"+c.get(Calendar.YEAR)+c.get(Calendar.MONTH)+c.get(Calendar.DAY_OF_MONTH)+"_"+c.get(Calendar.HOUR)+c.get(Calendar.MINUTE)+c.get(Calendar.SECOND);
                System.out.println(name);
                File file = new File(nuevaCarpeta, name+ ".txt");
                file.createNewFile();
                FileWriter writer = new FileWriter(file);
                writer.append(info_report());
                writer.flush();
                writer.close();
            } catch (Exception ex) {
                Log.e("Error", "ex create file : " + ex);
                Toast.makeText(activity, "Sin permisos, debe habilitar los permisos de Escritura", Toast.LENGTH_SHORT).show();
            }
        }
        catch (Exception e)
        {
            Log.e("Error", "e folder: " + e);
        }
    }


    //for debug

    public static int returnIndex()
    {
        DB_simple_operations operator_db=new  DB_simple_operations(activity);
        int x=operator_db.get_last_index("ZPagos","id");
        return x;
    }

    public static void mostrar_tabla(String Table)
    {
        DB_simple_operations operator_db=new  DB_simple_operations(activity);
        operator_db.mostrar_tabla(Table);
    }

    public static void clean_data()
    {
        DB_simple_operations operator_db=new  DB_simple_operations(activity);
        operator_db.clean_data("Zpagos");
        Toast.makeText(activity, "información de pagos borrada", Toast.LENGTH_SHORT).show();
    }


    //process for registration and licence
    public static void register()
    {
        if (CheckLicenceState()== false)
        {
            String myIMEI = Build.SERIAL;
            System.out.println(myIMEI);
            Blowfish.setPlainText(myIMEI);
            registerGen=Blowfish.getblow().substring(1,18);
            String pass=MD5.getMD5(registerGen).substring(0,6);
            pass=pass.substring(1,2)+pass.substring(3,4)+pass.substring(5,6)+pass.substring(0,1)+pass.substring(2,3)+pass.substring(4,5);
            System.out.println(registerGen+"-"+pass);
            final EditText input = new EditText(activity.getApplicationContext());
            // Use the Builder class for convenient dialog construction
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

            builder.setMessage("Realice el registro de la app...")
                    .setPositiveButton("Acepto", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id)
                        {
                            // FIRE ZE MISSILES!

                            licence(input.getText().toString());

                        }
                    })
                    .setNegativeButton("Cancelo", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User cancelled the dialog
                        }
                    });
            // Create the AlertDialog object and return it
            builder.setIcon(android.R.drawable.ic_dialog_alert);


            input.setText(registerGen);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);
            input.setLayoutParams(lp);

            builder.setView(input); // uncomment this line
            builder.setCancelable(false);
            builder.show();
        }
        else
        {
            System.out.println("debug already licence");
        }


    }

    public static void licence(String data)
    {
        String myIMEI = Build.SERIAL;
        System.out.println(myIMEI);
        Blowfish.setPlainText(myIMEI);
        registerGen=Blowfish.getblow().substring(1,18);

        String pass=MD5.getMD5(registerGen).substring(0,6);
        pass=pass.substring(1,2)+pass.substring(3,4)+pass.substring(5,6)+pass.substring(0,1)+pass.substring(2,3)+pass.substring(4,5);

        String token=data.replace(registerGen,"");
        if (token.length()==6 && token.equals(pass))
        {
            saveLicenceState(token,registerGen);
            AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
            alertDialog.setTitle("Completo");
            alertDialog.setMessage("Registro completo de forma exitosa");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.setCancelable(false);
            alertDialog.show();
        }
        else
        {
            AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
            alertDialog.setTitle("Error");
            alertDialog.setMessage("EL token dado es incorrecto o se ha errado en el token inicial");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            register();
                        }
                    });
            alertDialog.setCancelable(false);
            alertDialog.show();
        }
        System.out.println("licence: "+token);

    }

    private static String getImeiNumber()
    {
        final TelephonyManager telephonyManager= (TelephonyManager) activity.getSystemService(Context.TELEPHONY_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //getDeviceId() is Deprecated so for android O we can use getImei() method
            return telephonyManager.getImei();
        }
        else {
            return telephonyManager.getDeviceId();
        }

    }

    //save status licenced
    public static void saveLicenceState(String token,String pass)
    {
        DB_simple_operations operator_db=new  DB_simple_operations(activity);
        int id=operator_db.get_last_index("Zregister","id"); //it plu

        ContentValues contenedor=new ContentValues();
        //for table Ciclos
        contenedor.put("id",id);
        contenedor.put("token",token);
        contenedor.put("pass",pass);
        contenedor.put("fechaRegistro", String.valueOf(Calendar.getInstance().getTime()));
        operator_db.add_row_table_db(contenedor,"Zregister");

    }

    //save status licenced
    public static boolean CheckLicenceState()
    {
        DB_simple_operations operator_db=new  DB_simple_operations(activity);
        // db.execSQL("Create table Zregister(id int primary key, token text, pass text, usuario text,fechaRegistro text)");
        List<String> data=operator_db.put_list_data_licence();
        if (data==null || data.size()==0){return false;}

        String myIMEI = Build.SERIAL;
        System.out.println(myIMEI);
        Blowfish.setPlainText(myIMEI);
        registerGen=Blowfish.getblow().substring(1,18);

        String pass=MD5.getMD5(registerGen).substring(0,6);
        pass=pass.substring(1,2)+pass.substring(3,4)+pass.substring(5,6)+pass.substring(0,1)+pass.substring(2,3)+pass.substring(4,5);
        if (data.get(2).equals(registerGen) && data.get(1).equals(pass))
        {
            return true ;
        }
        return false;
    }

    public static String getData_dates() {
        return data_dates;
    }

    public static void setData_dates(String data_dates) {
        DataHolder.data_dates = data_dates;
    }

    public static Boolean checkdatevsreadlecture(String lecture, String init)
    {
        String  day =lecture.substring(3,5);
        String  month=lecture.substring(0,2);
        String  year=lecture.substring(6,8);

        String  dayi =init.substring(6,8);
        String  monthi=init.substring(4,6);
        String  yeari=init.substring(2,4);
        //System.out.println("debug : "+dayi+"@"+monthi+"@"+yeari);
        System.out.println("debug chekdate: "+day+"@"+month+"@"+year);
        int b=(Integer.parseInt(dayi)*1)+Integer.parseInt(monthi)*30+Integer.parseInt(yeari)*365;
        int a=(Integer.parseInt(day)*1)+Integer.parseInt(month)*30+Integer.parseInt(year)*365;
        if (a>b)
        {
            return true;
        }
        else
        {
            return false;
        }


    }
}