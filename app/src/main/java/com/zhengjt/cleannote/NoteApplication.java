package com.zhengjt.cleannote;

import android.app.Application;

public class NoteApplication extends Application {

  @Override
  public void onCreate() {
    super.onCreate();
    AVOS.AVInit(this);
  }
}
