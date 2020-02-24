package com.han.koopon.animation;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.animation.PathInterpolatorCompat;
import androidx.transition.Fade;
import androidx.transition.SidePropagation;
import androidx.transition.TransitionValues;

public class Stagger extends Fade {

    public Stagger(){
        setDuration(AnimManager.LARGE_COLLAPSE_DURATION/2);
        setInterpolator(Inteporator.LINEAR_OUT_SLOW_IN);


        SidePropagation sp=new SidePropagation();
        sp.setSide(Gravity.TOP);
        sp.setPropagationSpeed(10f);
        setPropagation(sp);
    }

    @Nullable
    @Override
    public Animator createAnimator(@NonNull ViewGroup sceneRoot, @Nullable TransitionValues startValues, @Nullable TransitionValues endValues) {
        View view =  null;
        try {
           view = startValues.view;
        }catch (NullPointerException e){
            try {
                view = endValues.view;
            }catch (NullPointerException e2){
                return null;
            }
        }

        Animator fadeAnimator = super.createAnimator(sceneRoot, startValues, endValues);
        if (fadeAnimator==null){
            return null;
        }

        AnimatorSet aSet =new AnimatorSet( );
        aSet.playTogether(fadeAnimator, ObjectAnimator.ofFloat(view ,View.TRANSLATION_Y,view.getHeight()*0.5f,0f));

        return aSet;
    }

}
