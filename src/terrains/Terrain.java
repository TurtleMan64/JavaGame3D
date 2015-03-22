package terrains;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import models.RawModel;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import renderEngine.Loader;
import textures.TerrainTexture;
import textures.TerrainTexturePack;
import toolbox.Maths;
import collision.CollisionModel;
import collision.Point3D;
import collision.Triangle3D;

public class Terrain 
{
	private static final float SIZE = 800;
	private static final float MAX_HEIGHT = 40;
	private static final float MAX_PIXEL_COLOUR = 256*256*256;
	private static final float MIN_HEIGHT = -40;
	
	private float x;
	private float z;
	private RawModel model;
	private TerrainTexturePack texturePack;
	private TerrainTexture blendMap;
	private CollisionModel collisionModel;
	private float damper;
	private float reflectivity;
	
	private float[][] heights;
	
	public Terrain(int gridX, int gridZ, Loader loader, TerrainTexturePack texturePack, TerrainTexture blendMap, String heightMap, float damper, float reflectivity)
	{
		this.texturePack = texturePack;
		this.blendMap = blendMap;
		this.x = gridX * SIZE;
		this.z = gridZ * SIZE;
		this.model = generateTerrain(loader, heightMap);
		this.damper = damper;
		this.reflectivity = reflectivity;
	}
	
	public float getX() 
	{
		return x;
	}

	public float getZ() 
	{
		return z;
	}

	public RawModel getModel() 
	{
		return model;
	}

	public TerrainTexturePack getTexturePack() 
	{
		return texturePack;
	}

	public TerrainTexture getBlendMap() 
	{
		return blendMap;
	}
	
	public float getDamper() 
	{
		return damper;
	}

	public float getReflectivity() 
	{
		return reflectivity;
	}

	public float getHeightOfTerrain(float worldX, float worldZ)
	{
		float terrainX = worldX - this.x;
		float terrainZ = worldZ - this.z;
		float gridSquareSize = SIZE / (float)(heights.length - 1);
		int gridX = (int) Math.floor(terrainX / gridSquareSize);
		int gridZ = (int) Math.floor(terrainZ / gridSquareSize);
		if(gridX >= heights.length - 1 || gridZ >= heights.length -1 || gridX < 0 || gridZ < 0)
		{
			return 0;
		}
		else
		{
			float xCoord = (terrainX % gridSquareSize)/gridSquareSize;
			float zCoord = (terrainZ % gridSquareSize)/gridSquareSize;
			float answer;
			if (xCoord <= (1-zCoord)) 
			{
				answer = Maths.barryCentric(new Vector3f(0, heights[gridX][gridZ], 0), new Vector3f(1,
								heights[gridX + 1][gridZ], 0), new Vector3f(0,
								heights[gridX][gridZ + 1], 1), new Vector2f(xCoord, zCoord));
			} 
			else 
			{
				answer = Maths.barryCentric(new Vector3f(1, heights[gridX + 1][gridZ], 0), new Vector3f(1,
								heights[gridX + 1][gridZ + 1], 1), new Vector3f(0,
								heights[gridX][gridZ + 1], 1), new Vector2f(xCoord, zCoord));
			}
			return answer;
		}
	}

