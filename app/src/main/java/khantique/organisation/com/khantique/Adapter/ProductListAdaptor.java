package khantique.organisation.com.khantique.Adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.squareup.picasso.Picasso;
import com.wang.avi.AVLoadingIndicatorView;

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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import khantique.organisation.com.khantique.ProductDetailsActivity;
import khantique.organisation.com.khantique.R;


/**
 * Created by lue on 23-06-2017.
 */

public class ProductListAdaptor extends BaseAdapter {

  //  LayoutInflater mInflater;
   // private Activity context;
   ImageView circleImageView;
  //  ArrayList<User> Contact=new ArrayList<>();
    ArrayList<ProductPojo> promotionDetailses=new ArrayList<>();
     TextView griddetail_text;
    TextView price;
    //TextView mEmail;
    ImageView mgridimage;
    Button add_To_Cart;
    Button add;
    String ReciverMobile="";
    private static final int SHOW_PROCESS_DIALOG = 1;
    private static final int HIDE_PROCESS_DIALOG = 0;
    AVLoadingIndicatorView dialog;
    LayoutInflater mInflater;
    ArrayList<String> addToCart_list=new ArrayList<String>();
    private Activity context;//
    SessionManagement session;
    String userid;
    String pr_id;
    String qty;
    public ProductListAdaptor(FragmentActivity activity, ArrayList<ProductPojo> storeContacts) {
        this.context=activity;
        this.promotionDetailses=storeContacts;
    }
    @Override
    public int getCount() {
        return promotionDetailses.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        view = mInflater.inflate(R.layout.product_list_adapter, null);
        griddetail_text = (TextView) view.findViewById(R.id.griddetail_text);
        mgridimage=(ImageView) view.findViewById(R.id.grid_image);
        price = (TextView) view.findViewById(R.id.price);
        add_To_Cart=(Button)view.findViewById(R.id.add_To_Cart);

        add_To_Cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                session = new SessionManagement(context);
                HashMap<String, String> user1 = session.getUserDetails();
                userid = user1.get(session.KEY_ID);
                pr_id=promotionDetailses.get(i).getID();
                qty="1";
               // Toast.makeText(context, "pr_id : "+pr_id+"userid : "+userid, Toast.LENGTH_SHORT).show();
                new AddToCard().execute();
                ((Button)v).setEnabled(false);
            }
        });

        mgridimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProductPojo m = promotionDetailses.get(i);
                Intent i = new Intent(v.getContext(), ProductDetailsActivity.class);
                i.putExtra("id", m.getID());
                i.putExtra("pr_name", m.getPost_title());
                i.putExtra("pr_price", m.getRegular_price());
                // i.putExtra("sub_category", sub_category);
                i.putExtra("image_one",m.getProduct_image());
                v.getContext().startActivity(i);
            }
        });
        if(promotionDetailses.get(i).getPost_title()!=null){
            griddetail_text.setText(promotionDetailses.get(i).getPost_title());
        }else {
            griddetail_text.setText(" ");
        }
        if(promotionDetailses.get(i).getRegular_price()!=null){
            price.setText("$ "+promotionDetailses.get(i).getRegular_price()+".00");
        }else {
            price.setText(" ");
        }
        Picasso.with(context).load(promotionDetailses.get(i).getProduct_image()).into(mgridimage);

       /* if(compareList.get(i).getContactName().equals("true")){
            add.setText("Added");
        }*/
        return view;
    }

    class AddToCard extends AsyncTask<String, Void, String> {

        private ProgressDialog pDialog;
        String priceString = price.getText().toString();


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(context);
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
                HttpPost httpPost = new HttpPost("https://khantique.com/Api/cart.php");
                List< NameValuePair > nameValuePairs = new ArrayList< NameValuePair >();
                nameValuePairs.add(new BasicNameValuePair("p_id", pr_id));
                nameValuePairs.add(new BasicNameValuePair("qty", qty));
                nameValuePairs.add(new BasicNameValuePair("unit_price", priceString));
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

                    //JSONObject jsonObject=new JSONObject(s);
                    JSONArray jsonArray = objone.getJSONArray("Info");
                    Log.d("sizee", "kk1111 " + jsonArray.length());
                    for (int i = 0; i < jsonArray.length(); i++) {
                        Log.d("object", "kk1111 " + jsonArray.getJSONObject(i).toString());
                        JSONObject jobject = jsonArray.getJSONObject(i);

                    }
                    Toast.makeText(context, "Add to cart successfully", Toast.LENGTH_LONG).show();
                   /* add_To_Cart.setBackgroundResource(R.drawable.bg);
                    add_To_Cart.setTextColor(Color.parseColor("#000000"));
                    add_To_Cart.setEnabled(false);*/



                }else {

                }
                Log.d("cotegory_list", "size: " + addToCart_list.size());

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
