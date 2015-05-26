package com.example.weatherserviceapp.utils;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import com.example.weatherserviceapp.json.JsonWeather;
import com.example.weatherserviceapp.json.WeatherJSONParser;

import vandy.mooc.aidl.WeatherData;
import android.app.Activity;
import android.content.Context;
import android.os.IBinder;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

/**
 * @class WeatherDownloadUtils
 * 
 * @brief Handles the actual downloading of Weather information from the Weather
 *        web service.
 */
public class Utils
{
	/**
	 * Logging tag used by the debugger.
	 */
	private final static String TAG = Utils.class.getCanonicalName();

	/**
	 * URL to the Weather web service.
	 */
	private final static String sWeather_Web_Service_URL = "http://api.openweathermap.org/data/2.5/weather?units=metric&q=";

	/**
	 * Obtain the Weather information.
	 * 
	 * @return The information that responds to your current weather search.
	 */

	public static List<WeatherData> getResults(final String location)
	{
		// Create a List that will return the WeatherData obtained
		// from the Weather Service web service.
		final List<WeatherData> returnList = new ArrayList<WeatherData>();

		// A List of JsonWeather objects.
		List<JsonWeather> jsonWeathers = null;

		try
		{
			String encodedLocation = URLEncoder.encode(location, "UTF-8");
			
			// Append the location to create the full URL.
			final URL url = new URL(sWeather_Web_Service_URL + encodedLocation);

			// Opens a connection to the Weather Service.
			HttpURLConnection urlConnection = (HttpURLConnection) url
					.openConnection();

			// Sends the GET request and reads the Json results.
			try (InputStream in = new BufferedInputStream(
					urlConnection.getInputStream()))
			{
				// Create the parser.
				final WeatherJSONParser parser = new WeatherJSONParser();

				// Parse the Json results and create JsonWeather data
				// objects.
				jsonWeathers = parser.parseJsonStream(in);
				System.out.println("----- finished parsing ----");
			}
			finally
			{
				urlConnection.disconnect();
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	
		if (jsonWeathers != null && jsonWeathers.size() > 0) 
		{ 
			// Convert the JsonWeather data objects to our WeatherData object, 
			// which can be passed between processes. 
			
			System.out.println("creating return list");
			
			for (JsonWeather jsonWeather : jsonWeathers) 
			{				
				returnList.add(new	WeatherData(
					jsonWeather.getName() + ", " + jsonWeather.getSys().getCountry(),
					jsonWeather.getWind().getSpeed(),
					jsonWeather.getWind().getDeg(),
					jsonWeather.getMain().getTemp(),
					jsonWeather.getMain().getHumidity(),
					jsonWeather.getSys().getSunrise(),
					jsonWeather.getSys().getSunset()
					));
				
			}
			// Return the List of WeatherData. 
			return returnList; 
		} 
		else 
			return null;
	}

	/**
	 * This method is used to hide a keyboard after a user has finished typing
	 * the url.
	 */
	public static void hideKeyboard(Activity activity, IBinder windowToken)
	{
		InputMethodManager mgr = (InputMethodManager) activity
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		mgr.hideSoftInputFromWindow(windowToken, 0);
	}

	/**
	 * Show a toast message.
	 */
	public static void showToast(Context context, String message)
	{
		Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
	}

	/**
	 * Ensure this class is only used as a utility.
	 */
	private Utils()
	{
		throw new AssertionError();
	}
}
