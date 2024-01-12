package com.jt17.finalprojectandroid.feature.main;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import com.jt17.finalprojectandroid.R;
import com.jt17.finalprojectandroid.databinding.ActivityMainBinding;
import com.jt17.finalprojectandroid.feature.display.DisplayDataActivity;

import io.reactivex.rxjava3.core.Completable;

@RequiresApi(api = Build.VERSION_CODES.N)
public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View bindingView = binding.getRoot();
        setContentView(bindingView);

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        displayName();
        displaySumOfArguments();

        binding.displayButton.setOnClickListener(view -> {
            Intent intent = new Intent(this, DisplayDataActivity.class);
            startActivity(intent);
        });

        binding.addButton.setOnClickListener(view -> addNewItemToDb());

        binding.clearButton.setOnClickListener(view -> viewModel.clearAll()
                .andThen(Completable.fromRunnable(() -> showToast("All data cleared!")))
                .subscribe()
        );
    }

    private void addNewItemToDb() {
        final String labelName = binding.labelName.getText().toString();
        final String sumOfArgs = binding.labelSumOfArgs.getText().toString();
        if (!labelName.equals("none") && !sumOfArgs.equals("0")) {
            viewModel.insertData(labelName, sumOfArgs).
                    andThen(Completable.fromRunnable(() -> showToast("Added")))
                    .subscribe();
        } else {
            showToast("fill in the fields!");
        }
    }

    private void displayName() {
        binding.nameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                final String nameEditText = binding.nameEditText.getText().toString();

                if (nameEditText.isEmpty()) {
                    binding.labelName.setText(R.string.none);
                } else {
                    binding.labelName.setText(nameEditText);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    private void displaySumOfArguments() {
        // listen argument1 edit text
        binding.argument1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                final String firstArgumentValue = binding.argument1.getText().toString();
                final String secondArgumentValue = binding.argument2.getText().toString();

                if (!firstArgumentValue.isEmpty() && !secondArgumentValue.isEmpty()) {
                    incrementArguments(
                            Integer.parseInt(firstArgumentValue),
                            Integer.parseInt(secondArgumentValue)
                    );
                } else if (firstArgumentValue.isEmpty() && secondArgumentValue.isEmpty()) {
                    binding.labelSumOfArgs.setText("0");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        // listen argument2 edit text
        binding.argument2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                final String secondArgumentValue = binding.argument2.getText().toString();
                final String firstArgumentValue = binding.argument1.getText().toString();

                if (!firstArgumentValue.isEmpty() && !secondArgumentValue.isEmpty()) {
                    incrementArguments(
                            Integer.parseInt(firstArgumentValue),
                            Integer.parseInt(secondArgumentValue)
                    );
                } else if (firstArgumentValue.isEmpty() && secondArgumentValue.isEmpty()) {
                    binding.labelSumOfArgs.setText("0");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    private void incrementArguments(int arg1, int arg2) {
        final int sumOfArguments = arg1 + arg2;
        binding.labelSumOfArgs.setText(String.valueOf(sumOfArguments));
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    private void getNotificationPermission() {
        if (ContextCompat.checkSelfPermission(
                this, android.Manifest.permission.POST_NOTIFICATIONS
        ) != PackageManager.PERMISSION_GRANTED) {

            // check if the user has previously denied the permission
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this, android.Manifest.permission.POST_NOTIFICATIONS
            )) {
                // Show an explanation to the user
                // You can customize the message or UI based on your requirements
            }

            // Request the permission
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{android.Manifest.permission.POST_NOTIFICATIONS},
                    1001
            );
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    @Override
    protected void onStart() {
        super.onStart();
        getNotificationPermission();
    }

    @Override
    protected void onStop() {
        super.onStop();
        showToast("OnStopped: main");
        viewModel.onStopCountingAndShowNotification();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}