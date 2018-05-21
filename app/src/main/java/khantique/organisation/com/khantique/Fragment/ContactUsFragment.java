package khantique.organisation.com.khantique.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import khantique.organisation.com.khantique.R;


public class ContactUsFragment extends Fragment {
  TextView email, usa_phone, egypt_phone;
    String text1;
    public ContactUsFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_contact_us, container, false);
         email= (TextView)view.findViewById(R.id.email);
        usa_phone= (TextView)view.findViewById(R.id.usa_phone);
        egypt_phone= (TextView)view.findViewById(R.id.egypt_phone);

        text1 = "<html><head>"
                + "<p align=\"justify\">"
                + "<style type=\"text/css\">body"
                + "</style></head>"
                + "<body>"
                + "</body></html>";
        text1+= "Please feel free to send us an e-mail with an inquiry or request and will be happy to respond to you in less than 24 hours.";
        text1+= "</p></body></html>";
        WebView webView = (WebView)view.findViewById(R.id.webView1);
        WebSettings webSettings = webView.getSettings();
        webSettings.setDefaultFontSize(15);
        webView.loadData(text1, "text/html", "utf-8");

         email.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent email = new Intent(Intent.ACTION_SEND);
                 email.putExtra(Intent.EXTRA_EMAIL, new String[]{"Contact-us@Khantique.com"});
                 email.putExtra(Intent.EXTRA_SUBJECT, "Feedback Khantique");
                 email.putExtra(Intent.EXTRA_TEXT, "Write your feedback here");
                 email.setType("message/rfc822");
                 try{
                     startActivity(Intent.createChooser(email, "Choose an Email client :"));
                 }catch (android.content.ActivityNotFoundException ex) {
                     Toast.makeText(getContext(), "Not send", Toast.LENGTH_SHORT).show();
                 }
             }
         });

        usa_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone1 = "001/917-935-7411";
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone1));
                startActivity(intent);
            }
        });

        egypt_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone2 = "002/010-6404-4477";
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone2));
                startActivity(intent);
            }
        });

        return  view;
    }
}
