package khantique.organisation.com.khantique.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import khantique.organisation.com.khantique.R;


public class BlogFragment extends Fragment {


    public BlogFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_blog, container, false);
        String text1, text2;

        text1 = "<html><head>"
                + "<p align=\"justify\">"
                + "<style type=\"text/css\">"
                + "</style></head>"
                + "<body>"
                + "</body></html>";
        text1+= "A cartouche is an oval frame which surrounds the hieroglyphs that make up the name of an Egyptian God or royal person. The above example is based on the cartouche of Tutankhamun. It represents a looped rope which has the magical power to protect the name that is written inside it. A cartouche was meant to protect against";
        text1+= "</p></body></html>";

        text2 = "<html><head>"
                + "<p align=\"justify\">"
                + "<style type=\"text/css\">"
                + "</style></head>"
                + "<body>"
                + "</body></html>";
        text2+= "Al Maghrib, the Arabic name for Morocco, means “far west” or “where the sun sets.” When the Arabs first arrived in northern Africa in the seventh centuryC.E., Morocco was believed to be the westernmost point in the world. At that time, the Maghrib region included the countries that are today Morocco, Algeria, and Tunisia.";
        text2+= "</p></body></html>";



        WebView webView = (WebView)view.findViewById(R.id.webView1);
        WebView webView2 = (WebView)view.findViewById(R.id.webView2);
        WebSettings webSettings = webView.getSettings();
        webSettings.setDefaultFontSize(15);
        WebSettings webSettings2 = webView2.getSettings();
        webSettings2.setDefaultFontSize(15);
        webView.loadData(text1, "text/html", "utf-8");
        webView2.loadData(text2, "text/html", "utf-8");
        return  view;
    }
}
