package particle.joeypak.Fragments;

import android.app.ActionBar;
import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.facebook.login.LoginManager;
import particle.joeypak.AppCoordinator;
import particle.joeypak.Utilities.ColorUtilities;
import particle.joeypak.Utilities.FontUtilities;

import java.util.Arrays;

public class SettingsFragment extends Fragment {
    public SettingsFragment() {
        setArguments(new Bundle());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Instantiate Properties
        RelativeLayout superLayout = new RelativeLayout(getActivity());
        LinearLayout layout = new LinearLayout(getActivity());
        Button shareButton  = new Button(getActivity());
        Button logoutButton = new Button(getActivity());

        //Set Properties
        layout.setOrientation(LinearLayout.VERTICAL);
        shareButton.setText("Share");
        shareButton.setTextColor(ColorUtilities.actionBlue);
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCoordinator.sharedInstance().navigateToShareSheet();
            }
        });
        logoutButton.setText("Logout");
        logoutButton.setTextColor(ColorUtilities.red);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().logOut();
                AppCoordinator.sharedInstance().navigateToLanding();
            }
        });
        for (Button button : Arrays.asList(shareButton, logoutButton)) {
            button.setStateListAnimator(null);
            button.setAllCaps(false);
            button.setTextSize(24);
            button.setBackgroundColor(Color.TRANSPARENT);
            button.setTypeface(FontUtilities.avenirRegular);
        }

        //Set Constraints
        LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
        buttonParams.setMarginEnd(10);
        buttonParams.setMarginStart(10);

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);

        //Add Views
        layout.addView(shareButton, buttonParams);
        layout.addView(logoutButton, buttonParams);
        superLayout.addView(layout, layoutParams);

        return superLayout;
    }
}
