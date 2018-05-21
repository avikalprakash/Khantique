package khantique.organisation.com.khantique.Fragment;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.GridView;
import android.widget.ListView;

import com.viewpagerindicator.CirclePageIndicator;

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
import java.util.Random;

import khantique.organisation.com.khantique.Adapter.AndroidImageAdapternew;
import khantique.organisation.com.khantique.Adapter.AndroidImageAdapternew2;
import khantique.organisation.com.khantique.Adapter.ExpandableHeightGridView;
import khantique.organisation.com.khantique.Adapter.InnerGridView;
import khantique.organisation.com.khantique.Adapter.ItemEntity;
import khantique.organisation.com.khantique.Adapter.MainCotegotieAdapter;
import khantique.organisation.com.khantique.Adapter.MainCotegotieAdapter2;
import khantique.organisation.com.khantique.Adapter.ServiceHandler;
import khantique.organisation.com.khantique.Adapter.Urls;
import khantique.organisation.com.khantique.R;


public class HomeFragment extends Fragment {
  String sv;
    AndroidImageAdapternew adapterView;
    AndroidImageAdapternew2 adapterView2;
  ViewPager mViewPager1, mViewPager2;
    ArrayList<String> ImageList = new ArrayList<>();
    ArrayList<String> ImageList2 = new ArrayList<>();
    ArrayList<ItemEntity> SliderImage = new ArrayList<ItemEntity>();
    ArrayList<ItemEntity> cotegory_list=new ArrayList<ItemEntity>();
    ArrayList<ItemEntity> slideGallery = new ArrayList<>();

    CirclePageIndicator indicator;
    CirclePageIndicator indicator2;
    float density;
    private int delay = 5000;
    private int page = 0;
    Handler handler1;
    Runnable runnable;
    ExpandableHeightGridView listView;
    MainCotegotieAdapter mainCotegotieAdapter;
    String text1;
    Button gallery;


    public HomeFragment() {

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_home, container, false);
        mViewPager1=(ViewPager)view.findViewById(R.id.slideImage1);
        mViewPager2=(ViewPager)view.findViewById(R.id.slideImage2);
        listView=(ExpandableHeightGridView)view.findViewById(R.id.cot_list);

        indicator = (CirclePageIndicator)
                view.findViewById(R.id.indicator);
        indicator2 = (CirclePageIndicator)
                view.findViewById(R.id.indicator2);
        gallery=(Button)view.findViewById(R.id.gallery);
        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                page = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        indicator2.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                page = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        new SlidingImage1().execute();
    //    new SlidingImage2().execute();

        new SlidingImageGallery().execute();

        new CategoryLoad().execute();
        return  view;
    }


    @Override
    public void onResume() {
        super.onResume();
     //   handler1.postDelayed(runnable, delay);

    }

    class SlidingImage1 extends AsyncTask<Void, Void, Void> {

        private ProgressDialog pDialog;

        Random rnd = new Random();
        int n = 100000 + rnd.nextInt(900000);

        String otp = String.valueOf(n);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Loading...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
            ImageList.clear();

        }

        @Override
        protected Void doInBackground(Void... voids) {
             ServiceHandler jsonParser = new ServiceHandler();
            String json = jsonParser.makeServiceCall(Urls.slideImage1, ServiceHandler.GET);
            String s = "";

          try {

                JSONObject objone = new JSONObject(json);
                int status  = objone.getInt("status");
                String msg  = objone.getString("msg");
                if(msg.equals("success")) {
               JSONArray jsonArray = objone.getJSONArray("Info");
                for (int i=0; i<jsonArray.length(); i++) {
                    ItemEntity itemEntity2 = new ItemEntity();
                    JSONObject jobject = jsonArray.getJSONObject(i);
                   // itemEntity2.setSliderImage(jobject.getString("id"));
                    itemEntity2.setSliderImage(jobject.getString("image_url"));
                    SliderImage.add(itemEntity2);
                    ImageList.add(jobject.getString("image_url")+"?per"+otp);

                }
                   if (ImageList.size() > 0) {
                adapterView = new AndroidImageAdapternew(getActivity(), ImageList);
                mViewPager1.setAdapter(adapterView);
                indicator.setViewPager(mViewPager1);
                try {
                    density = getResources().getDisplayMetrics().density;
                    indicator.setRadius(5 * density);
                }catch (Exception e){}
                runnable = new Runnable() {
                    public void run() {
                        if (adapterView.getCount() == page) {
                            page = 0;
                        } else {
                            page++;
                        }
                        mViewPager1.setCurrentItem(page, true);
                        handler1.postDelayed(this, delay);
                    }
                };


            }
                }
               }catch(Exception e){}
               return null;
        }
        @Override
        protected void onPostExecute(Void json) {
            super.onPostExecute(json);
            pDialog.dismiss();

        }


    }


    class SlidingImageGallery extends AsyncTask<Void, Void, Void> {

        private ProgressDialog pDialog;

        Random rnd = new Random();
        int n = 100000 + rnd.nextInt(900000);

        String otp = String.valueOf(n);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Loading...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
            ImageList.clear();

        }

        @Override
        protected Void doInBackground(Void... voids) {
            ServiceHandler jsonParser = new ServiceHandler();
            String json = jsonParser.makeServiceCall(Urls.slideImageGallery, ServiceHandler.GET);
            String s = "";

            try {

                JSONObject objone = new JSONObject(json);
                int status  = objone.getInt("status");
                String msg  = objone.getString("msg");
                if(msg.equals("success")) {
                    JSONArray jsonArray = objone.getJSONArray("Info");
                    for (int i=0; i<jsonArray.length(); i++) {
                        ItemEntity itemEntity2 = new ItemEntity();
                        JSONObject jobject = jsonArray.getJSONObject(i);
                        // itemEntity2.setSliderImage(jobject.getString("id"));
                        itemEntity2.setSliderImageGallery(jobject.getString("image_url"));
                        slideGallery.add(itemEntity2);
                        ImageList2.add(jobject.getString("image_url")+"?per"+otp);

                    }
                    if (ImageList2.size() > 0) {
                        adapterView2 = new AndroidImageAdapternew2(getActivity(), ImageList2);
                        mViewPager2.setAdapter(adapterView2);
                        indicator.setViewPager(mViewPager2);
                        try {
                            density = getResources().getDisplayMetrics().density;
                            indicator2.setRadius(5 * density);
                        }catch (Exception e){}
                        runnable = new Runnable() {
                            public void run() {
                                if (adapterView2.getCount() == page) {
                                    page = 0;
                                } else {
                                    page++;
                                }
                                mViewPager2.setCurrentItem(page, true);
                                handler1.postDelayed(this, delay);
                            }
                        };
                    }
                }
            }catch(Exception e){}
            return null;
        }
        @Override
        protected void onPostExecute(Void json) {
            super.onPostExecute(json);
            pDialog.dismiss();

        }


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
                        itemEntity.setCategory_image(jobject.getString("category_image"));
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
                mainCotegotieAdapter=new MainCotegotieAdapter(getActivity(),cotegory_list);
                listView.setAdapter(mainCotegotieAdapter);
                listView.setExpanded(true);
                listView.setFocusable(false);
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
                //empty_data.setVisibility(View.VISIBLE);
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
