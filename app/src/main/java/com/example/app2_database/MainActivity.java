package com.example.app2_database;


import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.w3c.dom.Text;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ElementListAdapter.OnItemClickListener {
    ElementViewModel mElementViewModel;
    private ElementListAdapter mAdapter;
    private Element e1;
    FloatingActionButton fab;
    ActivityResultLauncher<Intent> mActivityResultLauncher;
    long id = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createScreen();

        //onClick
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, table_form.class);
                mActivityResultLauncher.launch(intent);
            }
        });
        //usuwanie po przesuniciu

    }
    void createScreen()
    {
        setContentView(R.layout.activity_main);
        fab = findViewById(R.id.floatingActionButton);
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        Context context;
        mAdapter = new ElementListAdapter(this, this);
        recyclerView.setAdapter(mAdapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mElementViewModel = new ViewModelProvider(this).get(ElementViewModel.class);

        //insert data if list empty
        e1 = new Element("Google", "Pixel", "2.0", "https://pollub.pl");mElementViewModel.insert(e1);
        //Second Activity
        manageSecondActivity();
        //Observer::onChanged -> lambda
        mElementViewModel.getAllElements().observe(this, elements -> {
            mAdapter.setElementList(elements);
        });
    }


    void manageSecondActivity()
    {
        mActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        ArrayList<String> list= result.getData().getStringArrayListExtra(table_form.EXTRA_REPLY);
                        Element e = new Element(list.get(0), list.get(1), list.get(2), list.get(3));
                        Toast.makeText(MainActivity.this, e.getProducent(),Toast.LENGTH_SHORT).show();
                        mElementViewModel.insert(e);
                    }
                    else if(result.getResultCode() == RESULT_CANCELED) {}
                    else
                    {
                        ArrayList<String> list= result.getData().getStringArrayListExtra(table_form.EXTRA_REPLY);
                        Element e = new Element(id, list.get(0), list.get(1), list.get(2), list.get(3));
                        mElementViewModel.update(e);
                    }
                });
    }

    //Delete = SWIPE
    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT)
    {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView,
                              @NonNull RecyclerView.ViewHolder
                                      viewHolder,
                              @NonNull RecyclerView.ViewHolder
                                      target) {
            return false;
        }
        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder
                                     viewHolder, int direction) {
            //kasowanie „przeciągniętego” elementu
            int adapter_position = viewHolder.getAdapterPosition();
            List<Element> mElementList = mElementViewModel.getmAllElements().getValue();
            mElementViewModel.deleteElement(mElementList.get(adapter_position));
            mAdapter.notifyItemRemoved(adapter_position);
        }
    };

    //MENU - Clear
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu1) {
            Toast.makeText(this,"Clearing the data...",
                    Toast.LENGTH_SHORT).show();
            mElementViewModel.deleteAll();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //Click on Item and send Data
    @Override
    public void onItemClick(int pos) {
        List<Element> mElementList= mElementViewModel.getmAllElements().getValue();
        Element e = mElementList.get(pos);
        id = e.getId();
        Intent intent = new Intent(MainActivity.this, table_form.class);
        intent.putExtra("producent", e.getProducent());
        intent.putExtra("model", e.getModel());
        intent.putExtra("version", e.getVersion());
        intent.putExtra("www", e.getWww());
        mActivityResultLauncher.launch(intent);

    }
}