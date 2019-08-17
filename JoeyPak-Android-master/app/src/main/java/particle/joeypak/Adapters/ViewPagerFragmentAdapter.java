package particle.joeypak.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

public class ViewPagerFragmentAdapter extends FragmentStatePagerAdapter {
    private List mFragments;
    public ViewPagerFragmentAdapter(FragmentManager manager, List fragments) {
        super(manager);
        mFragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return (Fragment)mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }
}
