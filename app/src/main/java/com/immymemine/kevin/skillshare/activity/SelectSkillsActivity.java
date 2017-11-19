package com.immymemine.kevin.skillshare.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;

import com.immymemine.kevin.skillshare.R;

import net.colindodd.toggleimagebutton.ToggleImageButton;

import me.rishabhkhanna.customtogglebutton.CustomToggleButton;

public class SelectSkillsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_skills);

        // 뒤로가기 버튼
        ImageButton close_button = findViewById(R.id.toolbar_close_button);
        close_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 선택한 skills 의 정보를 넘겨줘야한다
                // start activity for result >>> reply
                finish();
            }
        });

        initiateView();
    }

    private void initiateView() {
        setToggleButton(R.id.i_toggle_photography, R.id.toggle_photography);

    }

    private void setToggleButton(int i_toggle, int toggle) {
        final ToggleImageButton tib = findViewById(i_toggle);
        ((CustomToggleButton)findViewById(toggle)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    tib.setChecked(true);
                } else {
                    tib.setChecked(false);
                }
            }
        });
    }

}
