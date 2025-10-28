package com.example.theluxe.util;

import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

public class AnimationUtils {

    /**
     * Apply shimmer/pulse animation to a view
     */
    public static void applyShimmerEffect(View view) {
        AlphaAnimation animation = new AlphaAnimation(0.3f, 1.0f);
        animation.setDuration(1000);
        animation.setRepeatCount(Animation.INFINITE);
        animation.setRepeatMode(Animation.REVERSE);
        view.startAnimation(animation);
    }

    /**
     * Apply fade in animation
     */
    public static void fadeIn(View view, long duration) {
        view.setAlpha(0f);
        view.animate()
                .alpha(1f)
                .setDuration(duration)
                .start();
    }

    /**
     * Apply fade out animation
     */
    public static void fadeOut(View view, long duration) {
        view.animate()
                .alpha(0f)
                .setDuration(duration)
                .withEndAction(() -> view.setVisibility(View.GONE))
                .start();
    }

    /**
     * Apply scale animation (bounce effect)
     */
    public static void scaleAnimation(View view, float toScale, long duration, Runnable onEnd) {
        view.animate()
                .scaleX(toScale)
                .scaleY(toScale)
                .setDuration(duration)
                .withEndAction(() -> {
                    view.animate()
                            .scaleX(1f)
                            .scaleY(1f)
                            .setDuration(duration)
                            .withEndAction(onEnd)
                            .start();
                })
                .start();
    }

    /**
     * Apply slide up animation
     */
    public static void slideUp(View view, long duration) {
        view.setTranslationY(view.getHeight());
        view.animate()
                .translationY(0)
                .setDuration(duration)
                .start();
    }

    /**
     * Apply slide down animation
     */
    public static void slideDown(View view, long duration) {
        view.animate()
                .translationY(view.getHeight())
                .setDuration(duration)
                .withEndAction(() -> view.setVisibility(View.GONE))
                .start();
    }
}



