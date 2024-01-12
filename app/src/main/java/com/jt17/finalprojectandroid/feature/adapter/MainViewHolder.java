package com.jt17.finalprojectandroid.feature.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jt17.finalprojectandroid.databinding.ItemsListLyBinding;

public class MainViewHolder extends RecyclerView.ViewHolder {
    public ItemsListLyBinding itemBinding;

    public MainViewHolder(@NonNull ItemsListLyBinding itemBinding) {
        super(itemBinding.getRoot());
        this.itemBinding = itemBinding;
    }
}
