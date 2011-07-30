package com.idflood.sky;

import com.idflood.sky.items.DynamicSkyBackground;
import com.idflood.sky.items.DynamicSun;
import com.jme3.asset.AssetManager;
import com.jme3.math.Vector3f;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;

public class DynamicSky extends Node {
    private ViewPort viewPort = null;
    private AssetManager assetManager = null;
    
    private DynamicSun dynamicSun= null;
    private DynamicSkyBackground dynamicBackground = null;
    
    public DynamicSky(AssetManager assetManager, ViewPort viewPort, Node rootNode) {
        super("Sky");
        this.assetManager = assetManager;
        this.viewPort = viewPort;
        
        dynamicSun = new DynamicSun(assetManager, viewPort, rootNode);
        rootNode.attachChild(dynamicSun);
        
        dynamicBackground = new DynamicSkyBackground(assetManager, viewPort, rootNode);
    }
    
    public Vector3f getSunDirection(){
        return dynamicSun.getSunDirection();
    }
        
    public void updateTime(){
        dynamicSun.updateTime();
        dynamicBackground.updateLightPosition(dynamicSun.getSunSystem().getPosition());
    }
    
}