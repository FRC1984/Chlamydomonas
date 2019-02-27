package org.usfirst.frc.team1984.subsystems;

import org.usfirst.frc.team1984.lib.*;

import com.ctre.CANTalon;

public class Feeder {
	private CANTalon rampFeeder;
	private GenericPID pid;
	public Feeder(int talonSRX)
	{
		rampFeeder = new CANTalon(talonSRX);
		
	}
	public void feederPIDOn(double targetSpeed)
	{
		
	}
	public void feederVoltOn(double voltage)
	{
		
	}
	public void feederOff()
	{
		
	}
}
