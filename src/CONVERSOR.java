import okhttp3.*;
import org.json.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class CONVERSOR {

    public static void main(String[] args) {

        JFrame f=new JFrame("CONVERSOR DE DIVISAS");
        JToggleButton b = new JToggleButton("Intercambiar");
        //b.setIcon(new ImageIcon( "replace.png"));
        b.setBounds(50,200,150,50);

        JTextField t1,t2;
        t1=new JTextField("0.00");
        t1.setBounds(50,100, 200,30);
        t2=new JTextField("0.00");
        t2.setBounds(50,150, 200,30);
        t2.setBackground(Color.white);

        String[] monedas_extr = { "USD","EUR","JPY","GBP","KRW"};
        String[] moneda_local = { "COP"};

        JComboBox cb1=new JComboBox(monedas_extr);
        cb1.setBounds(250, 100,200,30);
        cb1.setEditable(true);

        JComboBox cb2=new JComboBox(moneda_local);
        cb2.setBounds(250, 150,200,30);
        cb2.setEditable(true);

        b.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent itemEvent) {

                cb1.removeAllItems();
                cb2.removeAllItems();
                int estado = itemEvent.getStateChange();
                if(estado == ItemEvent.SELECTED){

                    for (int i = 0; i < moneda_local.length; i++) {
                        cb1.addItem(moneda_local[i]);
                    }

                    for (int i = 0; i < monedas_extr.length; i++) {
                        cb2.addItem(monedas_extr[i]);
                    }

                    }
                else {
                    for (int i = 0; i < moneda_local.length; i++) {
                        cb2.addItem(moneda_local[i]);
                    }

                    for (int i = 0; i < monedas_extr.length; i++) {
                        cb1.addItem(monedas_extr[i]);
                    }

                }

            }
        });

        t1.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {

                double amount = 0.0;
                String from;
                String to;


                try {
                    if (t1.getText().isBlank()){
                        t1.requestFocus();
                    }
                    else {
                        from = (String) cb1.getSelectedItem();
                        to = (String) cb2.getSelectedItem();
                        amount = Double.parseDouble(t1.getText());
                        //convertir(amount, from, to);

                        OkHttpClient client = new OkHttpClient().newBuilder().build();
                        Request request = new Request.Builder()
                                .url("https://api.apilayer.com/currency_data/convert?to=" + to + "&from=" + from + "&amount=" + amount
                                        + "")
                                .addHeader("apikey", "9icPWRrUZ8Qt49LPI1kMoCmwX0B9ssBy")
                                .build();
                        Response response = client.newCall(request).execute();
                        //System.out.println(response.body().string());
                        String responseBody = response.body().string();
                        JSONObject jsonResponse = new JSONObject(responseBody);
                        double conversionResult = jsonResponse.getDouble("result");
                        System.out.println(conversionResult);
                        t2.setText(String.valueOf(conversionResult));

                        //Double.toString(d)
                        //  Block of code to try
                        //convertir(String "EUR", String "USD", double 1.0);
                        //t2.setEditable(false);



                    }

                }
                catch(Exception d) {


                    //t1.setText("0.00");
                    //  Block of code to handle errors
                    t2.setText("0.00");
                    System.out.println("Error");

                }
                //String text = t1.getText();
            }
        });



        f.add(b);f.add(t1); f.add(t2);f.add(cb1);f.add(cb2);
        f.setSize(600,400);
        f.setLayout(null);
        f.setVisible(true);

    }







}
