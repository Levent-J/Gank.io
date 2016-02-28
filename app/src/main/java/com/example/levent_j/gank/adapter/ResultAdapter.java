package com.example.levent_j.gank.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.levent_j.gank.R;
import com.example.levent_j.gank.bean.Result;
import com.example.levent_j.gank.bean.Results;
import com.example.levent_j.gank.utils.Util;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by levent_j on 16-2-28.
 */
public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.mViewHolder>{

    private ArrayList<Results> resultsArrayList;
    private Context context;
    private final LayoutInflater layoutInflater;


    public ResultAdapter(Context context){
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        resultsArrayList = new ArrayList<>();
    }

    @Override
    public mViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.result_item,null);
        return new mViewHolder(view);
    }

    @Override
    public void onBindViewHolder(mViewHolder holder, int position) {
        Results results = resultsArrayList.get(position);
        //设置item显示
        holder.editer.setText(results.getWho());
//        holder.desc.setText(Util.unicode2String(results.getDesc()));
        holder.desc.setText(results.getDesc());
        //截取字符串
        String s = results.getPublishedAt().substring(0,10);
        holder.time.setText("创建于"+s);
    }

    @Override
    public int getItemCount() {
        return resultsArrayList.size();
    }

    public void updateResult(Results[] resultses,boolean isClear) {
        if (isClear){
            resultsArrayList.clear();
        }

        for (int i=0;i<resultses.length;i++){
            resultsArrayList.add(resultses[i]);
        }
        notifyDataSetChanged();
    }

    class mViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @Bind(R.id.tv_editer)
        TextView editer;
        @Bind(R.id.tv_desc)
        TextView desc;
        @Bind(R.id.tv_time)
        TextView time;
        @Bind(R.id.card_view)
        CardView cardView;

        public mViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            cardView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.card_view:
                    String url = resultsArrayList.get(getPosition()).getUrl();
                    Intent intent= new Intent();
                    intent.setAction("android.intent.action.VIEW");
                    Uri content_url = Uri.parse(url);
                    intent.setData(content_url);
                    context.startActivity(intent);
                    Log.d("Click",resultsArrayList.get(getPosition()).getUrl());
                    break;
            }
        }
    }
}
