package com.immymemine.kevin.skillshare.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.immymemine.kevin.skillshare.R;

public class CreateGroupActivity extends AppCompatActivity {

    private Toolbar toolbar2;
    private ImageButton toolbarCloseButton;
    private TextView toolbarTitle;
    private EditText editTextGroupName;
    private View view3;
    private TextView textViewCharactersLeft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_group_dialog);
        initView();
    }

    private void initView() {
        toolbar2 = findViewById(R.id.toolbar2);
        toolbarCloseButton = findViewById(R.id.toolbar_close_button);
        toolbarTitle = findViewById(R.id.toolbar_title);
        editTextGroupName = findViewById(R.id.edit_text_group_name);
        view3 = findViewById(R.id.view3);
        textViewCharactersLeft = findViewById(R.id.text_view_characters_left);
    }
}
