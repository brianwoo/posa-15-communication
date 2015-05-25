/**
 * 
 */
package com.example.weatherserviceapp.tasks;

import java.lang.ref.WeakReference;
import java.util.List;

import com.example.weatherserviceapp.R;
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
	 * the AcronymServiceSync Service using bindService().
	 */
	private GenericServiceConnection<WeatherCall> mServiceConnectionSync;

	/**
	 * This GenericServiceConnection is used to receive results after binding to
	 * the AcronymServiceAsync Service using bindService().
	 */
	private GenericServiceConnection<WeatherRequest> mServiceConnectionAsync;

	private WeakReference<EditText> mEditText;

	/**
	 * 
	 */
	public WeatherFetchTask()
	{
		initializeViewFields();
		initializeNonViewFields();
	}

	private void initializeViewFields()
	{
        // Store the EditText that holds the urls entered by the user
        // (if any).
        mEditText = new WeakReference<>
            ((EditText) mActivityRef.get().findViewById(R.id.editText1));
		
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

		// Launch the Acronym Bound Services if they aren't already
		// running via a call to bindService(), which binds this
		// activity to the AcronymService* if they aren't already
		// bound.
		if (mServiceConnectionSync.getInterface() == null)
			mActivityRef.get().bindService(
					WeatherServiceSync.makeIntent(mActivityRef.get()),
					mServiceConnectionSync, Context.BIND_AUTO_CREATE);

		if (mServiceConnectionAsync.getInterface() == null)
			mActivityRef.get().bindService(
					WeatherServiceAsync.makeIntent(mActivityRef.get()),
					mServiceConnectionAsync, Context.BIND_AUTO_CREATE);
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

	}

	
	
    /*
     * Initiate the asynchronous acronym lookup when the user presses
     * the "Look Up Async" button.
     */
    public void fetchWeatherAsync(View v) {
        WeatherRequest acronymRequest = 
            mServiceConnectionAsync.getInterface();

        if (acronymRequest != null) {
            // Get the acronym entered by the user.
            final String acronym =
                mEditText.get().getText().toString();

            Utils.hideKeyboard(mActivityRef.get(),
                               mEditText.get().getWindowToken());

            try {
                // Invoke a one-way AIDL call, which does not block
                // the client.  The results are returned via the
                // sendResults() method of the mAcronymResults
                // callback object, which runs in a Thread from the
                // Thread pool managed by the Binder framework.
                acronymRequest.getCurrentWeather(acronym, mWeatherResults);
                
                
            } catch (RemoteException e) {
                Log.e(TAG, "RemoteException:" + e.getMessage());
            }
        } else {
            Log.d(TAG, "acronymRequest was null.");
        }
    }
    
    
    
    /**
     * The implementation of the AcronymResults AIDL Interface, which
     * will be passed to the Acronym Web service using the
     * AcronymRequest.expandAcronym() method.
     * 
     * This implementation of AcronymResults.Stub plays the role of
     * Invoker in the Broker Pattern since it dispatches the upcall to
     * sendResults().
     */
    private WeatherResults.Stub mWeatherResults = new WeatherResults.Stub() {
            /**
             * This method is invoked by the AcronymServiceAsync to
             * return the results back to the AcronymActivity.
             */
    	/*
            @Override
            public void sendResults(final List<AcronymData> acronymDataList)
                throws RemoteException {
                // Since the Android Binder framework dispatches this
                // method in a background Thread we need to explicitly
                // post a runnable containing the results to the UI
                // Thread, where it's displayed.
                mActivity.get().runOnUiThread(new Runnable() {
                        public void run() {
                            displayResults(acronymDataList);
                        }
                    });
            }
            */

            /**
             * This method is invoked by the AcronymServiceAsync to
             * return error results back to the AcronymActivity.
             */
    	/*
            @Override
            public void sendError(final String reason)
                throws RemoteException {
                // Since the Android Binder framework dispatches this
                // method in a background Thread we need to explicitly
                // post a runnable containing the results to the UI
                // Thread, where it's displayed.
                mActivity.get().runOnUiThread(new Runnable() {
                        public void run() {
                            Utils.showToast(mActivity.get(),
                                            reason);
                        }
                    });
            }
            */

			@Override
			public void sendResults(List<WeatherData> results)
					throws RemoteException
			{
				System.out.println("===== SEND RESULTS from Async =====");
				
			}
	};
    
    
}
