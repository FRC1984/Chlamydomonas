/**
 * @author Aidan Smith
 */
package org.usfirst.frc.team1984.lib;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.*;

public class Dashboard extends SmartDashboard{
	public static SendableChooser<Integer> autoMode = new SendableChooser<Integer>();
	
	public static void autoAddObject(String name,Integer mode)
		{autoMode.addObject(name, mode);}
	public static void autoAddDefault(String name,Integer mode)
		{autoMode.addDefault(name, mode);}
	public static int autoGetSelected()
		{return autoMode.getSelected().intValue();}
	public static void displayAutoSelector()
		{SmartDashboard.putData("AutoMode",autoMode);}

	
	public static String getPrefsString(String value)
		{return Preferences.getInstance().getString(value, "Error");}
	public static int getPrefsInt(String value)
		{return Preferences.getInstance().getInt(value, -999);}
	public static double getPrefsDouble(String value)
		{return Preferences.getInstance().getDouble(value, -999.99);}
	
	public static void putPrefsString(String value,String val)
		{Preferences.getInstance().putString(value, val);}
	public static void putPrefsInt(String value,int val)
		{Preferences.getInstance().putInt(value, val);}
	public static void putPrefsDouble(String value,double val)
		{Preferences.getInstance().putDouble(value, val);}
}
