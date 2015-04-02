package shaders;

import java.util.List;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import toolbox.Maths;
import collision.CollisionModel;
import entities.Camera;
import entities.Light;

public class StaticShader extends ShaderProgram
{
	private static final int MAX_LIGHTS = 4;
	private static final int TRI_NUMBER = 100;
	private static final String VERTEX_FILE = "src/shaders/vertexShader.txt";
	private static final String FRAGMENT_FILE = "src/shaders/fragmentShader.txt";

	private int location_transformationMatrix;
	private int location_projectionMatrix;
	private int location_viewMatrix;
	private int location_lightPosition[];
	private int location_lightColour[];
	private int location_attenuation[];
	private int location_shineDamper;
	private int location_shiny;
	private int location_reflectivity;
	private int location_useFakeLighting;
	private int location_skyColour;
	private int location_y1[];
	private int location_x1[];
	private int location_z1[];
	private int location_x2[];
	private int location_y2[];
	private int location_z2[];
	private int location_x3[];
	private int location_y3[];
	private int location_z3[];
	private int location_A[];
	private int location_B[];
	private int location_C[];
	private int location_D[];
	private int location_turtle[];
	
	public StaticShader() 
	{
		super(VERTEX_FILE, FRAGMENT_FILE);
	}
	
	@Override
	protected void bindAttributes()
	{
		super.bindAttribute(0,"position");
		super.bindAttribute(1,"textureCoords");
		super.bindAttribute(2,"normal");
	}

	@Override
	protected void getAllUniformLocation() 
	{
		location_transformationMatrix = super.getUniformLocation("transformationMatrix");
		location_projectionMatrix = super.getUniformLocation("projectionMatrix");
		location_viewMatrix = super.getUniformLocation("viewMatrix");
		location_shineDamper = super.getUniformLocation("shineDamper");
		location_shiny = super.getUniformLocation("shiny");
		location_reflectivity = super.getUniformLocation("reflectivity");
		location_useFakeLighting = super.getUniformLocation("useFakeLighting");
		location_skyColour = super.getUniformLocation("skyColour");
		
		location_lightPosition = new int[MAX_LIGHTS];
		location_lightColour = new int[MAX_LIGHTS];
		location_attenuation = new int[MAX_LIGHTS];
		
		location_y1 = new int[TRI_NUMBER];
		location_x1 = new int[TRI_NUMBER];
		location_z1 = new int[TRI_NUMBER];
		location_x2 = new int[TRI_NUMBER];
		location_y2 = new int[TRI_NUMBER];
		location_z2 = new int[TRI_NUMBER];
		location_x3 = new int[TRI_NUMBER];
		location_y3 = new int[TRI_NUMBER];
		location_z3 = new int[TRI_NUMBER];
		location_A = new int[TRI_NUMBER];
		location_B = new int[TRI_NUMBER];
		location_C = new int[TRI_NUMBER];
		location_D = new int[TRI_NUMBER];
		
		location_turtle = new int[3];
		location_turtle[0] = super.getUniformLocation("turtle[0]");
		location_turtle[1] = super.getUniformLocation("turtle[1]");
		location_turtle[2] = super.getUniformLocation("turtle[2]");
		
		
		for(int i = 0; i < MAX_LIGHTS; i++)
		{
			location_lightPosition[i] = super.getUniformLocation("lightPosition[" + i + "]");
			location_lightColour[i] = super.getUniformLocation("lightColour[" + i + "]");
			location_attenuation[i] = super.getUniformLocation("attenuation[" + i + "]");
		}
		
		for(int i = 0; i < TRI_NUMBER; i++)
		{
			location_y1[i] = super.getUniformLocation("y1[" + i + "]");
			location_x1[i] = super.getUniformLocation("x1[" + i + "]");
			location_z1[i] = super.getUniformLocation("z1[" + i + "]");
			location_x2[i] = super.getUniformLocation("x2[" + i + "]");
			location_y2[i] = super.getUniformLocation("y2[" + i + "]");
			location_z2[i] = super.getUniformLocation("z2[" + i + "]");
			location_x3[i] = super.getUniformLocation("x3[" + i + "]");
			location_y3[i] = super.getUniformLocation("y3[" + i + "]");
			location_z3[i] = super.getUniformLocation("z3[" + i + "]");
			location_A[i] = super.getUniformLocation("Aval[" + i + "]");
			location_B[i] = super.getUniformLocation("Bval[" + i + "]");
			location_C[i] = super.getUniformLocation("Cval[" + i + "]");
			location_D[i] = super.getUniformLocation("Dval[" + i + "]");
			

		}
	}
	
