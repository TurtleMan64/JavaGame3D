package collision;


import org.lwjgl.util.vector.Vector3f;

public class CollisionChecker 
{
	private CollisionModel collideModel;
	private Vector3f collidePosition;
	public Vector3f p1Collide;
	public Vector3f p2Collide;
	
	public CollisionChecker(CollisionModel myCollideModel)//colision object)
	{
		this.collideModel = myCollideModel;
		collidePosition = new Vector3f(0, 0, 0);
		p1Collide = new Vector3f(0, 0, 0);
		p2Collide = new Vector3f(0, 0, 0);
	}
	
	
	
	public boolean checkCollision(Vector3f startPoint, Vector3f endPoint)
	{
		return this.checkCollision(startPoint.x, startPoint.y, startPoint.z, endPoint.x, endPoint.y, endPoint.z);
	}
	//Anders Sjoboen
	//checks for a collision
	//sets the field "collidePosition" to the coordinates of the collision
	
	//takes in a starting position and an ending position
	
	//returns whether or not there was a collision (true if there was)
	public boolean checkCollision(float px1, float py1, float pz1, float px2, float py2, float pz2)
	{
		
		//var tridex,px1,py1,pz1,px2,py2,pz2,firstabove,secondabove,trianglecollide,
		//i,tx1,ty1,tz1,tx2,ty2,tz2,tx3,ty3,tz3,A,B,C,D,cix,ciy,ciz,numerator,
		//denominator,u,normalx,normaly,normalz,checktriangle;
		//p1Collide.set(startPoint);
		//p2Collide.set(endPoint);
		int triIndex;
		//float px1 = startPoint.x, py1 = startPoint.y, pz1 = startPoint.z;
		//float px2 = endPoint.x, py2 = endPoint.y, pz2 = endPoint.z;
		int firstAbove, secondAbove, triangleCollide, i;
		//Triangle3D testTri = null;
		float tx1, ty1, tz1, tx2, ty2, tz2, tx3, ty3, tz3;
		float A, B, C, D;
		//Point3D collisionPoint = null;
		float cix, ciy, ciz;
		float numerator, denominator, u;
		//Vector3f normal = null;
		boolean checkTriangle;
		
		/*
		px1 = argument0;
		py1 = argument1;
		pz1 = argument2;

		px2 = argument3;
		py2 = argument4;
		pz2 = argument5;
		*/

		checkTriangle = false;
		firstAbove = -2;
		secondAbove = -2;
		triangleCollide = -1;
		boolean onPlane = false;
		float minDist = -1;
		triIndex = -1;
		
		//ty1 = 0;
		//ty2 = 0;
		//ty3 = 0;
		//tz1 = 0;
		//tz2 = 0;
		//tz3 = 0;

		i = 0;

		//while(i < ob_map.tricount)
		for(i = 0; i < collideModel.triangles.size(); i++)
		{
			//System.out.println("checking triangle# "+i);
			//System.out.println("triangle p1 =  "+collideModel.triangles.get(i).p1);
		    checkTriangle = false;
			//checkTriangle = true;
		    
		    tx1 = collideModel.triangles.get(i).p1.x;
		    tx2 = collideModel.triangles.get(i).p2.x;
		    tx3 = collideModel.triangles.get(i).p3.x;
		    //if(px1-6 <= Math.max(tx1, Math.max(tx2,tx3)) && px1+6 >= Math.min(tx1, Math.min(tx2,tx3)))
		    {
		        ty1 = collideModel.triangles.get(i).p1.y;
			    ty2 = collideModel.triangles.get(i).p2.y;
			    ty3 = collideModel.triangles.get(i).p3.y;
		        //if(py1-6 <= Math.max(ty1, Math.max(ty2,ty3)) && py1+6 >= Math.min(ty1, Math.min(ty2,ty3)))
		        {
		            tz1 = collideModel.triangles.get(i).p1.z;
				    tz2 = collideModel.triangles.get(i).p2.z;
				    tz3 = collideModel.triangles.get(i).p3.z;
		            //if(pz1-6 <= Math.max(tz1, Math.max(tz2,tz3)) && pz1+6 >= Math.min(tz1, Math.min(tz2,tz3)))
		            {
		            	checkTriangle = true;
		            }
		        }
		    }
		    
		    
		    if(checkTriangle == true)
		    {
		    	A = collideModel.triangles.get(i).getA();
		    	B = collideModel.triangles.get(i).getB();
		    	C = collideModel.triangles.get(i).getC();
		    	D = collideModel.triangles.get(i).getD();
		        
		        numerator = (A*px1+B*py1+C*pz1+D);
		        denominator = (A*(px1-px2)+B*(py1-py2)+C*(pz1-pz2));
		        
		        //System.out.println("numerator = "+numerator);
		        //System.out.println("denominator = "+denominator);
		        
		        if(denominator != 0)
		        {
		            u = (numerator/denominator);
		            cix = px1+u*(px2-px1);
		            ciy = py1+u*(py2-py1);
		            ciz = pz1+u*(pz2-pz1);
		            //System.out.println("cix = "+cix);
		            //System.out.println("ciy = "+ciy);
		            //System.out.println("ciz = "+ciz);
		            //collidePosition.x = cix;
                    //collidePosition.y = ciy;
                    //collidePosition.z = ciz;
		            
		            if(B != 0)
		            {
		                //float planez1 = (((-A*px1) + (-B*py1)-D)/C);
		                //collidePosition.z = planez1;
		                //System.out.println("planez1 = "+planez1);
		                //float planez2 = (((-A*px2) + (-B*py2)-D)/C);
		            	float planey1 = (((-A*px1) + (-C*pz1)-D)/B);
		                float planey2 = (((-A*px2) + (-C*pz2)-D)/B);
		                //collidePosition.y = planey1;
		                //p1Collide.y = planey1;
		                //p2Collide.y = planey2;
		                //System.out.println("planey2");
		                if (py1 > planey1)
		                {
		                    firstAbove = 1;
		                }
		                else
		                {
		                    if (py1 < planey1)
		                    {
		                        firstAbove = -1;
		                    }
		                    else
		                    {
		                        firstAbove = 0;
		                    }
		                }
		                
		                if (py2 < planey2)
		                {
		                    secondAbove = -1;
		                }
		                else
		                {
		                    if (py2 > planey2)
		                    {
		                        secondAbove = 1;
		                    }
		                    else
		                    {
		                        secondAbove = 0;
		                    }
		                }
		            }
		            else
		            {
		                //think about this later, it is with a vertical wall, and can be determined
		                //coming back later, it looks like this is not for a vertical wall at all, instead is already after the check for a vertical wall
		                //this is if you are still on a plane
		                firstAbove = 0;
		                secondAbove = 0;
		            }
		        }
		        else
		        {
		            //vertical wall
		            //u = (numerator/denominator);
		            //cix = px1+u*(px2-px1);
		            //ciy = py1+u*(py2-py1);
		            //ciz = pz1+u*(pz2-pz1);
		            u = -1;
		            cix = -1;
		            ciy = -1;
		            ciz = -1;
		            triIndex = -1;
		        }
		        
		        if ((secondAbove != firstAbove))
		        {
		        	//System.out.println("made it to here");
		            //if the line actually intersects the triangle
		            if (u == -1 && ciz == -1 && ciy == -1 && ciz == -1 && triIndex == -1)
		            {
		            	
		            }
		            else
		            {
		                if (checkInsideTriangle(cix,ciy,ciz,tx1,ty1,tz1,tx2,ty2,tz2,tx3,ty3,tz3,10))
		                {
		                	//System.out.println("point is inside the triangle");
		                    //what is the distance to the triangle? set it to maxdist
		                    if (minDist == -1)
		                    {
		                        triangleCollide = i;
		                        triIndex = i;
		                        
		                        collidePosition.x = cix;
	                            collidePosition.y = ciy;
	                            collidePosition.z = ciz;

		                        minDist = (float)(Math.sqrt(Math.abs((cix-px1)*(cix-px1)+(ciy-py1)*(ciy-py1)+(ciz-pz1)*(ciz-pz1))));
		                    }
		                    else
		                    {
		                        //if (this distance is closer)
		                        if ((float)(Math.sqrt(Math.abs((cix-px1)*(cix-px1)+(ciy-py1)*(ciy-py1)+(ciz-pz1)*(ciz-pz1)))) < minDist)
		                        {
		                            triangleCollide = i;
		                            triIndex = i;
		                            
		                            collidePosition.x = cix;
		                            collidePosition.y = ciy;
		                            collidePosition.z = ciz;
		                            
		                            minDist = (float)(Math.sqrt(Math.abs((cix-px1)*(cix-px1)+(ciy-py1)*(ciy-py1)+(ciz-pz1)*(ciz-pz1))));
		                        }
		                    }
		                }
		                else
		                {
		                	//System.out.println("collision point was not inside the triangle");
		                }
		            }
		        }
		        else
		        {
		            if (firstAbove == 0 && secondAbove == 0)
		            {
		                //trianglecollide = i;
		                //tridex = i;
		                onPlane = true;
		            }
		        }
		    }
		}
		
		if (triangleCollide > -1)
		{
		    return true;
		}
		else
		{
		    //if (onPlane == false)
		    {
		    	//triangleCollideIndex = -1;
		    }
		    return false;
		}
	}
	
	
	
	
	
