/**
 * 
 */
package com.example.weatherserviceapp.tasks;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import com.example.weatherserviceapp.R;
import com.example.weatherserviceapp.activities.MainActivity;
import com.example.weatherserviceapp.services.WeatherServiceAsync;
import com.example.weatherserviceapp.services.WeatherServiceSync;
import com.example.weatherserviceapp.utils.GenericServiceConnection;
import com.example.weatherserviceapp.utils.Utils;

import vandy.mooc.aidl.WeatherCall;
import vandy.mooc.aidl.WeatherData;
import vandy.mooc.aidl.WeatherRequest;
import vandy.mooc.aidl.WeatherResults;
import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

/**
 * @author bwoo
 * 
 */
public class WeatherFetchTask implements RetainedTask
{

	private static final String TAG = WeatherFetchTask.class.getSimpleName();

	private WeakReference<Activity> mActivityRef;

	/**
	 * This GenericServiceConnection is used to receive results after binding to
	 * the WeatherServiceSync Service using bindService().
	 */
	private GenericServiceConnection<WeatherCall> mServiceConnectionSync;

	/**
	 * This GenericServiceConnection is used to receive results after binding to
	 * the WeatherServiceAsync Service using bindService().
	 */
	private GenericServiceConnection<WeatherRequest> mServiceConnectionAsync;

	private WeakReference<EditText> mEditText;

	private WeatherData mCachedWeatherData;
	
	/**
	 * Constructor
	 */
	public WeatherFetchTask() {	}

	
	/**
	 * Cache the view fields (e.g., text, mEditText objects).
	 */
	private void initializeViewFields()
	{
		System.out.println("---- initializeViewFields -----");
		
		
		// Store the EditText that holds the urls entered by the user
		// (if any).
		mEditText = new WeakReference<>((EditText) mActivityRef.get()
				.findViewById(R.id.editText1));
		
		if (mCachedWeatherData != null)
		{
			displayWeatherData(mCachedWeatherData);
		}

	}

	/**
	 * (Re)initialize the non-view fields (e.g., GenericServiceConnection
	 * objects).
	 */
	private void initializeNonViewFields()
	{
		mServiceConnectionSync = new GenericServiceConnection<WeatherCall>(
				WeatherCall.class);

		mServiceConnectionAsync = new GenericServiceConnection<WeatherRequest>(
				WeatherRequest.class);
	}

	/**
	 * Initiate the service binding protocol.
	 */
	public void bindService()
	{
		Log.d(TAG, "calling bindService()");

		// Launch the Weather Bound Services if they aren't already
		// running via a call to bindService(), which binds this
		// activity to the WeatherService* if they aren't already
		// bound.
		if (mServiceConnectionSync.getInterface() == null)
			mActivityRef.get().bindService(
					WeatherServiceSync.makeIntent(mActivityRef.get()),
					mServiceConnectionSync, Context.BIND_AUTO_CREATE);

		if (mServiceConnectionAsync.getInterface() == null)
		{
			Log.d(TAG, "bind to mServiceConnectionAsync");
			mActivityRef.get().bindService(
					WeatherServiceAsync.makeIntent(mActivityRef.get()),
					mServiceConnectionAsync, Context.BIND_AUTO_CREATE);
		}
	}

