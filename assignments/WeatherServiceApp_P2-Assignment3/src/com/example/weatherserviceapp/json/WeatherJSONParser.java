package com.example.weatherserviceapp.json;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import android.util.JsonReader;
import android.util.JsonToken;
import android.util.Log;

/**
 * Parses the Json acronym data returned from the Acronym Services API
 * and returns a List of JsonAcronym objects that contain this data.
 */
public class WeatherJSONParser {
    /**
     * Used for logging purposes.
     */
    private final String TAG =
        this.getClass().getCanonicalName();

    /**
     * Parse the @a inputStream and convert it into a List of JsonAcronym
     * objects.
     */
    public List<JsonWeather> parseJsonStream(InputStream inputStream)
        throws IOException {

        // Create a JsonReader for the inputStream.
        try (JsonReader reader =
             new JsonReader(new InputStreamReader(inputStream,
                                                  "UTF-8"))) {
            Log.d(TAG, "Parsing the results returned as an array");

            // Handle the array returned from the Acronym Service.
            return parseAcronymServiceResults(reader);
        }
    }

    /**
     * Parse a Json stream and convert it into a List of JsonAcronym
     * objects.
     */
    public List<JsonWeather> parseAcronymServiceResults(JsonReader reader)
        throws IOException {

        reader.beginArray();
        try {
            // If the acronym wasn't expanded return null;
            if (reader.peek() == JsonToken.END_ARRAY)
                return null;

            // Create a JsonAcronym object for each element in the
            // Json array.
            return parseAcronymMessage(reader);
        } finally {
            reader.endArray();
        }
    }

    public List<JsonWeather> parseAcronymMessage(JsonReader reader)
        throws IOException {

        List<JsonWeather> acronyms = new ArrayList<JsonWeather>();
        reader.beginObject();

        try {
            outerloop:
            while (reader.hasNext()) {
            	
            	JsonWeather jsonWeather = new JsonWeather();
            	
                String name = reader.nextName();
                switch (name) {
                case JsonWeather.MAIN_JSON:
                    Log.d(TAG, "reading main field");
                    reader.nextString();
                    break;
                case JsonWeather.WEATHER_JSON:
                    Log.d(TAG, "reading weather field");
                    if (reader.peek() == JsonToken.BEGIN_ARRAY)
                    {
                        List<Weather> weatherList = parseWeatherArray(reader);
                        jsonWeather.setWeather(weatherList);
                    }
                    break outerloop;
                default:
		    reader.skipValue();
                    Log.d(TAG, "weird problem with " + name + " field");
                    break;
                }
            }
        } finally {
                reader.endObject();
        }
        return acronyms;
    }

    /**
     * Parse a Json stream and convert it into a List of JsonAcronym
     * objects.
     */
    public List<Weather> parseWeatherArray(JsonReader reader)
        throws IOException {

        Log.d(TAG, "reading weather elements");

        reader.beginArray();

        try {
            List<Weather> acronyms = new ArrayList<Weather>();

            while (reader.hasNext()) 
                acronyms.add(parseWeather(reader));
            
            return acronyms;
        } finally {
            reader.endArray();
        }
    }

    /**
     * Parse a Json stream and return a JsonAcronym object.
     */
    public Weather parseWeather(JsonReader reader) 
        throws IOException {

        reader.beginObject();

        Weather acronym = new Weather();
        try {
            while (reader.hasNext()) {
                String name = reader.nextName();
                switch (name) {
                case JsonWeather.WEATHER_MAIN_JSON:
                    acronym.setMain(reader.nextString());
                    Log.d(TAG, "reading weather main " + acronym.getMain());
                    break;
                case JsonWeather.WEATHER_DESC_JSON:
                    acronym.setDescription(reader.nextString());
                    Log.d(TAG, "reading desc " + acronym.getDescription());
                    break;
                case JsonWeather.WEATHER_ICON_JSON:
                    acronym.setIcon(reader.nextString());
                    Log.d(TAG, "reading icon " + acronym.getIcon());
                    break;
                default:
                    reader.skipValue();
                    Log.d(TAG, "ignoring " + name);
                    break;
                }
            } 
        } finally {
                reader.endObject();
        }
        return acronym;
    }
}
