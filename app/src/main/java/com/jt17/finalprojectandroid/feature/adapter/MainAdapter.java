package com.jt17.finalprojectandroid.feature.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jt17.finalprojectandroid.databinding.ItemsListLyBinding;
import com.jt17.finalprojectandroid.domain.model.ItemsModel;

import java.util.ArrayList;
import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainViewHolder> {
    public List<ItemsModel> itemsList = new ArrayList<>();
    public OnItemClickListener listener;

    public MainAdapter(OnItemClickListener listener) {
        this.listener = listener;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<ItemsModel> list) {
        itemsList.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemsListLyBinding itemBinding = ItemsListLyBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        return new MainViewHolder(itemBinding);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {
        ItemsModel item = itemsList.get(position);

        holder.itemBinding.idText.setText(String.valueOf(item.getId()));
        holder.itemBinding.nameText.setText(item.getName());
        holder.itemBinding.sumText.setText(item.getSum());

        holder.itemBinding.deleteItemButton.setOnClickListener(view -> {
//            itemsList.remove(position);
//            notifyItemRemoved(position);
            itemsList.clear();
            listener.onDeleteClick(item.getId());
        });
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }
}

