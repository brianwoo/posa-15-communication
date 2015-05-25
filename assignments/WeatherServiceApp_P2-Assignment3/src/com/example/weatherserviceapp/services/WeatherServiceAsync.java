/**
 * 
 */
package com.example.weatherserviceapp.services;



import vandy.mooc.aidl.WeatherRequest;
import vandy.mooc.aidl.WeatherResults;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

/**
 * @author bwoo
 *
 */
public class WeatherServiceAsync extends LifecycleLoggingService
{
	
	
    /**
     * Factory method that makes an Intent used to start the
     * WeatherServiceAsync when passed to bindService().
     * 
     * @param context
     *            The context of the calling component.
     */
    public static Intent makeIntent(Context context) {
        return new Intent(context,
        		WeatherServiceAsync.class);
    }
    
    
    /**
     * Called when a client (e.g., AcronymActivity) calls
     * bindService() with the proper Intent.  Returns the
     * implementation of AcronymRequest, which is implicitly cast as
     * an IBinder.
     */
    @Override
    public IBinder onBind(Intent intent) {
        return mWeatherRequestImpl;
    }
    
    
    /**
     * The concrete implementation of the AIDL Interface
     * AcronymRequest, which extends the Stub class that implements
     * AcronymRequest, thereby allowing Android to handle calls across
     * process boundaries.  This method runs in a separate Thread as
     * part of the Android Binder framework.
     * 
     * This implementation plays the role of Invoker in the Broker
     * Pattern.
     */
    WeatherRequest.Stub mWeatherRequestImpl = new WeatherRequest.Stub() 
    {
            /**
             * Implement the AIDL AcronymRequest expandAcronym()
             * method, which forwards to DownloadUtils getResults() to
             * obtain the results from the Acronym Web service and
             * then sends the results back to the Activity via a
             * callback.
             */
    	/*
            @Override
            public void expandAcronym(String acronym,
                                      AcronymResults callback)
                throws RemoteException {

                // Call the Acronym Web service to get the list of
                // possible expansions of the designated acronym.
                List<AcronymData> acronymResults = 
                    Utils.getResults(acronym);

                // Invoke a one-way callback to send list of acronym
                // expansions back to the AcronymActivity.
                if (acronymResults != null) {
                    Log.d(TAG, "" 
                          + acronymResults.size() 
                          + " results for acronym: " 
                          + acronym);
                    callback.sendResults(acronymResults);
                } else
                    callback.sendError("No expansions for " 
                                       + acronym
                                       + " found");
            }
            */

			@Override
			public void getCurrentWeather(String Weather, WeatherResults results)
					throws RemoteException
			{
				// TODO Auto-generated method stub
				
			}
	};
}
