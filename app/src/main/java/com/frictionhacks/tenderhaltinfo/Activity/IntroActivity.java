package com.frictionhacks.tenderhaltinfo.Activity;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.frictionhacks.tenderhaltinfo.Util.CubeOutDepthTransformation;
import com.frictionhacks.tenderhaltinfo.Util.Preferences;
import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;
import com.github.paolorotolo.appintro.model.SliderPage;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class IntroActivity extends AppIntro {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        SliderPage sp1 = new SliderPage();
        sp1.setTitle("TITLE1");
        sp1.setDescription("DES");
        sp1.setBgColor(Color.parseColor("#00FFAA"));

        SliderPage sp2 = new SliderPage();
        sp2.setTitle("TITLE2");
        sp2.setDescription("LOCATION WALLI");
        sp2.setBgColor(Color.parseColor("#00FA2A"));

        SliderPage sp3 = new SliderPage();
        sp3.setTitle("TITLE3");
        sp3.setDescription("DES");
        sp3.setBgColor(Color.parseColor("#50FF5A"));


        addSlide(AppIntroFragment.newInstance(sp1));
        addSlide(AppIntroFragment.newInstance(sp2));
        addSlide(AppIntroFragment.newInstance(sp3));

        setCustomTransformer(new CubeOutDepthTransformation());

    }
    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        Preferences.setFirstRun(this);
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        Preferences.setFirstRun(this);
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

}
