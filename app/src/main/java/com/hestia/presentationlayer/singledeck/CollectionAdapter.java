package com.hestia.presentationlayer.singledeck;

import android.icu.text.IDNA;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.widget.Toast;

import com.hestia.domainlayer.Deck;

/**
 * Created by Richard on 3/17/2018.
 */

/**
 * Note that we use a FragmentPagerAdapter instead of a FragmentStatePagerAdapter
 * It's better for a small and fixed number of screens
 */
public class CollectionAdapter extends FragmentPagerAdapter{
  private SingleDeckContract.Presenter cPresenter;
  private String [] tabNames = {"INFO", "LIST"};
  private int numTabs = tabNames.length;

  public CollectionAdapter(FragmentManager fragManager, SingleDeckContract.Presenter presenter) {
    super(fragManager);
    this.cPresenter = presenter;
  }

  @Override
  public Fragment getItem(int position) {
    TabFragment newTabFragment;
    // return the right fragment depending on the position
    if (position == 0) {
      newTabFragment = InfoFragment.newInstance(0, "INFO", cPresenter);
    } else {
      newTabFragment = DeckListFragment.newInstance(1, "LIST", cPresenter);
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
