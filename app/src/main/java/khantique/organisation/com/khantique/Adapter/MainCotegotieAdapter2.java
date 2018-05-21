package khantique.organisation.com.khantique.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;



import java.util.ArrayList;

import khantique.organisation.com.khantique.R;

public class MainCotegotieAdapter2 extends BaseAdapter {
    String json;
    LayoutInflater mInflater;
    private Activity context;
    private ArrayList<ItemEntity> items=new ArrayList<ItemEntity>();
    public MainCotegotieAdapter2(Activity context, ArrayList<ItemEntity> items) {
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
    public View getView(int i, View view, ViewGroup viewGroup) {
        mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        view = mInflater.inflate(R.layout.category_item, null);
        TextView txtTitle = (TextView) view
                .findViewById(R.id.cot_item_text);
        ImageView imageView = (ImageView) view
                .findViewById(R.id.cot_item_img);
        txtTitle.setText(items.get(i).getCategory_name());

        return view;
    }
}
