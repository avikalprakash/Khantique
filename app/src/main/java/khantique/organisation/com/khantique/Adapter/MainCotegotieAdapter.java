package khantique.organisation.com.khantique.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import khantique.organisation.com.khantique.R;

public class MainCotegotieAdapter extends BaseAdapter {
    String json;
    LayoutInflater mInflater;
    private Activity context;
    private ArrayList<ItemEntity> items=new ArrayList<ItemEntity>();
    public MainCotegotieAdapter(Activity context, ArrayList<ItemEntity> items) {
        this.context = context;
        this.items=items;
    }
    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public boolean isEnabled(int i) {
        return true;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        view = mInflater.inflate(R.layout.cotegory_layout_adapter, null);
        TextView txtTitle = (TextView) view
                .findViewById(R.id.cat_name);
        ImageView imageView = (ImageView) view
                .findViewById(R.id.cat_image);
        txtTitle.setText(items.get(i).getCategory_name());
        try {
            Picasso.with(context).load(items.get(i).getCategory_image()).into(imageView);
        }catch (Exception e){}
        return view;
    }

}
