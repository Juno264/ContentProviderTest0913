package com.index.musicrack;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class MusicAdapter extends ArrayAdapter<Music> {
    List<Music> mMusics;
    Context mContext;


    public MusicAdapter(Context context, int layoutResourceId, List<Music> objects) {
        super(context, layoutResourceId, objects);

        mContext = context;
        mMusics = objects;
    }

    @Override
    public int getCount() {
        return mMusics.size();
    }

    @Override
    public Music getItem(int position) {
        return mMusics.get(position);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.music, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final  Music item=getItem(position);

        if(item != null){
//            viewHolder.idTextView.setText("Id:"+item.id);

            String[] splitString = item.uri.split("/");
            String title = splitString[splitString.length - 1];
            viewHolder.titleTextView.setText(title);
//            viewHolder.uriTextView.setText("URI:"+item.uri);
                    viewHolder.container.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                    Intent intent = new Intent(mContext, Main2Activity.class);
                    intent.putExtra("URL", item.uri);
                    mContext.startActivity(intent);
                }
            });
//                @Override
//                        public void onClick(View v){
//                    item.
//                }
        }
        return convertView;
    }

    public static class ViewHolder {

        LinearLayout container;
        TextView idTextView;
        TextView titleTextView;
        TextView uriTextView;

        public ViewHolder(View view) {

            container = view.findViewById(R.id.container);
            idTextView = view.findViewById(R.id.id_textview);
            titleTextView = view.findViewById(R.id.title_textview);
            uriTextView = view.findViewById(R.id.uri_textview);
        }
    }


}
