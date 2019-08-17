package particle.joeypak.Fragments;

import android.app.ActionBar;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import particle.joeypak.AppCoordinator;
import particle.joeypak.JoeyPakApplication;
import particle.joeypak.Model.User;
import particle.joeypak.R;
import particle.joeypak.Stores.UserStore;
import particle.joeypak.Utilities.ColorUtilities;
import particle.joeypak.Utilities.FontUtilities;
import particle.joeypak.Views.HighlightedButton;
import particle.joeypak.Views.InputField;

public class TransactionFragment extends ViewPagerFragment.Page {
    protected HighlightedButton mButton;
    protected EditText mValueField;
    protected InputField mForField;
    protected User mUser;
    public CompletionFragment mCompletion;

    public TransactionFragment() {
        setArguments(new Bundle());
        mCompletion = new CompletionFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Instantiate Properties
        RelativeLayout superLayout = new RelativeLayout(getActivity());
        LinearLayout layout = new LinearLayout(getActivity());
        ImageButton closeButton = new ImageButton(getActivity());
        final InputField toField = new InputField(getActivity());
        View seperator = new View(getActivity());
        final UserSearchFragment tableFragment = new UserSearchFragment();
        LinearLayout tableFragmentLayout = new LinearLayout(JoeyPakApplication.getContext());
        mButton = new HighlightedButton(getActivity());
        mValueField = new EditText(getActivity());
        mForField = new InputField(getActivity());
        final UserStore.UserCallback userCallback = new UserStore.UserCallback() {
            @Override
            public void success(User user) {
                mUser = user;
                toField.mEditText.setText(user.getName());
                mForField.mEditText.requestFocus();
                tableFragment.mItemAdapter.clearResults();
            }
        };

        //Set Ids
        closeButton.setId(1);
        layout.setId(2);
        mButton.setId(3);
        tableFragmentLayout.setId(4);

        //Set Properties
        layout.setOrientation(LinearLayout.VERTICAL);
        closeButton.setImageResource(R.drawable.close);
        //closeButton.setScaleType(ImageView.ScaleType.FIT_CENTER);
        //closeButton.setAdjustViewBounds(true);
        mButton.setVisibility(View.GONE);
        mValueField.setText("J ");
        mValueField.requestFocus();
        mValueField.setTextSize(46);
        mValueField.setBackgroundColor(Color.TRANSPARENT);
        mValueField.setTypeface(FontUtilities.avenirMedium);
        mValueField.setInputType(InputType.TYPE_CLASS_NUMBER);
        mValueField.setTextColor(ColorUtilities.darkTextColor);
        mValueField.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        mValueField.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                checkButtonStatus();
                return false;
            }
        });
        toField.mLabel.setText("To:");
        mForField.mLabel.setText("For:");
        seperator.setBackgroundColor(ColorUtilities.gray);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCoordinator.sharedInstance().dismissFragment();
            }
        });
        tableFragmentLayout.setOrientation(LinearLayout.VERTICAL);
        toField.mEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (toField.mEditText.hasFocus()) {
                    mUser = null;
                    toField.mEditText.setText("");
                    checkButtonStatus();
                }
            }
        });
        toField.mEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                tableFragment.mItemAdapter.searchForString(toField.mEditText.getText().toString());
                tableFragment.mItemAdapter.selectedUserCallback = userCallback;
                checkButtonStatus();
                return false;
            }
        });
        mForField.mEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                checkButtonStatus();
                return false;
            }
        });

        //Add Child Fragment
        android.support.v4.app.FragmentManager childFragMan = getChildFragmentManager();
        android.support.v4.app.FragmentTransaction childFragTrans = childFragMan.beginTransaction();
        childFragTrans.add(tableFragmentLayout.getId(), tableFragment, "someTag8");
        childFragTrans.commit();

        //Set Constraints
        RelativeLayout.LayoutParams closeButtonParams = new RelativeLayout.LayoutParams(64, 62);
        closeButtonParams.setMargins(40,100,0,20);

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        layoutParams.addRule(RelativeLayout.ABOVE, mButton.getId());

        RelativeLayout.LayoutParams buttonParams = new RelativeLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
        buttonParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        buttonParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        buttonParams.setMargins(40,40,40,40);

        LinearLayout.LayoutParams valueParams = new LinearLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
        valueParams.setMargins(0,0,0,30);

        LinearLayout.LayoutParams toInputParams = new LinearLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, 100);
        toInputParams.setMargins(0,30,0,0);

        LinearLayout.LayoutParams seperateParams = new LinearLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, 5);
        valueParams.setMargins(0,10,0,10);

        LinearLayout.LayoutParams fromInputParams = new LinearLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, 100);
        fromInputParams.setMargins(0,0,0,30);

        LinearLayout.LayoutParams fragParams = new LinearLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        //Add Views
        layout.addView(mValueField, valueParams);
        layout.addView(toField, toInputParams);
        layout.addView(seperator, seperateParams);
        layout.addView(mForField, fromInputParams);
        layout.addView(tableFragmentLayout, fragParams);
        superLayout.addView(closeButton, closeButtonParams);
        superLayout.addView(layout, layoutParams);
        superLayout.addView(mButton, buttonParams);

        return superLayout;
    }

    private void checkButtonStatus() {
        if (mValueField.getText().length() > 2 && mUser != null && mForField.mEditText.length() > 0) {
            mButton.setVisibility(View.VISIBLE);
        }
        else {
            mButton.setVisibility(View.GONE);
        }

    }
}

