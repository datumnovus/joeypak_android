package particle.joeypak.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import particle.joeypak.Adapters.HomeRecyclerAdapter;

public class HomeRecyclerFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private HomeRecyclerAdapter mItemAdapter;

    public HomeRecyclerFragment() {
        setArguments(new Bundle());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Instantiate Member Variables
        mRecyclerView = new RecyclerView(getActivity());
        mItemAdapter  = new HomeRecyclerAdapter();

        //Set Properties
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mItemAdapter);

        return mRecyclerView;
    }

    public void setHeaderSize(int size) {
        mItemAdapter.mHeaderSize = size;
        mItemAdapter.notifyItemChanged(0);
    }
}
