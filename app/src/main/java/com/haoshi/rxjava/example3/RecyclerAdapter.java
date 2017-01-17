package com.haoshi.rxjava.example3;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.haoshi.R;
import com.haoshi.listview.VH;
import com.haoshi.rxjava.example3.bean.News;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: HaoShi
 */
public class RecyclerAdapter extends RecyclerView.Adapter<VH> {

    private Context context;
    private List<News.RecentBean> list = new ArrayList<>();


    public void setList(List<News.RecentBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View v = View.inflate(parent.getContext(), R.layout.rxjava_item, null);
        VH vh = new VH(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        holder.textView.setText(list.get(position).getTitle());
        Glide.with(context).load(list.get(position).getThumbnail()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
