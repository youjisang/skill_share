package com.immymemine.kevin.skillshare.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.CompoundButton;

import com.immymemine.kevin.skillshare.R;
import com.immymemine.kevin.skillshare.model.user.User;
import com.immymemine.kevin.skillshare.utility.ConstantUtil;
import com.immymemine.kevin.skillshare.utility.StateUtil;

import java.util.ArrayList;
import java.util.List;

import me.rishabhkhanna.customtogglebutton.CustomToggleButton;

public class SelectSkillsActivity extends Activity implements CompoundButton.OnCheckedChangeListener {

    List<String> skills;

    CustomToggleButton toggleButtonDesign, toggleButtonPhotography, toggleButtonBusiness, toggleButtonTechnology,
            toggleButtonCrafts, toggleButtonCulinary, toggleButtonFilm, toggleButtonFashion, toggleButtonMusic,
            toggleButtonLifestyle, toggleButtonGaming, toggleButtonTeaching;

    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_skills);

        initiateView();

        user = StateUtil.getInstance().getUserInstance();

        // 초기화
        if(user.getFollowingSkills() != null) {
            skills = StateUtil.getInstance().getUserInstance().getFollowingSkills();
            setAlreadyCheckedSkills(skills);
        } else {
            skills = new ArrayList<>();
        }

        attachOnCheckedChangeListener();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putStringArrayListExtra(ConstantUtil.SKILLS_FLAG, (ArrayList<String>) skills);
        setResult(RESULT_OK, intent);
        finish();

        super.onBackPressed();
    }

    private void initiateView() {
        toggleButtonDesign = findViewById(R.id.toggle_design);
        toggleButtonPhotography = findViewById(R.id.toggle_photography);
        toggleButtonBusiness = findViewById(R.id.toggle_business);
        toggleButtonTechnology = findViewById(R.id.toggle_technology);
        toggleButtonCrafts = findViewById(R.id.toggle_crafts);
        toggleButtonCulinary = findViewById(R.id.toggle_culinary);
        toggleButtonFilm = findViewById(R.id.toggle_film);
        toggleButtonFashion = findViewById(R.id.toggle_fashion);
        toggleButtonMusic = findViewById(R.id.toggle_music);
        toggleButtonLifestyle = findViewById(R.id.toggle_lifestyle);
        toggleButtonGaming = findViewById(R.id.toggle_gaming);
        toggleButtonTeaching = findViewById(R.id.toggle_teaching);

        findViewById(R.id.toolbar_close_button).setOnClickListener(view -> {
            Intent intent = new Intent();
            intent.putStringArrayListExtra(ConstantUtil.SKILLS_FLAG, (ArrayList<String>) skills);
            setResult(RESULT_OK, intent);
            finish();
        });
    }

    private void attachOnCheckedChangeListener() {
        toggleButtonDesign.setOnCheckedChangeListener(this);
        toggleButtonPhotography.setOnCheckedChangeListener(this);
        toggleButtonBusiness.setOnCheckedChangeListener(this);
        toggleButtonTechnology.setOnCheckedChangeListener(this);
        toggleButtonCrafts.setOnCheckedChangeListener(this);
        toggleButtonCulinary.setOnCheckedChangeListener(this);
        toggleButtonFilm.setOnCheckedChangeListener(this);
        toggleButtonFashion.setOnCheckedChangeListener(this);
        toggleButtonMusic.setOnCheckedChangeListener(this);
        toggleButtonLifestyle.setOnCheckedChangeListener(this);
        toggleButtonGaming.setOnCheckedChangeListener(this);
        toggleButtonTeaching.setOnCheckedChangeListener(this);
    }

    private void setAlreadyCheckedSkills(List<String> skills) {
        for(int i=0; i<skills.size(); i++) {
            switch (skills.get(i)) {
                case "Design":
                    toggleButtonDesign.setChecked(true);
                    break;
                case "Photography":
                    toggleButtonPhotography.setChecked(true);
                    break;
                case "Business":
                    toggleButtonBusiness.setChecked(true);
                    break;
                case "Technology":
                    toggleButtonTechnology.setChecked(true);
                    break;
                case "Crafts":
                    toggleButtonCrafts.setChecked(true);
                    break;
                case "Culinary":
                    toggleButtonCulinary.setChecked(true);
                    break;
                case "Film":
                    toggleButtonFilm.setChecked(true);
                    break;
                case "Fashion":
                    toggleButtonFashion.setChecked(true);
                    break;
                case "Music":
                    toggleButtonMusic.setChecked(true);
                    break;
                case "LifeStyle":
                    toggleButtonLifestyle.setChecked(true);
                    break;
                case "Gaming":
                    toggleButtonGaming.setChecked(true);
                    break;
                case "Teaching":
                    toggleButtonTeaching.setChecked(true);
                    break;
            }
        }
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
