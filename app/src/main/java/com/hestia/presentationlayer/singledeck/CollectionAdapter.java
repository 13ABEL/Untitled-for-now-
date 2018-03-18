package com.hestia.presentationlayer.singledeck;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Richard on 3/17/2018.
 */

/**
 * Note that we use a FragmentPagerAdapter instead of a FragmentStatePagerAdapter
 * It's better for a small and fixed number of screens
 */
public class CollectionAdapter extends FragmentPagerAdapter{
  private String [] tabNames = {"INFO", "LIST"};
  final int NUM_ITEMS = tabNames.length;

  public CollectionAdapter(FragmentManager fragManager) {
    super(fragManager);
  }

  @Override
  public Fragment getItem(int position) {
    Fragment newTabFragment;
    // return the right fragment depending on the position
    if (position == 0) {
      newTabFragment = InfoFragment.newInstance(0, "INFO");
    }
    else {
      newTabFragment = DeckListFragment.newInstance(0, "LIST");
    }

    return newTabFragment;
  }


  @Override
  public int getCount() {
    return NUM_ITEMS;
  }


  /**
   * Used to set the tab itles
   * @param position
   * @return
   */
  public CharSequence getPageTitle(int position) {
    return tabNames[position];
  }


}
