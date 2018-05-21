package khantique.organisation.com.khantique.Adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
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
import java.util.List;

import khantique.organisation.com.khantique.CartActivity;
import khantique.organisation.com.khantique.ProductDetailsActivity;
import khantique.organisation.com.khantique.R;


/**
 * Created by lue on 23-06-2017.
 */

public class CartListAdaptor2 extends BaseAdapter {

  //  LayoutInflater mInflater;
   // private Activity context;

  //  ArrayList<User> Contact=new ArrayList<>();
    ArrayList<CartPojo> promotionDetailses=new ArrayList<>();
     TextView griddetail_text;



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
    ImageView delete;
    TextView product_price;
    public CartListAdaptor2(FragmentActivity activity, ArrayList<CartPojo> storeContacts) {
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
        view = mInflater.inflate(R.layout.cart_adapter2, null);
        griddetail_text = (TextView) view.findViewById(R.id.product_name);
        product_price=(TextView)view.findViewById(R.id.product_price);

        if(promotionDetailses.get(i).getPost_title()!=null){
            griddetail_text.setText(promotionDetailses.get(i).getPost_title()+" X "+promotionDetailses.get(i).getQty());
        }else {
            griddetail_text.setText(" ");
        }
        if(promotionDetailses.get(i).getUnit_price()!=null){
            String Price = promotionDetailses.get(i).getUnit_price();
            Price=Price.replace("$ ", "").replace(".00", "");
            Log.d("proce", Price);
            int totalPrice = Integer.parseInt(Price)*Integer.parseInt(promotionDetailses.get(i).getQty());
            product_price.setText("$ "+String.valueOf(totalPrice)+".00");
        }else {
            product_price.setText(" ");
        }
        if(promotionDetailses.get(i).getQty()!=null){
         //   quantity.setText("Qty : "+promotionDetailses.get(i).getQty());
        }else {
         //   quantity.setText(" ");
        }


       /* if(compareList.get(i).getContactName().equals("true")){
            add.setText("Added");
        }*/
        return view;
    }

    class RemoveToCard extends AsyncTask<String, Void, String> {

        private ProgressDialog pDialog;



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
                HttpPost httpPost = new HttpPost(Urls.deleteCart);
                List< NameValuePair > nameValuePairs = new ArrayList< NameValuePair >();
                nameValuePairs.add(new BasicNameValuePair("p_id", pr_id));
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
                    Toast.makeText(context, "Remove to cart successfully", Toast.LENGTH_LONG).show();
                    Intent iSave=new Intent(context, CartActivity.class);
                    context.startActivity(iSave);
                    ((Activity)context).overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    ((Activity)context).finish();


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
