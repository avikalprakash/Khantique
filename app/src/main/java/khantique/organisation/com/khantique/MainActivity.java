package khantique.organisation.com.khantique;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


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

import khantique.organisation.com.khantique.Adapter.CartListAdaptor;
import khantique.organisation.com.khantique.Adapter.CartPojo;
import khantique.organisation.com.khantique.Adapter.ItemEntity;
import khantique.organisation.com.khantique.Adapter.Message;
import khantique.organisation.com.khantique.Adapter.SessionManagement;
import khantique.organisation.com.khantique.Adapter.Urls;
import khantique.organisation.com.khantique.Fragment.AboutUsFragment;
import khantique.organisation.com.khantique.Fragment.BlogFragment;
import khantique.organisation.com.khantique.Fragment.CategoryFragment;
import khantique.organisation.com.khantique.Fragment.ContactUsFragment;
import khantique.organisation.com.khantique.Fragment.HomeFragment;
import khantique.organisation.com.khantique.Fragment.LogOut;
import khantique.organisation.com.khantique.Fragment.NewsLetterFragment;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.CALL_PHONE;
import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
//

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    AlertDialog alertDialog;
    ImageView headerimage;
    ViewPager viewpager1;
    DrawerLayout drawer;
    HomeFragment home;
    Message m;
    SessionManagement session;
    TextView headertext, username1,username2;
    ArrayList<CartPojo> cartDetails_list=new ArrayList<CartPojo>();
    View hView;
    NavigationView navigationView;
    static FrameLayout frame_head;
    String user_id="";
    public static final int REQUEST_PERMISSION_CODE=1;
    int i=1;

    String name;
    TextView textView;
    String log="";
    String count;
    RelativeLayout headerlayout;
    SessionManagement sessionManagement;
    String userid;
    SharedPreferences sharedPrefotpaccheckst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        Toolbar toolbar1 = (Toolbar) findViewById(R.id.toolbarheader);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(null);

        checkRunTimePermission();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.addNewProduct);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            startActivity(new Intent(getApplicationContext(), ChatMessageActivity.class));

            }
        });

        sessionManagement = new SessionManagement(getApplicationContext());
        headerlayout = (RelativeLayout) findViewById(R.id.headerlayout);
        headerlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        headerimage=(ImageView)findViewById(R.id.search_header);
        textView = (TextView)findViewById(R.id.fab2);
        headerimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });


        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.setDrawerIndicatorEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        drawer.setDrawerListener(toggle);
        //
        toolbar1.setNavigationIcon(R.drawable.ic_dehaze);
        toolbar1.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(Gravity.LEFT);
            }
        });

        //  toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        hView = navigationView.getHeaderView(0);
        navigationView.setItemIconTintList(null);
        navigationView.getMenu().findItem(R.id.home).setVisible(true);

        if(sessionManagement.isLoggedIn()) {
            navigationView.getMenu().findItem(R.id.logout).setVisible(true);
            navigationView.getMenu().findItem(R.id.login).setVisible(false);
        }else {
            navigationView.getMenu().findItem(R.id.logout).setVisible(false);
            navigationView.getMenu().findItem(R.id.login).setVisible(true);

        }

        View header = navigationView.getHeaderView(0);

        home = new HomeFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, home);
        transaction.commit();
        frame_head = (FrameLayout)findViewById(R.id.frame_head);
        frame_head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(sessionManagement.isLoggedIn())
                {
                    Intent intent = new Intent(MainActivity.this,CartActivity.class);
                    startActivity(intent);

                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Please login first",Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                }

            }
        });
    }




    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else if(home.isVisible() && MainActivity.this!= null && !MainActivity.this.isFinishing()){
            showDilog();
        }
        else {
            super.onBackPressed();
        }
    }
