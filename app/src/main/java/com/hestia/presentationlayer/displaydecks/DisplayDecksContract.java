package com.hestia.presentationlayer.displaydecks;

import com.hestia.presentationlayer.Base;

/**
 * Created by Richard on 3/6/2018.
 *
 * This is a contract for the Presenters and Views
 *  > The interface will ensure we can easily swap implementations out
 *  > Also ensure out implementations implement "x" method(s)
 */
public interface DisplayDecksContract {

  interface Presenter extends Base.BasePresenter {
  }

  interface View extends Base.BaseView {
  }
}
