package entities;

import models.TexturedModel;

import org.lwjgl.util.vector.Vector3f;

import collision.CollisionChecker;

public class Ball extends Entity
{
	private float xVel;
	private float yVel;
	private float zVel;

	CollisionChecker collisionChecker;
	
	public Ball(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale, CollisionChecker collideCheck)
	{
		super(model, position, rotX, rotY, rotZ, scale);
		xVel = 0.0f;
		yVel = -0.1f;
		zVel = 0.0f;
		collisionChecker = collideCheck;
	}
	
	@Override
	public void step()
	{
		increasePosition(xVel, yVel, zVel);
		
		if(collisionChecker.checkCollision(super.getX(), super.getY()+5, super.getZ(), super.getX(), super.getY()-5, super.getZ()))
		{
			setPosition(collisionChecker.getCollidePosition());
			setyVel(0);
		}
		else
		{
			//yVel-=0.03;
		}
	}

	public float getxVel() 
	{
		return xVel;
	}

	public void setxVel(float xVel) 
	{
		this.xVel = xVel;
	}

	public float getyVel() 
	{
		return yVel;
	}

	public void setyVel(float yVel) 
	{
		this.yVel = yVel;
	}

	public float getzVel() 
	{
		return zVel;
	}

	public void setzVel(float zVel) 
	{
		this.zVel = zVel;
	}
}
