package khantique.organisation.com.khantique.Fragment;


import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
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

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import khantique.organisation.com.khantique.Adapter.ItemEntity;
import khantique.organisation.com.khantique.Adapter.MainCotegotieAdapter;
import khantique.organisation.com.khantique.Adapter.MainCotegotieAdapter2;
import khantique.organisation.com.khantique.Adapter.ServiceHandler;
import khantique.organisation.com.khantique.Adapter.Urls;
import khantique.organisation.com.khantique.R;
import khantique.organisation.com.khantique.SignupActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryFragment extends Fragment {

    ListView listView;
    ArrayList<ItemEntity> cotegory_list=new ArrayList<ItemEntity>();
    MainCotegotieAdapter2 mainCotegotieAdapter2;
    TextView empty_data;
    public CategoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        listView=(ListView)view.findViewById(R.id.listView);
        empty_data=(TextView)view.findViewById(R.id.empty_data);
        new CategoryLoad().execute();
        return view;
    }


    class CategoryLoad extends AsyncTask<Void, Void, Void> {

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
        protected Void doInBackground(Void... voids) {
            ServiceHandler jsonParser = new ServiceHandler();
            String json = jsonParser.makeServiceCall(Urls.category, ServiceHandler.GET);
            if(json!=null)
                cotegory_list.clear();
            try {

                JSONObject objone = new JSONObject(json);
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

                        cotegory_list.add(itemEntity);
                    }
                }else {

                }
                Log.d("cotegory_list", "size: " + cotegory_list.size());

            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            pDialog.dismiss();
            Log.d("onPostExcute",""+result);
            if (cotegory_list.size()>0){
                mainCotegotieAdapter2=new MainCotegotieAdapter2(getActivity(),cotegory_list);
                listView.setAdapter(mainCotegotieAdapter2);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        SubcategoryFragment subcategoryFragment = new SubcategoryFragment();
                        android.support.v4.app.FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.container, subcategoryFragment);
                        transaction.addToBackStack(null);
                        transaction.commit();
                        Bundle bundle = new Bundle();
                        bundle.putString("id", cotegory_list.get(position).getTerm_id());
                        bundle.putString("category", cotegory_list.get(position).getCategory_name());
                        subcategoryFragment.setArguments(bundle);
                    }
                });
            }else {
                empty_data.setVisibility(View.VISIBLE);
                listView.setVisibility(View.INVISIBLE);
            }
        }
    }

}
