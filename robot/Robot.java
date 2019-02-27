/** @author Aidan Smith */
/* @controllers
 * 
 * @dashboard
 * 
 * @ports
 */
package org.usfirst.frc.team1984.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.RobotDrive.MotorType;
import org.usfirst.frc.team1984.lib.*;
import org.usfirst.frc.team1984.subsystems.*;

public class Robot extends IterativeRobot {
	
	public static Drivetrain driveTrain;
	public static Xbox controller;
	public static Gyro gyro;
	public static Arduino ledLights;
	public static Shooter shooter;
	public static Intake intake;
	public static GearIntake gearIntake;
	public static AutoMode autonomous;
	
	public void robotInit() 
	{
		driveTrain = new Drivetrain(0,1,2,3);
		driveTrain.setInvertedMotor(MotorType.kFrontLeft, true);
		driveTrain.setInvertedMotor(MotorType.kRearLeft, true);
		
		controller = new Xbox(0);
		
		gyro = new Gyro();
		
		ledLights = new Arduino(0);
		ledLights.start();
		
		shooter = new Shooter(5,1);
		intake = new Intake(4,0);
		gearIntake = new GearIntake(9);
		autonomous = new AutoMode();
		//Auto
		Dashboard.autoAddDefault("Don't Move", 0);
		Dashboard.autoAddObject("Shoot", 1);
		Dashboard.autoAddObject("Forward", 2);
		Dashboard.autoAddObject("Test", 3);
		Dashboard.displayAutoSelector();
		//Shooter
		//Dashboard.putNumber("Feeder Velocity", 12000);//declare
		//Dashboard.putNumber("Shooter Velocity",);//show
		//putNumber("Shooter Output Velocity",);//show
		//Feeder Velocity or Feeder Voltage
		//Feeder Speed = .5
		
		//Prefs:
			//Shooter P = .0001
			//Shooter I = .00001
			//Shooter D = .00001
			//Shooter Target Velocity = 35000
			//Shooter Use PID = true
			//Shooter Backup Voltage = .75
		
		//In-take
		//Dashboard.putNumber(key, value);
		
	}
	public void teleopInit() {}
	public void teleopPeriodic() 
	{
		Dashboard.putNumber("Gyro",gyro.get());
		
		driveTrain.arcade();
		shooter.run();
		intake.run();
		gearIntake.run();
		
	}
	
	public void autonomousInit() {}
	public void autonomousPeriodic() {}
	
	public void disabledInit() {}
	public void disabledPeriodic() 
		{autonomous.confirmAuto();}
	
	public void testPeriodic() {}
}
