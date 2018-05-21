package khantique.organisation.com.khantique;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.squareup.picasso.Picasso;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import khantique.organisation.com.khantique.Adapter.ItemEntity;
import khantique.organisation.com.khantique.Adapter.MainCotegotieAdapter2;
import khantique.organisation.com.khantique.Adapter.ProductListAdaptor;
import khantique.organisation.com.khantique.Adapter.ProductPojo;
import khantique.organisation.com.khantique.Adapter.SessionManagement;
import khantique.organisation.com.khantique.Adapter.Urls;
import khantique.organisation.com.khantique.Fragment.ProductListFragment;

public class ProductDetailsActivity extends AppCompatActivity implements View.OnClickListener {
   String id, pr_name, pr_price, sub_category, image_one;
   ImageView image1, image2, image3, image4, image5;
   TextView prod_name, price, sub_cat, quantitytxt;
   ImageView back;
    String a,b,c,d;
    HorizontalScrollView linear;
    ImageView minus, add;
    int priceInt;
    String userid;
    Button addToCart;
    ArrayList<ItemEntity> productImages=new ArrayList<ItemEntity>();
    public static final String SUBCATEGORY = "subcategory";
    public static final String MyPref = "MyPref";
    SharedPreferences sharedpreferences;
    String subCategory;
    SessionManagement session;
    ArrayList<String> addToCart_list=new ArrayList<String>();
    int i = 1;
    String activity="activity1";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        id=getIntent().getStringExtra("id");
        pr_name=getIntent().getStringExtra("pr_name");
        pr_price=getIntent().getStringExtra("pr_price");
        sub_category=getIntent().getStringExtra("sub_category");
        image_one=getIntent().getStringExtra("image_one");
        minus=(ImageView)findViewById(R.id.minus);
        add=(ImageView)findViewById(R.id.add);
        sharedpreferences=getSharedPreferences(MyPref, MODE_PRIVATE);
        subCategory=sharedpreferences.getString(SUBCATEGORY,"");
        image1=(ImageView)findViewById(R.id.image1);
        image2=(ImageView)findViewById(R.id.image2);
        image3=(ImageView)findViewById(R.id.image3);
        image4=(ImageView)findViewById(R.id.image4);
        image5=(ImageView)findViewById(R.id.image5);
        prod_name=(TextView)findViewById(R.id.pr_name);
        price=(TextView)findViewById(R.id.price);
        sub_cat=(TextView)findViewById(R.id.sub_cat);
        quantitytxt=(TextView)findViewById(R.id.quantitytxt);
        addToCart=(Button)findViewById(R.id.addToCart);
        addToCart.setOnClickListener(this);
        sub_cat.setText("Category : "+subCategory);
        price.setText("$ "+pr_price+".00");
        linear=(HorizontalScrollView)findViewById(R.id.linear);
        back=(ImageView)findViewById(R.id.back);
        session = new SessionManagement(ProductDetailsActivity.this);
        HashMap<String, String> user1 = session.getUserDetails();
        userid = user1.get(session.KEY_ID);
        back.setOnClickListener(this);
        prod_name.setText(pr_name);
        Picasso.with(getApplicationContext()).load(image_one).into(image1);
        new ImageLoad().execute();

        image2.setOnClickListener(this);
        image3.setOnClickListener(this);
        image4.setOnClickListener(this);
        image5.setOnClickListener(this);
        add.setOnClickListener(this);
        minus.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.addToCart:
                if(session.isLoggedIn())
                {
                   // Addcart();
                    new AddToCard().execute();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Please login first",Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(ProductDetailsActivity.this, LoginActivity.class);
                   // intent.putExtra("send", activity);
                    startActivity(intent);

                }
            break;

            case R.id.back:
                finish();
                break;

            case R.id.image2:
                if (!a.equals("")){
                    try{
                        Picasso.with(getApplicationContext()).load(a).into(image1);
                    }catch (Exception e){}

                }
                break;

            case R.id.image3:
                if (!b.equals("")){
                    try{
                        Picasso.with(getApplicationContext()).load(b).into(image1);
                    }catch (Exception e){}

                }
                break;

            case R.id.image4:
                if (!c.equals("")){
                    try{
                        Picasso.with(getApplicationContext()).load(c).into(image1);
                    }catch (Exception e){}

                }
                break;

            case R.id.image5:
                if (!d.equals("")){
                    try{
                        Picasso.with(getApplicationContext()).load(d).into(image1);
                    }catch (Exception e){}

                }
                break;

            case R.id.add:
                i++;
                quantitytxt.setText(String.valueOf(i));
                priceInt=Integer.parseInt(pr_price)*i;
                price.setText("$ "+String.valueOf(priceInt)+".00");
                break;

            case R.id.minus:

