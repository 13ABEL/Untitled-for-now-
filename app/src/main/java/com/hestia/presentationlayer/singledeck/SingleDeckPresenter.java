package com.hestia.presentationlayer.singledeck;

/**
 * Created by Richard on 3/14/2018.
 */

public class SingleDeckPresenter implements SingleDeckContract.Presenter{
  private SingleDeckContract.View singleDeckView;


  public SingleDeckPresenter(SingleDeckContract.View view) {
    singleDeckView = view;
  }



}
