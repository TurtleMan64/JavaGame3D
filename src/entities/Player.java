package entities;

import models.TexturedModel;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

public class Player extends Entity
{

	private float moveSpeedFactor = 0.1f;
	private float moveSpeedMax = 1f;
	private float moveSpeedCurrent;
	private float xVel;
	private float yVel;
	private float zVel;
	private float movementInputX;
	private float movementInputY;
	private float movementAngle; // in degrees
	private float friction = 0.05f;
	private boolean jumpInput;
	private Vector3f cameraPosition;
	
	Camera currentCamera;
	
	public Player(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale, Camera cameraToUse)
	{
		super(model, position, rotX, rotY, rotZ, scale);
		currentCamera = cameraToUse;
		xVel = 0.0f;
		yVel = 0.0f;
		zVel = 0.0f;
		movementInputX = 0;
		movementInputY = 0;
		movementAngle = 0;
		moveSpeedCurrent = 0;
		jumpInput = false;
		cameraPosition = new Vector3f(0,0,0);
	}
	
	public void step()
	{
		setMovementInputs();

		xVel += (float)(moveSpeedCurrent*Math.cos(Math.toRadians(currentCamera.getYaw()+movementAngle)));
		zVel += (float)(moveSpeedCurrent*Math.sin(Math.toRadians(currentCamera.getYaw()+movementAngle)));
		
		Vector3f newCameraPosition = new Vector3f(this.getPosition().x,this.getPosition().y,this.getPosition().z);
		float radius = 104;
		newCameraPosition.x += (float)(Math.cos(Math.toRadians(currentCamera.getYaw()+270+180))*(radius*(Math.cos(Math.toRadians(currentCamera.getPitch())))));
		newCameraPosition.z += (float)(Math.sin(Math.toRadians(currentCamera.getYaw()+270+180))*(radius*(Math.cos(Math.toRadians(currentCamera.getPitch())))));
		newCameraPosition.y -= (float)(Math.sin(Math.toRadians(currentCamera.getPitch()+180))*radius);
		
		cameraPosition = newCameraPosition;
		
		System.out.println(newCameraPosition);
		System.out.println(cameraPosition);
		
		
		currentCamera.setPosition(newCameraPosition);
		
		limitMovementSpeed();
		
		applyFriction();
		
		if(jumpInput)
		{
			jump();
		}
		yVel-=0.1;
		if(yVel < -5)
		{
			yVel = -5;
		}
		
		
		super.increasePosition(xVel, yVel, zVel);
	}
	
	private void setMovementInputs()
	{
		movementInputX = 0;
		movementInputY = 0;
		if(Keyboard.isKeyDown(Keyboard.KEY_W))
		{
			movementInputY = -1;
		}
		else
		{
			if(Keyboard.isKeyDown(Keyboard.KEY_S))
			{
				movementInputY = 1;
			}
			else
			{
				movementInputY = 0;
			}
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_A))
		{
			movementInputX = -1;
		}
		else
		{
			if(Keyboard.isKeyDown(Keyboard.KEY_D))
			{
				movementInputX = 1;
			}
			else
			{
				movementInputX = 0;
			}
		}
		
		if(Math.abs(movementInputX*movementInputY) == 1)
		{
			movementInputX = (float)(movementInputX*0.70710678118);
			movementInputY = (float)(movementInputY*0.70710678118);
		}
		
		moveSpeedCurrent = moveSpeedFactor*(float)Math.sqrt(movementInputX*movementInputX + movementInputY*movementInputY);
		movementAngle = (float)Math.toDegrees(Math.atan2(movementInputY, movementInputX));
	}
	
	private void jump()
	{
		yVel = 5;
	}
	
	private void applyFriction()
	{
		xVel = xVel*(1-friction);
		zVel = zVel*(1-friction);
	}
	
	
	private void limitMovementSpeed()
	{
		if(moveSpeedCurrent > moveSpeedMax)
		{
			float ratio;
			ratio = moveSpeedMax/moveSpeedCurrent;
			
			xVel = xVel*ratio;
			zVel = zVel*ratio;
		}
	}
	
	public Vector3f getViewPosition()
	{
		return cameraPosition;
	}
}
