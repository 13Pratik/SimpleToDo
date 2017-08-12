package com.codepath.simpletodo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static com.codepath.simpletodo.R.id.etNewItem;
import static com.codepath.simpletodo.R.id.lvItems;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> items;
    ArrayAdapter<String> itemsAdapter;
    ListView lvItems;
    private final int REQUEST_CODE = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvItems = (ListView)findViewById(R.id.lvItems);
   //     items = new ArrayList<>();
        readItems();
        itemsAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, items);
        lvItems.setAdapter(itemsAdapter);
    //    items.add("First Item");
    //    items.add("Second Item");
        setupListViewListener();
        setUpListViewClickListener();

    }

    public void onAddItem(View V) {
        EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
        String itemText = etNewItem.getText().toString();
        itemsAdapter.add(itemText);
        etNewItem.setText("");
        writeItems();

    }

    private void setupListViewListener() {
        lvItems.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener(){
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapter,
                                                   View Item, int pos, long id) {
                        items.remove(pos);
                        itemsAdapter.notifyDataSetChanged();
                        writeItems();
                        return true;
                    }
        });
    }

    private void readItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir,"todo.txt");
        try {
            items = new ArrayList<String>(FileUtils.readLines(todoFile));
        } catch (IOException e) {
            items = new ArrayList<String>();
        }
    }

    private void writeItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir,"todo.txt");
        try {
            FileUtils.writeLines(todoFile,items);
            // FIleUtils require "compile 'commons-io:commons-io:2.4'" as a dependency in app/build,gradle file
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void launchComposeView(String txt,int pos) {

        Intent i = new Intent(MainActivity.this,EditItemActivity.class);
        i.putExtra("text",txt);
        i.putExtra("pos",pos);
     //   i.putExtra("code",400);
        startActivityForResult(i, pos);

    }

    private void setUpListViewClickListener() {
        lvItems.setOnItemClickListener(
                new AdapterView.OnItemClickListener(){
                    @Override
                    public void  onItemClick(AdapterView<?> adapter,
                                                       View Item, int pos, long id) {
                        launchComposeView(items.get(pos),pos);
                      //  return true;
                    }
                }
        );
    }

    @Override
    protected void  onActivityResult(int requestCode,int resultCode, Intent data){
        if (resultCode == RESULT_OK) {
            String result = data.getExtras().getString("result");
            int pos = data.getExtras().getInt("pos");

            lvItems = (ListView)findViewById(R.id.lvItems);
            //     items = new ArrayList<>();
            itemsAdapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_list_item_1, items);
            lvItems.setAdapter(itemsAdapter);
            items.set(pos,result);
            writeItems();
            //Toast.makeText(this,result,Toast.LENGTH_SHORT).show();
        }
    }
}
