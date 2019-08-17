package particle.joeypak.Model;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.Date;

public class Transaction {
    static class Keys {
        public static String to = "to";
        public static String from =  "from";
        public static String value = "value";
        public static String description = "description";
        public static String date = "date";
        public static String status = "status";
        public static String transactionId = "transfer_id";
    }
    public enum TransactionStatus {
        Pending, Complete
    }

    private String mUniqueId;
    private String mToUserId;
    private String mFromUserId;
    private Integer mValue;
    private String mDescription;
    private Date mDate;
    private TransactionStatus mStatus;

    public String getUniqueId() { return mUniqueId; }
    public String getToUserId() { return mToUserId; }
    public String getFromUserId() { return mFromUserId; }
    public Integer getValue() { return mValue; }
    public String getDescription() { return mDescription; }
    public TransactionStatus getStatus() { return mStatus; }

    public Transaction(JSONObject object) {
        try {
            mUniqueId = object.getString(Keys.transactionId);
            mToUserId = object.getString(Keys.to);
            mFromUserId = object.getString(Keys.from);
            mValue = object.getInt(Keys.value);
            mDescription = object.getString(Keys.description);
            mDate = new Date(object.getInt(Keys.date));

            Integer status = object.getInt(Keys.status);
            if (status == 0) {
                mStatus = TransactionStatus.Pending;
            }
            else {
                mStatus = TransactionStatus.Complete;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
