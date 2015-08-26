package com.sample.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.sample.R;
import com.sample.model.SearchModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private List<SearchModel.Datum> items;

    public RecyclerViewAdapter() {
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_image, parent, false);
        return new ViewHolder(v);
    }

    public void setData(SearchModel searchModel) {
        this.items = searchModel.data;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        SearchModel.Datum movie = items.get(position);

        holder.trailer.setImageBitmap(null);
        Picasso.with(holder.trailer.getContext()).load(movie.assets.preview.url).into(holder.trailer);

        holder.itemView.setTag(movie);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView trailer;

        public ViewHolder(View itemView) {
            super(itemView);
            trailer = (ImageView) itemView.findViewById(R.id.trailerCover);
        }
    }
}
