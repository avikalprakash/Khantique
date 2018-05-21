package khantique.organisation.com.khantique;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import khantique.organisation.com.khantique.Adapter.Urls;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView back;
    TextView already_user, signup;
    CheckBox hidePass;
    EditText editTextPassword, firstname, lastname, editTexteEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        back=(ImageView)findViewById(R.id.back);
        already_user=(TextView)findViewById(R.id.already_user);
        signup=(TextView)findViewById(R.id.signup);
        hidePass = (CheckBox)findViewById(R.id.hidePass);
        firstname=(EditText)findViewById(R.id.firstname);
        lastname=(EditText)findViewById(R.id.lastName);
        editTexteEmail=(EditText)findViewById(R.id.email);
        editTextPassword=(EditText)findViewById(R.id.password);
        already_user.setOnClickListener(this);
        signup.setOnClickListener(this);
        back.setOnClickListener(this);

        hidePass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!isChecked)
                {
                    editTextPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                else
                {
                    editTextPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }

            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.already_user:
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                break;

            case R.id.back:
                finish();
                break;

            case R.id.signup:
             new Register().execute();
                break;
        }
    }

    class Register extends AsyncTask<String, Void, String> {

        private ProgressDialog pDialog;

        String username=firstname.getText().toString()+" "+lastname.getText().toString();
        String password=editTextPassword.getText().toString();
        String email=editTexteEmail.getText().toString();


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(SignupActivity.this);
            pDialog.setMessage("loading ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();

        }

        @Override
        protected String doInBackground(String... args) {
            String s = "";

            try {
                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(Urls.register);


                List< NameValuePair > nameValuePairs = new ArrayList< NameValuePair >();
                nameValuePairs.add(new BasicNameValuePair("uname", username));
                nameValuePairs.add(new BasicNameValuePair("upass", password));
                nameValuePairs.add(new BasicNameValuePair("uemail", email));
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
                    Toast.makeText(getApplicationContext(), "Register Successfully", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));


                }else {
                    String msg = objone.getString("msg");
                    Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
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
}
