package com.example.nedo.cccompare;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ExecutionException;


public class MainActivity extends Activity {

    // The following Arrays contain the attributes for each of the top currencies.
    String[] names = new String[5];
    String[] icons = new String[5];
    Double[] prices = new Double[5];
    Boolean[] priceUps = new Boolean[5];

    //For collecting the results of the API queries to cryptocompare.com
    String result1 = "";
    String result2 = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Get the reponse from the first querry at cryptocompare.com/api as 'result1'.
        //From here we get the names of the Top 5 and their image urls.
        try {
            result1 = new JsonTask().execute("https://www.cryptocompare.com/api/data/coinlist/").get();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        //Call 'manipulate()' to get the names and icon urls of top 5 currencies. Required for getting price and state.
        manipulate();


        //Form link for prices:
        String urlPrices = "https://min-api.cryptocompare.com/data/pricemulti?fsyms=";
        for (int i = 0; i<5; i++){
            if(i<4) {
                urlPrices += names[i]+",";
            }else{
                urlPrices+= names[i];
            }
        }
        urlPrices += "&tsyms=USD";

        //Get response from the second querry as 'result2'. Used for prices.
        //TODO this method should be called more often than once on startup but for now this is the implementation.
        try {
            result2 = new JsonTask().execute(urlPrices).get();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        //Call 'manipulate2()' to extract current prices from 'response2'.
        manipulate2();


        //----------------------------------------------------------
        //Filling up the 'priceUps[]' array:
        //TODO In the current implementation the stats are taken once on startup. If later they need to be...
        //TODO ...displayed dynamically a better implementation would be with two arrays for price[](old & new)...
        //TODO ...whose values are updated every minute and compared to each other.

        for (int i = 0; i < 5; i++){
            String url = "https://min-api.cryptocompare.com/data/histominute?fsym=" + names[i]+ "&tsym=USD&limit=1";
            try {
                String result = new JsonTask().execute(url).get();
                JSONObject obj1 = new JSONObject(result);
                JSONArray arr = new JSONArray(obj1.getString("Data"));

                JSONObject obj2 = arr.getJSONObject(0);
                JSONObject obj3 = arr.getJSONObject(1);

                Double value1 = obj2.getDouble("high");
                Double value2 = obj3.getDouble("high");

                if (value1 >= value2){
                    priceUps[i] = false;
                }else{
                    priceUps[i] = true;
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        //----------------------------------------------------------
        //Initialise the ELV
        ExpandableListView elv = (ExpandableListView) findViewById(R.id.expandableListView);

        //Initialise data for probe testing of the ELV Adapter
        final ArrayList<Currency> currencies = getData();

        //Create Adapter and bind it to ELV
        CustomAdapter adapter = new CustomAdapter(this, currencies);
        elv.setAdapter(adapter);
    }
    //End of 'onCreate()'
    //-----------------------------------------------------------------------------------------------
    //-----------------------------------------------------------------------------------------------


    //Manipulating Data Methods:
    //Manipulate 'result1' to get the top5 list ('names[]') and the urls to their icons ('icons[]')
    private void manipulate(){
        try {
            JSONObject obj1 = new JSONObject(result1);
            JSONObject obj2 = new JSONObject(obj1.getString("Data"));
            Iterator<String> iterator = obj2.keys();

            while(iterator.hasNext()){
                String temp = iterator.next();
                JSONObject current = new JSONObject(obj2.getString(temp));
                String order = current.getString("SortOrder");
                int orderInt = Integer.parseInt(order);

                if(orderInt<6){
                    names[orderInt-1] = temp;
                    icons[orderInt-1] = current.getString("ImageUrl");
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    //Manipulate 'result2' to get current prices ('prices[]'):
    private void manipulate2(){
        try {
            JSONObject obj1 = new JSONObject(result2);
            for (int i = 0; i<5; i++){
                JSONObject current = new JSONObject(obj1.getString(names[i]));
                prices[i] = Double.parseDouble(current.getString("USD"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    //--------------------------------------------
    //Method for creating 5 'Currency' objects with the data from the arrays.
    private ArrayList<Currency> getData(){
        ArrayList currencies = new ArrayList();
        for (int i = 0; i < 5; i++){
            Currency c = new Currency(names[i], icons[i], priceUps[i], prices[i]);
            currencies.add(c);
        }
        return currencies;
    }


    //Methods for opening new Activities for clicking Buy and Sell.
    public void openBuyActivity(View v){
        startActivity(new Intent(MainActivity.this, BuyActivity.class));
    }

    public void openSellActivity(View v){
        startActivity(new Intent(MainActivity.this, SellActivity.class));
    }
   }
