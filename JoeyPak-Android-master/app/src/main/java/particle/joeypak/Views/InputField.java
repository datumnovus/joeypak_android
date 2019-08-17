package particle.joeypak.Views;

import android.app.ActionBar;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import particle.joeypak.Utilities.ColorUtilities;
import particle.joeypak.Utilities.FontUtilities;

public class InputField extends RelativeLayout {
    public TextView mLabel;
    public EditText mEditText;

    public InputField(Context context) {
        super(context);

        //Instantiate Variables
        LinearLayout layout = new LinearLayout(context);
        mLabel = new TextView(context);
        mEditText = new EditText(context);

        //Set Ids
        layout.setId(1);
        mLabel.setId(2);
        mEditText.setId(3);

        //Set Properties
        layout.setGravity(Gravity.CENTER_VERTICAL);
        layout.setOrientation(LinearLayout.HORIZONTAL);
        mLabel.setTextSize(20);
        mLabel.setGravity(Gravity.CENTER_VERTICAL);
        mLabel.setTypeface(FontUtilities.avenirMedium);
        mLabel.setTextColor(ColorUtilities.darkTextColor);
        mLabel.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_END);
        mEditText.setTextSize(20);
        mEditText.setGravity(Gravity.CENTER_VERTICAL);
        mEditText.setBackgroundColor(Color.TRANSPARENT);
        mEditText.setTypeface(FontUtilities.avenirRegular);
        mEditText.setTextColor(ColorUtilities.darkTextColor);
        mEditText.setPadding(0 , 10,0,10);

        //Set Constraints
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);

        LinearLayout.LayoutParams labelParams = new LinearLayout.LayoutParams(200, ActionBar.LayoutParams.MATCH_PARENT);
        labelParams.setMarginStart(40);
        labelParams.setMarginEnd(60);

        LinearLayout.LayoutParams valueParams = new LinearLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT);
        valueParams.setMarginEnd(40);

        //Add Views
        layout.addView(mLabel, labelParams);
        layout.addView(mEditText, valueParams);
        this.addView(layout, layoutParams);
    }
}
