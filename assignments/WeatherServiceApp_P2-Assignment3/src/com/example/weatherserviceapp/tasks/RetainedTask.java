/**
 * 
 */
package com.example.weatherserviceapp.tasks;

import java.lang.ref.WeakReference;

import android.app.Activity;




/**
 * @author bwoo
 *
 */
public interface RetainedTask
{

    /**
     * Called after a runtime configuration change occurs to finish
     * the initialization steps.
     */
    public void onConfigurationChange(WeakReference<Activity> activity);
}
