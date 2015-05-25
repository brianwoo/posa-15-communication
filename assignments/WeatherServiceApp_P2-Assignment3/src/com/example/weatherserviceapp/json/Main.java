package com.example.weatherserviceapp.json;

import java.util.HashMap;
import java.util.Map;

public class Main
{

	private Float temp;
	private Float tempMin;
	private Float tempMax;
	private Float pressure;
	private Float seaLevel;
	private Float grndLevel;
	private Long humidity;
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	/**
	 * 
	 * @return The temp
	 */
	public Float getTemp()
	{
		return temp;
	}

	/**
	 * 
	 * @param temp
	 *            The temp
	 */
	public void setTemp(Float temp)
	{
		this.temp = temp;
	}

	/**
	 * 
	 * @return The tempMin
	 */
	public Float getTempMin()
	{
		return tempMin;
	}

	/**
	 * 
	 * @param tempMin
	 *            The temp_min
	 */
	public void setTempMin(Float tempMin)
	{
		this.tempMin = tempMin;
	}

	/**
	 * 
	 * @return The tempMax
	 */
	public Float getTempMax()
	{
		return tempMax;
	}

	/**
	 * 
	 * @param tempMax
	 *            The temp_max
	 */
	public void setTempMax(Float tempMax)
	{
		this.tempMax = tempMax;
	}

	/**
	 * 
	 * @return The pressure
	 */
	public Float getPressure()
	{
		return pressure;
	}

	/**
	 * 
	 * @param pressure
	 *            The pressure
	 */
	public void setPressure(Float pressure)
	{
		this.pressure = pressure;
	}

	/**
	 * 
	 * @return The seaLevel
	 */
	public Float getSeaLevel()
	{
		return seaLevel;
	}

	/**
	 * 
	 * @param seaLevel
	 *            The sea_level
	 */
	public void setSeaLevel(Float seaLevel)
	{
		this.seaLevel = seaLevel;
	}

	/**
	 * 
	 * @return The grndLevel
	 */
	public Float getGrndLevel()
	{
		return grndLevel;
	}

	/**
	 * 
	 * @param grndLevel
	 *            The grnd_level
	 */
	public void setGrndLevel(Float grndLevel)
	{
		this.grndLevel = grndLevel;
	}

	/**
	 * 
	 * @return The humidity
	 */
	public Long getHumidity()
	{
		return humidity;
	}

	/**
	 * 
	 * @param humidity
	 *            The humidity
	 */
	public void setHumidity(Long humidity)
	{
		this.humidity = humidity;
	}

	public Map<String, Object> getAdditionalProperties()
	{
		return this.additionalProperties;
	}

	public void setAdditionalProperty(String name, Object value)
	{
		this.additionalProperties.put(name, value);
	}

}