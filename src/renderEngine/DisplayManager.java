package renderEngine;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.PixelFormat;
import org.lwjgl.opengl.ContextAttribs;

public class DisplayManager 
{
	private static final int WIDTH = 160*8;
	private static final int HEIGHT = 90*8;
	private static final int FPS_CAP = 60;
	
	
	public static void createDisplay()
	{
		ContextAttribs attribs = 
				new ContextAttribs(3,2)
				.withForwardCompatible(true)
				.withProfileCore(true);
		
		try 
		{
			Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
			//PixelFormat(Alpha Bits, Depth Bits, Stencil Bits, Samples)
			Display.create(new PixelFormat(8, 8, 0, 8), attribs);
			Display.setTitle("My First Display!");
		} 
		catch (LWJGLException e) 
		{
			e.printStackTrace();
		}
		
		GL11.glViewport(0, 0, WIDTH, HEIGHT);
		
	}
	
	public static void updateDisplay()
	{
		Display.sync(FPS_CAP);
		Display.update();
	}
	
	public static void closeDisplay()
	{
		Display.destroy();
	}
	
	public static int getWidth()
	{
		return WIDTH;
	}
	
	public static int getHeight()
	{
		return HEIGHT;
	}
}
