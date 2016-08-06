package torres.basedatossqlite;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class Factura_Frm extends Activity implements View.OnClickListener {


    ListView InvoiceList;
    TextView cid,cname,cphone,pid,pname,pprice;
    Button ok;



    SQLite_Class controlDB = new SQLite_Class(this);

    Util_Strings_Class UtileStrings = new Util_Strings_Class();
    Util_UI_Class UtilesUI = new Util_UI_Class();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_factura__frm);


        ok= (Button) findViewById(R.id.ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        cid= (TextView) findViewById(R.id.cid);
        cname= (TextView) findViewById(R.id.cname);
        cphone= (TextView) findViewById(R.id.cphone);
        pid= (TextView) findViewById(R.id.pid);
        pname= (TextView) findViewById(R.id.pname);
        pprice= (TextView) findViewById(R.id.pprice);
        cid.setText(getIntent().getStringExtra("cid"));
        cname.setText(getIntent().getStringExtra("cname"));
        cphone.setText(getIntent().getStringExtra("cphone"));
        pid.setText(getIntent().getStringExtra("pid"));
        pname.setText(getIntent().getStringExtra("pname"));
        pprice.setText(getIntent().getStringExtra("pprice"));


    }


    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.BackProd_Btn:
                startActivity(new Intent(Factura_Frm.this, Principal.class));
                break;
        }//End switch
    }//Fin onClick
}

