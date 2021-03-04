package com.akshay.mypicsumapplication.ui.imagelist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;


import com.akshay.mypicsumapplication.R;
import com.akshay.mypicsumapplication.databinding.ImageListBinding;
import com.akshay.mypicsumapplication.model.responses.ImageListResponse;
import com.bumptech.glide.Glide;

import java.util.List;

public class ImageListAdapter extends RecyclerView.Adapter<ImageListViewHolder> {

    private Context mContext;
    private List<ImageListResponse> imageList;

    ImageListAdapter(Context mContext, List<ImageListResponse> imageList) {
        this.mContext = mContext;
        this.imageList = imageList;
    }

    public void updateList(List<ImageListResponse> imageList) {
        if (imageList != null) {
            this.imageList = imageList;
        }
        notifyDataSetChanged();
    }

    @Override
    public ImageListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ImageListBinding imageListBinding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.recyclerview_row_item, parent, false);
        return new ImageListViewHolder(imageListBinding);
    }

    @Override
    public void onBindViewHolder(final ImageListViewHolder holder, int position) {
        ImageListResponse listItem = imageList.get(position);
        holder.imageListBinding.tvTitle.setText( imageList.get(position).getAuthor());
        Glide.with(mContext).load("https://picsum.photos/300/300?image=" + String.valueOf(listItem.getId())).into(holder.imageListBinding.ivImage);

    }

    @Override
    public int getItemCount() {
        if (imageList != null)
            return imageList.size();
        return 0;
    }
}

class ImageListViewHolder extends RecyclerView.ViewHolder {


    ImageListBinding imageListBinding;

    ImageListViewHolder(ImageListBinding imageListBinding) {
        super(imageListBinding.getRoot());
        this.setIsRecyclable(false);
        this.imageListBinding = imageListBinding;
        setIsRecyclable(false);

    }
}
