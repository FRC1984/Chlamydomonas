/**
 * @author AJ Awesome
 */

package org.usfirst.frc.team1984.subsystems;

import org.usfirst.frc.team1984.robot.*;
import org.usfirst.frc.team1984.lib.*;

import com.ctre.CANTalon;
import com.ctre.CANTalon.*;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Shooter {
	private Victor		ramp;
	private CANTalon 	talon,rampCim;
	private GenericPID 	pid,feederPID;
	private Preferences prefs;
	private AnalogInput isShooty;
	private Timer 		delay;
	private double 		currentSpeed,
						targetSpeed,
						rampDelay;
	private boolean		atSpeed;

	public Shooter(int victor,int talon)
	{
		this.ramp = new Victor(victor);
		
		this.talon = new CANTalon(talon);
		this.talon.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
		this.talon.changeControlMode(TalonControlMode.PercentVbus);
		this.talon.reverseSensor(true);
		this.talon.setInverted(true);
		
		targetSpeed = 35000;
		
		pid = new GenericPID();
		pid.setMaxSpeed(1);
		pid.setMinSpeed(0);
		pid.setPID(.0001, 0.00001, 0.00001);

		this.rampCim = new CANTalon(2);
		this.rampCim.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
		this.rampCim.changeControlMode(TalonControlMode.PercentVbus);
		this.rampCim.reverseSensor(true);
		this.rampCim.setInverted(true);
		
		feederPID = new GenericPID();
		feederPID.setMaxSpeed(1);
		feederPID.setMinSpeed(0);
		feederPID.setPID(0.0001,0.000001,0.000001);
		
		isShooty = new AnalogInput(1);
		delay = new Timer();
		delay.start();
		rampDelay = 2;
		
		atSpeed = false;
		SmartDashboard.putNumber("Ball Feeder Velocity",12000);
	}
	

	
	public void run()
	{
		prefs = Preferences.getInstance();
		SmartDashboard.putNumber("Shooty Vel",-talon.getEncVelocity());
		SmartDashboard.putNumber("isShooty", isShooty.getValue());
		
		
		//Shoot
			if (Robot.controller.toggleRB())
			{
				Robot.ledLights.setShoot(true);
				shoot();
				atSpeed = true;
			}
		//Stop Shoot
			else
			{
				Robot.ledLights.setShoot(false);
				atSpeed = false;
				killShooter();
			}
		
		//Feed shooter
			if(Robot.controller.getLB() && atSpeed)
			{
				Robot.intake.intakeOff();
				if(isShooty.getValue() > 200 || delay.get() > rampDelay + .5)
				{
					delay.reset();
					Robot.intake.intakeOff();
					/*if(currentSpeed < prefs.getDouble("Goal Vel",33000) - prefs.getDouble("Needed Vel Dif",0))
					{
						rampOff();
					}
					else
					{
						rampUp();						
					}*/
					rampUp();
				}
				else if(delay.get() > rampDelay && delay.get() < rampDelay+.5)
				{
					rampDown();
					Robot.intake.intakeOn();
				}
				else
				{
					Robot.intake.intakeOff();
					rampUp();
				}
				
			}
			else if(Robot.controller.getLB() && !atSpeed)
			{
				Robot.intake.intakeOff();
				if(isShooty.getValue() < 200)
					rampUp();
				else
					rampOff();
			}
		//Reverse Feeder while B is pressed
			else if (Robot.controller.getA())//getB())
			{
				rampDown();
				Robot.intake.intakeOn();
			}
		//Stop feeder if nothing pressed
			else if (Robot.controller.getY())
			{
				rampDown();
				Robot.intake.intakeOut();
			}
			else
			{
				Robot.intake.intakeOff();
				rampOff();
			}
		
	}

	private void shoot()
	{
		double p,i,d,pidOutput;
		
		prefs = Preferences.getInstance();
		//SmartDashboard.putNumber("Velocity", talon.getEncVelocity());
		
		p = prefs.getDouble("P",0.0001);
		i = prefs.getDouble("I",0.00001);//.0001
		d = prefs.getDouble("D",0.00001);//.000001
		pid.setPID( p , i , d );
		
		targetSpeed = prefs.getDouble("Goal Vel",35000);
		currentSpeed = -talon.getEncVelocity();
		
		pidOutput = pid.setPIDVal(targetSpeed, currentSpeed);
		
		
//		talon.set(pidOutput);
		
//		//Add after PID works
		if(prefs.getBoolean("use pid",true))
		{
			talon.set(pidOutput);

		}
		else
		{
			pidOutput = prefs.getDouble("Shooty Speed",.75);
			talon.set(pidOutput);
		}
		SmartDashboard.putNumber("Talon Output", pidOutput);
		
	}
	public void setShooter(double value)
	{
		talon.set(value);
	}
	
	private void killShooter()
	{
		talon.set(0);
	}
	
	
	private void rampUp()
	{
		double blahy=0,blah2y=0;
		blah2y = rampCim.getEncVelocity();
		blahy = feederPID.setPIDVal(SmartDashboard.getNumber("Ball Feeder Velocity",12000),blah2y);//12000
		//ramp.set(prefs.getDouble("Feeder Speed",.5));
		SmartDashboard.putNumber("Feeder Velocity",blah2y);
		SmartDashboard.putNumber("Feeder Talon Val",blahy);
		rampCim.set(blahy);
		//rampCim.set(.75);
	}
	private void rampOff()
	{
		//ramp.set(0);
		rampCim.set(0);
	}
	private void rampDown()
	{
		ramp.set(-prefs.getDouble("Feeder Speed",.5));
		rampCim.set(-1);
	}
	
//Auto mode code////////////////////////////////////////////
	public void autoFeed()
	{
		prefs = Preferences.getInstance();
		ramp.set(prefs.getDouble("Feeder Speed",.5));
		rampCim.set(.6);
	}
	public void autoShoot()
	{
		shoot();
	}
	public void autoShooterOff()
	{
		talon.set(0);
		ramp.set(0);
	}
	
	
	public void autoShootWall()
	{
		double p,i,d,pidOutput;
		
		prefs = Preferences.getInstance();
		//SmartDashboard.putNumber("Velocity", talon.getEncVelocity());
		
		p = prefs.getDouble("P",0.0001);
		i = prefs.getDouble("I",0.00001);//.0001
		d = prefs.getDouble("D",0.00001);//.000001
		pid.setPID( p , i , d );
		
		targetSpeed = 34500;//prefs.getDouble("Goal Vel",34000);
		currentSpeed = -talon.getEncVelocity();
		
		pidOutput = pid.setPIDVal(targetSpeed, currentSpeed);
		
		
//		talon.set(pidOutput);
		
//		//Add after PID works
		if(prefs.getBoolean("use pid",true))
		{
			talon.set(pidOutput);

		}
		else
		{
			pidOutput = prefs.getDouble("Shooty Speed",.75);
			talon.set(pidOutput);
		}
		SmartDashboard.putNumber("Talon Output", pidOutput);
		
	}
	
	public void autoShootNotWall()
	{
		double p,i,d,pidOutput;
		
		prefs = Preferences.getInstance();
		//SmartDashboard.putNumber("Velocity", talon.getEncVelocity());
		
		p = prefs.getDouble("P",0.0001);
		i = prefs.getDouble("I",0.00001);//.0001
		d = prefs.getDouble("D",0.00001);//.000001
		pid.setPID( p , i , d );
		
		targetSpeed = 34500;//prefs.getDouble("Goal Vel",34000);
		currentSpeed = -talon.getEncVelocity();
		
		pidOutput = pid.setPIDVal(targetSpeed, currentSpeed);
		
		
//		talon.set(pidOutput);
		
//		//Add after PID works
		if(prefs.getBoolean("use pid",true))
		{
			talon.set(pidOutput);

		}
		else
		{
			pidOutput = prefs.getDouble("Shooty Speed",.75);
			talon.set(pidOutput);
		}
		SmartDashboard.putNumber("Talon Output", pidOutput);
		
	}
}
