package particle.joeypak.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import particle.joeypak.AppCoordinator;
import particle.joeypak.Model.Transaction;
import particle.joeypak.Stores.TransactionStore;

public class RequestTransactionFragment extends TransactionFragment {
    public RequestTransactionFragment() { setArguments(new Bundle()); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        mButton.setText("Confirm Request");
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mViewPagerManager != null) {
                    AppCoordinator.sharedInstance().dismissKeyboard();
                    mViewPagerManager.navigateToNextFragment();

                    TransactionStore.sharedInstance().send(Integer.valueOf(mValueField.getText().toString().replaceAll("[^0-9.]", "")), mUser, mForField.mEditText.getText().toString(), new TransactionStore.TransactionCallback() {
                        @Override
                        public void success(Transaction transaction) {
                            mCompletion.setText("👍", "Success!", String.format("You requested %d Joeys from %s.", transaction.getValue(), mUser.getName()));
                            mViewPagerManager.navigateToNextFragment();
                        }
                    });
                }
            }
        });

        return view;
    }
}
