package torres.basedatossqlite;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Principal extends Activity implements View.OnClickListener{
    Button ProductosBtn;
    Button ClientesBtn;
    Button SalirBtn;
    Button SincronizaBtn;
    Button GeneraInvoice;
    JSONArray Jarray;

    // Progress Dialog
    private ProgressDialog pDialog;

    // Creating JSON Parser object

    JSONParser jParser;
    private static String url_all_products = "http://192.167.1.7:80/getProducts.php";
    private static String url_all_clients = "http://192.167.1.7:80/getClients.php";


    Util_UI_Class UtilesUI = new Util_UI_Class();
    CargaDatos_Class CargaDatosClass = new CargaDatos_Class(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        incializaPantalla();
    }

    private void incializaPantalla() {

        ProductosBtn = (Button) findViewById(R.id.ListaProductos_Btn);
        ClientesBtn = (Button) findViewById(R.id.ListaClientes_Btn);
        SalirBtn = (Button) findViewById(R.id.Salir_Btn);
        GeneraInvoice=(Button)findViewById(R.id.button_invoice);
        SincronizaBtn = (Button) findViewById(R.id.Sincroniza_Btn);
        GeneraInvoice.setOnClickListener(this);
        ProductosBtn.setOnClickListener(this);
        ClientesBtn.setOnClickListener(this);
        SalirBtn.setOnClickListener(this);
        SincronizaBtn.setOnClickListener(this);
    }


    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.ListaClientes_Btn:
                startActivity(new Intent(Principal.this,Clientes_Frm.class));
                break;
            case R.id.ListaProductos_Btn:
                startActivity(new Intent(Principal.this, Articulos_Frm.class));
                break;
            case R.id.button_invoice:
                startActivity(new Intent(Principal.this, Factura_Frm.class));
                break;
            case R.id.Sincroniza_Btn:

                    if(checkInternetConnection()){
                        UtilesUI.MensajeToast(getApplicationContext(), "Sincronizando..");
                        CargaDatosClass.SincronizaTodo();
                        getProductFromServer();
                        getClientsFromServer();
                    }
                else
                        UtilesUI.MensajeToast(getApplicationContext(), "Need internet connection");

                break;
            case R.id.Salir_Btn:
                UtilesUI.MensajeToast(getApplicationContext(), "Cerrando App..");
                Intent elIntent = new Intent(Intent.ACTION_MAIN);
                elIntent.addCategory(Intent.CATEGORY_HOME);
                elIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(elIntent);
                break;
        }//End switch
    }//onClick


    public void getProductFromServer(){

        final OkHttpClient client = new OkHttpClient();
        final Request request = new Request.Builder()

                .url(url_all_products)
                .get()
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {

                if(response.isSuccessful()) {
                        Log.d("SHAN","Product response sucess full");
                    try{


                        String jsonData = response.body().string();
                        Log.d("SHAN","resssss****"+jsonData.toString());
                        JSONObject Jobject = new JSONObject(jsonData);
                        Jarray = Jobject.getJSONArray("products");
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {

                    }
                    for (int i = 0; i < Jarray.length(); i++) {

                        try {
                            JSONObject object     = Jarray.getJSONObject(i);

                            String name =  object.get("name").toString();
                            String price =  object.get("price").toString();
                            CargaDatosClass.GuardaArticulo(name,price);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                        }
                    });

                }
                else {
                    Log.d("SHAN","fail response call"+response.toString());

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                        }
                    });
                }

            }


        });



    }
    public void getClientsFromServer(){

       final OkHttpClient client = new OkHttpClient();
       final Request request = new Request.Builder()
               .url(url_all_clients)
               .get()
               .build();
       Call call = client.newCall(request);
       call.enqueue(new Callback() {
           @Override
           public void onFailure(Call call, IOException e) {
           }

           @Override
           public void onResponse(Call call, final Response response) throws IOException {

               if(response.isSuccessful()) {

                   try{


                       String jsonData = response.body().string();
                       Log.d("SHAN","resssss****"+jsonData.toString());
                       JSONObject Jobject = new JSONObject(jsonData);
                       Jarray = Jobject.getJSONArray("clients");
                   } catch (IOException e) {
                       e.printStackTrace();
                   } catch (JSONException e) {

                   }
                   for (int i = 0; i < Jarray.length(); i++) {


                       try {
                           JSONObject object     = Jarray.getJSONObject(i);
                           String name =  object.get("name").toString();
                           String phone =  object.get("phone").toString();
                           CargaDatosClass.GuardaCliente(name,phone);


                       } catch (JSONException e) {
                           e.printStackTrace();
                       }
                   }
                   runOnUiThread(new Runnable() {
                       @Override
                       public void run() {

                       }
                   });


               }
               else {

                   runOnUiThread(new Runnable() {
                       @Override
                       public void run() {
                           Toast.makeText(Principal.this, "Conection error"+response.toString(), Toast.LENGTH_SHORT).show();
                       }
                   });
               }

           }


       });
   }
    private boolean checkInternetConnection() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm.getActiveNetworkInfo() != null
                && cm.getActiveNetworkInfo().isAvailable()
                && cm.getActiveNetworkInfo().isConnected()) {
            return true;
        } else {
            Log.v("SHAN", "Internet Connection Not Present");
            return false;
        }

    }





}