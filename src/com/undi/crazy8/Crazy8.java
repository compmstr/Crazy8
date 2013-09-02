package com.undi.crazy8;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class Crazy8 extends Activity
{
	private TitleView tView;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    	tView = new TitleView(this);
        tView.setKeepScreenOn(true);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
        			WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(tView);
    }
}
