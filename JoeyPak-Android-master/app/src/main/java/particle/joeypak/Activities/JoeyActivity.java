package particle.joeypak.Activities;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import particle.joeypak.AppCoordinator;
import particle.joeypak.Utilities.ColorUtilities;

public class JoeyActivity extends FragmentActivity {
    private static final int animationDuration = 375;
    private static final int tagOffset = 650;
    private int fragId;
    public Fragment mCurrentFragment;
    protected RelativeLayout mLayout;
    private LinearLayout mPopOverLayout;
    private View mShade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragId = 3432;

        //Set statusbar color
        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ColorUtilities.green);

        //Instantiate Variables
        mShade         = new View(this);
        mLayout        = new RelativeLayout(this);
        mPopOverLayout = new LinearLayout(this);

        GradientDrawable popOverDrawable = new GradientDrawable();
        popOverDrawable.setShape(GradientDrawable.RECTANGLE);
        popOverDrawable.setColor(Color.WHITE);
        popOverDrawable.setCornerRadii(new float [] { 50, 50,
                50, 50,
                0, 0,
                0, 0});

        //Set Ids
        mShade.setId(tagOffset+1);
        mPopOverLayout.setId(tagOffset+2);

        //Set Properties
        mPopOverLayout.setBackground(popOverDrawable);
        mShade.setBackgroundColor(Color.BLACK);
        mShade.setClickable(true);
        mShade.setAlpha(0.2f);

        //Finish Set Up
        setContentView(mLayout);
        AppCoordinator.sharedInstance().setActivity(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        AppCoordinator.sharedInstance().setActivity(this);
    }

    //Actions
    public void showPopOver(Fragment fragment) {
        fragId = fragId + 1;

        //Set new fragment
        mCurrentFragment = fragment;
        getFragmentManager().beginTransaction().add(mPopOverLayout.getId(), fragment, String.format("fragment%d", fragId)).commit();

        //Add Constraints
        RelativeLayout.LayoutParams fragParams = new RelativeLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
        fragParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        fragParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        fragParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);

        RelativeLayout.LayoutParams shadeParams = new RelativeLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT,ActionBar.LayoutParams.MATCH_PARENT);
        shadeParams.addRule(RelativeLayout.CENTER_IN_PARENT);

        //Add Views
        mLayout.addView(mShade, shadeParams);
        mLayout.addView(mPopOverLayout, fragParams);

        //Animate Views
        TranslateAnimation popOverAnimation = new TranslateAnimation(
                0, 0.0f, 0, 0.0f, Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
        popOverAnimation.setDuration(animationDuration);
        mPopOverLayout.startAnimation(popOverAnimation);

        AlphaAnimation shadeAnimation = new AlphaAnimation(0.0f, 1f);
        shadeAnimation.setDuration(animationDuration);
        mShade.startAnimation(shadeAnimation);
    }
    public void hidePopover() {
        //Clear Previous Fragment
        mCurrentFragment = null;
        
        TranslateAnimation animation = new TranslateAnimation(
                0, 0.0f, 0, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,1.0f);
        animation.setDuration(animationDuration);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}
            @Override
            public void onAnimationRepeat(Animation animation) {}
            @Override
            public void onAnimationEnd(Animation animation) {
                mLayout.removeView(mPopOverLayout);
                mPopOverLayout.removeAllViews();
            }

        });
        mPopOverLayout.startAnimation(animation);

        AlphaAnimation shadeAnimation = new AlphaAnimation(1.0f, 0.0f);
        shadeAnimation.setDuration(animationDuration);
        shadeAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}
            @Override
            public void onAnimationRepeat(Animation animation) {}
            @Override
            public void onAnimationEnd(Animation animation) {
                mLayout.removeView(mShade);
            }

        });
        mShade.startAnimation(shadeAnimation);
    }

    public void onBackPressed() {
        if (AppCoordinator.sharedInstance().shouldNavigateBack()) {
            super.onBackPressed();
        }
    }
}
