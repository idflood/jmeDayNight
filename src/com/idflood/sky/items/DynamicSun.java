package com.idflood.sky.items;

import com.idflood.sky.utils.SkyBillboardItem;
import com.idflood.sky.utils.SunSystem;
import com.jme3.asset.AssetManager;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.ViewPort;
import com.jme3.renderer.queue.RenderQueue.Bucket;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Sphere;
import java.util.Date;

public class DynamicSun extends Node {
    private static final Sphere sphereMesh = new Sphere(40, 40, 900, false, true);
    
    private ViewPort viewPort = null;
    private AssetManager assetManager = null;
    
    private SunSystem sunSystem = new SunSystem(new Date(), 0, 0, 0);
    private SkyBillboardItem sun;
    
    private DirectionalLight sunLight = null;
    private Vector3f lightDir = sunSystem.getPosition();
    private Vector3f lightPosition = new Vector3f();
    
    private float scaling = 900;
    
    public DynamicSun(AssetManager assetManager, ViewPort viewPort, Node rootNode) {
        this.assetManager = assetManager;
        this.viewPort = viewPort;
        
        sunLight = getSunLight();
        rootNode.addLight(sunLight);
                
        sunSystem.setSiteLatitude(46.32f);
        sunSystem.setSiteLongitude(6.38f);
        updateLightPosition();
        
        sun = new SkyBillboardItem(assetManager, "sun", "Textures/sun.png", 170f);
        attachChild(sun);
        
        setQueueBucket(Bucket.Sky);
        setCullHint(CullHint.Never);
    }
    
    public SunSystem getSunSystem(){
        return sunSystem;
    }
    
    
    protected DirectionalLight getSunLight(){
        DirectionalLight dl = new DirectionalLight();
        dl.setDirection(lightDir.normalize());
        dl.setColor(ColorRGBA.White);
        return dl;
    }
        
    protected void updateLightPosition(){
        lightDir = sunSystem.getDirection();
        lightPosition = sunSystem.getPosition();
    }
    
    
    public Vector3f getSunDirection(){
        return sunSystem.getPosition();
    }

    public void updateTime() {
        // make everything follow the camera
        setLocalTranslation(viewPort.getCamera().getLocation());
        
        sunSystem.updateSunPosition(0, 0, 30); // increment by 30 seconds
        updateLightPosition();
        
        sunLight.setDirection(lightDir);
        sun.setLocalTranslation(lightPosition.mult(0.95f));
    }
}