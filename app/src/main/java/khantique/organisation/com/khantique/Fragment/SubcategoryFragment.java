package khantique.organisation.com.khantique.Fragment;


import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import khantique.organisation.com.khantique.Adapter.ItemEntity;
import khantique.organisation.com.khantique.Adapter.MainCotegotieAdapter2;
import khantique.organisation.com.khantique.Adapter.Urls;
import khantique.organisation.com.khantique.R;


public class SubcategoryFragment extends Fragment {
    ListView listView;
    ArrayList<ItemEntity> subcotegory_list=new ArrayList<ItemEntity>();
    MainCotegotieAdapter2 mainCotegotieAdapter2;
    String id, category;
    TextView category_title;
    TextView empty_data;
    public SubcategoryFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_subcategory, container, false);
        listView=(ListView)view.findViewById(R.id.listView);
        category_title=(TextView)view.findViewById(R.id.category_title);
        empty_data=(TextView)view.findViewById(R.id.empty_data);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            id = bundle.getString("id", "");
            category = bundle.getString("category", "");
            category_title.setText(category);
        }
        new SubCategoryLoad().execute();

        return view;
    }

    class SubCategoryLoad extends AsyncTask<String, Void, String> {

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
                HttpPost httpPost = new HttpPost(Urls.sub_category);
                List< NameValuePair > nameValuePairs = new ArrayList < NameValuePair > ();
                nameValuePairs.add(new BasicNameValuePair("main", id));

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
            subcotegory_list.clear();
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
                        itemEntity.setTerm_id(jobject.getString("term_id"));
                        itemEntity.setCategory_name(jobject.getString("name"));
                        itemEntity.setSlug(jobject.getString("slug"));
                        itemEntity.setTerm_group(jobject.getString("term_group"));

                        subcotegory_list.add(itemEntity);
                    }
                }else {

                }
                Log.d("cotegory_list", "size: " + subcotegory_list.size());

            }catch (Exception e){
                e.printStackTrace();
            }
            if (subcotegory_list.size()>0){
                mainCotegotieAdapter2=new MainCotegotieAdapter2(getActivity(),subcotegory_list);
                listView.setAdapter(mainCotegotieAdapter2);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        ProductListFragment productListFragment = new ProductListFragment();
                        android.support.v4.app.FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.container, productListFragment);
                        transaction.addToBackStack(null);
                        transaction.commit();
                        Bundle bundle = new Bundle();
                        bundle.putString("id", subcotegory_list.get(position).getTerm_id());
                        bundle.putString("sub_category", subcotegory_list.get(position).getCategory_name());
                        productListFragment.setArguments(bundle);
                    }
                });
            }else {
                empty_data.setVisibility(View.VISIBLE);
                listView.setVisibility(View.INVISIBLE);
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
