package com.immymemine.kevin.skillshare.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ToggleButton;

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
        close_button.setOnClickListener(view -> {
            // 선택한 skills 의 정보를 넘겨줘야한다
            // start activity for result >>> reply
            finish();
        });

        initiateView();
    }

    private void initiateView() {
        setToggleButton(R.id.i_toggle_design, R.id.toggle_design);
        setToggleButton(R.id.i_toggle_photography, R.id.toggle_photography);
        setToggleButton(R.id.i_toggle_business, R.id.toggle_business);
        setToggleButton(R.id.i_toggle_technology, R.id.toggle_technology);
        setToggleButton(R.id.i_toggle_crafts, R.id.toggle_crafts);
        setToggleButton(R.id.i_toggle_culinary, R.id.toggle_culinary);
        setToggleButton(R.id.i_toggle_film, R.id.toggle_film);
        setToggleButton(R.id.i_toggle_fashion, R.id.toggle_fashion);
        setToggleButton(R.id.i_toggle_music, R.id.toggle_music);
        setToggleButton(R.id.i_toggle_lifestyle, R.id.toggle_lifestyle);
        setToggleButton(R.id.i_toggle_gaming, R.id.toggle_gaming);
        setToggleButton(R.id.i_toggle_teaching, R.id.toggle_teaching);
    }

    // TODO 버튼 연동
    // TODO startactivityforresult >>> 선택된 skills 넘기기
    // TODO main > select skills activity 넘어올 때 기존에 선택된 skills 는 선택되어 있도록 처리
    private void setToggleButton(int i_toggle, int toggle) {
        final ToggleImageButton tib = this.findViewById(i_toggle);

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
