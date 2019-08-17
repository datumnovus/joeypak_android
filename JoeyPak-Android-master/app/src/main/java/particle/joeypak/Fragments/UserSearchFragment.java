package particle.joeypak.Fragments;

//import android.app.Fragment;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import particle.joeypak.Adapters.UserSearchAdapter;

public class UserSearchFragment extends Fragment {
    private RecyclerView mRecyclerView;
    public UserSearchAdapter mItemAdapter;

    public UserSearchFragment() {
        setArguments(new Bundle());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Instantiate Member Variables
        mRecyclerView = new RecyclerView(getActivity());
        mItemAdapter  = new UserSearchAdapter();

        //Set Properties
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mItemAdapter);

        return mRecyclerView;
    }
}