	private RawModel generateTerrain(Loader loader, String heightMap)
	{
		this.collisionModel = new CollisionModel();
		BufferedImage image = null;
		try 
		{
			image = ImageIO.read(new File("res/" + heightMap + ".png"));
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		int VERTEX_COUNT = image.getHeight();
		heights = new float [VERTEX_COUNT][VERTEX_COUNT];
		
		int count = VERTEX_COUNT * VERTEX_COUNT;
		float[] vertices = new float[count * 3];
		float[] normals = new float[count * 3];
		float[] textureCoords = new float[count*2];
		int[] indices = new int[6*(VERTEX_COUNT-1)*(VERTEX_COUNT*1)];
		int vertexPointer = 0;
		for(int i=0;i<VERTEX_COUNT;i++)
		{
			for(int j=0;j<VERTEX_COUNT;j++)
			{
				vertices[vertexPointer*3] = (float)j/((float)VERTEX_COUNT - 1) * SIZE;
				float height = getHeight(j, i, image);
				heights[j][i] = height;
				vertices[vertexPointer*3+1] = height;
				vertices[vertexPointer*3+2] = (float)i/((float)VERTEX_COUNT - 1) * SIZE;
				Vector3f normal = calculateNormal(j, i, image);
				normals[vertexPointer*3] = normal.x;
				normals[vertexPointer*3+1] = normal.y;
				normals[vertexPointer*3+2] = normal.z;
				textureCoords[vertexPointer*2] = (float)j/((float)VERTEX_COUNT - 1);
				textureCoords[vertexPointer*2+1] = (float)i/((float)VERTEX_COUNT - 1);
				
				vertexPointer++;
			}
		}
		int pointer = 0;
		for(int gz=0;gz<VERTEX_COUNT-1;gz++)
		{
			for(int gx=0;gx<VERTEX_COUNT-1;gx++)
			{
				int topLeft = (gz*VERTEX_COUNT)+gx;
				int topRight = topLeft + 1;
				int bottomLeft = ((gz+1)*VERTEX_COUNT)+gx;
				int bottomRight = bottomLeft + 1;
				indices[pointer++] = topLeft;
				indices[pointer++] = bottomLeft;
				indices[pointer++] = topRight;
				indices[pointer++] = topRight;
				indices[pointer++] = bottomLeft;
				indices[pointer++] = bottomRight;
			}
		}
		
		/*
		System.out.println(indices.length);
		System.out.println(indices[0]);
		System.out.println(indices[1]);
		System.out.println(indices[2]);
		System.out.println(indices[3]);
		System.out.println(indices[4]);
		System.out.println(indices[5]);
		System.out.println(indices[6]);
		System.out.println(vertices.length);
		System.out.println(vertices[0]);
		System.out.println(vertices[1]);
		System.out.println(vertices[2]);
		System.out.println(vertices[3]);
		System.out.println(vertices[4]);
		System.out.println(vertices[5]);
		System.out.println(vertices[6]);
		*/
		for(int number=0;number<indices.length-3;number+=9)
		{
			float vx1 = vertices[indices[number]];
			float vy1 = vertices[indices[number+1]];
			float vz1 = vertices[indices[number+2]];
			float vx2 = vertices[indices[number+3]];
			float vy2 = vertices[indices[number+4]];
			float vz2 = vertices[indices[number+5]];
			float vx3 = vertices[indices[number+6]];
			float vy3 = vertices[indices[number+7]];
			float vz3 = vertices[indices[number+8]];
			System.out.println(vx1);
			System.out.println(vy1);
			System.out.println(vz1);
			System.out.println(vx2);
			System.out.println(vy2);
			System.out.println(vz2);
			System.out.println(vx3);
			System.out.println(vy3);
			System.out.println(vz3);
					
			collisionModel.triangles.add(new Triangle3D(
				new Point3D(vx1,vy1,vz1), 
				new Point3D(vx2,vy2,vz2), 
				new Point3D(vx3,vy3,vz3)));
		}
		
		
		
		return loader.loadToVAO(vertices, textureCoords, normals, indices);
	}
	
	private Vector3f calculateNormal(int x, int y, BufferedImage image)
	{
		float heightL = getHeight(x-1, y, image);
		float heightR = getHeight(x+1, y, image);
		float heightD = getHeight(x, y-1, image);
		float heightU = getHeight(x, y+1, image);
		
		Vector3f normal = new Vector3f(heightL-heightR, 5f, heightD-heightU);
		normal.normalise();
		return normal;
	}
	
	private float getHeight(int x, int y, BufferedImage image)
	{
		if(x < 0 || x >= image.getHeight() || y < 0 || y >= image.getHeight())
		{
			return 0;
		}
		else
		{
			float height = image.getRGB(x,  y);
			height += MAX_PIXEL_COLOUR/2f;
			height /= MAX_PIXEL_COLOUR/2f;
			height *= MAX_HEIGHT;
			return height;
		}
	}
}
