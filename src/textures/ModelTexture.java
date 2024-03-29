package textures;

public class ModelTexture 
{
	private int textureID;
	
	private float shineDamper;
	private float reflectivity;
	
	private boolean hasTransparency = false;
	private boolean useFakeLighting = false;
	
	
	public boolean isUseFakeLighting() 
	{
		return useFakeLighting;
	}

	public void setUseFakeLighting(boolean useFakeLighting) 
	{
		this.useFakeLighting = useFakeLighting;
	}

	public boolean isHasTransparency() 
	{
		return hasTransparency;
	}

	public void setHasTransparency(boolean hasTransparency) 
	{
		this.hasTransparency = hasTransparency;
	}

	public ModelTexture(int id)
	{
		this.textureID = id;
		shineDamper = 1;
		reflectivity = 0;
	}

	public int getID() 
	{
		return this.textureID;
	}

	public float getShineDamper() 
	{
		return shineDamper;
	}

	public void setShineDamper(float shineDamper) 
	{
		this.shineDamper = shineDamper;
	}

	public float getReflectivity() 
	{
		return reflectivity;
	}

	public void setReflectivity(float reflectivity) 
	{
		this.reflectivity = reflectivity;
	}
}
