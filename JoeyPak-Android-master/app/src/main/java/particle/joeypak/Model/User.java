package particle.joeypak.Model;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;

public class User {
    static class Keys {
        public static String uniqueId = "fb_uid";
        public static String name = "name";
        public static String imageURL = "image";
        public static String fbImageURL = "fb_img";
    }

    private String mUniqueId;
    private String mName;
    private URL mImageURL;

    public String getUniqueId() { return mUniqueId; }
    public String getName() { return mName; }
    public URL getImageURL() { return mImageURL; }

    public User(JSONObject object) {
        try {
            mUniqueId = object.getString(Keys.uniqueId);
            mName = object.getString(Keys.name);

            if (object.has(Keys.imageURL)) {
                mImageURL = new URL(object.getString(Keys.imageURL));
            }
            else if (object.has(Keys.fbImageURL)) {
                mImageURL = new URL(object.getString(Keys.fbImageURL));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
