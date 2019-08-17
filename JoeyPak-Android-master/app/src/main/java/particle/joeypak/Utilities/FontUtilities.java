package particle.joeypak.Utilities;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import particle.joeypak.JoeyPakApplication;

import java.util.Locale;

public class FontUtilities {
    public static final Typeface avenirItalic = Typeface.createFromAsset(JoeyPakApplication.getContext().getAssets(), String.format(Locale.US, "fonts/%s", "AvenirNext-Italic.ttf"));
    public static final Typeface avenirRegular = Typeface.createFromAsset(JoeyPakApplication.getContext().getAssets(), String.format(Locale.US, "fonts/%s", "AvenirNext-Regular.ttf"));
    public static final Typeface avenirMedium = Typeface.createFromAsset(JoeyPakApplication.getContext().getAssets(), String.format(Locale.US, "fonts/%s", "AvenirNext-Medium.ttf"));
    public static final Typeface avenirSemiBold = Typeface.createFromAsset(JoeyPakApplication.getContext().getAssets(), String.format(Locale.US, "fonts/%s", "AvenirNext-DemiBold.ttf"));
    public static final Typeface avenirBold = Typeface.createFromAsset(JoeyPakApplication.getContext().getAssets(), String.format(Locale.US, "fonts/%s", "AvenirNext-Bold.ttf"));
}
