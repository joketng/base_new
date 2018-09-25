package com.joketng.base.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.joketng.base.R;


/**
 * @author heyn
 * @Title: MoreAdapter
 * @Package com.jointem.zyb.adapter
 * @Description:更多
 * @date 2016/2/23 16:23
 */
public class MoreAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater mInflater;
    private int[] itemIcons;
    private String[] itemText;

    public MoreAdapter(Context context, int[] itemIcons, String[] itemText) {
        this.context = context;
        this.itemIcons = itemIcons;
        this.itemText = itemText;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return itemText.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ItemHolder holder = null;
        if (convertView == null) {
            holder = new ItemHolder();
            convertView = mInflater.inflate(R.layout.item_more, null);
            holder.imvMoreIcon = (ImageView) convertView.findViewById(R.id.imv_more_icon);
            holder.tvMoreText = (TextView) convertView.findViewById(R.id.tv_more_text);
            convertView.setTag(holder);
        } else {
            holder = (ItemHolder) convertView.getTag();
        }
        holder.imvMoreIcon.setImageResource(itemIcons[position]);
        holder.tvMoreText.setText(itemText[position]);
        holder.tvMoreText.setTextColor(context.getResources().getColor(R.color.gray));

        return convertView;
    }

    private class ItemHolder {
        ImageView imvMoreIcon;
        TextView tvMoreText;
    }
}
