package collision;

import org.lwjgl.util.vector.Vector3f;


public class Triangle3D 
{
	public Vector3f p1;
	public Vector3f p2;
	public Vector3f p3;
	public float A;
	public float B;
	public float C;
	public float D;
	public Vector3f normal;
	
	
	public Triangle3D(Vector3f newP1, Vector3f newP2, Vector3f newP3)
	{
		this.p1 = newP1;
		this.p2 = newP2;
		this.p3 = newP3;
		generateValues();
	}
	
	public void generateValues()
	{
		A = p1.y*(p2.z - p3.z) + p2.y*(p3.z - p1.z) + p3.y*(p1.z - p2.z);
	    B = p1.z*(p2.x - p3.x) + p2.z*(p3.x - p1.x) + p3.z*(p1.x - p2.x);
	    C = p1.x*(p2.y - p3.y) + p2.x*(p3.y - p1.y) + p3.x*(p1.y - p2.y);
	    D = -1*(p1.x*(p2.y*p3.z - p3.y*p2.z) + p2.x*(p3.y*p1.z - p1.y*p3.z) + p3.x*(p1.y*p2.z - p2.y*p1.z));
	    
	    float mag = (float)(Math.sqrt((A*A)+(B*B)+(C*C)));
	    
	    normal = null;
	    if (mag!= 0)
	    {
	    	normal = new Vector3f(A*(1.0f/mag), B*(1.0f/mag), C*(1.0f/mag));
	    }
	    else
	    {
	    	normal = new Vector3f(0,0,0);
	    }
	}
	
	public float getA()
	{
		return A;
	}
	
	public float getB()
	{
		return B;
	}
	
	public float getC()
	{
		return C;
	}
	
	public float getD()
	{
		return D;
	}
}
