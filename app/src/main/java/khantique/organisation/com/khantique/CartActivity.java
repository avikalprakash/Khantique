package khantique.organisation.com.khantique;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import khantique.organisation.com.khantique.Adapter.CartListAdaptor;
import khantique.organisation.com.khantique.Adapter.CartPojo;
import khantique.organisation.com.khantique.Adapter.ItemEntity;
import khantique.organisation.com.khantique.Adapter.NonScrollListView;
import khantique.organisation.com.khantique.Adapter.ProductListAdaptor;
import khantique.organisation.com.khantique.Adapter.ProductPojo;
import khantique.organisation.com.khantique.Adapter.SessionManagement;
import khantique.organisation.com.khantique.Adapter.Urls;

public class CartActivity extends AppCompatActivity implements View.OnClickListener {
    SessionManagement session;
    String userid;
    NonScrollListView cartList;
    TextView sub_total, totalText;
    int totalP=0;
    int qty = 0;
    ImageView back;
    Button proceed;
    ArrayList<CartPojo> cartDetails_list=new ArrayList<CartPojo>();
    CartListAdaptor cartListAdaptor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        cartList=(NonScrollListView)findViewById(R.id.cartList);
        sub_total=(TextView)findViewById(R.id.sub_total);
        totalText=(TextView)findViewById(R.id.total);
        back=(ImageView)findViewById(R.id.back);
        proceed=(Button)findViewById(R.id.proceed);
        session = new SessionManagement(CartActivity.this);
        HashMap<String, String> user1 = session.getUserDetails();
        userid = user1.get(session.KEY_ID);
        proceed.setOnClickListener(this);
        new CartDetailsLoad().execute();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.proceed:
                Intent intent = new Intent(getApplicationContext(), Checkout.class);
                startActivity(intent);
                break;
        }
    }


    class CartDetailsLoad extends AsyncTask<String, Void, String> {

        private ProgressDialog pDialog;



        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(CartActivity.this);
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
                HttpPost httpPost = new HttpPost(Urls.getCart);
                List< NameValuePair > nameValuePairs = new ArrayList< NameValuePair >();
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
            cartDetails_list.clear();
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
                        CartPojo cartPojo = new CartPojo();
                        cartPojo.setId(jobject.getString("id"));
                        cartPojo.setP_id(jobject.getString("p_id"));
                        cartPojo.setQty(jobject.getString("qty"));
                        cartPojo.setUnit_price(jobject.getString("unit_price"));
                        cartPojo.setTotal_price(jobject.getString("total_price"));
                        cartPojo.setU_id(jobject.getString("u_id"));
                        cartPojo.setPost_date(jobject.getString("post_date"));
                        cartPojo.setPost_date_gmt(jobject.getString("post_date_gmt"));
                        cartPojo.setPost_title(jobject.getString("post_title"));
                        cartPojo.setPost_excerpt(jobject.getString("post_excerpt"));
                        cartPojo.setPost_status(jobject.getString("post_status"));
                        cartPojo.setComment_status(jobject.getString("comment_status"));
                        cartPojo.setPing_status(jobject.getString("ping_status"));
                        cartPojo.setPost_password(jobject.getString("post_password"));
                        cartPojo.setPost_name(jobject.getString("post_name"));
                        cartPojo.setTo_ping(jobject.getString("to_ping"));
                        cartPojo.setPinged(jobject.getString("pinged"));
                        cartPojo.setPost_modified(jobject.getString("post_modified"));
                        cartPojo.setPost_modified_gmt(jobject.getString("post_modified_gmt"));
                        cartPojo.setPost_content_filtered(jobject.getString("post_content_filtered"));
                        cartPojo.setPost_parent(jobject.getString("post_parent"));
                        cartPojo.setGuid(jobject.getString("guid"));
                        cartPojo.setMenu_order(jobject.getString("menu_order"));

                        cartPojo.setPost_type(jobject.getString("post_type"));
                        cartPojo.setPost_mime_type(jobject.getString("post_mime_type"));
                        cartPojo.setComment_count(jobject.getString("comment_count"));
                        cartPojo.setProduct_image(jobject.getString("product_image"));


                        cartDetails_list.add(cartPojo);
                        String unit_price = cartPojo.getUnit_price();
                        String unit_qty = cartPojo.getQty();
                        Log.d("newll_list1", "kk1 " + unit_price);
                        String Price = unit_price;
                        String QTY = unit_qty;
                        Price=Price.replace("$ ", "").replace(".00", "");
                        int tp = Integer.parseInt(Price)* Integer.parseInt(QTY);
                        totalP = totalP + tp;
                        Log.d("price_sub_t", "pp " + String.valueOf(totalP));
                    }

                    sub_total.setText("$ "+String.valueOf(totalP)+".00");
                    totalText.setText("$ "+String.valueOf(totalP)+".00");
                    cartListAdaptor = new CartListAdaptor(CartActivity.this, cartDetails_list);
                    cartList.setAdapter(cartListAdaptor);
                    cartListAdaptor.notifyDataSetChanged();

                }else {

                }
                Log.d("cotegory_list", "size: " + cartDetails_list.size());

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
}
