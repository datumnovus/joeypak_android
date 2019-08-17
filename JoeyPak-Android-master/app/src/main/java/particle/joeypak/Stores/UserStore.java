package particle.joeypak.Stores;

import android.os.Bundle;
import android.util.Log;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import particle.joeypak.Callbacks.CompletionCallback;
import particle.joeypak.Model.User;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class UserStore {
    //Static Variables
    private static UserStore mOurInstance = new UserStore();
    public static UserStore sharedInstance() {
        return mOurInstance;
    }

    //Internal Callbacks
    private interface FacebookCallback {
        void graphResponse(String id, String name, URL imageURL, CompletionCallback pass);
    }
    private interface AccountDetailsCallback {
        void accountResponse(JSONObject jsonObject, CompletionCallback pass);
    }

    //External Callbacks
    public interface SearchCallback {
        void success(ArrayList<User> users);
    }
    public interface UserCallback {
        void success(User user);
    }

    //Internal Variables
    private User mCurrentUser;
    private String mAuthToken;
    private Integer mBalance;
    private HashMap<String, User> mUsers = new HashMap<>();

    //Accessors
    public User getCurrentUser() { return mCurrentUser; }
    public String getAuthToken() { return mAuthToken; }
    public Integer getBalance() { return mBalance; }

    //Actions
    private void refreshCurrentJoeyUser(final CompletionCallback pass, String name, URL imageURL, String facebookId, final AccountDetailsCallback callback) {
        AndroidNetworking.get("http://13.56.210.36:3000/account_details")
                .addQueryParameter("name", name)
                .addQueryParameter("fb_img", imageURL.toString())
                .addQueryParameter("fb_uid", facebookId)
                .addQueryParameter("fb_token", AccessToken.getCurrentAccessToken().getToken())
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (!response.has("err")) {
                            callback.accountResponse(response, pass);
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.i("Error", anError.toString());
                    }
                });
    }
    private void refreshFacebookUser(final CompletionCallback pass, final FacebookCallback callback) {
        GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        String id = null;
                        String name = null;
                        String imageString = null;
                        try {
                            id = object.getString("id");
                            name = object.getString("name");
                            imageString = object.getJSONObject("picture").getJSONObject("data").getString("url");
                            URL imageURL = new URL(imageString);
                            callback.graphResponse(id, name, imageURL, pass);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id, name, picture.type(large)");
        request.setParameters(parameters);
        request.executeAsync();
    }
    public void refreshCurrentUser(CompletionCallback completion) {
        refreshFacebookUser(completion, new FacebookCallback() {
            @Override
            public void graphResponse(String id, String name, URL imageURL, CompletionCallback pass) {
                refreshCurrentJoeyUser(pass, name, imageURL, id, new AccountDetailsCallback() {
                    @Override
                    public void accountResponse(JSONObject jsonObject, CompletionCallback completion) {
                        try {
                            mBalance = jsonObject.getInt("balance");
                            mAuthToken = jsonObject.getString("auth_token");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        mCurrentUser = new User(jsonObject);
                        mUsers.put(mCurrentUser.getUniqueId(), mCurrentUser);

                        completion.success();
                    }
                });
            }
        });
    }
    public void searchForUsers(String searchString, Integer page, final SearchCallback callback) {
        AndroidNetworking.get("http://13.56.210.36:3000/user_lookup")
                .addQueryParameter("auth_token", mAuthToken)
                .addQueryParameter("name", searchString)
                .addQueryParameter("page", String.valueOf(page))
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        ArrayList<User> users = new ArrayList<>();
                        for (int i = 0; i<response.length(); i++) {
                            User user = null;
                            try {
                                user = new User(response.getJSONObject(i));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            if (!user.getUniqueId().equals(mCurrentUser.getUniqueId())) {
                                users.add(user);
                                mUsers.put(user.getUniqueId(), user);
                            }
                        }

                        callback.success(users);
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.i("Error", anError.toString());
                    }
                });
    }
    public void fetchUser(String uniqueId, final UserCallback callback) {
        if (mUsers.keySet().contains(uniqueId)) {
            callback.success(mUsers.get(uniqueId));
        }
        else {
            AndroidNetworking.get("http://13.56.210.36:3000/user_lookup_uid")
                    .addQueryParameter("auth_token", mAuthToken)
                    .addQueryParameter("fb_uid", uniqueId)
                    .addQueryParameter("page", "0")
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            User user = new User(response);
                            callback.success(user);
                        }

                        @Override
                        public void onError(ANError anError) {
                            Log.i("Error", anError.toString());
                        }
                    });
        }
    }
}
