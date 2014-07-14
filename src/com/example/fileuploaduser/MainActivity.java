package com.example.fileuploaduser;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;

@SuppressLint("NewApi")
public class MainActivity extends FragmentActivity implements ActionBar.TabListener {
	
	private ProgressDialog loading;
	private String myToken;
	private static final String STATE_SELECTED_NAVIGATION_ITEM = "selected_navigation_item";
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitymain);
        
        myToken = getIntent().getStringExtra("token");
//        Intent i = new Intent(this, FileFragmentActivity.class);
//        i.putExtra("myToken", myToken);

        // Set up the action bar.
        final ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // For each of the sections in the app, add a tab to the action bar.
        actionBar.addTab(actionBar.newTab().setText("My File").setTabListener(this));
        actionBar.addTab(actionBar.newTab().setText("All File").setTabListener(this));
        //actionBar.addTab(actionBar.newTab().setText(R.string.title_section3).setTabListener(this));
    }
	
	public String getToken(){
		return myToken;
	}
	
	public void onRestoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState.containsKey(STATE_SELECTED_NAVIGATION_ITEM)) {
            getActionBar().setSelectedNavigationItem(savedInstanceState.getInt(STATE_SELECTED_NAVIGATION_ITEM));
        }
    }
	
	 public void onSaveInstanceState(Bundle outState) {
	        outState.putInt(STATE_SELECTED_NAVIGATION_ITEM, getActionBar().getSelectedNavigationIndex());
	    }
	 
	 public boolean onCreateOptionsMenu(Menu menu) {
	        getMenuInflater().inflate(R.menu.main, menu);
	        return true;
	    }
	 
	 
	
	

	@Override
	public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
		// TODO Auto-generated method stub
		
		/**
    	 * On first tab we will show our list
    	 */
		//buat loading
		loading = ProgressDialog.show(MainActivity.this, "", "Loading");
		new Thread(){
			public void run(){
			try{
				sleep(10000);
			}catch (Exception e){
				Log.e("tag", e.getMessage());
			}
			loading.dismiss();
			}
		}.start();
		
		
    	if (tab.getPosition() == 0) {
    		FileFragmentMy myfile = new FileFragmentMy();
    		getSupportFragmentManager().beginTransaction().replace(R.id.container, myfile).commit();
    	} 
    	else if (tab.getPosition() == 1) {
    		FileFragmentAll allfile = new FileFragmentAll();
    		getSupportFragmentManager().beginTransaction().replace(R.id.container, allfile).commit(); 		
		}
//    	
//    	
//    	
//    	else {
//    		
//    		AndroidVersionList androidversionlist = new AndroidVersionList();
//    		getSupportFragmentManager().beginTransaction().replace(R.id.container, androidversionlist).commit();
//            /*Fragment fragment = new DummySectionFragment();
//            Bundle args = new Bundle();
//            args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, tab.getPosition() + 1);
//            fragment.setArguments(args);
//            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();*/
//    	}
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
		// TODO Auto-generated method stub
		
	}

}
