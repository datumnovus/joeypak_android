package particle.joeypak.Fragments;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import particle.joeypak.Utilities.ColorUtilities;

public class LoadingFragment extends ViewPagerFragment.Page {
    public LoadingFragment() {
        setArguments(new Bundle());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Instantiate Variables
        RelativeLayout layout = new RelativeLayout(getActivity());
        ProgressBar progressBar = new ProgressBar(getActivity());

        //Set Properties
        progressBar.setIndeterminate(true);
        progressBar.setIndeterminateTintList(ColorStateList.valueOf(ColorUtilities.green));
        layout.setGravity(Gravity.CENTER);

        //Set Constraints
        RelativeLayout.LayoutParams parameters = new RelativeLayout.LayoutParams(300,300);

        //Add Views
        layout.addView(progressBar, parameters);

        return layout;
    }
}
