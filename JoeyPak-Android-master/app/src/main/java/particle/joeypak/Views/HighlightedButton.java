package particle.joeypak.Views;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import particle.joeypak.Utilities.ColorUtilities;
import particle.joeypak.Utilities.FontUtilities;

public class HighlightedButton extends Button {
    public HighlightedButton(Context context) {
        super(context);

        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setColor(ColorUtilities.gray);
        drawable.setCornerRadius(20);

        setStateListAnimator(null);
        setAllCaps(false);
        setBackgroundColor(ColorUtilities.gray);
        setTextAlignment(TEXT_ALIGNMENT_CENTER);
        setTypeface(FontUtilities.avenirMedium);
        setTextColor(Color.WHITE);
        setBackground(drawable);
        setTextSize(18);

        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                GradientDrawable drawable = new GradientDrawable();
                drawable.setShape(GradientDrawable.RECTANGLE);
                drawable.setCornerRadius(20);

                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    drawable.setColor(ColorUtilities.gray);
                    setBackground(drawable);
                }
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    drawable.setColor(ColorUtilities.green);
                    setBackground(drawable);
                }
                return false;
            }
        });
    }
}
