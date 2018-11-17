package com.example.mdbook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class CustomAdapter extends ArrayAdapter {
    String [] names;
    int [] flags;
    Context mContext;

    public CustomAdapter( Context context, String[] countryNames, int[]countryFlags) {
        super(context, R.layout.record_listview_item);
        this.names = countryNames;
        this.flags = countryFlags;
        this.mContext = context;
   }

   @Override
   public int getCount() {
       return names.length;
   }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mViewHolder = new ViewHolder();
        if (convertView == null) {

            LayoutInflater mInflater = (LayoutInflater) mContext.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.record_listview_item, parent, false);
            mViewHolder.mFlag = (ImageView) convertView.findViewById((R.id.imageView));
            mViewHolder.mName = (TextView) convertView.findViewById(R.id.textView3);
            convertView.setTag(mViewHolder);

        }
        else {
            mViewHolder = (ViewHolder)convertView.getTag();
        }
            mViewHolder.mFlag.setImageResource(flags[position]);
            mViewHolder.mName.setText(names[position]);

        return convertView;
    }

    static  class ViewHolder{
        ImageView mFlag;
        TextView mName;
    }
}
