package particle.joeypak.Activities;

import android.app.ActionBar;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import particle.joeypak.AppCoordinator;
import particle.joeypak.Callbacks.CompletionCallback;
import particle.joeypak.JoeyPakApplication;
import particle.joeypak.R;
import particle.joeypak.Stores.UserStore;
import particle.joeypak.Utilities.ColorUtilities;
import particle.joeypak.Utilities.FontUtilities;

import java.util.Locale;

public class LandingActivity extends JoeyActivity {
    private CallbackManager mCallbackManager = CallbackManager.Factory.create();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Create Variables
        ImageView imageView   = new ImageView(this);
        TextView title        = new TextView(this);
        TextView subtitle     = new TextView(this);
        View bottomBackgroundView = new View(this);
        LoginButton facebookButton = new LoginButton(this);

        GradientDrawable bottomBackgroundGradient = new GradientDrawable();
        bottomBackgroundGradient.setShape(GradientDrawable.RECTANGLE);
        bottomBackgroundGradient.setColor(Color.WHITE);
        bottomBackgroundGradient.setCornerRadii(new float [] { 70, 70,
                70, 70,
                0, 0,
                0, 0});

        GradientDrawable facebookButtonGradient = new GradientDrawable();
        facebookButtonGradient.setShape(GradientDrawable.RECTANGLE);
        facebookButtonGradient.setColor(ColorUtilities.facebookBlue);
        facebookButtonGradient.setCornerRadius(30);

        //Set Ids
        imageView.setId(1);
        title.setId(2);
        subtitle.setId(3);
        bottomBackgroundView.setId(4);
        facebookButton.setId(5);

        //Set Properties
        getWindow().getDecorView().setBackgroundColor(ColorUtilities.green);
        imageView.setImageResource(R.drawable.joey_icon);
        title.setTypeface(FontUtilities.avenirBold);
        title.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        title.setTextColor(Color.WHITE);
        title.setText("JoeyPak");
        title.setTextSize(32);
        subtitle.setTypeface(FontUtilities.avenirMedium);
        subtitle.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        subtitle.setTextColor(Color.WHITE);
        subtitle.setText("Lorem ipsum dolor sit amet, consectetur adipiscing elit");
        subtitle.setTextSize(20);
        bottomBackgroundView.setBackgroundColor(Color.WHITE);
        bottomBackgroundView.setBackgroundDrawable(bottomBackgroundGradient);
        facebookButton.setReadPermissions("email", "public_profile");
        facebookButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                AppCoordinator.sharedInstance().navigateToHome();
                UserStore.sharedInstance().refreshCurrentUser(new CompletionCallback() {
                    @Override
                    public void success() {
                        AppCoordinator.sharedInstance().navigateToHome();
                    }

                    @Override
                    public void failure() {
                        Log.i("Fetch User", "Failed");
                    }
                });
                Log.i("Facebook:", "Success");
            }

            @Override
            public void onCancel() {
                // App code
                Log.i("Facebook:", "Cancel");
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
                Log.i("Facebook:", "Error");
            }
        });

        //Set Constraints
        RelativeLayout.LayoutParams imageParams = new RelativeLayout.LayoutParams(274, 302);
        imageParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        imageParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        imageParams.setMargins(0,80,0,0);

        RelativeLayout.LayoutParams titleParams = new RelativeLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        titleParams.addRule(RelativeLayout.BELOW, imageView.getId());
        titleParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        titleParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        titleParams.setMargins(70,50,70,0);

        RelativeLayout.LayoutParams subtitleParams = new RelativeLayout.LayoutParams(750, ActionBar.LayoutParams.WRAP_CONTENT);
        subtitleParams.addRule(RelativeLayout.BELOW, title.getId());
        subtitleParams.addRule(RelativeLayout.ALIGN_LEFT, title.getId());
        subtitleParams.addRule(RelativeLayout.ALIGN_RIGHT, title.getId());
        subtitleParams.setMargins(0,50,0,0);

        RelativeLayout.LayoutParams bottomBackgroundParams = new RelativeLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, 300);
        bottomBackgroundParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        bottomBackgroundParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        bottomBackgroundParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);

        RelativeLayout.LayoutParams facebookButtonParams = new RelativeLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, 150);
        facebookButtonParams.addRule(RelativeLayout.ALIGN_LEFT, bottomBackgroundView.getId());
        facebookButtonParams.addRule(RelativeLayout.ALIGN_RIGHT, bottomBackgroundView.getId());
        facebookButtonParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        facebookButtonParams.setMargins(25,0,25,75);

        //Add Views
        mLayout.addView(title, titleParams);
        mLayout.addView(subtitle, subtitleParams);
        mLayout.addView(imageView, imageParams);
        mLayout.addView(bottomBackgroundView, bottomBackgroundParams);
        mLayout.addView(facebookButton, facebookButtonParams);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
