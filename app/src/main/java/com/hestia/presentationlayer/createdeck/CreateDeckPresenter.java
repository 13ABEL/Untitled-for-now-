package com.hestia.presentationlayer.createdeck;

public class CreateDeckPresenter implements CreateDeckContract.Presenter {
  CreateDeckContract.View cView;


  CreateDeckPresenter(CreateDeckContract.View newView) {
    this.cView = newView;


  }
}
