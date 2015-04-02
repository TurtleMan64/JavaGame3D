package engineTester;

import java.util.ArrayList;
import java.util.List;

import models.RawModel;
import models.TexturedModel;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import renderEngine.OBJLoader;
import terrains.Terrain;
import textures.ModelTexture;
import textures.TerrainTexture;
import textures.TerrainTexturePack;
import collision.CollisionChecker;
import collision.CollisionModel;
import entities.Ball;
import entities.Camera;
import entities.Entity;
import entities.Light;
import entities.Player;

public class MainGameLoop 
{
	static ArrayList<Entity> gameEntities;
	
	public static void main(String[] args) 
	{
		long timeStamp = System.currentTimeMillis();
		int frameCount = 0;
		gameEntities = new ArrayList<Entity>();
		
		DisplayManager.createDisplay();
		
		Loader loader = new Loader();
		
		TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("grass"));
		TerrainTexture rTexture = new TerrainTexture(loader.loadTexture("mud"));
		TerrainTexture gTexture = new TerrainTexture(loader.loadTexture("grassy2"));
		TerrainTexture bTexture = new TerrainTexture(loader.loadTexture("path"));
		
		TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture, rTexture, gTexture, bTexture);
		
		TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("blendMap"));
		
		
		
		RawModel model = OBJLoader.loadObjModel("BeachStage", loader);
		
		CollisionModel beachCollideModel = OBJLoader.loadObjModel("BeachStage");
		
		RawModel modelOotShield = OBJLoader.loadObjModel("ootShield", loader);
		
		RawModel modelFern = OBJLoader.loadObjModel("fern", loader);
		
		RawModel modelSun = OBJLoader.loadObjModel("sphere3dsmax", loader);
		
		TexturedModel staticModel = new TexturedModel(model, new ModelTexture(loader.loadTexture("rainbow")));
		
		TexturedModel staticModelOotShield = new TexturedModel(modelOotShield, new ModelTexture(loader.loadTexture("orig")));
		
		TexturedModel staticModelFern = new TexturedModel(modelFern, new ModelTexture(loader.loadTexture("fern")));
		
		TexturedModel staticModelSun = new TexturedModel(modelSun, new ModelTexture(loader.loadTexture("image")));
		
		ModelTexture texture = staticModel.getTexture();
		texture.setShineDamper(40.0f);
		texture.setReflectivity(0.5f);
		
		ModelTexture textureFern = staticModelFern.getTexture();
		textureFern.setShineDamper(1.0f);
		textureFern.setReflectivity(0.0f);
		textureFern.setHasTransparency(true);
		textureFern.setUseFakeLighting(true);
		
		ModelTexture textureSun = staticModelSun.getTexture();
		textureSun.setShineDamper(1.0f);
		textureSun.setReflectivity(0.0f);
		
		ModelTexture textureOotShield = staticModelOotShield.getTexture();
		textureOotShield.setShineDamper(1.0f);
		textureOotShield.setReflectivity(1.0f);
		
		
		Entity entity = new Entity(staticModel, new Vector3f(0,0,0),0,0,0,1);
		Entity entityFern = new Entity(staticModelFern, new Vector3f(0,0,0),0,0,0,1);
		Entity entitySun = new Entity(staticModelSun, new Vector3f(0,0,0),0,0,0,1);
		Entity lampEntity = new Entity(staticModelSun, new Vector3f(183,-15,-140),0,0,0,1);
		
		
		
		gameEntities.add(entity);
		gameEntities.add(entityFern);
		gameEntities.add(entitySun);
		gameEntities.add(lampEntity);
		
		
		
		
		//Light light = new Light(new Vector3f(0,1000000,-500000), new Vector3f(1f,1f,1f));
		
		Light light = new Light(new Vector3f(0,100000,-500000), new Vector3f(1f,1f,1f));
		
		
		List<Light> lights = new ArrayList<Light>();
		lights.add(light);
		lights.add(new Light(new Vector3f(183,-15,-140), new Vector3f(2,2,0), new Vector3f(1, 0.01f, 0.002f)));
		
		Terrain terrain = new Terrain(0, -1, loader, texturePack, blendMap, "heightMap", 1f, 0f);
		
		CollisionChecker collisionChecker = new CollisionChecker(beachCollideModel);
		
		Camera camera = new Camera();
		
		for(int i = 0; i < 1; i++)
		{
			gameEntities.add(new Ball(staticModelSun, new Vector3f((float)Math.random()*100,(float)Math.random()*100,(float)Math.random()*100), 0,0,0,1, collisionChecker));
		}
		
		Player player = new Player(staticModelOotShield, new Vector3f(0,0,0), 0,0,0,1, camera, collisionChecker);
		
		gameEntities.add(player);
		
		MasterRenderer renderer = new MasterRenderer();
		
		
		while(!Display.isCloseRequested() && !(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)))
		{
			lampEntity.setPosition(collisionChecker.getCollidePosition());

			entitySun.setPosition(light.getPosition());
			
			camera.step();
			for(Entity currentEntity: gameEntities)
			{
				currentEntity.step();
				renderer.processEntity(currentEntity);
			}
			lights.get(1).setPosition(player.getPosition());
			
			renderer.processTerrain(terrain);
			
			renderer.render(lights, camera, collisionChecker.getCollideModel());

			DisplayManager.updateDisplay();
			
			frameCount+=1;
			if(frameCount >= 60)
			{
				frameCount = 0;
				System.out.println(0.06*(System.currentTimeMillis() - timeStamp));
				timeStamp = System.currentTimeMillis();
			}
		}
		
		renderer.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();
	}
}
