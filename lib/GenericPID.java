package org.usfirst.frc.team1984.lib;


public class GenericPID 
{
	private double p;
	private double i;
	private double d;
//	private double outputDeadZone;
//	private double inputDeadZone;
	private double deadZone;
	private double maxSpeed;
	private double minSpeed;
	private double lastVal;
	
	/**
	 * Sets up the gyro to be used in PID
	 *  
	 * @param imu ADIS16448 IMU
	 * @param outputDeadZone Auto set to: 0.6
	 * @param inputDeadZone Auto set to: 0.01
	 * @param maxSpeed Auto set to: 0.9
	 */
	public GenericPID()
	{
//		outputDeadZone = 0.05;
//		inputDeadZone = 0.01;
		maxSpeed = 1;
		minSpeed = -1;
		lastVal = 0;
		deadZone = 0;
	}
	
	/**
	 * Sets the values of the PID controls
	 * @param p
	 * @param i
	 * @param d
	 */
	public void setPID(double p, double i, double d)
	{
		this.p = p;
		this.i = i;
		this.d = d;
	}
	/**
	 * Sets the dead zone that the motors won't move the robot.
	 * @param d value from 0 to 1
	 */
//	public void setOutputDeadZone(double d)
//	{
//		outputDeadZone = d;
//	}
	
	/**
	 * Sets the value the gyro that will no longer make the robot move if 
	 * it is close to the target.
	 * @param g Have fun guessing; maybe 0.01
	 */
//	public void setInputDeadZone(double g)
//	{
//		inputDeadZone = g;
//	}
	public void setDeadZone(double d)
	{
		deadZone = d;
	}
	
	/**
	 * Sets the top speed that you will allow the output to move if it 
	 * is far from the desired angle.
	 * @param s value 0 to 1
	 */
	public void setMaxSpeed(double s)
	{
		maxSpeed = s;
	}
	
	/**
	 * Sets the top speed that you will allow the output to move if it 
	 * is far from the desired angle.
	 * @param s value 0 to 1
	 */
	public void setMinSpeed(double s)
	{
		minSpeed = s;
	}
	
	/**
	 * @param targetVal to command PID to go to
	 * @param currentVal current value of PID input
	 * @return value -1 to 1 to move the output
	 */
	public double setPIDVal(double targetVal, double currentVal)
	{
		double output_set = 0;
		double i_value = 0;
		double targt_cur_diff;
		double d_value = 0;
		double p_value;
		double mod_value;
		
		targt_cur_diff = targetVal - currentVal; 
		
		d_value = lastVal - targt_cur_diff;
		d_value *= d;
		i_value = i_value + targt_cur_diff;
		i_value *= i;
		p_value = targt_cur_diff;
		p_value *= p;
		
		lastVal = targt_cur_diff;
		mod_value = ( p_value + d_value + i_value );
		
		output_set = mod_value;
		if( output_set < deadZone && output_set > 0)
		{
			output_set += deadZone;
		}
		else if( output_set > -deadZone && output_set < 0)
		{
			output_set -= deadZone;
		}
		
		/*scale ouput to min / max values allowable*/
		if( output_set > maxSpeed)
		{
			output_set = (float)maxSpeed;
		}
		if( output_set < minSpeed)
		{
			output_set = (float)minSpeed;
		}
		
		return output_set;

	}
	
	
}
