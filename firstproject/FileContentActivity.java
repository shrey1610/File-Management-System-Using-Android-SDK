package com.example.firstproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileContentActivity extends AppCompatActivity {

    private TextView textViewFileName;
    private EditText editTextFileContent;
    private Button buttonSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_content);

        // Initialize views
        textViewFileName = findViewById(R.id.textViewFileName);
        editTextFileContent = findViewById(R.id.editTextFileContent);
        buttonSave = findViewById(R.id.buttonSave);

        // Retrieve the file name passed from MainActivity
        Intent intent = getIntent();
        String fileName = intent.getStringExtra("FILE_NAME");

        // Set the file name at the top
        textViewFileName.setText(fileName);

        // Load file content if it exists
        loadFileContent(fileName);

        // Set up "Save" button
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fileContent = editTextFileContent.getText().toString().trim();
                if (fileContent.isEmpty()) {
                    Toast.makeText(FileContentActivity.this, "Please enter some content", Toast.LENGTH_SHORT).show();
                } else {
                    saveToFile(fileName, fileContent);
                }
            }
        });
    }

    private void loadFileContent(String fileName) {
        File file = new File(getExternalFilesDir(null), fileName);
        if (file.exists()) {
            try {
                FileInputStream fis = new FileInputStream(file);
                byte[] data = new byte[(int) file.length()];
                fis.read(data);
                fis.close();
                String content = new String(data);
                editTextFileContent.setText(content);
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Error reading file", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void saveToFile(String fileName, String fileContent) {
        File file = new File(getExternalFilesDir(null), fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(fileContent.getBytes());
            fos.close();
            Toast.makeText(this, "File content saved successfully", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error saving file content", Toast.LENGTH_SHORT).show();
        }
    }
}