	public void loadSkyColour(float r, float g, float b)
	{
		super.loadVector(location_skyColour, new Vector3f(r,g,b));
	}
	
	public void loadFakeLightingVariable(boolean useFake)
	{
		super.loadBoolean(location_useFakeLighting, useFake);
	}
	
	public void loadShineVariables(float damper, float reflectivity)
	{
		super.loadFloat(location_shineDamper, damper);
		super.loadFloat(location_reflectivity, reflectivity);
	}
	
	public void loadTransformationMatrix(Matrix4f matrix)
	{
		super.loadMatrix(location_transformationMatrix, matrix);
	}
	
	public void loadLights(List<Light> lights)
	{
		for(int i = 0; i < MAX_LIGHTS; i++)
		{
			if(i < lights.size())
			{
				super.loadVector(location_lightPosition[i], lights.get(i).getPosition());
				super.loadVector(location_lightColour[i], lights.get(i).getColour());
				super.loadVector(location_attenuation[i], lights.get(i).getAttenuation());
			}
			else
			{
				super.loadVector(location_lightPosition[i], new Vector3f(0, 0, 0));
				super.loadVector(location_lightColour[i], new Vector3f(0, 0, 0));
				super.loadVector(location_attenuation[i], new Vector3f(1, 0, 0));
			}
		}
	}
	
	public void loadCollision(CollisionModel model)
	{
		super.loadFloat(location_shiny, 1.0f);
		super.loadVector(location_turtle[0], new Vector3f(0,0,0));
		super.loadVector(location_turtle[1], new Vector3f(1,1,1));
		super.loadVector(location_turtle[2], new Vector3f(2,2,2));
		for(int i = 0; i < TRI_NUMBER; i++)
		{
			/*
			super.loadFloat(location_x1[i], model.triangles.get(i).p1.x);
			super.loadFloat(location_y1[i], model.triangles.get(i).p1.y);
			super.loadFloat(location_z1[i], model.triangles.get(i).p1.z);
			super.loadFloat(location_x2[i], model.triangles.get(i).p2.x);
			super.loadFloat(location_y2[i], model.triangles.get(i).p2.y);
			super.loadFloat(location_z2[i], model.triangles.get(i).p2.z);
			super.loadFloat(location_x3[i], model.triangles.get(i).p3.x);
			super.loadFloat(location_y3[i], model.triangles.get(i).p3.y);
			super.loadFloat(location_z3[i], model.triangles.get(i).p3.z);
			super.loadFloat(location_A[i], model.triangles.get(i).A);
			super.loadFloat(location_B[i], model.triangles.get(i).B);
			super.loadFloat(location_C[i], model.triangles.get(i).C);
			super.loadFloat(location_D[i], model.triangles.get(i).D);
			*/
			
			
			//super.loadFloat(location_x1[i], 0f);
			super.loadFloat(location_y1[i], 0.5f);
			super.loadFloat(location_x1[i], 1.0f);
			super.loadFloat(location_z1[i], 1.0f);
			super.loadFloat(location_x2[i], 1.0f);
			super.loadFloat(location_y2[i], 1.0f);
			super.loadFloat(location_z2[i], 1.0f);
			super.loadFloat(location_x3[i], 1.0f);
			super.loadFloat(location_y3[i], 1.0f);
			super.loadFloat(location_z3[i], 1.0f);
			super.loadFloat(location_A[i], 1.0f);
			super.loadFloat(location_B[i], 1.0f);
			super.loadFloat(location_C[i], 1.0f);
			super.loadFloat(location_D[i], 1.0f);
			
			
		}
	}
	
	public void loadViewMatrix(Camera camera)
	{
		Matrix4f viewMatrix = Maths.createViewMatrix(camera);
		super.loadMatrix(location_viewMatrix, viewMatrix);
	}
	
	public void loadProjectionMatrix(Matrix4f projection)
	{
		super.loadMatrix(location_projectionMatrix, projection);
	}
}