// Permission giving at runtime

    private void checkRunTimePermission() {

        if(checkPermission()){

            Toast.makeText(MainActivity.this, "All Permissions Granted Successfully", Toast.LENGTH_LONG).show();

        }
        else {

            requestPermission();
        }
    }

    private void requestPermission() {

        ActivityCompat.requestPermissions(MainActivity.this, new String[]
                {
                        CAMERA,
                        ACCESS_COARSE_LOCATION,
                        READ_EXTERNAL_STORAGE,
                        WRITE_EXTERNAL_STORAGE,
                        CALL_PHONE

                },  REQUEST_PERMISSION_CODE);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {

            case  REQUEST_PERMISSION_CODE:

                if (grantResults.length > 0) {

                    boolean CameraPermission = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean ReadContactsPermission = grantResults[1] == PackageManager.PERMISSION_GRANTED;
//                    boolean ReadPhoneStatePermission = grantResults[2] == PackageManager.PERMISSION_GRANTED;

                    if (CameraPermission && ReadContactsPermission ) {

                     //   Toast.makeText(MainActivity.this, "Permission Granted", Toast.LENGTH_LONG).show();
                    }
                    else {
                     //   Toast.makeText(MainActivity.this,"Permission Denied", Toast.LENGTH_LONG).show();

                    }
                }

                break;
        }
    }

    public boolean checkPermission() {

        int FirstPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA);
        int SecondPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), ACCESS_COARSE_LOCATION);
        int ThirdPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE);
        int FourthPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        int FifthPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), CALL_PHONE);

        return FirstPermissionResult == PackageManager.PERMISSION_GRANTED &&
                SecondPermissionResult == PackageManager.PERMISSION_GRANTED &&
                ThirdPermissionResult == PackageManager.PERMISSION_GRANTED &&
                FourthPermissionResult == PackageManager.PERMISSION_GRANTED &&
                FifthPermissionResult == PackageManager.PERMISSION_GRANTED;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.home) {

            home = new HomeFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container, home);
            transaction.commit();
            // Handle the camera action
        }else if (id == R.id.category){
            CategoryFragment categoryFragment = new CategoryFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container, categoryFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }else if (id == R.id.cart){
           /* CartFragment cartFragment = new CartFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container, cartFragment);
            transaction.addToBackStack(null);
            transaction.commit();*/
            if(session.isLoggedIn())
            {
                startActivity(new Intent(getApplicationContext(), CartActivity.class));
            }else
            {
                Toast.makeText(getApplicationContext(),"Please login first",Toast.LENGTH_LONG).show();
                Intent intent=new Intent(MainActivity.this, LoginActivity.class);
                // intent.putExtra("send", activity);
                startActivity(intent);

            }

        }else if (id == R.id.blog){
            BlogFragment blogFragment = new BlogFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container, blogFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }else if (id == R.id.newsletter){
            NewsLetterFragment newsLetterFragment = new NewsLetterFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container, newsLetterFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }else if (id == R.id.about_us){
            AboutUsFragment aboutUsFragment = new AboutUsFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container, aboutUsFragment);
            transaction.addToBackStack(null);
            transaction.commit();

        }else if (id == R.id.contact_us){
            ContactUsFragment contactUsFragment = new ContactUsFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container, contactUsFragment);
            transaction.addToBackStack(null);
            transaction.commit();



        }else if (id == R.id.login){
           startActivity(new Intent(getApplicationContext(), LoginActivity.class));



        }else if (id == R.id.logout){

            LogOut logOut=new LogOut();
            FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container,logOut);
            transaction.addToBackStack(null);
            transaction.commit();
        }
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;

    }


    void showDilog(){
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Are you sure you want to exit ?");
        alertDialogBuilder.setCancelable( false );
        alertDialogBuilder.setPositiveButton("Exit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {

                finish();
            }
        });

        alertDialogBuilder.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        alertDialog = alertDialogBuilder.create();
        alertDialog.setOnShowListener( new DialogInterface.OnShowListener() {
                                           @Override
                                           public void onShow(DialogInterface arg0) {
                                               alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor( getResources().getColor( R.color.colorPrimary ));
                                               alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor( getResources().getColor( R.color.colorPrimary ));
                                           }
                                       }
        );
        alertDialog.show();
    }

    @Override
    protected void onResume() {
        if(sessionManagement.isLoggedIn())
        {
            session = new SessionManagement(MainActivity.this);
            HashMap<String, String> user1 = session.getUserDetails();
            userid = user1.get(session.KEY_ID);
          //  ImageSLideShow();
               new CartDetailsLoad().execute();
//            sharedPrefotpaccheckst = getSharedPreferences("MyPreffacultyaccheckstudent", Context.MODE_PRIVATE);
//            count = sharedPrefotpaccheckst.getString("count",null);
//            Log.d("kjhbibsivdb",""+count);
//            textView.setText(count);


        }else
        {
            textView.setText("0");
        }


        super.onResume();

        username2=(TextView)hView.findViewById(R.id.username2);
        session = new SessionManagement(this);
        if(sessionManagement.isLoggedIn()) {
            HashMap<String, String> user1 = session.getUserDetails();


            // names
            if (user1 != null) {
                try {
                    name = user1.get(session.KEY_NAME);
                    m.log("HELLO<><><>" + name);
                }catch (Exception e){}
                try {
                    if (!name.equals("null")) {
                        username2.setText(name);
                        username2.setVisibility(View.VISIBLE);
                    }
                }catch (Exception e){}

            }
        }



   //     super.onResume();

    }

    private String getResString(int resId){
        return getResources().getString(resId);
    }


    class CartDetailsLoad extends AsyncTask<String, Void, String> {

        private ProgressDialog pDialog;



        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
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

                        String total = jobject.getString("total_price");
                        cartDetails_list.add(cartPojo);
                    }

                    ArrayList<String> newll = new ArrayList<String>();
                    for (int k = 0; k < cartDetails_list.size(); k++) {
                        CartPojo obj = cartDetails_list.get(k);
                        String dd = obj.getId();
                        Log.d("newll_list", "kk1111 " + dd);
                        newll.add(dd);
                    }
                    String count = String.valueOf(newll.size());
                   // Toast.makeText(getApplicationContext(), count, Toast.LENGTH_LONG).show();
                    textView.setText(count);

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
