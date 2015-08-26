package com.sample.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sample.App;
import com.sample.R;
import com.sample.adapter.RecyclerViewAdapter;
import com.sample.model.SearchModel;
import com.sample.service.ApiService;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import dagger.Lazy;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ShutterStockFragment extends Fragment {

    private final static String LOG_TAG = ShutterStockFragment.class.getName();

    @Inject
    RestAdapter mRestAdapter;

    @Inject
    Lazy<RecyclerViewAdapter> mLazyRecyclerAdapter;

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;

    public ShutterStockFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((App) getActivity().getApplication()).getComponent().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_shutterstock, container, false);
        ButterKnife.bind(this, view);

        setHasOptionsMenu(true);

        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        ApiService service = mRestAdapter.create(ApiService.class);
        service.getMovieList(new Callback<SearchModel>() {

            @Override
            public void success(SearchModel searchModel, Response response) {
                if (searchModel != null) {
                    RecyclerViewAdapter adapter = mLazyRecyclerAdapter.get();
                    adapter.setData(searchModel);
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e(LOG_TAG, error.toString());
            }
        });
    }
}
