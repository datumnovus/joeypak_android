package particle.joeypak;

import android.content.Context;
import android.content.Intent;
import android.hardware.input.InputManager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.RelativeLayout;
import particle.joeypak.Activities.HomeActivity;
import particle.joeypak.Activities.JoeyActivity;
import particle.joeypak.Activities.LandingActivity;
import particle.joeypak.Adapters.ViewPagerFragmentAdapter;
import particle.joeypak.Fragments.*;
import particle.joeypak.Model.Transaction;

import java.util.ArrayList;
import java.util.Arrays;

public class AppCoordinator {
    private static AppCoordinator mOurInstance = new AppCoordinator();
    private JoeyActivity mActivity;

    //Initializers
    public static AppCoordinator sharedInstance() {
        return mOurInstance;
    }
    private AppCoordinator() {
    }

    //Modifiers
    public void setActivity(JoeyActivity activity) {
        mActivity = activity;
    }

    //Actions
    private void navigateToActivity(Class<?> activityClass) {
        Intent intent = new Intent(mActivity, activityClass);
        mActivity.startActivity(intent);
        mActivity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
    public void dismissFragment() {
        mActivity.hidePopover();
    }
    public void dismissKeyboard() {
        View view = mActivity.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
    public boolean shouldNavigateBack() {
        if (mActivity instanceof LandingActivity) {
            return false;
        }
        if (mActivity instanceof HomeActivity) {
            if (mActivity.mCurrentFragment != null) {
                dismissKeyboard();
                dismissFragment();
            }
            return false;
        }
        return true;
    }

    //Navigators
    public void navigateToLanding() { navigateToActivity(LandingActivity.class); }
    public void navigateToHome() {
        navigateToActivity(HomeActivity.class);
    }

    public void navigateToShareSheet() {
        if (mActivity.mCurrentFragment != null) {
            dismissFragment();
        }

        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "Download JoeyPak to send the digital currency with your friends!"); //TODO: Replace with real text
        sendIntent.setType("text/plain");
        mActivity.startActivity(sendIntent);
    }
    public void navigateToResponse(Transaction transaction) {
        ResponseFragment responseFragment = new ResponseFragment(transaction);
        LoadingFragment loadingFragment = new LoadingFragment();
        CompletionFragment completionFragment = new CompletionFragment();
        ArrayList fragments = new ArrayList<>(Arrays.asList(responseFragment, loadingFragment, completionFragment));
        ViewPagerFragmentAdapter adapter = new ViewPagerFragmentAdapter(mActivity.getSupportFragmentManager(), fragments);
        ViewPagerFragment fragment = new ViewPagerFragment(adapter, 1150);
        mActivity.showPopOver(fragment);
    }
    public void navigateToPayment() {
        PayTransactionFragment payFragment = new PayTransactionFragment();
        LoadingFragment loadingFragment = new LoadingFragment();
        ArrayList fragments = new ArrayList<>(Arrays.asList(payFragment, loadingFragment, payFragment.mCompletion));
        ViewPagerFragmentAdapter adapter = new ViewPagerFragmentAdapter(mActivity.getSupportFragmentManager(), fragments);
        ViewPagerFragment fragment = new ViewPagerFragment(adapter, RelativeLayout.LayoutParams.WRAP_CONTENT);
        mActivity.showPopOver(fragment);
    }
    public void navigateToRequest() {
        RequestTransactionFragment requestFragment = new RequestTransactionFragment();
        LoadingFragment loadingFragment = new LoadingFragment();
        ArrayList fragments = new ArrayList<>(Arrays.asList(requestFragment, loadingFragment, requestFragment.mCompletion));
        ViewPagerFragmentAdapter adapter = new ViewPagerFragmentAdapter(mActivity.getSupportFragmentManager(), fragments);
        ViewPagerFragment fragment = new ViewPagerFragment(adapter, RelativeLayout.LayoutParams.WRAP_CONTENT);
        mActivity.showPopOver(fragment);
    }
    public void navigateToSettings() {
        SettingsFragment fragment = new SettingsFragment();
        mActivity.showPopOver(fragment);
    }
}
