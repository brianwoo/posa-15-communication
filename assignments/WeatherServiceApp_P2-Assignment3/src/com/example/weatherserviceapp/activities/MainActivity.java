package com.example.weatherserviceapp.activities;

import com.example.weatherserviceapp.R;
import com.example.weatherserviceapp.tasks.DummyTask;
import com.example.weatherserviceapp.tasks.WeatherFetchTask;
import com.example.weatherserviceapp.utils.RetainedFragmentManager;

import android.app.FragmentManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends LifecycleLoggingActivity
{
	private static final String TAG_TASK_FRAGMENT = "task_fragment";
	private RetainedFragmentManager mTaskFragment;
	
	private WeatherFetchTask mWeatherFetchTask;

	/**
	 * Used to retain the AcronymOps state between runtime configuration
	 * changes.
	 */
	// protected final OldRetainedFragmentManager mRetainedFragmentManager =
	// new OldRetainedFragmentManager(this.getFragmentManager(),
	// TAG);

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		handleConfigurationChanges();
	}

	/**
	 * Hook method called after onCreate() or after onRestart() (when the
	 * activity is being restarted from stopped state).
	 */
	@Override
	protected void onStart()
	{
		// Always call super class for necessary
		// initialization/implementation.
		super.onStart();

		// Initiate the service binding protocol.
		mWeatherFetchTask.bindService();
	}

	
    /**
     * Hook method called by Android when this Activity becomes
     * invisible.
     */
    @Override
    protected void onStop() {
        // Unbind from the Service.
    	mWeatherFetchTask.unbindService();

        // Always call super class for necessary operations when
        // stopping.
        super.onStop();
    }
    
	
	/**
	 * Handle hardware reconfigurations, such as rotating the display.
	 */
	protected void handleConfigurationChanges()
	{
		FragmentManager fm = getFragmentManager();
		mTaskFragment = (RetainedFragmentManager) fm
				.findFragmentByTag(TAG_TASK_FRAGMENT);

		// If the Fragment is non-null, then it is currently being
		// retained across a configuration change.
		if (mTaskFragment == null)
		{
			mTaskFragment = new RetainedFragmentManager();
			
			// add the tasks to the RetainedFragmentManager
			mTaskFragment.put("dummyTask", new DummyTask());
			
			mWeatherFetchTask = new WeatherFetchTask();
			mTaskFragment.put("WeatherFetchTask", mWeatherFetchTask);
			
			fm.beginTransaction().add(mTaskFragment, TAG_TASK_FRAGMENT)
					.commit();

		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings)
		{
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/*
	 * Initiate the synchronous acronym lookup when the user presses the
	 * "Look Up Sync" button.
	 */
	public void fetchWeatherAsync(View v)
	{

	}

	public void fetchWeatherSync(View v)
	{

	}

}
