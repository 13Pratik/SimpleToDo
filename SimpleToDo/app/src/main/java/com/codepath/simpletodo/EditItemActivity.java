package com.codepath.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import static com.codepath.simpletodo.R.id.txtEdit;

public class EditItemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        String itemText = getIntent().getStringExtra("text");
        EditText et = (EditText) findViewById(R.id.txtEdit);
        et.setText(itemText);
    }

    public void onSubmit(View V) {
        EditText et = (EditText) findViewById(R.id.txtEdit);

        String itemPos = getIntent().getStringExtra("pos");
        Intent data = new Intent();
        data.putExtra("result",et.getText().toString());
        data.putExtra("pos",itemPos);
        setResult(RESULT_OK,data);

        this.finish();
    }

}
