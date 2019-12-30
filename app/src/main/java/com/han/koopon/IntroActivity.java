package com.han.koopon;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class IntroActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        transitionAnimation(findViewById(R.id.intro_iv));
    }




    private void transitionAnimation(View view){
        view.animate()
                .setDuration(700)
                .translationY(+300)
                .alpha(1.0f)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        alphaAnimation(findViewById(R.id.intro_tv));
                    }
                });
    }

    private void alphaAnimation(View view){
        view.animate()
                .setDuration(500)
                .translationY(-300)
                .alpha(1.0f)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        super.onAnimationStart(animation);
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        startMain();
                    }
                });
    }

    private void startMain(){
        Intent i = new Intent(IntroActivity.this,MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(IntroActivity.this, findViewById(R.id.intro_tv),"title_transition");
        startActivity(i, options.toBundle());
        finish();
    }
}
