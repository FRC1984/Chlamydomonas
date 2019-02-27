package org.usfirst.frc.team1984.lib;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
//import edu.wpi.first.wpilibj.smartdashboard.*;

public class Gyro 
{
	private ADXRS450_Gyro g;
	
	public Gyro()
	{
		g = new ADXRS450_Gyro();
		g.calibrate();
	}

	/**
	 * 
	 * @return angle 0-359 degrees
	 */
	public double get()
	{
		return ((g.getAngle()%360+360)%360);
	}
	
	public double getRaw()
	{
		return g.getAngle();
	}
	
	/**
	 * 
	 * @return angle 0-359 degrees as an integer
	 */
	public int getInt()
	{
		return (int)((g.getAngle()%360+360)%360);
	}
	
	/**
	 * Reset the gyro.
	 */
	public void reset()
	{
		g.reset();
	}
	
}
