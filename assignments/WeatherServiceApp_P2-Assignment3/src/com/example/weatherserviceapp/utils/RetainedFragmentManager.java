/**
 * 
 */
package com.example.weatherserviceapp.utils;

import java.lang.ref.WeakReference;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.example.weatherserviceapp.tasks.RetainedTask;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;

/**
 * @author bwoo
 * 
 */
public class RetainedFragmentManager extends Fragment
{

	private static final String TAG = RetainedFragmentManager.class
			.getSimpleName();
	private WeakReference<Activity> mCallbacks;

	/**
	 * Maps keys to objects.
	 */
	private Map<String, RetainedTask> mTasks = new HashMap<String, RetainedTask>();

	/**
	 * This method will only be called once when the retained Fragment is first
	 * created.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		// Retain this fragment across configuration changes.
		setRetainInstance(true);

		// Create and execute the background task.
		// mTask = new DummyTask();
		// mTask.execute();

	}

	/**
	 * Hold a reference to the parent Activity so we can report the task's
	 * current progress and results. The Android framework will pass us a
	 * reference to the newly created Activity after each configuration change.
	 */
	@Override
	public void onAttach(Activity activity)
	{
		super.onAttach(activity);
		mCallbacks = new WeakReference<Activity>(activity);
		Log.d(TAG, "----- RetainedFragmentManager.onAttach(). Activity="
				+ activity.toString() + " -----");
		
		// assuming there are more than one tasks in the map, we need
		// to reattach the weak ref of the MainActivity to all of them.
		reattachMainActivityToTask();

	}

	/**
	 * Set the callback to null so we don't accidentally leak the Activity
	 * instance.
	 */
	@Override
	public void onDetach()
	{
		Log.d(TAG, "----- RetainedFragmentManager.onDetach() -----");
		super.onDetach();
		mCallbacks = null;
	}

	
	/**
	 * Add the @a object with the @a key.
	 */
	public void put(String key, RetainedTask task)
	{
		mTasks.put(key, task);
	}

	/**
	 * Get the object with @a key.
	 */
	public RetainedTask get(String key)
	{
		return mTasks.get(key);
	}
	
	
	private void reattachMainActivityToTask()
	{
		Collection<RetainedTask> tasks = mTasks.values();
		for (RetainedTask task : tasks)
		{
			task.onConfigurationChange(mCallbacks);
		}
	}
}
