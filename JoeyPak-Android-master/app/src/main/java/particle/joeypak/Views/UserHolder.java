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

public class UserHolder extends RecyclerView.ViewHolder {
    private ImageView mImageView;
    private TextView mTitleView;

    public static class Model {
        public URL imageURL;
        public String title;
    }

    public UserHolder(View itemView) {
        super(itemView);

        //Instantiate Local Variables
        RelativeLayout layout = (RelativeLayout)itemView;
        GradientDrawable imageDrawable = new GradientDrawable();

        //Instantiate Member Variables
        mImageView = new ImageView(itemView.getContext());
        mTitleView= new TextView(itemView.getContext());

        //Set Ids
        mImageView.setId(1);
        mTitleView.setId(2);

        //Set Properties
        layout.setBackgroundColor(Color.WHITE);
        imageDrawable.setShape(GradientDrawable.RECTANGLE);
        imageDrawable.setCornerRadius(20);
        mTitleView.setTypeface(FontUtilities.avenirBold);
        mTitleView.setTextColor(ColorUtilities.darkTextColor);
        mTitleView.setTextSize(16);
        mImageView.setBackground(imageDrawable);

        //Set Constraints
        RelativeLayout.LayoutParams imageParams = new RelativeLayout.LayoutParams(100,100);
        imageParams.addRule(RelativeLayout.CENTER_VERTICAL);
        imageParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        imageParams.setMargins(20,10,0,10);

        RelativeLayout.LayoutParams titleParams = new RelativeLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        titleParams.addRule(RelativeLayout.CENTER_VERTICAL);
        titleParams.addRule(RelativeLayout.RIGHT_OF, mImageView.getId());
        titleParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        titleParams.setMargins(20,0,10,0);

        //Add Views
        layout.addView(mImageView, imageParams);
        layout.addView(mTitleView, titleParams);
    }

    public void configureWithModel(UserHolder.Model model) {
        mTitleView.setText(model.title);
        mImageView.setBackgroundColor(Color.GRAY);
        mImageView.setImageResource(0);
        if (model.imageURL != null) {
            Picasso.with(mImageView.getContext())
                    .load(model.imageURL.toString())
                    .fit()
                    .centerCrop()
                    .into(mImageView);
        }
    }
}
