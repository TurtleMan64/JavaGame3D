package engineTester;

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
import entities.Camera;
import entities.Entity;
import entities.Light;
import entities.Player;

public class MainGameLoop 
{
	public static void main(String[] args) 
	{
		DisplayManager.createDisplay();
		
		Loader loader = new Loader();
		
		//terrain stuff start
		TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("grass"));
		TerrainTexture rTexture = new TerrainTexture(loader.loadTexture("mud"));
		TerrainTexture gTexture = new TerrainTexture(loader.loadTexture("grassy2"));
		TerrainTexture bTexture = new TerrainTexture(loader.loadTexture("path"));
		
		TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture, rTexture, gTexture, bTexture);
		
		TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("blendMap"));
		
		
		
		
		
		
		RawModel model = OBJLoader.loadObjModel("dragon3dsmax", loader);
		
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
		
		
		Entity entity = new Entity(staticModel, new Vector3f(0,-5,-20),0,0,0,1);
		Entity entityFern = new Entity(staticModelFern, new Vector3f(0,0,0),0,0,0,1);
		Entity entitySun = new Entity(staticModelSun, new Vector3f(0,0,0),0,0,0,1);
		Entity entityBall = new Entity(staticModelSun, new Vector3f(0,0,0),0,0,0,1);
		entity.setScale(0.1f);
		
		
		Light light = new Light(new Vector3f(0,1000000,40), new Vector3f(1,1,1));
		
		Terrain terrain = new Terrain(0, -1, loader, texturePack, blendMap);
		Terrain terrain2 = new Terrain(-1, -1, loader, texturePack, blendMap);
		
		//Entity terrainentity = new Entity(new TexturedModel(terrain.getModel(), new ModelTexture(loader.loadTexture("image"))), new Vector3f(0,0,0), 0,0,0,1);
		
		Camera camera = new Camera();
		
		Player player = new Player(staticModelOotShield, new Vector3f(0,0,0), 0,0,0,1, camera);
		
		MasterRenderer renderer = new MasterRenderer();
		
		
		while(!Display.isCloseRequested() && !(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)))
		{
			//entity.increasePosition(0, 0, 0);
			camera.move();
			//camera.setPosition(player.getPosition());
			player.step();
			light.setPosition(new Vector3f(light.getPosition().x, light.getPosition().y+0.02f, light.getPosition().z));
			entity.increaseRotation(0, 1, 0);
			entitySun.setPosition(light.getPosition());
			
			player.increaseRotation(1, 1, 1);
			//entityBall.setPosition(player.getViewPosition());
			
			//game logic
			
			renderer.processTerrain(terrain);
			renderer.processTerrain(terrain2);
			
			renderer.processEntity(entity);
			renderer.processEntity(entityFern);
			renderer.processEntity(entitySun);
			renderer.processEntity(entityBall);
			renderer.processEntity(player);
			
			renderer.render(light, camera);

			DisplayManager.updateDisplay();
		}
		
		renderer.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();
	}

}