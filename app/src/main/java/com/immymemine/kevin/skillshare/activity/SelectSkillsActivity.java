package com.immymemine.kevin.skillshare.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;

import com.immymemine.kevin.skillshare.R;
import com.immymemine.kevin.skillshare.utility.ConstantUtil;

import java.util.ArrayList;
import java.util.List;

import me.rishabhkhanna.customtogglebutton.CustomToggleButton;

public class SelectSkillsActivity extends Activity implements CompoundButton.OnCheckedChangeListener {

    Intent intent;
    List<String> skills;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_skills);

        intent = getIntent();
        // 초기화
        if(intent.getStringArrayListExtra(ConstantUtil.SKILLS_FLAG) != null)
            skills = intent.getStringArrayListExtra(ConstantUtil.SKILLS_FLAG);
        else
            skills = new ArrayList<>();

        initiateView();
    }

    @Override
    public void onBackPressed() {
        Log.d("JUWONLEE", skills.get(0));
        Intent intent = new Intent();
        intent.putStringArrayListExtra(ConstantUtil.SKILLS_FLAG, (ArrayList<String>) skills);
        setResult(RESULT_OK, intent);
        finish();

        super.onBackPressed();
    }

    private void initiateView() {
        ((CustomToggleButton)findViewById(R.id.toggle_design)).setOnCheckedChangeListener(this);
        ((CustomToggleButton)findViewById(R.id.toggle_photography)).setOnCheckedChangeListener(this);
        ((CustomToggleButton)findViewById(R.id.toggle_business)).setOnCheckedChangeListener(this);
        ((CustomToggleButton)findViewById(R.id.toggle_technology)).setOnCheckedChangeListener(this);
        ((CustomToggleButton)findViewById(R.id.toggle_crafts)).setOnCheckedChangeListener(this);
        ((CustomToggleButton)findViewById(R.id.toggle_culinary)).setOnCheckedChangeListener(this);
        ((CustomToggleButton)findViewById(R.id.toggle_film)).setOnCheckedChangeListener(this);
        ((CustomToggleButton)findViewById(R.id.toggle_fashion)).setOnCheckedChangeListener(this);
        ((CustomToggleButton)findViewById(R.id.toggle_music)).setOnCheckedChangeListener(this);
        ((CustomToggleButton)findViewById(R.id.toggle_lifestyle)).setOnCheckedChangeListener(this);
        ((CustomToggleButton)findViewById(R.id.toggle_gaming)).setOnCheckedChangeListener(this);
        ((CustomToggleButton)findViewById(R.id.toggle_teaching)).setOnCheckedChangeListener(this);

        findViewById(R.id.toolbar_close_button).setOnClickListener(view -> {
            intent.putStringArrayListExtra(ConstantUtil.SKILLS_FLAG, (ArrayList<String>) skills);
            setResult(RESULT_OK, intent);
            finish();
        });
    }

    private void addSkill(String skill) {
        skills.add(skill);
    }

    private void removeSkill(String skill) {
        skills.remove(skill);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        int id = buttonView.getId();
        switch (id) {
            case R.id.toggle_design:
                if(isChecked)
                    addSkill("Design");
                else
                    removeSkill("Design");
                break;
            case R.id.toggle_photography:
                if(isChecked)
                    addSkill("Photography");
                else
                    removeSkill("Photography");
                break;
            case R.id.toggle_business:
                if(isChecked)
                    addSkill("Business");
                else
                    removeSkill("Business");
                break;
            case R.id.toggle_technology:
                if(isChecked)
                    addSkill("Technology");
                else
                    removeSkill("Technology");
                break;
            case R.id.toggle_crafts:
                if(isChecked)
                    addSkill("Crafts");
                else
                    removeSkill("Crafts");
                break;
            case R.id.toggle_culinary:
                if(isChecked)
                    addSkill("Culinary");
                else
                    removeSkill("Culinary");
                break;
            case R.id.toggle_film:
                if(isChecked)
                    addSkill("Film");
                else
                    removeSkill("Film");
                break;
            case R.id.toggle_fashion:
                if(isChecked)
                    addSkill("Fashion");
                else
                    removeSkill("Fashion");
                break;
            case R.id.toggle_music:
                if(isChecked)
                    addSkill("Music");
                else
                    removeSkill("Music");
                break;
            case R.id.toggle_lifestyle:
                if(isChecked)
                    addSkill("LifeStyle");
                else
                    removeSkill("LifeStyle");
                break;
            case R.id.toggle_gaming:
                if(isChecked)
                    addSkill("Gaming");
                else
                    removeSkill("Gaming");
                break;
            case R.id.toggle_teaching:
                if(isChecked)
                    addSkill("Teaching");
                else
                    removeSkill("Teaching");
                break;

        }
    }
}