package com.han.koopon.animation;

import android.animation.TimeInterpolator;

import androidx.core.view.animation.PathInterpolatorCompat;

public class Inteporator {
    /**
     * Standard easing.
     *
     * Elements that begin and end at rest use standard easing. They speed up quickly and slow down
     * gradually, in order to emphasize the end of the transition.
     */
    public static TimeInterpolator FAST_OUT_SLOW_IN = PathInterpolatorCompat.create(0.4f, 0f, 0.2f, 1f);

    /**
     * Decelerate easing.
     *
     * Incoming elements are animated using deceleration easing, which starts a transition at peak
     * velocity (the fastest point of an element’s movement) and ends at rest.
     */
    public static TimeInterpolator LINEAR_OUT_SLOW_IN = PathInterpolatorCompat.create(0f, 0f, 0.2f, 1f);

    /**
     * Accelerate easing.
     *
     * Elements exiting a screen use acceleration easing, where they start at rest and end at peak
     * velocity.
     */
    public static TimeInterpolator FAST_OUT_LINEAR_IN =  PathInterpolatorCompat.create(0.4f, 0f, 1f, 1f);
}
