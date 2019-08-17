package particle.joeypak.Views;

import android.app.ActionBar;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import particle.joeypak.Utilities.ColorUtilities;
import particle.joeypak.Utilities.FontUtilities;

import java.net.URL;

public class HomeHolder extends RecyclerView.ViewHolder {
    private ImageView mImageView;
    private TextView mTitle;
    private TextView mSubtitle;
    private TextView mValue;

    public enum HomeHolderType {
       Negative, Positive, Action, Neutral
    }
    public static class Model {
       public HomeHolderType type;
       public URL imageURL;
       public String title;
       public String subtitle;
       public String action;
       public String uniqueId;
    }

    public HomeHolder(View itemView) {
        super(itemView);

        //Instantiate Local Variables
        RelativeLayout layout = (RelativeLayout)itemView;
        GradientDrawable imageDrawable = new GradientDrawable();

        //Instantiate Member Variables
        mImageView = new ImageView(itemView.getContext());
        mTitle = new TextView(itemView.getContext());
        mSubtitle = new TextView(itemView.getContext());
        mValue = new TextView(itemView.getContext());

        //Set Ids
        mImageView.setId(1);
        mTitle.setId(2);
        mSubtitle.setId(3);
        mValue.setId(4);

        //Set Properties
        layout.setBackgroundColor(Color.WHITE);
        imageDrawable.setShape(GradientDrawable.RECTANGLE);
        imageDrawable.setCornerRadius(20);
        mTitle.setTypeface(FontUtilities.avenirBold);
        mTitle.setTextColor(ColorUtilities.darkTextColor);
        mTitle.setTextSize(16);
        mSubtitle.setTypeface(FontUtilities.avenirRegular);
        mSubtitle.setTextColor(ColorUtilities.darkTextColor);
        mSubtitle.setTextSize(16);
        mValue.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_END);
        mValue.setTextSize(18);
        mImageView.setBackground(imageDrawable);

        //Set Constraints
        RelativeLayout.LayoutParams imageParams = new RelativeLayout.LayoutParams(150,150);
        imageParams.addRule(RelativeLayout.CENTER_VERTICAL);
        imageParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        imageParams.setMargins(20,20,0,20);

        RelativeLayout.LayoutParams titleParams = new RelativeLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        titleParams.addRule(RelativeLayout.ALIGN_TOP, mImageView.getId());
        titleParams.addRule(RelativeLayout.RIGHT_OF, mImageView.getId());
        titleParams.addRule(RelativeLayout.LEFT_OF, mValue.getId());
        titleParams.setMargins(20,0,10,0);

        RelativeLayout.LayoutParams subtitleParams = new RelativeLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        subtitleParams.addRule(RelativeLayout.BELOW, mTitle.getId());
        subtitleParams.addRule(RelativeLayout.RIGHT_OF, mImageView.getId());
        subtitleParams.addRule(RelativeLayout.LEFT_OF, mValue.getId());
        subtitleParams.setMargins(20,0,20,0);

        RelativeLayout.LayoutParams valueParams = new RelativeLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        valueParams.addRule(RelativeLayout.CENTER_VERTICAL);
        valueParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        valueParams.setMargins(0,0,20,0);

        //Add Views
        layout.addView(mImageView, imageParams);
        layout.addView(mTitle, titleParams);
        layout.addView(mSubtitle, subtitleParams);
        layout.addView(mValue, valueParams);
    }

    public void configureWithModel(HomeHolder.Model model) {
        mImageView.setBackgroundColor(Color.GRAY);
        mTitle.setText(model.title);
        mSubtitle.setText(model.subtitle);
        mValue.setText(model.action);

        mImageView.setImageResource(0);
        if (model.imageURL != null) {
            Picasso.with(mImageView.getContext())
                    .load(model.imageURL.toString())
                    .fit()
                    .centerCrop()
                    .into(mImageView);
        }

        switch (model.type) {
            case Action:
                mValue.setTypeface(FontUtilities.avenirSemiBold);
                mValue.setTextColor(ColorUtilities.actionBlue);
                break;
            case Negative:
                mValue.setTypeface(FontUtilities.avenirMedium);
                mValue.setTextColor(ColorUtilities.red);
                break;
            case Positive:
                mValue.setTypeface(FontUtilities.avenirMedium);
                mValue.setTextColor(ColorUtilities.green);
                break;
            case Neutral:
                mValue.setTypeface(FontUtilities.avenirMedium);
                mValue.setTextColor(ColorUtilities.gray);
                break;
        }
    }
}