package particle.joeypak.Adapters;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TableRow;
import particle.joeypak.Model.User;
import particle.joeypak.Stores.UserStore;
import particle.joeypak.Views.UserHolder;

import java.util.ArrayList;
import java.util.List;

public class UserSearchAdapter extends RecyclerView.Adapter<UserHolder> implements View.OnClickListener {
    private List<UserHolder.Model> mModels = new ArrayList<>();
    private List<User> mUsers = new ArrayList<>();
    public UserStore.UserCallback selectedUserCallback;

    public UserSearchAdapter() {

    }

    //Actions
    public void clearResults() {
        while (mModels.size() > 0) {
            mModels.remove(0);
        }
        notifyDataSetChanged();
    }
    public void searchForString(String searchString) {
        UserStore.sharedInstance().searchForUsers(searchString, 0, new UserStore.SearchCallback() {
            @Override
            public void success(ArrayList<User> users) {
                mUsers = users;
                updateModels();
            }
        });
    }
    public void updateModels() {
        //Clear Existing Models
        while (mModels.size() > 0) {
            mModels.remove(0);
        }

        //Add New Models
        for (User user : mUsers) {
            UserHolder.Model model = new UserHolder.Model();

            model.title = user.getName();
            model.imageURL = user.getImageURL();

            mModels.add(model);
        }

        notifyDataSetChanged();
    }

    //View Holder
    @Override
    public int getItemCount() {
        return mModels.size();
    }

    @Override
    public UserHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new UserHolder(new RelativeLayout(parent.getContext()));
    }

    @Override
    public void onBindViewHolder(UserHolder holder, int position) {
        TableRow.LayoutParams params = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);

        holder.configureWithModel(mModels.get(position));
        holder.itemView.setOnClickListener(this);
        holder.itemView.setLayoutParams(params);
        holder.itemView.setId(position);
    }

    @Override
    public void onClick(View view) {
        //TODO: Implement me!
        if (selectedUserCallback != null) {
            User selectedUser = mUsers.get(view.getId());
            selectedUserCallback.success(selectedUser);
        }
    }
}
