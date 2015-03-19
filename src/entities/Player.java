package entities;

import models.TexturedModel;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

import terrains.Terrain;

public class Player extends Entity
{

	private float moveSpeedFactor = 0.075f;
	private float moveSpeedMax = 0.7f;
	private float moveSpeedCurrent;
	private float xVel;
	private float yVel;
	private float zVel;
	private float movementInputX;
	private float movementInputY;
	private float movementAngle; // in degrees
	private float friction = 0.05f;
	private boolean jumpInput;
	private boolean previousJumpInput;
	private Vector3f cameraPosition;
	private float TERRAIN_HEIGHT;
	
	Camera currentCamera;
	Terrain collisionTerrain;
	
	public Player(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale, Camera cameraToUse, Terrain terrainToCollide)
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
		previousJumpInput = false;
		cameraPosition = new Vector3f(0,0,0);
		TERRAIN_HEIGHT = 0;
		collisionTerrain = terrainToCollide;
	}
	
	public void step()
	{
		super.increasePosition(xVel, yVel, zVel);
		setMovementInputs();

		xVel += (float)(moveSpeedCurrent*Math.cos(Math.toRadians(currentCamera.getYaw()+movementAngle)));
		zVel += (float)(moveSpeedCurrent*Math.sin(Math.toRadians(currentCamera.getYaw()+movementAngle)));
		
		Vector3f newCameraPosition = new Vector3f(super.getPosition().x,super.getPosition().y,super.getPosition().z);
		float radius = 104;
		newCameraPosition.x += (float)(Math.cos(Math.toRadians(currentCamera.getYaw()+270+180))*(radius*(Math.cos(Math.toRadians(currentCamera.getPitch())))));
		newCameraPosition.z += (float)(Math.sin(Math.toRadians(currentCamera.getYaw()+270+180))*(radius*(Math.cos(Math.toRadians(currentCamera.getPitch())))));
		newCameraPosition.y -= (float)(Math.sin(Math.toRadians(currentCamera.getPitch()+180))*radius);
		
		cameraPosition = newCameraPosition;
		
		//System.out.println(newCameraPosition);
		//System.out.println(cameraPosition);
		
		
		currentCamera.setPosition(newCameraPosition);
		
		limitMovementSpeed();
		
		applyFriction();
		
		TERRAIN_HEIGHT = collisionTerrain.getHeightOfTerrain(getX(), getZ());
		
		if(super.getY() <= TERRAIN_HEIGHT)
		{
			yVel = 0;
			super.setY(TERRAIN_HEIGHT);
			if(jumpInput)
			{
				jump();
			}
		}
		else
		{
			yVel-=0.1;
			if(yVel < -5)
			{
				yVel = -5;
			}
		}
		
		
		//super.increasePosition(xVel, yVel, zVel);
	}
	
	private void setMovementInputs()
	{
		previousJumpInput = jumpInput;
		jumpInput = false;
		if(Keyboard.isKeyDown(Keyboard.KEY_SPACE) && previousJumpInput == false)
		{
			jumpInput = true;
		}
		
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
