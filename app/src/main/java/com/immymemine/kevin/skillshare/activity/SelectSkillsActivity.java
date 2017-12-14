package com.immymemine.kevin.skillshare.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.ImageButton;

import com.immymemine.kevin.skillshare.R;
import com.immymemine.kevin.skillshare.utility.ConstantUtil;

import net.colindodd.toggleimagebutton.ToggleImageButton;

import java.util.ArrayList;

import me.rishabhkhanna.customtogglebutton.CustomToggleButton;

public class SelectSkillsActivity extends Activity {

    Intent intent;
    ArrayList<String> toggleArray;

    ToggleImageButton design, photography, business, technology, craft, culinary, film, fashion, music, lifestyle, gaming, teaching;
    CustomToggleButton design2, photography2, business2, technology2, craft2, culinary2, film2, fashion2, music2, lifestyle2, gaming2, teaching2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_skills);

        // 뒤로가기 버튼
        ImageButton close_button = findViewById(R.id.toolbar_close_button);
        close_button.setOnClickListener(view -> {
            intent = new Intent();
            intent.putExtra("toggleArray",toggleArray);
            setResult(RESULT_OK, intent);
            Log.e("RESULT OK!","CHECK RESULT OK"+intent);
            // 선택한 skills 의 정보를 넘겨줘야한다
            // start activity for result >>> reply

             finish();
        });
        initSetting();
        initiateView();
    }

    private void initSetting() {
        design = findViewById(R.id.i_toggle_design);
        design2 = findViewById(R.id.toggle_design);
        photography = findViewById(R.id.i_toggle_photography);
        photography2 = findViewById(R.id.toggle_photography);
        business = findViewById(R.id.i_toggle_business);
        business2 = findViewById(R.id.toggle_business);
        technology = findViewById(R.id.i_toggle_technology);
        technology2 = findViewById(R.id.toggle_technology);
        craft = findViewById(R.id.i_toggle_crafts);
        craft2 = findViewById(R.id.toggle_crafts);
        culinary = findViewById(R.id.i_toggle_culinary);
        culinary2 = findViewById(R.id.toggle_culinary);
        film = findViewById(R.id.i_toggle_film);
        film2 = findViewById(R.id.toggle_film);
        fashion = findViewById(R.id.i_toggle_fashion);
        fashion2 = findViewById(R.id.toggle_fashion);
        music = findViewById(R.id.i_toggle_music);
        music2 = findViewById(R.id.toggle_music);
        lifestyle = findViewById(R.id.i_toggle_lifestyle);
        lifestyle2 = findViewById(R.id.toggle_lifestyle);
        gaming = findViewById(R.id.i_toggle_gaming);
        gaming2 = findViewById(R.id.toggle_gaming);
        teaching = findViewById(R.id.i_toggle_teaching);
        teaching2 = findViewById(R.id.toggle_teaching);


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
        toggleArray = new ArrayList<String>();

        final ToggleImageButton tib = this.findViewById(i_toggle);
        final CustomToggleButton tib2 = this.findViewById(toggle);
        ((CustomToggleButton) findViewById(toggle)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    tib.setChecked(true);
                    if (tib2 == design2) {
                        toggleArray.add(ConstantUtil.DESIGN);
                    }
                    if (tib2 == photography2) {
                        toggleArray.add(ConstantUtil.PHOTOGRAPHY);
                    }
                    if (tib2 == business2) {
                        toggleArray.add(ConstantUtil.BUSINESS);
                    }
                    if (tib2 == technology2) {
                        toggleArray.add(ConstantUtil.TECHNOLOGY);
                    }
                    if (tib2 == craft2) {
                        toggleArray.add(ConstantUtil.CRAFTS);
                    }
                    if (tib2 == culinary2) {
                        toggleArray.add(ConstantUtil.CULINARY);
                    }
                    if (tib2 == film2) {
                        toggleArray.add(ConstantUtil.FILM);
                    }
                    if (tib2 == fashion2) {
                        toggleArray.add(ConstantUtil.FASHION);
                    }
                    if (tib2 == music2) {
                        toggleArray.add(ConstantUtil.FASHION);
                    }
                    if (tib2 == lifestyle2) {
                        toggleArray.add(ConstantUtil.LIFESTYLE);
                    }
                    if (tib2 == gaming2) {
                        toggleArray.add(ConstantUtil.GAMING);
                    }
                    if (tib2 == teaching2) {
                        toggleArray.add(ConstantUtil.TEACHING);
                    }
                    Log.e("check add", "toggleArray" + toggleArray);
                    Log.e("check add", "toggleArray" + toggleArray.size());

                } else {
                    tib.setChecked(false);
                    if (tib2 == design2) {
                        toggleArray.remove(ConstantUtil.DESIGN);
                    }
                    if (tib2 == photography2) {
                        toggleArray.remove(ConstantUtil.PHOTOGRAPHY);
                    }
                    if (tib2 == business2) {
                        toggleArray.remove(ConstantUtil.BUSINESS);
                    }
                    if (tib2 == technology2) {
                        toggleArray.remove(ConstantUtil.TECHNOLOGY);
                    }
                    if (tib2 == craft2) {
                        toggleArray.remove(ConstantUtil.CRAFTS);
                    }
                    if (tib2 == culinary2) {
                        toggleArray.remove(ConstantUtil.CULINARY);
                    }
                    if (tib2 == film2) {
                        toggleArray.remove(ConstantUtil.FILM);
                    }
                    if (tib2 == fashion2) {
                        toggleArray.remove(ConstantUtil.FASHION);
                    }
                    if (tib2 == music2) {
                        toggleArray.remove(ConstantUtil.FASHION);
                    }
                    if (tib2 == lifestyle2) {
                        toggleArray.remove(ConstantUtil.LIFESTYLE);
                    }
                    if (tib2 == gaming2) {
                        toggleArray.remove(ConstantUtil.GAMING);
                    }
                    if (tib2 == teaching2) {
                        toggleArray.remove(ConstantUtil.TEACHING);
                    }
                    Log.e("check remove", "map!!!" + toggleArray);
                    Log.e("check remove", "map!!!" + toggleArray.size());
                }
            }
        });

    }

}