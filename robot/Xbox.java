/**
 * @author Aidan Smith
 */
package org.usfirst.frc.team1984.robot;

import edu.wpi.first.wpilibj.*;

public class Xbox{
	private XboxController xbox;
	private boolean APress,BPress,XPress,YPress,LBPress,RBPress,BackPress,StartPress;
	
	private boolean ALast,BLast,XLast,YLast,LBLast,RBLast,BackLast,StartLast;
	public Xbox(int location)
	{
		xbox = new XboxController(location);
		APress = false;
		BPress = false;
		XPress = false;
		YPress = false;
		LBPress = false;
		RBPress = false;
		BackPress = false;
		StartPress = false;
		
		ALast = false;
		BLast = false;
		XLast = false;
		YLast = false;
		LBLast = false;
		RBLast = false;
		BackLast = false;
		StartLast = false;
		
	}
	
	/**
	 * @Buttons: (1-A)(2-B)(3-X)(4-Y)(5-LB)(6-RB)(7-Back)(8-Start)(9-LS)(10-RS)
	 * 
	 * @Sticks: LS:(0-X)(1-Y) RS:(4-X)(5-Y) Triggers:(2-LT)(3-RT)
	 */
	public static void getControllerInfo(){}
	//Buttons
	public boolean getA(){return xbox.getRawButton(1);}
	public boolean getB(){return xbox.getRawButton(2);}
	public boolean getX(){return xbox.getRawButton(3);}
	public boolean getY(){return xbox.getRawButton(4);}
	public boolean getLB(){return xbox.getRawButton(5);}
	public boolean getRB(){return xbox.getRawButton(6);}
	public boolean getBack(){return xbox.getRawButton(7);}
	public boolean getStart(){return xbox.getRawButton(8);}
	public boolean getLSBtn(){return xbox.getRawButton(9);}
	public boolean getRSBtn(){return xbox.getRawButton(10);}
	//Sticks
	public double getLSX(){return xbox.getRawAxis(0);}
	public double getLSY(){return xbox.getRawAxis(1);}
	public double getRSX(){return xbox.getRawAxis(4);}
	public double getRSY(){return xbox.getRawAxis(5);}
	//Triggers
	public double getLT(){return xbox.getRawAxis(2);}
	public double getRT(){return xbox.getRawAxis(3);}
	
	public double getDPad(){return xbox.getPOV();}
	
	//Rumblers in the joystick
	public void setRumble(double value)
	{
		xbox.setRumble(GenericHID.RumbleType.kLeftRumble, value);
		xbox.setRumble(GenericHID.RumbleType.kRightRumble, value);
	}
	
	public boolean pressA()
	{
		if (getA() && APress == false)
		{
			APress = true;
			return APress;
		}
		else if (getA() && APress)
		{
			return false;
		}
		else
		{
			APress = false;
		}
		
		return APress;
	}
	public boolean toggleA()
	{
		if(pressA())
		{
			if(ALast)
				ALast = false;
			else
				ALast = true;
		}
		return ALast;	
	}
	
	public boolean pressB()
	{
		if (getB() && BPress == false)
		{
			BPress = true;
			return BPress;
		}
		else if (getB() && BPress)
		{
			return false;
		}
		else
		{
			BPress = false;
		}
		
		return BPress;
	}
	public boolean toggleB()
	{
		if(pressB())
		{
			if(BLast)
				BLast = false;
			else
				BLast = true;
		}
		return BLast;	
	}
	
	public boolean pressX()
	{
		if (getX() && XPress == false)
		{
			XPress = true;
			return XPress;
		}
		else if (getX() && XPress)
		{
			return false;
		}
		else
		{
			XPress = false;
		}
		
		return XPress;
	}
	public boolean toggleX()
	{
		if(pressX())
		{
			if(XLast)
				XLast = false;
			else
				XLast = true;
		}
		return XLast;	
	}
	
	public boolean pressY()
	{
		if (getY() && YPress == false)
		{
			YPress = true;
			return YPress;
		}
		else if (getY() && YPress)
		{
			return false;
		}
		else
		{
			YPress = false;
		}
		
		return YPress;
	}
	public boolean toggleY()
	{
		if(pressY())
		{
			if(YLast)
				YLast = false;
			else
				YLast = true;
		}
		return YLast;	
	}
	
	public boolean pressLB()
	{
		if (getLB() && LBPress == false)
		{
			LBPress = true;
			return LBPress;
		}
		else if (getLB() && LBPress)
		{
			return false;
		}
		else
		{
			LBPress = false;
		}
		
		return LBPress;
	}
	public boolean toggleLB()
	{
		if(pressLB())
		{
			if(LBLast)
				LBLast = false;
			else
				LBLast = true;
		}
		return LBLast;	
	}
	
	public boolean pressRB()
	{
		if (getRB() && RBPress == false)
		{
			RBPress = true;
			return RBPress;
		}
		else if (getRB() && RBPress)
		{
			return false;
		}
		else
		{
			RBPress = false;
		}
		
		return RBPress;
	}
	public boolean toggleRB()
	{
		if(pressRB())
		{
			if(RBLast)
				RBLast = false;
			else
				RBLast = true;
		}
		return RBLast;	
	}
	
	public boolean pressBack()
	{
		if (getBack() && BackPress == false)
		{
			BackPress = true;
			return BackPress;
		}
		else if (getBack() && BackPress)
		{
			return false;
		}
		else
		{
			BackPress = false;
		}
		
		return BackPress;
	}
	public boolean toggleBack()
	{
		if(pressBack())
		{
			if(BackLast)
				BackLast = false;
			else
				BackLast = true;
		}
		return BackLast;	
	}
	
	public boolean pressStart()
	{
		if (getStart() && StartPress == false)
		{
			StartPress = true;
			return StartPress;
		}
		else if (getStart() && StartPress)
		{
			return false;
		}
		else
		{
			StartPress = false;
		}
		
		return StartPress;
	}
	public boolean toggleStart()
	{
		if(pressStart())
		{
			if(StartLast)
				StartLast = false;
			else
				StartLast = true;
		}
		return StartLast;	
	}
	
}
