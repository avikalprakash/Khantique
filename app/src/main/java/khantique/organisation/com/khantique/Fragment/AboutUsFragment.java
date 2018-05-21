package khantique.organisation.com.khantique.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import khantique.organisation.com.khantique.R;


public class AboutUsFragment extends Fragment {


    public AboutUsFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_about_us, container, false);
        String htmlText = " %s ";
        String text1, text2;


        text1 = "<html><head>"
                + "<p align=\"justify\">"
                + "<style type=\"text/css\">body"
                + "</style></head>"
                + "<body>"
                + "</body></html>";
        text1+= "Khantique name is a blend of Khan and Antiques, and Khan is short for Khan El Khalili which is the focus of this site, and so the name of Khantique came out. Khan El Khalili is located in the heart of Islamic Cairo, it was given this name in the 14th Century and by the late of 15th century, the district of Khan el-Khalili had also become the major center of foreign trade, including the sale of slaves and precious stones.\n" +
                "\n" +
                "Khantique came out to the light early in 2017, with the main target of online displaying most “if not all “products offered at Khan el Khaily. E-Commerce technology is Khantique backbone that made Khan El Khalily market highly accessible remotely to anyone, with having high level of logistics management in place, all products could be easily delivered at any place around the world in short time and at a very moderate cost, making the buying through Khanique a very exciting and enjoyable experience. Khantique does not just offer products online, but rather aims to deliver authenticity to the rest of the world!";
        text1+= "</p></body></html>";

        text2 = "<html><head>"
                + "<p align=\"justify\">"
                + "<style type=\"text/css\">"
                + "</style></head>"
                + "<body>"
                + "</body></html>";
        text2+= "At Khantique, we are determined to serve you at a world class service level, so your satisfaction is guaranteed as you get what you exactly bought through us, the exact specification, the exact image that resembles what you get, and at the price that was tagged. At any point you feel dissatisfied, you either get a new product of the item you selected and paid for or get a full refund for the value of the money you spent, and that is all based on your wish and request, we do not mess with you and we will satisfy you one way or another.\n" +
                "\n" +
                "The indicator for our success is to see you return back and have you a regular customer, that is our main goal, and for us to make that happen, we offer you with the best product at the best cost, so trust that we do a lot of negotiation on your behalf, in other words the products you see here at Khantqiue had been selected from Khan El Khalily with great care to offer you the best offered in the market and at a very good deal, the kind that you would not be able to make by yourself, so trust that we do a lot on your behalf, and that is where we see us at a great value to you.";
        text2+= "</p></body></html>";

        WebView webView = (WebView)view.findViewById(R.id.webView1);
        WebView webView2 = (WebView)view.findViewById(R.id.webView2);
        WebSettings webSettings = webView.getSettings();
        WebSettings webSettings2 = webView2.getSettings();
        webSettings.setDefaultFontSize(15);
        webSettings2.setDefaultFontSize(15);
        webView.loadData(text1, "text/html", "utf-8");
        webView2.loadData(text2, "text/html", "utf-8");
        return  view;
    }
}
