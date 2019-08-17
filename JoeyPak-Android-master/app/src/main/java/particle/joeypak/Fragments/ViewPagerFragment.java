package particle.joeypak.Fragments;

import android.app.ActionBar;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import particle.joeypak.Adapters.ViewPagerFragmentAdapter;

public class ViewPagerFragment extends Fragment {
    private ViewPagerFragmentAdapter mAdapter;
    private ViewPager mViewPager;
    private int mHeight;

    private static class ManagedViewPager extends ViewPager {
        public ManagedViewPager(Context context) {
            super(context);
        }

        public ManagedViewPager(Context context, AttributeSet attrs) {
            super(context, attrs);
        }
        @Override
        public boolean onInterceptTouchEvent(MotionEvent event) {
            // Never allow swiping to switch between pages
            return false;
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            // Never allow swiping to switch between pages
            return false;
        }
    }
    public static class Page extends android.support.v4.app.Fragment {
        protected ViewPagerFragment mViewPagerManager;
    }

    public ViewPagerFragment(ViewPagerFragmentAdapter adapter, int height) {
        setArguments(new Bundle());
        mAdapter = adapter;
        mHeight = height;

        //Set Managers
        for (int index = 0; index<mAdapter.getCount(); index++) {
            Object item = mAdapter.getItem(index);
            if (item instanceof ViewPagerFragment.Page) {
                Page page = (Page)item;
                page.mViewPagerManager = this;
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Instantiate Properties
        RelativeLayout layout = new RelativeLayout(getActivity());
        mViewPager = new ManagedViewPager(getActivity());

        //Set Properties
        mViewPager.setId(12412);
        mViewPager.setAdapter(mAdapter);

        //Set Constraints
        RelativeLayout.LayoutParams pagerParams = new RelativeLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, mHeight);
        pagerParams.addRule(RelativeLayout.CENTER_IN_PARENT);

        //Add Views
        layout.addView(mViewPager, pagerParams);

        return layout;
    }

    //MARK: Actions
    public void navigateToNextFragment() {
        int position = mViewPager.getCurrentItem()+1;
        if (position < mAdapter.getCount()) {
            mViewPager.setCurrentItem(position);
        }
    }
    public void navigateToPreviousFragment() {
        int position = mViewPager.getCurrentItem()-1;
        if (position >= 0) {
            mViewPager.setCurrentItem(position);
        }
    }
}
