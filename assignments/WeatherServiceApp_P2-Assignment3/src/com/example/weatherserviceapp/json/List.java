package com.example.weatherserviceapp.json;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class List
{

	private Long id;
	private String name;
	private Main main;
	private Long dt;
	private java.util.List<Weather> weather = new ArrayList<Weather>();
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	/**
	 * 
	 * @return The id
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * 
	 * @param id
	 *            The id
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * 
	 * @return The name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * 
	 * @param name
	 *            The name
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * 
	 * @return The main
	 */
	public Main getMain()
	{
		return main;
	}

	/**
	 * 
	 * @param main
	 *            The main
	 */
	public void setMain(Main main)
	{
		this.main = main;
	}

	/**
	 * 
	 * @return The dt
	 */
	public Long getDt()
	{
		return dt;
	}

	/**
	 * 
	 * @param dt
	 *            The dt
	 */
	public void setDt(Long dt)
	{
		this.dt = dt;
	}

	/**
	 * 
	 * @return The weather
	 */
	public java.util.List<Weather> getWeather()
	{
		return weather;
	}

	/**
	 * 
	 * @param weather
	 *            The weather
	 */
	public void setWeather(java.util.List<Weather> weather)
	{
		this.weather = weather;
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