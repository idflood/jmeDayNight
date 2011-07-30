package com.idflood.sky;

import com.jme3.asset.AssetManager;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.material.RenderState.BlendMode;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.renderer.ViewPort;
import com.jme3.renderer.queue.RenderQueue.Bucket;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.BillboardControl;
import com.jme3.scene.shape.Quad;
import com.jme3.scene.shape.Sphere;
import java.util.Date;

public class DynamicSun extends Node {
    private static final Sphere sphereMesh = new Sphere(40, 40, 900, false, true);
    
    private ViewPort viewPort = null;
    private AssetManager assetManager = null;
    
    private Vector3f screenLightPos = new Vector3f();
    
    public Vector3f viewLightPos = new Vector3f();
    
    private DirectionalLight sunLight = null;
    private Geometry skyGeom = null;
    private Material skyMaterial = null;
    
    private SunSystem sunSystem = new SunSystem(new Date(), 0, 0, 0);
    
    private Vector3f lightDir = sunSystem.getPosition();
    private Vector3f lightPosition = new Vector3f();
    
    private Quad sunDisc;
    private Geometry sunDiscGeom;
    
    private float scaling = 900;
    
    public DynamicSun(AssetManager assetManager, ViewPort viewPort, Node rootNode) {
        this.assetManager = assetManager;
        this.viewPort = viewPort;
        
        sunLight = getSunLight();
        rootNode.addLight(sunLight);
        
        skyGeom = getSkyGeometry();
        rootNode.attachChild(skyGeom);
        
        sunSystem.setSiteLatitude(46.32f);
        sunSystem.setSiteLongitude(6.38f);
        updateLightPosition();
        
        sunDisc = new Quad(170, 170);
        sunDiscGeom = new Geometry("sunDisc", sunDisc);
        Material sunDiscMat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        sunDiscMat.setTexture("ColorMap", assetManager.loadTexture("Textures/sun.png"));
        sunDiscGeom.setMaterial(sunDiscMat);
        sunDiscMat.getAdditionalRenderState().setBlendMode(BlendMode.Alpha);
        sunDiscGeom.setCullHint(CullHint.Never);
        
        BillboardControl control = new BillboardControl();
        sunDiscGeom.addControl(control);
        sunDiscGeom.setLocalTranslation(0.5f, 5f, 3f);
        sunDiscGeom.setQueueBucket(Bucket.Transparent);
        attachChild(sunDiscGeom);
        
        setQueueBucket(Bucket.Sky);
        setCullHint(CullHint.Never);
    }
    
    protected Geometry getSkyGeometry(){
        Geometry geom = new Geometry("Sky", sphereMesh);
        geom.setQueueBucket(Bucket.Sky);
        geom.setCullHint(Spatial.CullHint.Never);
        
        skyMaterial = getDynamicSkyMaterial();
        geom.setMaterial(skyMaterial);
        return geom;
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

        viewPort.getCamera().getViewMatrix().mult(lightPosition, viewLightPos);
        skyMaterial.setVector3("lightPosition", lightPosition);
    }
    
    protected Material getDynamicSkyMaterial(){
        Material skyMat = new Material(assetManager, "MatDefs/dynamic_sky.j3md");
        skyMat.setTexture("glow_texture", assetManager.loadTexture("Textures/glow.png"));
        skyMat.setTexture("color_texture", assetManager.loadTexture("Textures/sky.png"));
        skyMat.setVector3("lightPosition", screenLightPos);
        
        return skyMat;
    }
    
    public Vector3f getSunDirection(){
        return sunSystem.getPosition();
    }

    void updateTime() {
        // make everything follow camery
        skyGeom.setLocalTranslation(viewPort.getCamera().getLocation());
        setLocalTranslation(viewPort.getCamera().getLocation());
        
        sunSystem.updateSunPosition(0, 0, 30); // increment by 30 seconds
        updateLightPosition();
        
        sunLight.setDirection(lightDir);
        sunDiscGeom.setLocalTranslation(lightPosition.mult(0.95f));
    }
}
