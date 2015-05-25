/**
 * 
 */
package com.example.weatherserviceapp.services;


import java.util.List;

import vandy.mooc.aidl.WeatherCall;
import vandy.mooc.aidl.WeatherData;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;


/**
 * @author bwoo
 *
 */
public class WeatherServiceSync extends LifecycleLoggingService
{

    /**
     * Factory method that makes an Intent used to start the
     * WeatherServiceSync when passed to bindService().
     * 
     * @param context
     *            The context of the calling component.
     */
    public static Intent makeIntent(Context context) {
        return new Intent(context,
        		WeatherServiceSync.class);
    }

    
    /**
     * Called when a client (e.g., WeatherActivity) calls
     * bindService() with the proper Intent.  Returns the
     * implementation of WeatherCall, which is implicitly cast as an
     * IBinder.
     */
    @Override
    public IBinder onBind(Intent intent) {
        return mWeatherCallImpl;
    }

    /**
     * The concrete implementation of the AIDL Interface WeatherCall,
     * which extends the Stub class that implements WeatherCall,
     * thereby allowing Android to handle calls across process
     * boundaries.  This method runs in a separate Thread as part of
     * the Android Binder framework.
     * 
     * This implementation plays the role of Invoker in the Broker
     * Pattern.
     */
    WeatherCall.Stub mWeatherCallImpl = new WeatherCall.Stub() {
            /**
             * Implement the AIDL WeatherCall expandWeather() method,
             * which forwards to DownloadUtils getResults() to obtain
             * the results from the Weather Web service and then
             * returns the results back to the Activity.
             */
    	/*
            @Override
            public List<WeatherData> expandWeather(String Weather)
                throws RemoteException {

                // Call the Weather Web service to get the list of
                // possible expansions of the designated Weather.
                List<WeatherData> WeatherResults = 
                    Utils.getResults(Weather);

                if (WeatherResults != null) {
                    Log.d(TAG, "" 
                          + WeatherResults.size() 
                          + " results for Weather: " 
                          + Weather);

                    // Return the list of Weather expansions back to the
                    // WeatherActivity.
                    return WeatherResults;
                } else {
                    // Create a zero-sized WeatherResults object to
                    // indicate to the caller that the Weather had no
                    // expansions.
                    WeatherResults = new ArrayList<WeatherData>();
                    return WeatherResults;
                }
            }
            */

			@Override
			public List<WeatherData> getCurrentWeather(String Weather)
					throws RemoteException
			{
				System.out.println("----- getCurrentWeather Sync version-----");
				return null;
			}
	};
    
}
