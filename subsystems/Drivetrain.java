/**
 * @author Aidan Smith
 */
package org.usfirst.frc.team1984.subsystems;

import org.usfirst.frc.team1984.robot.Robot;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.RobotDrive.MotorType;

public class Drivetrain
{
	public RobotDrive base;
	
	public Drivetrain(int frontLeft, int rearLeft, int frontRight,int rearRight)
	{
		base = new RobotDrive(frontLeft,rearLeft,frontRight,rearRight);
	}
	public void setInvertedMotor(MotorType motor,boolean inverted)
	{
		base.setInvertedMotor(motor, inverted);		
	}
	public void mecanumDrive()
	{
		base.mecanumDrive_Cartesian(-Robot.controller.getLSX(), -Robot.controller.getLSY(), -Robot.controller.getRSX(), 0);
	}
	
	public void arcade()
	{
		base.arcadeDrive(Robot.controller.getLSX(), Robot.controller.getLSY());
	}
	public void splitArcade()
	{
		base.arcadeDrive(Robot.controller.getRSX(), Robot.controller.getLSY());
	}
	
//Autonomous Driving code
	public void stay()
	{
		base.arcadeDrive(0, 0);
	}
	public void drive(double moveVal, double rotateVal)
	{
		base.arcadeDrive(moveVal, rotateVal);
	}
	public void forward(double speed)
	{
		base.arcadeDrive(0,-speed);
	}
	public void turn(double speed)
	{
		base.arcadeDrive(speed, 0);
	}
	
}
