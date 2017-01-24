package com.letterlocation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;

import java.util.List;

public class LetterAdapter extends BaseAdapter implements SectionIndexer {
    private List<LetterEntity> list = null;
    private Context mContext;

    public LetterAdapter(Context mContext, List<LetterEntity> list) {
        this.mContext = mContext;
        this.list = list;
    }

    public void updateListView(List<LetterEntity> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public int getCount() {
        return this.list.size();
    }

    public Object getItem(int position) {
        return list.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup arg2) {
        ViewHolder viewHolder = null;
        final LetterEntity mContent = list.get(position);
        if (view == null) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.list_item, null);
            viewHolder.mItemContent = (TextView) view.findViewById(R.id.item_content);
            viewHolder.mItemLetter = (TextView) view.findViewById(R.id.item_letter);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        int section = getSectionForPosition(position);

        if (position == getPositionForSection(section)) {
            viewHolder.mItemLetter.setVisibility(View.VISIBLE);
            viewHolder.mItemLetter.setText(mContent.getLetter());
        } else {
            viewHolder.mItemLetter.setVisibility(View.GONE);
        }

        viewHolder.mItemContent.setText(this.list.get(position).getContent());

        return view;

    }

    final static class ViewHolder {
        TextView mItemLetter;
        TextView mItemContent;
    }

    public int getSectionForPosition(int position) {
        return list.get(position).getLetter().charAt(0);
    }

    public int getPositionForSection(int section) {
        for (int i = 0; i < getCount(); i++) {
            String sortStr = list.get(i).getLetter();
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == section) {
                return i;
            }
        }

        return -1;
    }

    private String getAlpha(String str) {
        String  sortStr = str.trim().substring(0, 1).toUpperCase();
        if (sortStr.matches("[A-Z]")) {
            return sortStr;
        } else {
            return "#";
        }
    }

    @Override
    public Object[] getSections() {
        return null;
    }
}