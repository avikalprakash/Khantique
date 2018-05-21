package khantique.organisation.com.khantique;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import khantique.organisation.com.khantique.Adapter.ItemEntity;
import khantique.organisation.com.khantique.Adapter.SessionManagement;
import khantique.organisation.com.khantique.Adapter.Urls;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
   ImageView back;
   TextView create_account, login;
    String username="admin";
    String password="Maikhaled_2020";
    EditText editTextEmail, editTextpassword;
    String user_email;
    String user_nicename;
    CheckBox hidePass;
    String user_login;
    private SessionManagement session;
    String ID;
    ProgressDialog pDialog;
   // String activity="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        back=(ImageView)findViewById(R.id.back);
        login=(TextView)findViewById(R.id.login);
        create_account=(TextView)findViewById(R.id.create_account);
        editTextEmail=(EditText)findViewById(R.id.email);
        editTextpassword=(EditText)findViewById(R.id.password);
        hidePass=(CheckBox)findViewById(R.id.hidePass);
        create_account.setOnClickListener(this);
        back.setOnClickListener(this);
        login.setOnClickListener(this);
       // activity=getIntent().getStringExtra("send");


        // Session manager
        session = new SessionManagement(getApplicationContext());

        // Check if user is already logged in or not
        if (session.isLoggedIn()) {
            // User is already logged in. Take him to main activity
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        hidePass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!isChecked)
                {
                    editTextpassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                else
                {
                    editTextpassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.create_account:
                startActivity(new Intent(getApplicationContext(), SignupActivity.class));
                break;

            case R.id.back:
                finish();
                break;

            case R.id.login:

               new Login().execute();
             //   Login();

                break;
        }
    }


    class Login extends AsyncTask<String, Void, String> {

        private ProgressDialog pDialog;

        String username=editTextEmail.getText().toString();
        String password=editTextpassword.getText().toString();


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(LoginActivity.this);
            pDialog.setMessage("Please Wait ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();

        }

        @Override
        protected String doInBackground(String... args) {
            String s = "";

            try {
                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(Urls.login);



                List < NameValuePair > nameValuePairs = new ArrayList < NameValuePair > ();
                nameValuePairs.add(new BasicNameValuePair("uname", username));
                nameValuePairs.add(new BasicNameValuePair("upass", password));
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                // Execute HTTP Post Request
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
        protected void onPostExecute(String json) {
            super.onPostExecute(json);
            pDialog.dismiss();
            try {
                JSONObject objone = new JSONObject(json);
                Integer check  = objone.getInt("status");
                String mgs = objone.getString("msg");
                if (mgs.equals("success")){
                    Toast.makeText(getApplicationContext(), "Login Successfully", Toast.LENGTH_LONG).show();
                    JSONArray jsonArray = objone.getJSONArray("info");
                    Log.d("sizee", "kk1111 " + jsonArray.length());
                    for (int i = 0; i < jsonArray.length(); i++) {
                        Log.d("object", "kk1111 " + jsonArray.getJSONObject(i).toString());
                        JSONObject jobject = jsonArray.getJSONObject(i);
                        ID = jobject.getString("ID");
                        user_login = jobject.getString("user_login");
                        user_nicename = jobject.getString("user_nicename");
                        user_email = jobject.getString("user_email");
                        String user_url = jobject.getString("user_url");

                        String user_registered = jobject.getString("user_registered");
                        String user_activation_key = jobject.getString("user_activation_key");
                        String user_status = jobject.getString("user_status");
                        String display_name = jobject.getString("display_name");
                    }
                    session.createLoginSession(user_login,user_nicename, user_email, ID);

                  /*  if (activity.equals("activity1")){
                        startActivity(new Intent(getApplicationContext(), ProductDetailsActivity.class));
                    }else {*/

                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);

                }else {
                    Toast.makeText(getApplicationContext(), "please enter correct username & password", Toast.LENGTH_LONG).show();
                }



            } catch (JSONException e) {
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




    public void Login() {

        String user = editTextEmail.getText().toString().trim();
        String pass = editTextpassword.getText().toString().trim();
   //     final String tokenid = TokenSave.getInstance(LoginActivity.this).getDeviceToken();

        if (TextUtils.isEmpty(user)) {
            editTextEmail.requestFocus();
            editTextEmail.setError("This Field Is Mandatory");
        } else if (TextUtils.isEmpty(pass)) {
            editTextpassword.requestFocus();
            editTextpassword.setError("This Field Is Mandatory");
        }  else {

            pDialog = new ProgressDialog(LoginActivity.this);
            // Showing progress dialog before making http request
            pDialog.setMessage("Loading...");
            pDialog.show();

            Map<String, String> postParam = new HashMap<String, String>();
            // postParam.put("session_id", sessionid);
            postParam.put("uname", user);
            postParam.put("upass", pass);



            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                    Urls.login, new JSONObject(postParam),
                    new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject objone) {
                            pDialog.dismiss();
                            Log.d("tag", objone.toString());
                            try {
                               // JSONObject objone = new JSONObject(json);
                                Integer check  = objone.getInt("status");
                                String mgs = objone.getString("msg");
                                if (mgs.equals("success")){
                                    Toast.makeText(getApplicationContext(), "Login Successfully", Toast.LENGTH_LONG).show();
                                    JSONArray jsonArray = objone.getJSONArray("info");
                                    Log.d("sizee", "kk1111 " + jsonArray.length());
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        Log.d("object", "kk1111 " + jsonArray.getJSONObject(i).toString());
                                        JSONObject jobject = jsonArray.getJSONObject(i);
                                        ID = jobject.getString("ID");
                                        user_login = jobject.getString("user_login");
                                        user_nicename = jobject.getString("user_nicename");
                                        user_email = jobject.getString("user_email");
                                        String user_url = jobject.getString("user_url");

                                        String user_registered = jobject.getString("user_registered");
                                        String user_activation_key = jobject.getString("user_activation_key");
                                        String user_status = jobject.getString("user_status");
                                        String display_name = jobject.getString("display_name");
                                    }
                                    session.createLoginSession(user_login,user_nicename, user_email, ID);

                  /*  if (activity.equals("activity1")){
                        startActivity(new Intent(getApplicationContext(), ProductDetailsActivity.class));
                    }else {*/

                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);

                                }else {
                                    Toast.makeText(getApplicationContext(), "please enter correct username & password", Toast.LENGTH_LONG).show();
                                }



                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    pDialog.dismiss();
                    VolleyLog.d("tag", "Error: " + error.getMessage());
                    //  hideProgressDialog();
                }
            }) {

                /**
                 * Passing some request headers
                 */
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("Content-Type", "form-data; charset=utf-8");
                    return headers;
                }


            };

            jsonObjReq.setTag("tag");
            // Adding request to request queue
            RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
            queue.add(jsonObjReq);

        }
    }
}
