package com.example.app2_database;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class table_form extends AppCompatActivity {
    public static final String EXTRA_REPLY = "com.example.android.wordlistsql.REPLY";
    TextView add_producent, add_model, add_version, add_www;
    Button website, cancel, save;
    int code = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_form);
        add_producent = findViewById(R.id.add_producent);
        add_model = findViewById(R.id.add_model);
        add_version = findViewById(R.id.add_version);
        add_www = findViewById(R.id.add_www);
        cancel = findViewById(R.id.cancel);
        save = findViewById(R.id.save);
        website = findViewById(R.id.web_site);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null)
        {
            add_producent.setText(bundle.getString("producent"));
            add_model.setText(bundle.getString("model"));
            add_version.setText(bundle.getString("version"));
            add_www.setText(bundle.getString("www"));
            code = 2;
        }

        website.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (add_www.getText().toString().isEmpty()) add_www.setError("Nie podano strony");
                else {
                    String website_url = add_www.getText().toString();
                    website_url.startsWith("http://");
                    Intent useWebsite = new Intent("android.intent.action.VIEW", Uri.parse(website_url));
                    startActivity(useWebsite);
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(table_form.this, MainActivity.class);
                setResult(RESULT_CANCELED, intent);
                finish();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(add_producent.getText().toString())
                || TextUtils.isEmpty(add_model.getText().toString())
                || TextUtils.isEmpty(add_version.getText().toString())
                || TextUtils.isEmpty(add_www.getText().toString())) {
                    if(add_producent.getText().toString().isEmpty()) add_producent.setError("Nie podano producenta");
                    if(add_model.getText().toString().isEmpty()) add_model.setError("Nie podano modelu");
                    if(add_version.getText().toString().isEmpty()) add_version.setError("Nie podano wersji");
                    if(add_www.getText().toString().isEmpty()) add_www.setError("Nie podano strony");
                }
                else {
                    ArrayList<String> data = new ArrayList<String>();
                    data.add(add_producent.getText().toString());
                    data.add(add_model.getText().toString());
                    data.add(add_version.getText().toString());
                    data.add(add_www.getText().toString());

                    Intent intent = new Intent(table_form.this, MainActivity.class);
                    intent.putExtra(EXTRA_REPLY, data);
                    if(code == 2) setResult(code, intent);
                    else setResult(RESULT_OK, intent);

                    finish();
                }
            }
        });

    }
}