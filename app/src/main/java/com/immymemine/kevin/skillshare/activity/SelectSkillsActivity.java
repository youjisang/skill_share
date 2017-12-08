package com.immymemine.kevin.skillshare.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ToggleButton;

import com.immymemine.kevin.skillshare.R;
import com.immymemine.kevin.skillshare.utility.ConstantUtil;

import net.colindodd.toggleimagebutton.ToggleImageButton;

import java.util.ArrayList;

import me.rishabhkhanna.customtogglebutton.CustomToggleButton;

public class SelectSkillsActivity extends Activity {

    Intent intent;
    ArrayList toggleArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_skills);

        // 뒤로가기 버튼
        ImageButton close_button = findViewById(R.id.toolbar_close_button);
        close_button.setOnClickListener(view -> {
            // 선택한 skills 의 정보를 넘겨줘야한다
            // start activity for result >>> reply

            intent = new Intent(SelectSkillsActivity.this, MainActivity.class);
            intent.putExtra("toggleArray",toggleArray);
            startActivity(intent);

            /*TODO 지상

            뒤로 가기 버튼을 클릭 했을 시에
            intent = new intent(SelectSkillsActivity.this, MainActivity.class)
            toggleArray -> 를 MainActivity에 intent로 넘긴다.
            intent.putExtra("selectSkill", toggleArray);
            startActivity(intent);

            이런식으로 보내면, MainActivity에 보낸다.
            viewFactory와 같은 레이아웃 매니저가 있기 때문에, Main에다가만 보내면 보낸 데이터를
            GeneralViewFactory와 GetMeSkillView에 적용할 수 있을 것 같음.


            */


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
        toggleArray = new ArrayList();
        ((CustomToggleButton) findViewById(toggle)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    tib.setChecked(true);

                        Log.e("tib checked", "checked true==========================" + tib.toString());

                    //TODO 지상
                    // 선택 되었을 때는 다음과 같은 로직으로 toggleArray에서 추가함.
                    toggleArray.add(tib.toString());
                    Log.e("toggleArray checked", "toggleArray==========================" + toggleArray);

                } else {
                    tib.setChecked(false);

                    //TODO 지상
                    //선택이 되지 않았을 때는 다음과 같은 로직으로 toggleArray에서 삭제함.
                    toggleArray.remove(tib.toString());
                    Log.e("toggleArray checked", "toggleArray==========================" + toggleArray);

                }
            }
        });

    }

}
