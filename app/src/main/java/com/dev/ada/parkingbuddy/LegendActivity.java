package com.dev.ada.parkingbuddy;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class LegendActivity extends AppCompatActivity {

    ArrayList<LegendActivity.KeyItem> mKeyItems = new ArrayList<LegendActivity.KeyItem>();
    ListView mKeyList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_legend);

        setTitle("Legend");

        mKeyItems.add(new LegendActivity.KeyItem("No Parking", "", R.drawable.no_parking_big));
        mKeyItems.add(new LegendActivity.KeyItem("Fire Hydrant", "(No Parking)", R.drawable.hydrantbig));
        mKeyItems.add(new LegendActivity.KeyItem("Unrestricted Parking", "Parking anytime for free!", R.drawable.free_big));
        mKeyItems.add(new LegendActivity.KeyItem("Restricted Parking", "May require permit", R.drawable.rbig));
        mKeyItems.add(new LegendActivity.KeyItem("Paid Parking", "Click for Hours/Rate/Pay-by-Phone #", R.drawable.pay_big));
        mKeyItems.add(new LegendActivity.KeyItem("Time Limited Parking", "Click for Time Limit in hours", R.drawable.clock_big));

//        // DrawerLayout
//        mKeyLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
//
//        // Populate the Navigation Drawer with options
//        mDrawerPane = (RelativeLayout) findViewById(R.id.drawerPane);
        mKeyList = (ListView) findViewById(R.id.keyList);
        LegendActivity.KeyListAdapter adapter = new LegendActivity.KeyListAdapter(this, mKeyItems);
        mKeyList.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    class KeyItem {
        String mTitle;
        String mSubtitle;
        int mIcon;

        public KeyItem(String title, String subtitle, int icon) {
            mTitle = title;
            mSubtitle = subtitle;
            mIcon = icon;
        }
    }

    class KeyListAdapter extends BaseAdapter {

        Context mContext;
        ArrayList<LegendActivity.KeyItem> mNavItems;

        public KeyListAdapter(Context context, ArrayList<LegendActivity.KeyItem> keyItems) {
            mContext = context;
            mKeyItems = keyItems;
        }

        @Override
        public int getCount() {
            return mKeyItems.size();
        }

        @Override
        public Object getItem(int position) {
            return mKeyItems.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;

            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.key_item, null);
            } else {
                view = convertView;
            }

            TextView titleView = (TextView) view.findViewById(R.id.key_title);
            TextView subtitleView = (TextView) view.findViewById(R.id.key_subTitle);
            ImageView iconView = (ImageView) view.findViewById(R.id.key_icon);

            titleView.setText(mKeyItems.get(position).mTitle);
            subtitleView.setText(mKeyItems.get(position).mSubtitle);
            iconView.setImageResource(mKeyItems.get(position).mIcon);

            return view;
        }
    }

}
