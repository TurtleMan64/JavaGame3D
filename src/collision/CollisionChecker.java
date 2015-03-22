package collision;


import org.lwjgl.util.vector.Vector3f;

public class CollisionChecker 
{
	private CollisionModel collideModel;
	private Point3D collidePosition;
	
	public CollisionChecker(CollisionModel myCollideModel)//colision object)
	{
		this.collideModel = myCollideModel;
		collidePosition = new Point3D(0, 0, 0);
	}
	
	
	//Anders Sjoboen
	//checks for a collision
	//sets the field "collidePosition" to the coordinates of the collision
	
	//takes in a starting position and an ending position
	
	//returns whether or not there was a collision (true if there was)
	public boolean checkCollision(Point3D startPoint, Point3D endPoint)
	{
		////////////////////////////////////////////////////////////////////////////////////////////////
		
		//var tridex,px1,py1,pz1,px2,py2,pz2,firstabove,secondabove,trianglecollide,
		//i,tx1,ty1,tz1,tx2,ty2,tz2,tx3,ty3,tz3,A,B,C,D,cix,ciy,ciz,numerator,
		//denominator,u,normalx,normaly,normalz,checktriangle;
		
		int triIndex;
		float px1 = startPoint.x, py1 = startPoint.y, pz1 = startPoint.z;
		float px2 = endPoint.x, py2 = endPoint.y, pz2 = endPoint.z;
		int firstAbove, secondAbove, triangleCollide, i;
		//Triangle3D testTri = null;
		float tx1, ty1, tz1, tx2, ty2, tz2, tx3, ty3, tz3;
		float A, B, C, D;
		//Point3D collisionPoint = null;
		float cix, ciy, ciz;
		float numerator, denominator, u;
		//Vector3f normal = null;
		float normalX, normalY, normalZ;
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

		i = 0;

		//while(i < ob_map.tricount)
		for(i = 0; i < collideModel.triangles.size()-1; i++)
		{
		    //checkTriangle = false;
			checkTriangle = true;
		    /*
		    tx1 = collideModel.triangles.get(i).p1.x;
		    tx2 = collideModel.triangles.get(i).p2.x;
		    tx3 = collideModel.triangles.get(i).p3.x;
		    if(px1-6 <= max(tx1,tx2,tx3) && px1+6 >= min(tx1,tx2,tx3))
		    {
		        ty1 = ob_map.y1[i];
		        ty2 = ob_map.y2[i];
		        ty3 = ob_map.y3[i];
		        if(py1-6 <= max(ty1,ty2,ty3) && py1+6 >= min(ty1,ty2,ty3))
		        {
		            tz1 = ob_map.z1[i];
		            tz2 = ob_map.z2[i];
		            tz3 = ob_map.z3[i];
		            if(pz1-6 <= max(tz1,tz2,tz3) && pz1+6 >= min(tz1,tz2,tz3))
		            {
		                checktriangle = 1;
		            }
		        }
		    }
		    */
		    
		    if(checkTriangle == true)
		    {
		    	A = collideModel.triangles.get(i).getA();
		    	B = collideModel.triangles.get(i).getB();
		    	C = collideModel.triangles.get(i).getC();
		    	D = collideModel.triangles.get(i).getD();
		        
		        numerator = (A*px1+B*py1+C*pz1+D);
		        denominator = (A*(px1-px2)+B*(py1-py2)+C*(pz1-pz2));
		        
		        if(denominator != 0)
		        {
		            u = (numerator/denominator);
		            cix = px1+u*(px2-px1);
		            ciy = py1+u*(py2-py1);
		            ciz = pz1+u*(pz2-pz1);
		            
		            if(C != 0)
		            {
		                float planez1 = (((-A*px1) + (-B*py1)-D)/C);
		                float planez2 = (((-A*px2) + (-B*py2)-D)/C);
		                if (pz1 > planez1)
		                {
		                    firstAbove = 1;
		                }
		                else
		                {
		                    if (pz1 < planez1)
		                    {
		                        firstAbove = -1;
		                    }
		                    else
		                    {
		                        firstAbove = 0;
		                    }
		                }
		                
		                if (pz2 < planez2)
		                {
		                    secondAbove = -1;
		                }
		                else
		                {
		                    if (pz2 > planez2)
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
		            //if the line actually intersects the triangle
		            if (string(cix) == "nothing")
		            {
		            
		            }
		            else
		            {
		                if (sc_check_intriangle(cix,ciy,ciz,tx1,ty1,tz1,tx2,ty2,tz2,tx3,ty3,tz3,1))
		                {
		                    //what is the distance to the triangle? set it to maxdist
		                    if (string(mindist) == "nothing")
		                    {
		                        trianglecollide = i;
		                        tridex = i;
		                        tricoldex = i;
		                        
		                        collidePosition.x = cix;
	                            collidePosition.y = ciy;
	                            collidePosition.z = ciz;

		                        mindist = sqrt(abs((cix-px1)*(cix-px1)+(ciy-py1)*(ciy-py1)+(ciz-pz1)*(ciz-pz1)));
		                    }
		                    else
		                    {
		                        //if (this distance is closer)
		                        if (sqrt(abs((cix-px1)*(cix-px1)+(ciy-py1)*(ciy-py1)+(ciz-pz1)*(ciz-pz1))) < mindist)
		                        {
		                            trianglecollide = i;
		                            tridex = i;
		                            tricoldex = i;
		                            
		                            collidePosition.x = cix;
		                            collidePosition.y = ciy;
		                            collidePosition.z = ciz;
		                            
		                            mindist = sqrt(abs((cix-px1)*(cix-px1)+(ciy-py1)*(ciy-py1)+(ciz-pz1)*(ciz-pz1)));
		                        }
		                    }
		                }
		            }
		        }
		        else
		        {
		            if (firstabove == 0 && secondabove == 0)
		            {
		                //trianglecollide = i;
		                //tridex = i;
		                tricoldex = i;
		                onplane = true;
		            }
		        }
		    }
		    i+=1;
		}
		if (trianglecollide > -1)
		{
		    return false;
		}
		else
		{
		    if (onplane == false)
		    {
		        tricoldex = "nothing";
		    }
		    return true;
		}
		
		
		
		////////////////////////////////////////////////////////////////////////////////////////////////
		
		
		return true;
	}
	
	
	
	
	
	public boolean checkInsideTriangle(
			float cx1, float cy1, float cz1,
			float cx2, float cy2, float cz2,
			float cx3, float cy3, float cz3,
			float prec, 
			float checkx, float checky, float checkz)
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
	
	
	public float getArea(float x1, float y1, float x2, float y2, float x3, float y3)
	{
		float argument0 = x1;
		float argument1 = y1;
		float argument2 = x2;
		float argument3 = y2;
		float argument4 = x3;
		float argument5 = y3;
		
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
}
