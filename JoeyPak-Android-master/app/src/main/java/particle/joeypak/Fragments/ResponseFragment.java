package particle.joeypak.Fragments;

import android.app.ActionBar;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.squareup.picasso.Picasso;
import particle.joeypak.AppCoordinator;
import particle.joeypak.Model.Transaction;
import particle.joeypak.Model.User;
import particle.joeypak.Stores.TransactionStore;
import particle.joeypak.Stores.UserStore;
import particle.joeypak.Utilities.ColorUtilities;
import particle.joeypak.Utilities.FontUtilities;
import particle.joeypak.Views.HighlightedButton;

public class ResponseFragment extends ViewPagerFragment.Page {
    private Transaction mTransaction;

    public ResponseFragment(Transaction transaction) {
        setArguments(new Bundle());
        mTransaction = transaction;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Instantiate Properties
        RelativeLayout superLayout = new RelativeLayout(getActivity());
        LinearLayout layout = new LinearLayout(getActivity());
        LinearLayout butonLayout = new LinearLayout(getActivity());
        final ImageView imageView = new ImageView(getActivity());
        final TextView nameTextView = new TextView(getActivity());
        TextView valueTextView = new TextView(getActivity());
        TextView descriptionTextView = new TextView(getActivity());
        HighlightedButton cancelButton = new HighlightedButton(getActivity());
        HighlightedButton confirmButton = new HighlightedButton(getActivity());

        //Set Properties
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setGravity(Gravity.CENTER);
        nameTextView.setTextSize(22);
        nameTextView.setTypeface(FontUtilities.avenirSemiBold);
        nameTextView.setTextColor(ColorUtilities.darkTextColor);
        nameTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        valueTextView.setTextSize(36);
        valueTextView.setTypeface(FontUtilities.avenirMedium);
        valueTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        descriptionTextView.setTextSize(16);
        descriptionTextView.setTypeface(FontUtilities.avenirRegular);
        descriptionTextView.setTextColor(ColorUtilities.darkTextColor);
        descriptionTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        cancelButton.setText("Cancel");
        confirmButton.setText("Confirm");
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCoordinator.sharedInstance().dismissFragment();
            }
        });
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mViewPagerManager != null) {
                    mViewPagerManager.navigateToNextFragment();

                    TransactionStore.sharedInstance().acceptTransaction(mTransaction, new TransactionStore.TransactionCallback() {
                        @Override
                        public void success(Transaction transaction) {
                            mViewPagerManager.navigateToNextFragment();
                        }
                    });
                }
            }
        });

        //Set Transaction Values
        descriptionTextView.setText(mTransaction.getDescription());
        imageView.setBackgroundColor(ColorUtilities.gray);
        imageView.setImageResource(0);
        UserStore.UserCallback callback = new UserStore.UserCallback() {
            @Override
            public void success(User user) {
                nameTextView.setText(user.getName());
                Picasso.with(imageView.getContext())
                        .load(user.getImageURL().toString())
                        .fit()
                        .centerCrop()
                        .into(imageView);
            }
        };
        if (mTransaction.getToUserId().equals(UserStore.sharedInstance().getCurrentUser().getUniqueId())) {
            valueTextView.setText(String.format("-%d", mTransaction.getValue()));
            valueTextView.setTextColor(ColorUtilities.red);
            UserStore.sharedInstance().fetchUser(mTransaction.getFromUserId(), callback);
        }
        else {
            valueTextView.setText(String.format("+%d", mTransaction.getValue()));
            valueTextView.setTextColor(ColorUtilities.green);
            UserStore.sharedInstance().fetchUser(mTransaction.getToUserId(), callback);
        }


        //Set Constraints
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT);
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);

        LinearLayout.LayoutParams imageParameters = new LinearLayout.LayoutParams(300,300);
        imageParameters.setMargins(0,40,0,40);

        LinearLayout.LayoutParams nameParameters = new LinearLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
        nameParameters.setMargins(0, 0,0, 60);

        LinearLayout.LayoutParams valueParameters = new LinearLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
        valueParameters.setMargins(0, 0,0, 60);

        LinearLayout.LayoutParams descriptionParameters = new LinearLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
        descriptionParameters.setMargins(0, 0,0, 60);

        LinearLayout.LayoutParams buttonsParameters = new LinearLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
        buttonsParameters.setMargins(40,0,40,40);

        LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(ActionBar.LayoutParams.FILL_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
        buttonParams.setMarginEnd(10);
        buttonParams.setMarginStart(10);
        buttonParams.weight = (float)0.5;

        //Add Views
        butonLayout.addView(cancelButton, buttonParams);
        butonLayout.addView(confirmButton, buttonParams);
        layout.addView(imageView, imageParameters);
        layout.addView(nameTextView, nameParameters);
        layout.addView(valueTextView, valueParameters);
        layout.addView(descriptionTextView, descriptionParameters);
        layout.addView(butonLayout, buttonsParameters);
        superLayout.addView(layout, layoutParams);

        return superLayout;
    }
}
