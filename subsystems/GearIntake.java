package org.usfirst.frc.team1984.subsystems;

import org.usfirst.frc.team1984.robot.Robot;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class GearIntake {
	private Victor gearIntake;
	private Timer time;
	private PowerDistributionPanel pdp;
	public GearIntake(int victor)
	{
		gearIntake = new Victor(victor);
		time = new Timer();
		pdp = new PowerDistributionPanel();
	}
	public void run() 
	{
		if(Robot.controller.toggleX() )//Toggled on with the xbox button
		{
			if(pdp.getCurrent(13) > 20 && time.get() == 0)//when the current gets high from holding the gear then start timer
			{
				time.start();
			}
			if(time.get() < 1)//higher speed when the time has recently started
			{
				gearIntake.set(-.8);///////////////////////////////////////////////<--Change it Here
				/*//Change for the working belt
				if (pdp.getCurrent(13)< //once it has the gear//)
					gear set(-.8);
				else
					gear.set(-1);
				 */
			}
			else
				gearIntake.set(SmartDashboard.getNumber("Gear intake stall speed",-.6));
		}
		else if(Robot.controller.getB())
		{
			gearIntake.set(.5);
			time.stop();
			time.reset();
			
		}
		else
		{
			time.stop();
			time.reset();
			gearIntake.set(0);
		}
	}
	public void intakeOn() {}
	public void intakeOff() {}
	public void intakeOut() {}
	
}
