package khantique.organisation.com.khantique.Fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListView;
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
import java.util.ArrayList;
import java.util.List;

import khantique.organisation.com.khantique.Adapter.ProductListAdaptor;
import khantique.organisation.com.khantique.Adapter.ProductPojo;
import khantique.organisation.com.khantique.Adapter.Urls;
import khantique.organisation.com.khantique.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductListFragment extends Fragment {
   String id, sub_category;
    ListView listView;
    GridView grid;
    TextView sub_cat_title;
    TextView empty_data;
    ProductListAdaptor productListAdapter;
    ArrayList<ProductPojo> productDetails_list=new ArrayList<ProductPojo>();

    public ProductListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_list, container, false);
        grid=(GridView)view.findViewById(R.id.grid);

        sub_cat_title=(TextView)view.findViewById(R.id.sub_cat_title);
        empty_data=(TextView)view.findViewById(R.id.empty_data);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            id = bundle.getString("id", "");
            sub_category=bundle.getString("sub_category", "");
            sub_cat_title.setText(sub_category);
          //  Toast.makeText(getContext(), id, Toast.LENGTH_LONG).show();

        }

        new ProductDetailsLoad().execute();

        return view;
    }



    class ProductDetailsLoad extends AsyncTask<String, Void, String> {

        private ProgressDialog pDialog;



        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
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
                HttpPost httpPost = new HttpPost(Urls.product);
                List< NameValuePair > nameValuePairs = new ArrayList< NameValuePair >();
                nameValuePairs.add(new BasicNameValuePair("cat_id", id));

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
            productDetails_list.clear();
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
                        ProductPojo productPojo = new ProductPojo();
                        productPojo.setID(jobject.getString("ID"));
                        productPojo.setPost_author(jobject.getString("post_author"));
                        productPojo.setPost_date(jobject.getString("post_date"));
                        productPojo.setPost_date_gmt(jobject.getString("post_date_gmt"));
                        productPojo.setPost_title(jobject.getString("post_title"));
                        productPojo.setPost_excerpt(jobject.getString("post_excerpt"));
                        productPojo.setPost_status(jobject.getString("post_status"));
                        productPojo.setComment_status(jobject.getString("comment_status"));
                        productPojo.setPing_status(jobject.getString("ping_status"));
                        productPojo.setPost_password(jobject.getString("post_password"));
                        productPojo.setPost_name(jobject.getString("post_name"));
                        productPojo.setTo_ping(jobject.getString("to_ping"));
                        productPojo.setPinged(jobject.getString("pinged"));
                        productPojo.setPost_modified(jobject.getString("post_modified"));
                        productPojo.setPost_modified_gmt(jobject.getString("post_modified_gmt"));
                        productPojo.setPost_content_filtered(jobject.getString("post_content_filtered"));
                        productPojo.setPost_parent(jobject.getString("post_parent"));
                        productPojo.setGuid(jobject.getString("guid"));
                        productPojo.setMenu_order(jobject.getString("menu_order"));
                        productPojo.setPost_type(jobject.getString("post_type"));
                        productPojo.setPost_mime_type(jobject.getString("post_mime_type"));
                        productPojo.setComment_count(jobject.getString("comment_count"));
                        productPojo.setProduct_image(jobject.getString("product_image"));
                        productPojo.setRegular_price(jobject.getString("regular_price"));


                        productDetails_list.add(productPojo);
                    }
                    productListAdapter = new ProductListAdaptor(getActivity(), productDetails_list);
                    grid.setAdapter(productListAdapter);
                    productListAdapter.notifyDataSetChanged();
                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("subcategory", sub_category);
                    editor.commit();
                   /* grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                          Toast.makeText(getContext(), "gvhjv", Toast.LENGTH_LONG).show();
                            Intent i = new Intent(getActivity(), ProductDetailsActivity.class);
                            i.putExtra("id", productDetails_list.get(position).getID());
                            i.putExtra("pr_name", productDetails_list.get(position).getPost_title());
                            i.putExtra("pr_price", productDetails_list.get(position).getRegular_price());
                            i.putExtra("sub_category", sub_category);
                             i.putExtra("image_one",productDetails_list.get(position).getProduct_image());
                            startActivity(i);
                        }
                    });*/


                }else {

                }
                Log.d("cotegory_list", "size: " + productDetails_list.size());

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
