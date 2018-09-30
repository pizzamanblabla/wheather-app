package com.example.eugene.weatherapp.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.eugene.weatherapp.R;

public class AddNewCity extends AppCompatActivity {

    public static final String EXTRA_REPLY = "com.example.android.wordlistsql.REPLY";

    private EditText editCityView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            getSupportActionBar().hide();
        } catch (Exception e) {
            Log.d("exception", e.getMessage());
        }

        setContentView(R.layout.activity_add_new_city);

        editCityView = findViewById(R.id.edit_word);

        final Button button = findViewById(R.id.button_save);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent replyIntent = new Intent();
                if (TextUtils.isEmpty(editCityView.getText())) {
                    setResult(RESULT_CANCELED, replyIntent);
                } else {
                    String city = editCityView.getText().toString();
                    replyIntent.putExtra(EXTRA_REPLY, city);
                    setResult(RESULT_OK, replyIntent);
                }

                finish();
            }
        });
    }
}