	public boolean checkInsideTriangle(
			float checkx, float checky, float checkz, 
			float cx1, float cy1, float cz1,
			float cx2, float cy2, float cz2,
			float cx3, float cy3, float cz3,
			float prec)
	{

		//totalarea = sc_get_area(cx1,cy1,cx2,cy2,cx3,cy3);

		//t1 = sc_get_area(checkx,checky,cx2,cy2,cx3,cy3);
		//t2 = sc_get_area(cx1,cy1,checkx,checky,cx3,cy3);
		//t3 = sc_get_area(cx1,cy1,cx2,cy2,checkx,checky);

		//return abs((t1+t2+t3)-(totalarea)) <= 0.000001;
		//return abs((t1+t2+t3)-(totalarea)) <= prec;

		//top down
		//left view
		//front view

		if (((getArea(checkx,checky,cx2,cy2,cx3,cy3)+getArea(cx1,cy1,checkx,checky,cx3,cy3)+getArea(cx1,cy1,cx2,cy2,checkx,checky))-getArea(cx1,cy1,cx2,cy2,cx3,cy3)) <= prec &&
		   ((getArea(checky,checkz,cy2,cz2,cy3,cz3)+getArea(cy1,cz1,checky,checkz,cy3,cz3)+getArea(cy1,cz1,cy2,cz2,checky,checkz))-getArea(cy1,cz1,cy2,cz2,cy3,cz3)) <= prec &&
		   ((getArea(checkx,checkz,cx2,cz2,cx3,cz3)+getArea(cx1,cz1,checkx,checkz,cx3,cz3)+getArea(cx1,cz1,cx2,cz2,checkx,checkz))-getArea(cx1,cz1,cx2,cz2,cx3,cz3)) <= prec)
		{
		    return true;
		}
		else
		{
		    return false;
		}
	}
	
	
	public float getArea(float argument0, float argument1, float argument2, float argument3, float argument4, float argument5)
	{
		//float argument0 = x1;
		//float argument1 = y1;
		//float argument2 = x2;
		//float argument3 = y2;
		//float argument4 = x3;
		//float argument5 = y3;
		
		float a,b,c,s;
		a = (float)Math.sqrt(Math.abs(((argument0-argument2)*(argument0-argument2))+((argument1-argument3)*(argument1-argument3))));
		b = (float)Math.sqrt(Math.abs(((argument0-argument4)*(argument0-argument4))+((argument1-argument5)*(argument1-argument5))));
		c = (float)Math.sqrt(Math.abs(((argument4-argument2)*(argument4-argument2))+((argument5-argument3)*(argument5-argument3))));
		s = (float)((a+b+c)/2.0);

		return (float)(Math.sqrt(Math.abs(s*(s-a)*(s-b)*(s-c))));
	}
	
	public CollisionModel getCollideModel()
	{
		return collideModel;
	}
	
	public void setCollideModel(CollisionModel newCollideModel)
	{
		this.collideModel = newCollideModel;
	}

	public Vector3f getCollidePosition() 
	{
		return collidePosition;
	}
}