	/**
	 * Initiate the service unbinding protocol.
	 */
	public void unbindService()
	{
		// Unbind the Async Service if it is connected.
		if (mServiceConnectionAsync.getInterface() != null)
			mActivityRef.get().unbindService(mServiceConnectionAsync);

		// Unbind the Sync Service if it is connected.
		if (mServiceConnectionSync.getInterface() != null)
			mActivityRef.get().unbindService(mServiceConnectionSync);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.example.weatherserviceapp.tasks.RetainedTask#onConfigurationChange
	 * (java.lang.ref.WeakReference)
	 */
	@Override
	public void onConfigurationChange(WeakReference<Activity> activity)
	{
		mActivityRef = activity;

		initializeViewFields();
		initializeNonViewFields();
	}

	/*
	 * Initiate the asynchronous weather lookup when the user presses the
	 * "Look Up Async" button.
	 */
	public void fetchWeatherAsync(View v)
	{
		WeatherRequest weatherRequest = mServiceConnectionAsync.getInterface();

		if (weatherRequest != null)
		{
			
			// Get the weather location entered by the user.
			final String location = mEditText.get().getText().toString();

			Utils.hideKeyboard(mActivityRef.get(), mEditText.get()
					.getWindowToken());

			try
			{
				// Invoke a one-way AIDL call, which does not block
				// the client. The results are returned via the
				// sendResults() method of the mWeatherResults
				// callback object, which runs in a Thread from the
				// Thread pool managed by the Binder framework.
				weatherRequest.getCurrentWeather(location, mWeatherResults);

			}
			catch (RemoteException e)
			{
				Log.e(TAG, "RemoteException:" + e.getMessage());
			}
		}
		else
		{
			Log.d(TAG, "weatherRequest was null.");
		}
	}

	/**
	 * The implementation of the WeatherResults AIDL Interface, which will be
	 * passed to the Weather Web service using the
	 * WeatherRequest.expandWeather() method.
	 * 
	 * This implementation of WeatherResults.Stub plays the role of Invoker in
	 * the Broker Pattern since it dispatches the upcall to sendResults().
	 */
	private WeatherResults.Stub mWeatherResults = new WeatherResults.Stub()
	{
		/**
		 * This method is invoked by the WeatherServiceAsync to return the
		 * results back to the WeatherActivity.
		 */
		/*
		 * @Override public void sendResults(final List<WeatherData>
		 * weatherDataList) throws RemoteException { // Since the Android Binder
		 * framework dispatches this // method in a background Thread we need to
		 * explicitly // post a runnable containing the results to the UI //
		 * Thread, where it's displayed. mActivity.get().runOnUiThread(new
		 * Runnable() { public void run() { displayResults(weatherDataList); }
		 * }); }
		 */

		/**
		 * This method is invoked by the WeatherServiceAsync to return error
		 * results back to the WeatherActivity.
		 */
		/*
		 * @Override public void sendError(final String reason) throws
		 * RemoteException { // Since the Android Binder framework dispatches
		 * this // method in a background Thread we need to explicitly // post a
		 * runnable containing the results to the UI // Thread, where it's
		 * displayed. mActivity.get().runOnUiThread(new Runnable() { public void
		 * run() { Utils.showToast(mActivity.get(), reason); } }); }
		 */

		@Override
		public void sendResults(List<WeatherData> results)
				throws RemoteException
		{
			System.out.println("===== Received Results from Async =====");
			
			if ((null == results) || null == results.get(0))
			{
				displayNoResultsFound();
				return;
			}
			
			mCachedWeatherData = results.get(0);
			displayWeatherData(mCachedWeatherData);

		}

		
		


		private void displayNoResultsFound()
		{
			// TODO Auto-generated method stub
			
		}
	};

	
	
	private void displayWeatherData(WeatherData weatherData)
	{
		if ((null == mActivityRef) || (null == mActivityRef.get()))
			return;
		
		MainActivity mainActivity = (MainActivity) mActivityRef.get();
		mainActivity.displayWeatherData(weatherData);
		
	}
	
	
	
	
	public void fetchWeatherSync(View v)
	{
		final WeatherCall weatherCall = mServiceConnectionSync.getInterface();

		if (weatherCall != null)
		{
			// Get the weather location entered by the user.
			final String location = mEditText.get().getText().toString();

			Utils.hideKeyboard(mActivityRef.get(), mEditText.get()
					.getWindowToken());

			// Use AsyncTask for the two-way sync call because
			// this is going to block the UI Thread.
			AsyncTask<String, Void, List<WeatherData>> weatherAsyncTask = 
					new AsyncTask<String, Void, List<WeatherData>>()
			{

				@Override
				protected List<WeatherData> doInBackground(String... params)
				{
					List<WeatherData> weatherDataList;

					try
					{
						weatherDataList = weatherCall
								.getCurrentWeather(location);
					}
					catch (RemoteException e)
					{
						e.printStackTrace();
						weatherDataList = new ArrayList<WeatherData>();
					}

					return weatherDataList;
				}

				/**
				 * 
				 */
				@Override
				protected void onPostExecute(List<WeatherData> result)
				{
					System.out.println("===== RECEIVED RESULTS from Sync =====");
				}
			};

			weatherAsyncTask.execute();

		}

		else
		{
			Log.d(TAG, "weatherRequest was null.");
		}
	}

}
