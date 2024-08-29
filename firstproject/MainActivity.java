package com.example.firstproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.io.File;
import java.util.HashSet;
import java.util.Set;
import android.content.SharedPreferences;
import android.content.Context;

public class MainActivity extends AppCompatActivity {

    private EditText editTextFileName;
    private Button buttonCreate, buttonDelete, buttonOpen, buttonShow;

    private static final String FILE_NAMES_PREF = "FileNamesPref";
    private static final String FILE_NAMES_KEY = "fileNames";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextFileName = findViewById(R.id.editTextFileName);
        buttonCreate = findViewById(R.id.buttonCreate);
        buttonDelete = findViewById(R.id.buttonDelete);
        buttonOpen = findViewById(R.id.buttonOpen);
        buttonShow = findViewById(R.id.buttonShow);

        buttonCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fileName = editTextFileName.getText().toString().trim();
                if (fileName.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter a file name", Toast.LENGTH_SHORT).show();
                } else if (!isFileNameValid(fileName)) {
                    Toast.makeText(MainActivity.this, "Invalid file name", Toast.LENGTH_SHORT).show();
                } else {
                    // Navigate to FileContentActivity to create or edit file
                    Intent intent = new Intent(MainActivity.this, FileContentActivity.class);
                    intent.putExtra("FILE_NAME", fileName);
                    startActivity(intent);
                    // Add file to SharedPreferences
                    addFileNameToPreferences(fileName);
                }
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fileName = editTextFileName.getText().toString().trim();
                if (fileName.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter a file name", Toast.LENGTH_SHORT).show();
                } else if (removeFile(fileName)) {
                    Toast.makeText(MainActivity.this, "File deleted successfully", Toast.LENGTH_SHORT).show();
                    // Remove file name from SharedPreferences
                    removeFileNameFromPreferences(fileName);
                } else {
                    Toast.makeText(MainActivity.this, "File not found", Toast.LENGTH_SHORT).show();
                }
            }
        });

        buttonOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fileName = editTextFileName.getText().toString().trim();
                if (fileName.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter a file name", Toast.LENGTH_SHORT).show();
                } else if (fileExists(fileName)) {
                    // Navigate to FileContentActivity to open and edit file
                    Intent intent = new Intent(MainActivity.this, FileContentActivity.class);
                    intent.putExtra("FILE_NAME", fileName);
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, "File not found", Toast.LENGTH_SHORT).show();
                }
            }
        });

        buttonShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFiles();
            }
        });
    }

    private boolean isFileNameValid(String fileName) {
        String INVALID_CHARACTERS_REGEX = "[\\\\/:*?\"<>|]";
        return !fileName.matches(INVALID_CHARACTERS_REGEX);
    }

    private boolean fileExists(String fileName) {
        File file = new File(getExternalFilesDir(null), fileName);
        return file.exists();
    }

    private boolean removeFile(String fileName) {
        File file = new File(getExternalFilesDir(null), fileName);
        return file.delete();
    }

    private void addFileNameToPreferences(String fileName) {
        SharedPreferences prefs = getSharedPreferences(FILE_NAMES_PREF, Context.MODE_PRIVATE);
        Set<String> fileNames = prefs.getStringSet(FILE_NAMES_KEY, new HashSet<String>());
        fileNames.add(fileName);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putStringSet(FILE_NAMES_KEY, fileNames);
        editor.apply();
    }

    private void removeFileNameFromPreferences(String fileName) {
        SharedPreferences prefs = getSharedPreferences(FILE_NAMES_PREF, Context.MODE_PRIVATE);
        Set<String> fileNames = prefs.getStringSet(FILE_NAMES_KEY, new HashSet<String>());
        fileNames.remove(fileName);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putStringSet(FILE_NAMES_KEY, fileNames);
        editor.apply();
    }

    private void showFiles() {
        File directory = getExternalFilesDir(null);
        File[] files = directory.listFiles();

        if (files == null || files.length == 0) {
            Toast.makeText(this, "No files to show", Toast.LENGTH_SHORT).show();
            return;
        }

        StringBuilder fileList = new StringBuilder();
        for (File file : files) {
            if (file.isFile()) {
                fileList.append(file.getName()).append("\n");
            }
        }

        // Display file names in a dialog
        new android.app.AlertDialog.Builder(this)
                .setTitle("Files")
                .setMessage(fileList.toString())
                .setPositiveButton("OK", null)
                .show();
    }

}
