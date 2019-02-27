/* Original Arduino Values
 * 151 - rainbow
 * 152 - flash green sides
 * 153 - shooting animation
 * 154 - clear shooter white
 * 160 - clear/turn off all
 * 170+ - for timer
 * 
 */

package org.usfirst.frc.team1984.robot;

import java.nio.ByteBuffer;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

@SuppressWarnings("unused")
public class Arduino extends Thread{
	private I2C i2c_arduino;
	private PacketRaw pkt_tx = new PacketRaw();
	private PacketRaw pkt_rx = new PacketRaw();
	private int[] ultrasonic;
	private int[] ultronHold;
	private int num_of_ultrasonic;
	private int time,lastTime;
	private boolean startTimer,disabled,shoot;
	private Timer timer;
	private boolean isShootyClear;
	private boolean startedShooting;
	private boolean writtenTime;
	private boolean lights;
	private int 	timeHolder;
	

	/**
	 * make sure to start the thread
	 * Adds new sensors starting at 1
	 * @param num_of_ultrasonic the total number of sensors
	 */
	public Arduino(int num_of_ultrasonic)
	{
		i2c_arduino = new I2C(I2C.Port.kOnboard,8);
		
		this.num_of_ultrasonic = num_of_ultrasonic;
		
		ultrasonic = new int[num_of_ultrasonic+1];
		ultronHold = new int[2];
		
		startTimer = false;
		disabled = true;
		shoot = false;
		time = -1;
		lastTime = 0;
		timer = new Timer();
		isShootyClear = true;
		startedShooting = false;
		writtenTime = false;
		timeHolder = 0;
		lights = true;

	}
	public void run()
	{
		while (true)
		{
	    		if (lights)
	    		{
	    			if(shoot)
	    				write(153,0);
	    			else
	    				write(151,0);
	    		}
	    		else
	    		{
	    			write(160,0);
	    		}
			Timer.delay(.05);
		}
	}
	public void lightsOn()
	{
		lights = true;
	}
	public void lightsOff()
	{
		lights = false;
	}
	public void setShoot(boolean x)
	{
		shoot = x;
	}
	
	public void setStartTimer(boolean x)
	{
		startTimer = x;
		timer.start();
	}
	public void setDisabled(boolean x)
	{
		disabled = x;
	}

	public void start()
	{
		Thread x = new Thread(this,"Arduino Thread");
		x.start();
	}
	
	/**
	 * 
	 * @param ID the id of the sensor [1,number of sensors] 
	 * @return distance in inches of the  sensor
	 */
	public int getUltrasonic(int ID)
	{
		return ultrasonic[ID];
	}
	
	private int[] read()
	{
		i2c_arduino.readOnly(pkt_rx.raw, 8);
		int[] retVal = new int[2];
		
		/*if( pkt_rx.GetID() == ID )
    	{
    		return (int) pkt_rx.GetData();
    	}*/
		if( pkt_rx.GetID() < 255)
		{
			retVal[0] = pkt_rx.GetID();
			retVal[1] = pkt_rx.GetData();
		}
		else
		{
			retVal[0] = -1;
			retVal[1] = -1;
		}
		return retVal;
	}
	/**
	 * 
	 * @param ID [151,254]
	 * @param data 
	 */
	public void write(int ID, int data)
	{
    	pkt_tx.SetIDData( (byte)ID, data);
    	i2c_arduino.writeBulk( pkt_tx.raw, 8 );
	}
	/**
	 * 
	 * @param ledId [1,150]
	 * @param r [0,255]
	 * @param g [0,255]
	 * @param b [0,255]
	 */
	public void writeLight(int ledId, int r,int g, int b)
	{
    	pkt_tx.SetLightIDData( (byte) ledId,(byte)r,(byte)g,(byte)b);
    	i2c_arduino.writeBulk( pkt_tx.raw, 8 );
    	Timer.delay(.25);
	}
}



class PacketRaw
{
	public ByteBuffer raw = ByteBuffer.allocateDirect( 8 );
	public void SetIDData( byte id, int data )
	{
		ByteBuffer buf = ByteBuffer.allocateDirect( 4 );
		buf.putInt(data);
		raw.position( 0 );
		raw.put(id);
		raw.put( buf.get(3));
		raw.put( buf.get(2));
		raw.put( buf.get(1));
		raw.put( buf.get(0));
		raw.put( (byte) 0x41 );
		raw.put( (byte) 0x41 );
		raw.put( (byte) 0x41 );
		
	}
	public void SetLightIDData( byte id, byte r,byte g, byte b )
	{	
		raw.position( 0 );
		raw.put(id);
		raw.put( r);
		raw.put( g);
		raw.put( b);
		raw.put( (byte) 0x41 );
		raw.put( (byte) 0x41 );
		raw.put( (byte) 0x41 );
		raw.put( (byte) 0x41 );
		
	}
	public int GetID( )
	{
		return( (int) raw.get(0) );
	}
	public int GetData()
	{
		ByteBuffer buf = ByteBuffer.allocateDirect( 4 );
		buf.put(raw.get(4) );
		buf.put(raw.get(3) );
		buf.put(raw.get(2) );
		buf.put(raw.get(1) );
		return( buf.getInt(0) );
	}
	
}