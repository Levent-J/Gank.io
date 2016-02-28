package com.example.levent_j.gank.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.example.levent_j.gank.R;
import com.example.levent_j.gank.adapter.ResultAdapter;
import com.example.levent_j.gank.bean.Result;
import com.example.levent_j.gank.net.Api;
import com.example.levent_j.gank.utils.SpaceItemDecoration;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observer;

/**
 * Created by levent_j on 16-2-27.
 */
public class PageFragment extends Fragment{
    @Bind(R.id.refresh)
    MaterialRefreshLayout materialRefreshLayout;
    @Bind(R.id.recycler_view)
    public RecyclerView recyclerView;

    private ResultAdapter resultAdapter;
    private boolean isLoading;
    private String mPage;
    private static String KEY = "ARGS";
    private int spacingInPixels;
    private int pages;
    private int items;
    private boolean isClear;

    public static PageFragment newInstance(String title){
        PageFragment pageFragment = new PageFragment();
        Bundle args = new Bundle();
        args.putString(KEY,title);
        pageFragment.setArguments(args);
        return pageFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments()!=null){
            mPage = getArguments().getString(KEY,"all");
        }
        resultAdapter = new ResultAdapter(getActivity());
        isLoading = false;
        spacingInPixels = getResources().getDimensionPixelSize(R.dimen.space);
        pages = 1;
        items = 5;
        isClear = true;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item,container,false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view,Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //设置recyclerview
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setAdapter(resultAdapter);
        recyclerView.addItemDecoration(new SpaceItemDecoration(spacingInPixels));
        recyclerView.setHasFixedSize(true);
        materialRefreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                loadDate();
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                super.onRefreshLoadMore(materialRefreshLayout);
                loadMoreDate();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //第一次加载数据
        Api.getInstance().getDates(mPage,String.valueOf(items), String.valueOf(pages), resultObserver);
        isLoading = false;
    }

    private void loadDate() {
        //调用api获取数据并刷新
        if (!isLoading){
            isLoading = true;
            Api.getInstance().getDates(mPage,String.valueOf(items*pages),String.valueOf(1),resultObserver);
            isClear = true;
        }
    }
    private void loadMoreDate() {
        //调用api获取数据并刷新
        if (!isLoading){
            isLoading = true;
            pages++;
            Api.getInstance().getDates(mPage,String.valueOf(items),String.valueOf(pages),resultObserver);
            isClear = false;
        }
    }

    private Observer<Result> resultObserver = new Observer<Result>() {
        @Override
        public void onCompleted() {
            isLoading = false;
            materialRefreshLayout.finishRefresh();
            materialRefreshLayout.finishRefreshLoadMore();
            recyclerView.setAdapter(resultAdapter);
        }

        @Override
        public void onError(Throwable e) {
            e.printStackTrace();
            Log.d("Net", "NET_ERROR");
        }

        @Override
        public void onNext(Result result) {
            resultAdapter.updateResult(result.getResultses(),isClear);
        }
    };
}
