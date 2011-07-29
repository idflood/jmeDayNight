package com.idflood.sky;

import com.jme3.asset.AssetManager;
import com.jme3.bounding.BoundingVolume;
import com.jme3.collision.Collidable;
import com.jme3.collision.CollisionResults;
import com.jme3.collision.UnsupportedCollisionException;
import com.jme3.scene.SceneGraphVisitor;
import com.jme3.scene.shape.Sphere;
import com.jme3.asset.AssetManager;
import com.jme3.asset.TextureKey;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.renderer.ViewPort;
import com.jme3.renderer.queue.RenderQueue.Bucket;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Sphere;
import com.jme3.texture.Image;
import com.jme3.texture.Image.Format;
import com.jme3.texture.Texture;
import com.jme3.texture.TextureCubeMap;
import java.util.Queue;

/**
 *
 * @author davidmignot
 */
public class DynamicSky extends Node {
    private ViewPort viewPort = null;
    private AssetManager assetManager = null;
    
    private DynamicSun dynamicSun= null;
    
    public DynamicSky(AssetManager assetManager, ViewPort viewPort, Node rootNode) {
        super("Sky");
        this.assetManager = assetManager;
        this.viewPort = viewPort;
        
        dynamicSun = new DynamicSun(assetManager, viewPort, rootNode);
        rootNode.attachChild(dynamicSun);
        
        setCullHint(Spatial.CullHint.Never);
    }
    
    public Vector3f getSunDirection(){
        return dynamicSun.getSunDirection();
    }
        
    public void updateTime(){
        dynamicSun.updateTime();
    }
    
}