package particle.joeypak.Activities;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import com.facebook.AccessToken;
import com.facebook.Profile;
import particle.joeypak.AppCoordinator;
import particle.joeypak.Callbacks.CompletionCallback;
import particle.joeypak.Stores.UserStore;
import particle.joeypak.Utilities.ColorUtilities;

public class LaunchActivity extends JoeyActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayout.setBackgroundColor(ColorUtilities.green);
    }

    @Override
    protected void onResume() {
        super.onResume();

        //HACK: Wait for facebook to load the access token
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
          @Override
          public void run() {
              //Check Facebook Status
              if (AccessToken.getCurrentAccessToken() != null || Profile.getCurrentProfile() != null) {
                  UserStore.sharedInstance().refreshCurrentUser(new CompletionCallback() {
                      @Override
                      public void success() {
                          AppCoordinator.sharedInstance().navigateToHome();
                      }

                      @Override
                      public void failure() {
                          AppCoordinator.sharedInstance().navigateToLanding();
                      }
                  });
              }
              else {
                  AppCoordinator.sharedInstance().navigateToLanding();
              }
          }
        }, 1000);
    }
}
