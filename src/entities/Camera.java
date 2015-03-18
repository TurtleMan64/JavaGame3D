package entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

public class Camera 
{
	private Vector3f position = new Vector3f(0,20,0);
	private float pitch;
	private float yaw;
	private float roll;
	private int mousePreviousX;
	private int mousePreviousY;
	
	public Camera()
	{
		mousePreviousX = Mouse.getX();
		mousePreviousY = Mouse.getY();
	}
	
	
	public void move()
	{
		
		this.pitch+=-0.5*(Mouse.getY()-mousePreviousY);
		this.yaw+=0.5*(Mouse.getX()-mousePreviousX);
		
		Mouse.setCursorPosition(1280/2, 720/2);
		mousePreviousX = Mouse.getX();
		mousePreviousY = Mouse.getY();
		//Mouse.setCursorPosition(1280/2, 720/2);
		
	}

	public Vector3f getPosition() 
	{
		return position;
	}
	
	public void setPosition(Vector3f position)
	{
		this.position = position;
	}

	public float getPitch() 
	{
		return pitch;
	}

	public float getYaw() 
	{
		return yaw;
	}

	public float getRoll() 
	{
		return roll;
	}
}
