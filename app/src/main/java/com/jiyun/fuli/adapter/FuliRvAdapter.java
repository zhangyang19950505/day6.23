package com.jiyun.fuli.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.jiyun.bean.FuliBean;
import com.jiyun.zhulong.R;

import java.util.ArrayList;
import java.util.List;

public class FuliRvAdapter extends RecyclerView.Adapter<FuliRvAdapter.ViewHolder> {

    private Context context;
    private List<FuliBean.DatasBean> datas=new ArrayList<>();


    public List<FuliBean.DatasBean> getDatas() {
        return datas;
    }

    public void initData(List<FuliBean.DatasBean> datas) {
        this.datas.addAll(datas);
        notifyDataSetChanged();
    }

    public FuliRvAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fuli_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FuliBean.DatasBean datasBean = datas.get(position);
        Glide.with(context).load(datasBean.getThumbnail()).into(holder.img_fuli);
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView img_fuli;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img_fuli = itemView.findViewById(R.id.img_fuli);
        }
    }
}
