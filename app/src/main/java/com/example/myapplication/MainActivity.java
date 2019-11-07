package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.FileOutputStream;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class MainActivity extends AppCompatActivity {
    TextView desplieguedelfitxer;
    Button btnCifraje;
    TextView fitxernocifradotxt;

    private static final String informexml = "informexmll.xml";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);//layout

        btnCifraje = findViewById(R.id.fitxercifradoo);
        desplieguedelfitxer = findViewById(R.id.desplieguedelfitxer);
        fitxernocifradotxt = findViewById(R.id.fitxernocifradoUsertxt);

        btnCifraje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String a = fitxernocifradotxt.getText().toString();
                String fixtercifrado = encriptarfrase(a);
                String fitxernocifrado = fitxernocifradotxt.getText().toString();
                crearfichero(fitxernocifrado,fixtercifrado);
                desplieguedelfitxer.setText(fixtercifrado);
            }
        });

    }

    private void crearfichero (String fixtercifrado,String fitxernocifrado) {
        String context = infomexml(fixtercifrado, fitxernocifrado);

        FileOutputStream fichero;

        try {
            fichero = openFileOutput(informexml,MODE_PRIVATE);
            fichero.write(context.getBytes());
            fichero.close();

        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("s'escriga a dintre el text en el mensaje de la excepción");
        }
    }

    private String timeactual() {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String strDate = formatter.format(date);
        System.out.println(strDate);
        return strDate;
    }

    private String encriptarfrase(String fitxernocifradotxt) {
        String encode_text = " ";

        try {

            RSA rsa = new RSA();

            //le asignamos el Contexto
            rsa.setContext(getBaseContext());

            //Generamos un juego de claves
            rsa.genKeyPair(1024);

            //Guardamos en la memoria las claves
            rsa.saveToDiskPrivateKey("rsa.pri");
            rsa.saveToDiskPublicKey("rsa.pub");

            //Ciframos
            encode_text = rsa.Encrypt(fitxernocifradotxt);

        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        }
        return encode_text;
    }

    private String desencriptarfrase (String fixtercifrado) {
        String encode_text = " ";

        try {

            RSA rsa2 = new RSA();

            //le asignamos el Contexto
            rsa2.setContext(getBaseContext());

            //Generamos un juego de claves
            rsa2.genKeyPair(1024);



            //Desciframos

            String decode_text = rsa2.Encrypt(encode_text);

            //Mostramos el texto ya descifrado
            desplieguedelfitxer.setText(decode_text);
        } catch (Exception e) {

        }
        return fixtercifrado;
    }

    public String infomexml(String fixtercifrado, String fitxernocifrado) {
        String informexml = "";
        informexml = informexml + "<?xml version= \"1.0\" encoding=\"UTF-8\"?>";
        informexml = informexml + "<content_file>";
        informexml = informexml + "    <data>";
        informexml = informexml + "        <time>" + timeactual() + "</time>";
        informexml = informexml + "        <text>" + fitxernocifrado + "</text>";
        informexml = informexml + "        <cipher_text>" + fixtercifrado + "</cipher_text>";
        informexml = informexml + "    </data>";
        informexml = informexml + "</content_file>";
        return informexml;
    }
}