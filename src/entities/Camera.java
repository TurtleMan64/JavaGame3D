package entities;

import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

import renderEngine.DisplayManager;

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
	
	public void step()
	{
		this.pitch+=-0.5*(Mouse.getY()-mousePreviousY);
		this.yaw+=0.5*(Mouse.getX()-mousePreviousX);
		
		Mouse.setCursorPosition(DisplayManager.getWidth()/2, DisplayManager.getHeight()/2);
		mousePreviousX = Mouse.getX();
		mousePreviousY = Mouse.getY();
	}
	
	public Vector3f getPosition() 
	{
		return position;
	}
	
	public void setPosition(Vector3f position)
	{
		this.position.set(position);
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
