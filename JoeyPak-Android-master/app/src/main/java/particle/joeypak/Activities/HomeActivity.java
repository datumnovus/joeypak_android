package particle.joeypak.Activities;

import android.app.ActionBar;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.*;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import org.w3c.dom.Text;
import particle.joeypak.AppCoordinator;
import particle.joeypak.Fragments.HomeRecyclerFragment;
import particle.joeypak.R;
import particle.joeypak.Stores.UserStore;
import particle.joeypak.Utilities.ColorUtilities;
import particle.joeypak.Utilities.FontUtilities;
import particle.joeypak.Views.HighlightedButton;

public class HomeActivity extends JoeyActivity {
    private ImageView mImageView;
    private TextView mTitle;
    private TextView mSubtitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Create Variables
        final HomeRecyclerFragment tableFragment = new HomeRecyclerFragment();
        final View tableCover = new View(this);
        HighlightedButton settingsButton = new HighlightedButton(this);
        HighlightedButton payButton = new HighlightedButton(this);
        HighlightedButton requestButton = new HighlightedButton(this);
        LinearLayout tableFragmentLayout = new LinearLayout(this);
        LinearLayout buttonLayout = new LinearLayout(this);
        mImageView = new ImageView(this);
        mTitle = new TextView(this);
        mSubtitle = new TextView(this);

        GradientDrawable imageDrawable = new GradientDrawable();
        imageDrawable.setShape(GradientDrawable.RECTANGLE);
        imageDrawable.setCornerRadius(30);

        //Set Ids
        mImageView.setId(1);
        mTitle.setId(2);
        mSubtitle.setId(3);
        tableFragmentLayout.setId(4);
        tableCover.setId(5);
        buttonLayout.setId(6);
        settingsButton.setId(7);
        payButton.setId(8);
        requestButton.setId(9);

        //Set Properties
        mImageView.setBackground(imageDrawable);
        mTitle.setTypeface(FontUtilities.avenirBold);
        mTitle.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        mTitle.setTextColor(Color.WHITE);
        mTitle.setTextSize(32);
        mTitle.setText(UserStore.sharedInstance().getCurrentUser().getName());
        mSubtitle.setTypeface(FontUtilities.avenirMedium);
        mSubtitle.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        mSubtitle.setTextColor(Color.WHITE);
        mSubtitle.setTextSize(20);
        mSubtitle.setText(String.format("%d Joeys", UserStore.sharedInstance().getBalance()));
        getWindow().getDecorView().setBackgroundColor(ColorUtilities.green);
        buttonLayout.setOrientation(LinearLayout.HORIZONTAL);
        tableFragmentLayout.setOrientation(LinearLayout.VERTICAL);
        getFragmentManager().beginTransaction().add(tableFragmentLayout.getId(), tableFragment, "someTag1").commit();
        tableCover.setBackgroundColor(Color.WHITE);
        settingsButton.setText("Settings");
        payButton.setText("Pay");
        requestButton.setText("Request");
        settingsButton.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                AppCoordinator.sharedInstance().navigateToSettings();
                                            }
                                        });
        payButton.setOnClickListener(new View.OnClickListener() {
                                              @Override
                                              public void onClick(View v) {
                                                  AppCoordinator.sharedInstance().navigateToPayment();
                                              }
                                          });
        requestButton.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View v) {
                                             AppCoordinator.sharedInstance().navigateToRequest();
                                         }
                                     });
        Picasso.with(mImageView.getContext())
                .load(UserStore.sharedInstance().getCurrentUser().getImageURL().toString())
                .fit()
                .centerCrop()
                .into(mImageView);

        //Set Constraints
        RelativeLayout.LayoutParams imageParams = new RelativeLayout.LayoutParams(274, 302);
        imageParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        imageParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        imageParams.setMargins(0, 80, 0, 0);

        RelativeLayout.LayoutParams titleParams = new RelativeLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        titleParams.addRule(RelativeLayout.BELOW, mImageView.getId());
        titleParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        titleParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        titleParams.setMargins(70, 30, 70, 0);

        RelativeLayout.LayoutParams subtitleParams = new RelativeLayout.LayoutParams(750, ActionBar.LayoutParams.WRAP_CONTENT);
        subtitleParams.addRule(RelativeLayout.BELOW, mTitle.getId());
        subtitleParams.addRule(RelativeLayout.ALIGN_LEFT, mTitle.getId());
        subtitleParams.addRule(RelativeLayout.ALIGN_RIGHT, mTitle.getId());
        subtitleParams.setMargins(0, 30, 0, 0);

        RelativeLayout.LayoutParams tableCoverParams = new RelativeLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        tableCoverParams.addRule(RelativeLayout.BELOW, mSubtitle.getId());
        tableCoverParams.addRule(RelativeLayout.ALIGN_BOTTOM, tableFragmentLayout.getId());
        tableCoverParams.addRule(RelativeLayout.ALIGN_LEFT, tableFragmentLayout.getId());
        tableCoverParams.addRule(RelativeLayout.ALIGN_RIGHT, tableFragmentLayout.getId());
        tableCoverParams.setMargins(0 ,10, 0,0);

        RelativeLayout.LayoutParams fragParams = new RelativeLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT);
        fragParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        
        RelativeLayout.LayoutParams buttonLayoutParams = new RelativeLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
        buttonLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        buttonLayoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        buttonLayoutParams.setMargins(10,0,10,30);
        
        LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(ActionBar.LayoutParams.FILL_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
        buttonParams.setMarginEnd(10);
        buttonParams.setMarginStart(10);
        buttonParams.weight = (float)0.5;

        //Add Views
        buttonLayout.addView(settingsButton, buttonParams);
        buttonLayout.addView(payButton, buttonParams);
        buttonLayout.addView(requestButton, buttonParams);
        mLayout.addView(mTitle, titleParams);
        mLayout.addView(mSubtitle, subtitleParams);
        mLayout.addView(mImageView, imageParams);
        mLayout.addView(tableCover, tableCoverParams);
        mLayout.addView(tableFragmentLayout, fragParams);
        mLayout.addView(buttonLayout, buttonLayoutParams);

        ViewTreeObserver viewTreeObserver = tableCover.getViewTreeObserver();
        if (viewTreeObserver.isAlive()) {
            viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    tableCover.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    tableFragment.setHeaderSize(tableCover.getTop());
                }
            });
        }
    }
}
