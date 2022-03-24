package com.dingdingyijian.ddyj.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.dingdingyijian.ddyj.R;

import java.util.ArrayList;
import java.util.List;

public class GuideRecyclerAdapter extends RecyclerView.Adapter<GuideRecyclerAdapter.ViewHolder> {
    private List<Integer> mList;

    public GuideRecyclerAdapter(List<Integer> mList) {
        if (this.mList == null) {
            this.mList = new ArrayList<>();
        }
        this.mList = mList;
    }


    @NonNull
    @Override
    public GuideRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.guide_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GuideRecyclerAdapter.ViewHolder holder, int position) {
        holder.image.setImageResource(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        AppCompatImageView image;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
        }
    }
}
