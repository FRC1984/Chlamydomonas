/**
 * @author Aidan Smith
 */
package org.usfirst.frc.team1984.robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.hal.HAL;
import org.usfirst.frc.team1984.lib.*;
import org.usfirst.frc.team1984.lib.GenericPID;

public class AutoMode {
	private String station;
	private GenericPID pidTurn;
	public AutoMode()
	{
		pidTurn = new GenericPID();
		pidTurn.setMaxSpeed(.65);
		pidTurn.setMinSpeed(-.65);
		pidTurn.setPID(.06, 0.04, 0.005);
		pidTurn.setDeadZone(.3);
	}
	public void run()
	{
		station = (HAL.getAllianceStation().toString()).substring(0, 1);
		switch(Dashboard.autoGetSelected())
		{
			case 0://Don't Move
				Robot.driveTrain.stay();
				break;
			case 1://Shoot
				if(station.equalsIgnoreCase("R")) //Red
				{
					shooterOn(.666);
					forward(.6,-.6);
					delay(1.1);
					
					shooterFeederOnWall(7.9);
					Robot.shooter.autoShooterOff();
					pidTurn(4,80);//turnRight(4,.65,80);
					forward(1.5,.65);
				}
				else //Blue
				{
					shooterOn(.666);
					forward(.6,.5);
					delay(1.1);
					
					shooterFeederOnWall(7.9);
					Robot.shooter.autoShooterOff();
					pidTurn(4,30);//turnRight(4,.65,30);
					forward(1.5,.65);
				}
				break;
			case 2://Forward
				forward(2,.7);
				break;
			case 3://Test
				Robot.driveTrain.stay();
				break;
			default:
				Robot.driveTrain.stay();
				break;
		}
	}
	
	public void confirmAuto()
	{
		station = (HAL.getAllianceStation().toString()).substring(0, 1);
		switch(Dashboard.autoGetSelected())
		{	
			case 0://Don't Move
				Dashboard.putString("Auto Confirm", "Don't Move");
				break;
				
			case 1://Shoot
				if(station.equalsIgnoreCase("R"))
					Dashboard.putString("Auto Confirm", "Shoot Red");
				else if(station.equalsIgnoreCase("B"))
					Dashboard.putString("Auto Confirm", "Shoot Blue");
				else
					Dashboard.putString("Auto Confirm", "ERROR");
				break;
			
			case 2://Forward
				if(station.equalsIgnoreCase("R"))
					Dashboard.putString("Auto Confirm", "Forward Red");
				else if(station.equalsIgnoreCase("B"))
					Dashboard.putString("Auto Confirm", "Forward Blue");
				else
					Dashboard.putString("Auto Confirm", "ERROR");
				break;
				
			case 3://Test
				if(station.equalsIgnoreCase("R"))
					Dashboard.putString("Auto Confirm", "Test Red");
				else if(station.equalsIgnoreCase("B"))
					Dashboard.putString("Auto Confirm", "Test Blue");
				else
					Dashboard.putString("Auto Confirm", "ERROR");
				break;
			default://Default for not moving
				Dashboard.putString("Auto Confirm","Wrong Selection");
				break;
		}
	}

	
//Methods for driving//////////////////////////////////////////////////
	public void forward(double time, double speed)
	{
		double begin = Timer.getFPGATimestamp();
		while (Timer.getFPGATimestamp()-begin < time){
			Robot.driveTrain.forward(speed);
		}
		Robot.driveTrain.stay();
	}
	public void delay(double time)
	{
		double begin = Timer.getFPGATimestamp();
		while (Timer.getFPGATimestamp()-begin < time){}
	}

	
//Methods for driving with PID//////////////////////////////////////////////////
	public void pidTurn(double time, double angle)
	{
		double begin = Timer.getFPGATimestamp();
		Robot.gyro.reset();
		while (Timer.getFPGATimestamp()-begin < time){
			Robot.driveTrain.turn(pidTurn.setPIDVal(angle, Robot.gyro.getRaw()));
		}
		Robot.driveTrain.stay();
	}	
//Auto shooting code////////////////////////////////////////////////////////////
	public void shooterFeederOn(double time)
	{
		double begin = Timer.getFPGATimestamp();
		while (Timer.getFPGATimestamp()-begin < time){
			Robot.shooter.autoFeed();
			Robot.shooter.autoShoot();
		}
	}
	public void shooterFeederOnWall(double time)
	{
		double begin = Timer.getFPGATimestamp();
		while (Timer.getFPGATimestamp()-begin < time){
			Robot.shooter.autoFeed();
			Robot.shooter.autoShootWall();
		}
	}
	public void shooterOn(double value)
	{
		Robot.shooter.setShooter(value);
	}
}
