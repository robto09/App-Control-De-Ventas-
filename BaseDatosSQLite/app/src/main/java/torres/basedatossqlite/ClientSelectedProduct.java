package torres.basedatossqlite;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;


import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ClientSelectedProduct extends Activity implements View.OnClickListener{

    EditText articuloTxt;
    EditText precioTxt;
    EditText codigoTxt;
    Button VuelveBtn;
    Button SelecArticBtn;
    ListView ListaProductos;
    String pName,pId,pPrice ,cId,cName,cPhone;
    private static String DATA_SEND_URL = "http://192.167.1.7:80/sendData.php";



    SQLite_Class controlDB = new SQLite_Class(this);

    Util_Strings_Class UtileStrings = new Util_Strings_Class();
    Util_UI_Class UtilesUI = new Util_UI_Class();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_selected_product);



              cId=getIntent().getStringExtra("id");
               cName=getIntent().getStringExtra("name");
               cPhone=getIntent().getStringExtra("number");
                incializaPantalla();
        ListaProductos.setAdapter(UtilesUI.CargaArrayAdapter(this, controlDB.ConsultArticulos()));
        ListViewClick();
    }

    private void ListViewClick(){
        ListaProductos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int laPosicion = position + 1;
                String elementoSeleccionado = (String) ListaProductos.getItemAtPosition(position);
                String[] TextoPartido = UtileStrings.CortaTextos(elementoSeleccionado, ",");
                pId=String.valueOf(laPosicion);
                pName=TextoPartido[0];
                pPrice=TextoPartido[1];

                codigoTxt.setText(pId);
                articuloTxt.setText(pName);
                precioTxt.setText(pPrice);
            }
        });//End setOnItemClickListener
    }//Fin ListViewClick

    public void incializaPantalla() {
        codigoTxt = (EditText) findViewById(R.id.CodArt_Txt);
        articuloTxt = (EditText) findViewById(R.id.NomProd_Txt);
        precioTxt = (EditText) findViewById(R.id.Precio_Txt);
        ListaProductos = (ListView) findViewById(R.id.ProductosView);
        VuelveBtn = (Button) findViewById(R.id.BackProd_Btn);
        VuelveBtn.setOnClickListener(this);
        SelecArticBtn  = (Button) findViewById(R.id.btn_Purchase);
        SelecArticBtn.setOnClickListener(this);
    }//Fin incializaPantalla

    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.BackProd_Btn:
                startActivity(new Intent(ClientSelectedProduct.this, Principal.class));
                break;
            case  R.id.btn_Purchase:
                if(pId==null || pId.equals(""))
                    Toast.makeText(ClientSelectedProduct.this, "Select product first", Toast.LENGTH_SHORT).show();
                else if(!checkInternetConnection()){
                    Toast.makeText(ClientSelectedProduct.this, "Need internet connection", Toast.LENGTH_SHORT).show();

                }

                else {
                    Toast.makeText(ClientSelectedProduct.this, "Data sending...", Toast.LENGTH_SHORT).show();

                    sendData();


                }




                Toast.makeText(ClientSelectedProduct.this, pName+"  "+pPrice+"  "+pId, Toast.LENGTH_SHORT).show();
        }//End switch
    }//Fin onClick

    private boolean checkInternetConnection() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        // test for connection
        if (cm.getActiveNetworkInfo() != null
                && cm.getActiveNetworkInfo().isAvailable()
                && cm.getActiveNetworkInfo().isConnected()) {
            return true;
        } else {
            Log.v("SHAN", "Internet Connection Not Present");
            return false;
        }

    }
    public void sendData(){

        final OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("clientName",cName )
                .add("clientPhone", cPhone)
                .add("clientId",cId )
                .add("productId",pId )
                .add("productName",pName )
                .add("productPrice",pPrice )

                .build();
        final Request request = new Request.Builder()
                .url(DATA_SEND_URL)
                .post(formBody)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("SHAN","EXCE"+e.getMessage());
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {

                if(response.isSuccessful()) {
Log.d("SHAN",response.toString());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {


                            Intent i=new Intent(ClientSelectedProduct.this,Factura_Frm.class);
                            i.putExtra("cid",cId);
                            i.putExtra("cname",cName);
                            i.putExtra("cphone",cPhone);
                            i.putExtra("pid",pId);
                            i.putExtra("pname",pName);
                            i.putExtra("pprice",pPrice);
                            startActivity(i);
                            Toast.makeText(ClientSelectedProduct.this, "Data sucessfully send on server", Toast.LENGTH_SHORT).show();
                        }
                    });


                }
                else {
                    Log.d("SHAN","fail response call"+response.toString());

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(ClientSelectedProduct.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                        }

                    });
                }

            }


        });



    }


}
