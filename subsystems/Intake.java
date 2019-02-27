package org.usfirst.frc.team1984.subsystems;

import org.usfirst.frc.team1984.robot.Robot;
import org.usfirst.frc.team1984.robot.*;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.*;

@SuppressWarnings("unused")
public class Intake {
	
	private Victor intake;
	private Preferences	prefs;
	private double speed;
	private AnalogInput line;
	private boolean intakeCalled,intakeOut;
	
	
	public Intake(int victor,int analog)
	{
		intake = new Victor(victor);
		this.line = new AnalogInput(analog);
		intakeCalled = false;
		intakeOut = false;
	}
	/**
	 * @Intake Push the balls out if the Y button is 
	 * pressed otherwise it will toggle in and off if 
	 * the X button is pressed.
	 * 
	 * @Climber uses the RT and LT buttons to climb the rope
	 * 
	 * @Smartdashboard preference value of "Intake Speed" to
	 * control the power to the motor.
	 * 
	 */
	public void run()
	{
		prefs = Preferences.getInstance();
		speed = prefs.getDouble("Intake Speed", .75);
		SmartDashboard.putNumber("\"Encoder\"", line.getValue());
		
	    //Climbing
		if (Robot.controller.getLT() > .95 && Robot.controller.getRT() >.95)
		{
			in(1);
		}
		//Align
		else if (Robot.controller.getLT()>.9 && line.getValue() > 3000)
		{
			in(.23);
		}
		//Half Speed
		else if (Robot.controller.getRT()> .95)
		{
			in(.5);
		}
		//Off
		else
		{
			if(intakeCalled)
				if (intakeOut)
					out(.8);
				else
					in(1);
			else
				off();
		}
	}
	
	private void in(double speed)
	{
		intake.set(speed);
	}
	
	private void out(double speed)
	{
		intake.set(-speed);
	}
	
	private void off()
	{
		intake.set(0);
	}
	public void intakeOn()
	{
		intakeCalled = true;
		intakeOut = false;
	}
	public void intakeOff()
	{
		intakeCalled = false;
		intakeOut = false;
	}
	public void intakeOut()
	{
		intakeCalled = true;
		intakeOut = true;
	}
}
