package com.hestia.presentationlayer.customadapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.hestia.presentationlayer.singledeck.DeckFragment;
import com.hestia.presentationlayer.singledeck.InfoFragment;
import com.hestia.presentationlayer.singledeck.SingleDeckContract;
import com.hestia.presentationlayer.singledeck.TabFragment;

/**
 * Created by Richard on 3/17/2018.
 */

/**
 * Note that we use a FragmentPagerAdapter instead of a FragmentStatePagerAdapter
 * It's better for a small and fixed number of screens
 */
public class SingleDeckTabAdapter extends FragmentStatePagerAdapter {
  private SingleDeckContract.Presenter cPresenter;
  private String [] tabNames = {"INFO", "LIST"};
  private int numTabs = tabNames.length;

  public SingleDeckTabAdapter(FragmentManager fragManager, TabFragment infoFrag, TabFragment deckFrag) {
    super(fragManager);
  }


  @Override
  public Fragment getItem(int position) {
    TabFragment newTabFragment;
    // return the right fragment depending on the position
    if (position == 0) {
      newTabFragment = new InfoFragment();
          //InfoFragment.newInstance(0, "INFO", cPresenter);
    } else {
      newTabFragment = new DeckFragment();
      //DeckFragment.newInstance(1, "LIST", cPresenter);
    }
    return newTabFragment;
  }


  @Override
  public int getCount() {
    return numTabs;
  }

  public CharSequence getPageTitle(int position) {
    return tabNames[position];
  }

}
