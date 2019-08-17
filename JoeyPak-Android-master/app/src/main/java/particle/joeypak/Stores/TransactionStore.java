package particle.joeypak.Stores;

import android.util.Log;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import particle.joeypak.Model.Transaction;
import particle.joeypak.Model.User;

import java.util.ArrayList;

public class TransactionStore {
    private static TransactionStore mOurInstance = new TransactionStore();

    public static TransactionStore sharedInstance() {
        return mOurInstance;
    }

    //External Callbacks
    public interface TransactionsCallback {
        void success(ArrayList<Transaction> transactions);
    }
    public interface TransactionCallback {
        void success(Transaction transaction);
    }

    //Accessors
    public void fetchTransactionHistory(Integer page, final TransactionsCallback callback) {
        AndroidNetworking.get("http://13.56.210.36:3000/get_history")
                .addQueryParameter("auth_token", UserStore.sharedInstance().getAuthToken())
                .addQueryParameter("page", page.toString())
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        ArrayList<Transaction> transactions = new ArrayList<>();
                        for (int i = 0; i<response.length(); i++) {
                            Transaction transaction = null;
                            try {
                                transaction = new Transaction(response.getJSONObject(i));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            transactions.add(transaction);
                        }

                        callback.success(transactions);
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }

    //Actions
    public void acceptTransaction(final Transaction transaction, final TransactionCallback callback) {
        AndroidNetworking.post("http://13.56.210.36:3000/accept_transactions")
                .addBodyParameter("auth_token", UserStore.sharedInstance().getAuthToken())
                .addBodyParameter("transfer_id", transaction.getUniqueId())
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Transaction updatedTransaction = new Transaction(response);
                        callback.success(updatedTransaction);
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }
    public void send(Integer joeys, User toUser, String description, TransactionCallback callback) {
        newTransaction(toUser, UserStore.sharedInstance().getCurrentUser(), joeys, description, callback);
    }
    public void request(Integer joeys, User fromUser, String description, TransactionCallback callback) {
        newTransaction(UserStore.sharedInstance().getCurrentUser(), fromUser, joeys, description, callback);
    }

    //Helpers
    private void newTransaction(User to, User from, Integer joeys, String description, final TransactionCallback callback) {
        AndroidNetworking.post("http://13.56.210.36:3000/new_transaction")
                .addBodyParameter("auth_token", UserStore.sharedInstance().getAuthToken())
                .addBodyParameter("to", to.getUniqueId())
                .addBodyParameter("from", from.getUniqueId())
                .addBodyParameter("value", String.valueOf(joeys))
                .addBodyParameter("description", description)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Transaction updatedTransaction = new Transaction(response);
                        callback.success(updatedTransaction);
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }
}
