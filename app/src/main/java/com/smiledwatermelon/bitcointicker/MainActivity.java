package com.smiledwatermelon.bitcointicker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    public  String baseCur="USD";
    public String equvCur="USD";

    private  final String BASE_URL="https://api.fixer.io/latest?base=";
    TextView mPriceTextView;
    Spinner mSpinner,mSpinner2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPriceTextView=findViewById(R.id.priceLabel);
        mSpinner=findViewById(R.id.currency_spinner);
        mSpinner2=findViewById(R.id.currency_spinner2);
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this,
                R.array.currency_array,R.layout.spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        mSpinner.setAdapter(adapter);
        mSpinner2.setAdapter(adapter);

        mSpinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d("Bit","Second Coin: "+parent.getItemAtPosition(position).toString());
                equvCur=parent.getItemAtPosition(position).toString();
                updateCurrency();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d("Bit","First Coin: "+parent.getItemAtPosition(position).toString());
                baseCur=parent.getItemAtPosition(position).toString();
                updateCurrency();

            }



            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            Log.d("Bit","Nothing selected");

            }
        });


    }
    private void updateCurrency() {
        String URL=BASE_URL+baseCur;
        Log.d("Bit",URL);
        AsyncHttpClient client=new AsyncHttpClient();
        client.get(URL,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response){
                Log.d("Bit",response.toString());
                try {
                    if(baseCur==equvCur){
                        mPriceTextView.setText("1");
                    }else

                    mPriceTextView.setText(response.getJSONObject("rates").getString(equvCur));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject response){
                Log.d("Bit","Sync Fail");

            }

        });
    }
}
