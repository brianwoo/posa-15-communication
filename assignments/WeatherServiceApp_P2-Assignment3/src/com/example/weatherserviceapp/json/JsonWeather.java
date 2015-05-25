package com.example.weatherserviceapp.json;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class JsonWeather
{

	private String message;
	private String cod;
	private Long count;
	private java.util.List<com.example.weatherserviceapp.json.List> list = new ArrayList<com.example.weatherserviceapp.json.List>();
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	/**
	 * 
	 * @return The message
	 */
	public String getMessage()
	{
		return message;
	}

	/**
	 * 
	 * @param message
	 *            The message
	 */
	public void setMessage(String message)
	{
		this.message = message;
	}

	/**
	 * 
	 * @return The cod
	 */
	public String getCod()
	{
		return cod;
	}

	/**
	 * 
	 * @param cod
	 *            The cod
	 */
	public void setCod(String cod)
	{
		this.cod = cod;
	}

	/**
	 * 
	 * @return The count
	 */
	public Long getCount()
	{
		return count;
	}

	/**
	 * 
	 * @param count
	 *            The count
	 */
	public void setCount(Long count)
	{
		this.count = count;
	}

	/**
	 * 
	 * @return The list
	 */
	public java.util.List<com.example.weatherserviceapp.json.List> getList()
	{
		return list;
	}

	/**
	 * 
	 * @param list
	 *            The list
	 */
	public void setList(
			java.util.List<com.example.weatherserviceapp.json.List> list)
	{
		this.list = list;
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