package com.example.zerotransfer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Toast;

import com.nbsp.materialfilepicker.MaterialFilePicker;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.regex.Pattern;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.content.pm.PackageManager.PERMISSION_GRANTED;

public class FileAccessorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_accessor);
        startFilePicker();
    }

    public void startFilePicker() {
        boolean permissionGranted;
        permissionGranted = checkSelfPermission(READ_EXTERNAL_STORAGE) == PERMISSION_GRANTED;

        if (permissionGranted) {
            pickFile();
        } else {
            if(shouldShowRequestPermissionRationale(READ_EXTERNAL_STORAGE)) {
                showError();
            } else {
                requestPermissions(new String[] {READ_EXTERNAL_STORAGE}, 0);
            }
        }
    }

    private void showError() {
        Toast.makeText(this, "Allow external storage reading", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @NotNull String[] permissions, @NonNull @NotNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 0) {
            if (grantResults.length != 0 && grantResults[0] == PERMISSION_GRANTED) {
                pickFile();
            } else {
                showError();
            }
        }
    }

    public void pickFile() {
        File externalstorage = Environment.getExternalStorageDirectory();
        File folder = new File(externalstorage, "Downloads");

        MaterialFilePicker materialfilepicker = new MaterialFilePicker();
        materialfilepicker
                // Pass a source of context. Can be:
                //    .withActivity(Activity activity)
                //    .withFragment(Fragment fragment)
                //    .withSupportFragment(androidx.fragment.app.Fragment fragment)
                .withActivity(this)
                // With cross icon on the right side of toolbar for closing picker straight away
                .withCloseMenu(true)
                // Entry point path (user will start from it)
                .withPath(folder.getAbsolutePath())
                // Root path (user won't be able to come higher than it)
                .withRootPath(externalstorage.getAbsolutePath())
                // Showing hidden files
                .withHiddenFiles(true)
                // Want to choose only jpg images
                .withFilter(Pattern.compile(".*\\.(jpg|jpeg)$"))
                // Don't apply filter to directories names
                .withFilterDirectories(false)
                .withTitle("Sample title")
                .withRequestCode(1)
                .start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            if (data == null) {
                throw new IllegalArgumentException("data must not be null");
            }

            String path = data.getStringExtra(com.nbsp.materialfilepicker.ui.FilePickerActivity.RESULT_FILE_PATH);
            if (path != null) {
                Intent intent = getIntent();
                Bundle bundle = new Bundle();
                bundle.putString("path", path);
                intent.putExtras(bundle);
                setResult(RESULT_OK, intent);
            } else {
                setResult(RESULT_CANCELED);
            }
        }
        finish();
    }
}