                if(i == 1)
                {

                }else {
                    i--;
                    quantitytxt.setText(String.valueOf(i));
                    priceInt=Integer.parseInt(pr_price)*i;
                    price.setText("$ "+String.valueOf(priceInt)+".00");
                }
                break;
        }
    }

    class ImageLoad extends AsyncTask<String, Void, String> {

        private ProgressDialog pDialog;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ProductDetailsActivity.this);
            pDialog.setMessage("Loading...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected String doInBackground(String... args) {
            String s = "";


            try {
                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(Urls.product_image);
                List< NameValuePair > nameValuePairs = new ArrayList < NameValuePair > ();
                nameValuePairs.add(new BasicNameValuePair("p_id", id));

                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                HttpResponse response = httpClient.execute(httpPost);

                HttpEntity httpEntity = response.getEntity();
                s = readadsResponse(response);
                Log.d("imageLoad", " " + s);
            } catch (Exception exception) {
                exception.printStackTrace();

                Log.d("espone",exception.toString());

            }

            return s;

        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pDialog.dismiss();
            Log.d("imageLoad",""+s);
            productImages.clear();
            try {

                JSONObject objone = new JSONObject(s);
                int status  = objone.getInt("status");
                String msg  = objone.getString("msg");
                if(msg.equals("success")) {

                    //JSONObject jsonObject=new JSONObject(s);
                    JSONArray jsonArray = objone.getJSONArray("Info");
                    Log.d("sizee", "kk1111 " + jsonArray.length());
                    for (int i = 0; i < jsonArray.length(); i++) {
                        Log.d("object", "kk1111 " + jsonArray.getJSONObject(i).toString());
                        JSONObject jobject = jsonArray.getJSONObject(i);
                        ItemEntity itemEntity = new ItemEntity();
                        itemEntity.setProduct_images2(jobject.getString("guid"));
                        productImages.add(itemEntity);
                    }


                    ArrayList<String> newll = new ArrayList<String>();
                    for (int k = 0; k < productImages.size(); k++) {
                        ItemEntity obj = productImages.get(k);
                        String dd = obj.getProduct_images2();
                        Log.d("newll_list", "kk1111 " + dd);
                        if (productImages.size()>0){
                            linear.setVisibility(View.VISIBLE);
                        }
                        newll.add(dd);
                    }
                    Log.d("list_size", "kk1111 " + newll.size());
                    if (newll.size()==4){
                        a= newll.get(0);
                        if (!a.equals(""))
                            Picasso.with(getApplicationContext()).load(a).into(image2);
                        b= newll.get(1);
                        if (!b.equals(""))
                            Picasso.with(getApplicationContext()).load(b).into(image3);
                        c= newll.get(2);
                        if (!c.equals(""))
                            Picasso.with(getApplicationContext()).load(c).into(image4);
                        d= newll.get(3);
                        if (!d.equals(""))
                            Picasso.with(getApplicationContext()).load(d).into(image5);
                    }else if (newll.size()==3){
                        a= newll.get(0);
                        if (!a.equals(""))
                            Picasso.with(getApplicationContext()).load(a).into(image2);
                        b= newll.get(1);
                        if (!b.equals(""))
                            Picasso.with(getApplicationContext()).load(b).into(image3);
                        c= newll.get(2);
                        if (!c.equals(""))
                            Picasso.with(getApplicationContext()).load(c).into(image4);
                        image5.setVisibility(View.INVISIBLE);
                    }else if (newll.size()==2){
                        a= newll.get(0);
                        if (!a.equals(""))
                            Picasso.with(getApplicationContext()).load(a).into(image2);
                        b= newll.get(1);
                        if (!b.equals(""))
                            Picasso.with(getApplicationContext()).load(b).into(image3);
                        image4.setVisibility(View.INVISIBLE);
                        image5.setVisibility(View.INVISIBLE);
                    }else if (newll.size()==1){
                        a= newll.get(0);
                        if (!a.equals(""))
                            Picasso.with(getApplicationContext()).load(a).into(image2);
                        image3.setVisibility(View.INVISIBLE);
                        image4.setVisibility(View.INVISIBLE);
                        image5.setVisibility(View.INVISIBLE);
                    }


                }else {

                }
                Log.d("cotegory_list", "size: " + productImages.size());

            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }


    private String readadsResponse(HttpResponse httpResponse) {

        InputStream is = null;
        String return_text = "";
        try {
            is = httpResponse.getEntity().getContent();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
            String line = "";
            StringBuffer sb = new StringBuffer();
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
            return_text = sb.toString();
            Log.d("return1230", "" + return_text);
        } catch (Exception e) {

        }
        return return_text;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    class AddToCard extends AsyncTask<String, Void, String> {

        private ProgressDialog pDialog;



        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ProductDetailsActivity.this);
            pDialog.setMessage("Loading...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected String doInBackground(String... args) {
            String s = "";


            try {
                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(Urls.addCart);
                List< NameValuePair > nameValuePairs = new ArrayList< NameValuePair >();
                nameValuePairs.add(new BasicNameValuePair("p_id", id));
                nameValuePairs.add(new BasicNameValuePair("qty", quantitytxt.getText().toString()));
                nameValuePairs.add(new BasicNameValuePair("unit_price", price.getText().toString()));
                nameValuePairs.add(new BasicNameValuePair("u_id", userid));

                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                HttpResponse response = httpClient.execute(httpPost);

                HttpEntity httpEntity = response.getEntity();
                s = readadsResponse(response);
                Log.d("tag1", " " + s);
            } catch (Exception exception) {
                exception.printStackTrace();

                Log.d("espone",exception.toString());

            }

            return s;

        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pDialog.dismiss();
            Log.d("onPostExcute",""+s);
            addToCart_list.clear();
            try {

                JSONObject objone = new JSONObject(s);
                int status  = objone.getInt("status");
                String msg  = objone.getString("msg");
                if(msg.equals("success")) {

                    JSONArray jsonArray = objone.getJSONArray("Info");
                    Log.d("sizee", "kk1111 " + jsonArray.length());
                    for (int i = 0; i < jsonArray.length(); i++) {
                        Log.d("object", "kk1111 " + jsonArray.getJSONObject(i).toString());
                        JSONObject jobject = jsonArray.getJSONObject(i);

                    }
                    Toast.makeText(getApplicationContext(), "Add to cart successfully", Toast.LENGTH_LONG).show();
                    addToCart.setBackgroundResource(R.drawable.bg);
                    addToCart.setTextColor(Color.parseColor("#000000"));
                    addToCart.setEnabled(false);

                }else {

                }
                Log.d("cotegory_list", "size: " + addToCart_list.size());

            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }


}
