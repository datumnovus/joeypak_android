package particle.joeypak.Adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TableRow;
import particle.joeypak.AppCoordinator;
import particle.joeypak.Callbacks.CompletionCallback;
import particle.joeypak.Model.Transaction;
import particle.joeypak.Model.User;
import particle.joeypak.Stores.TransactionStore;
import particle.joeypak.Stores.UserStore;
import particle.joeypak.Views.HomeHolder;
import particle.joeypak.Views.SpacerHolder;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

//Note: we will need to manually account for section headers with the data source.
//It will be annoying, right now we just offset the position by 1 for the header.

public class HomeRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {
    private static final int itemTypeHeader = 0;
    private static final int itemTypeSectionHeader = 1;
    private static final int itemTypeDefaultHolder = 2;
    private HashMap<String, Transaction> mTransactions = new HashMap<>();
    private List<HomeHolder.Model> mModels = new ArrayList<>();
    public int mHeaderSize;

    public HomeRecyclerAdapter() {
        mHeaderSize = 890;
        reload(new CompletionCallback() {
            @Override
            public void success() {
            }

            @Override
            public void failure() {
            }
        });
    }

    //Actions
    public void reload(final CompletionCallback callback) {
        TransactionStore.sharedInstance().fetchTransactionHistory(0, new TransactionStore.TransactionsCallback() {
            @Override
            public void success(ArrayList<Transaction> transactions) {
                for (int i = 0; i < transactions.size(); i++) {
                    Transaction transaction = transactions.get(i);
                    mTransactions.put(transaction.getUniqueId(), transaction);
                }
                updateModels();
                callback.success();
            }
        });
    }
    public void updateModels() {
        while (mModels.size() > 0) {
            mModels.remove(0);
        }
        for (String key: mTransactions.keySet()) {
            //Declare Variables
            Transaction transaction = mTransactions.get(key);
            final HomeHolder.Model model = new HomeHolder.Model();
            User currentUser = UserStore.sharedInstance().getCurrentUser();
            String otherUserString = transaction.getToUserId();

            //Set values we know from the current user
            if (transaction.getToUserId().equals(currentUser.getUniqueId())) {
                otherUserString = transaction.getFromUserId();
                if (transaction.getStatus().equals(Transaction.TransactionStatus.Pending)) {
                    model.type = HomeHolder.HomeHolderType.Neutral;
                    model.action = "Waiting";
                    model.subtitle = String.format("J %d Requested.", transaction.getValue());
                }
                else {
                    model.type = HomeHolder.HomeHolderType.Positive;
                    model.action = String.format("+%d", transaction.getValue());
                    model.subtitle = transaction.getDescription();
                }
            }
            else {
                if (transaction.getStatus().equals(Transaction.TransactionStatus.Pending)) {
                    model.type = HomeHolder.HomeHolderType.Action;
                    model.action = "Respond";
                    model.subtitle = String.format("Requests j %d", transaction.getValue());
                }
                else {
                    model.type = HomeHolder.HomeHolderType.Negative;
                    model.action = String.format("-%d", transaction.getValue());
                    model.subtitle = transaction.getDescription();
                }
            }

            //Fetch Other User
            model.title = "";
            model.imageURL = null;
            model.uniqueId = transaction.getUniqueId();
            UserStore.sharedInstance().fetchUser(otherUserString, new UserStore.UserCallback() {
                @Override
                public void success(User user) {
                    model.title = user.getName();
                    model.imageURL = user.getImageURL();
                    notifyDataSetChanged();
                }
            });

            //Add Model
            mModels.add(model);
        }
        notifyDataSetChanged();
    }

    //View Holder
    @Override
    public int getItemCount() {
        return mModels.size()+1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return itemTypeHeader;
        }
        return itemTypeDefaultHolder;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == itemTypeHeader) {
            return new SpacerHolder(new View(parent.getContext()));
        }
        return new HomeHolder(new RelativeLayout(parent.getContext()));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        TableRow.LayoutParams params = new TableRow.LayoutParams(0,0);
        if (getItemViewType(position) == itemTypeHeader) {
            params = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, mHeaderSize);
        }
        if (holder instanceof HomeHolder) {
            params = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
            ((HomeHolder)holder).configureWithModel(mModels.get(position-1));
            holder.itemView.setOnClickListener(this);
        }

        holder.itemView.setLayoutParams(params);
        holder.itemView.setId(position);
    }

    @Override
    public void onClick(View view) {
        int index = view.getId()-1;
        HomeHolder.Model model = mModels.get(index);
        if (model.type.equals(HomeHolder.HomeHolderType.Action)) {
            Transaction transaction = mTransactions.get(model.uniqueId);
            AppCoordinator.sharedInstance().navigateToResponse(transaction);
        }
    }
}
