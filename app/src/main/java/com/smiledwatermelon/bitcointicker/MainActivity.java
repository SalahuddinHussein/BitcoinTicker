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

    private  final String BASE_URL="https://apiv2.bitcoinaverage.com/indices/global/ticker/BTC";
    TextView mPriceTextView;
    Spinner mSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPriceTextView=findViewById(R.id.priceLabel);
        mSpinner=findViewById(R.id.currency_spinner);
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this,
                R.array.currency_array,R.layout.spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        mSpinner.setAdapter(adapter);

        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d("Bit",parent.getItemAtPosition(position).toString());
                String equvCur=parent.getItemAtPosition(position).toString();
                updateCurrency(equvCur);

            }

            private void updateCurrency(String equvCur) {
                String URL=BASE_URL+equvCur;
                AsyncHttpClient client=new AsyncHttpClient();
                client.get(URL,new JsonHttpResponseHandler(){
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response){
                        Log.d("Bit",response.toString());
                        try {
                            mPriceTextView.setText(response.getString("ask"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject response){

                    }

                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            Log.d("Bit","Nothing selected");

            }
        });

    }
}
