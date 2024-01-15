package com.jt17.finalprojectandroid.feature.display;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jt17.finalprojectandroid.databinding.ActivityDisplayDataBinding;
import com.jt17.finalprojectandroid.domain.model.ItemsModel;
import com.jt17.finalprojectandroid.feature.adapter.MainAdapter;
import com.jt17.finalprojectandroid.feature.adapter.OnItemClickListener;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;

public class DisplayDataActivity extends AppCompatActivity implements OnItemClickListener {
    private ActivityDisplayDataBinding binding;
    private DisplayDataViewModel viewModel;
    private MainAdapter adapter;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDisplayDataBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(DisplayDataViewModel.class);

        setupRecycler();
        getDataFromDb();

        binding.backButton.setOnClickListener(view -> finish());
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private void getDataFromDb() {
        Observer<List<ItemsModel>> observer = data -> {
            if (data.isEmpty()) {
                binding.emptyText.setVisibility(View.VISIBLE);
                binding.myRecycler.setVisibility(View.GONE);
            } else {
                adapter.setData(data);
            }
        };
        viewModel.getAllItemsData().observe(this, observer);
    }

    private void setupRecycler() {
        adapter = new MainAdapter(this);
        final RecyclerView myRecycler = binding.myRecycler;

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        myRecycler.setLayoutManager(layoutManager);
        myRecycler.setAdapter(adapter);
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(String name, String sum) {
        showToast("name: " + name + "\n" + "sum: " + sum);
    }

    @Override
    public void onDeleteClick(int id) {
        viewModel.deleteItem(id)
                .andThen(Completable.fromRunnable(() -> showToast("Deleted!")))
                .subscribe();
    }

    @Override
    protected void onStop() {
        super.onStop();
        showToast("OnStopped: 2-activity");
        viewModel.onStopCountingAndShowNotification();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}