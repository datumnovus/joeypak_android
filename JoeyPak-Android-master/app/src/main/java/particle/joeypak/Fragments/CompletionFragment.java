package particle.joeypak.Fragments;

import android.app.ActionBar;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import particle.joeypak.AppCoordinator;
import particle.joeypak.Utilities.ColorUtilities;
import particle.joeypak.Utilities.FontUtilities;
import particle.joeypak.Views.HighlightedButton;

public class CompletionFragment extends ViewPagerFragment.Page {
    private TextView mEmojiView;
    private TextView mTitleView;
    private TextView mSubtitleView;
    private String mEmoji;
    private String mTitle;
    private String mSubtitle;

    public CompletionFragment() {
        setArguments(new Bundle());

        //Set Temporary Values
        mEmoji = "👍";
        mTitle = "Complete!";
        mSubtitle = "You confirmed Dan Gillespie's request for 40 Joeys.";
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Instantiate Properties
        RelativeLayout superLayout = new RelativeLayout(getActivity());
        LinearLayout layout = new LinearLayout(getActivity());
        HighlightedButton button = new HighlightedButton(getActivity());
        mEmojiView = new TextView(getActivity());
        mTitleView = new TextView(getActivity());
        mSubtitleView = new TextView(getActivity());

        //Set Ids
        layout.setId(1);
        button.setId(2);

        //Set Properties
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setGravity(Gravity.CENTER);
        mEmojiView.setTextSize(100);
        mEmojiView.setTypeface(FontUtilities.avenirMedium);
        mEmojiView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        mTitleView.setTextSize(40);
        mTitleView.setTypeface(FontUtilities.avenirSemiBold);
        mTitleView.setTextColor(ColorUtilities.darkTextColor);
        mTitleView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        mSubtitleView.setTextSize(20);
        mSubtitleView.setTypeface(FontUtilities.avenirRegular);
        mSubtitleView.setTextColor(ColorUtilities.darkTextColor);
        mSubtitleView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        button.setText("Okay");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCoordinator.sharedInstance().dismissFragment();
            }
        });
        setText(mEmoji, mTitle, mSubtitle);

        //Set Constraints
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT);
        layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        layoutParams.addRule(RelativeLayout.ABOVE, button.getId());

        RelativeLayout.LayoutParams buttonParams = new RelativeLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
        buttonParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        buttonParams.setMargins(40,40,40,40);

        LinearLayout.LayoutParams textParameters = new LinearLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
        textParameters.setMarginStart(10);
        textParameters.setMarginEnd(10);

        //Add Views
        layout.addView(mEmojiView, textParameters);
        layout.addView(mTitleView, textParameters);
        layout.addView(mSubtitleView, textParameters);
        superLayout.addView(button, buttonParams);
        superLayout.addView(layout, layoutParams);

        return superLayout;
    }

    public void setText(String emoji, String title, String subtitle) {
        mEmoji = emoji;
        mTitle = title;
        mSubtitle = subtitle;

        if (mEmojiView != null) {
            mEmojiView.setText(mEmoji);
        }
        if (mTitleView != null) {
            mTitleView.setText(mTitle);
        }
        if (mSubtitleView != null) {
            mSubtitleView.setText(mSubtitle);
        }
    }
}
