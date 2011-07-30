/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package com.idflood.sky;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.material.RenderState.BlendMode;
import com.jme3.renderer.queue.RenderQueue.Bucket;
import com.jme3.scene.Geometry;
import com.jme3.scene.control.BillboardControl;
import com.jme3.scene.shape.Quad;

/**
 *
 * @author davidmignot
 */
public class SkyBillboardItem extends Geometry{
    private static BillboardControl billBoadControl = new BillboardControl();
    
    private Quad quad;
    
    public SkyBillboardItem(AssetManager assetManager, String name, String texture, Float scale){
        super(name);
        
        quad = new Quad(scale, scale);
        
        Material sunDiscMat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        sunDiscMat.setTexture("ColorMap", assetManager.loadTexture(texture));
        sunDiscMat.getAdditionalRenderState().setBlendMode(BlendMode.Alpha);
        
        setQueueBucket(Bucket.Transparent);
        setCullHint(CullHint.Never);
        
        setMesh(quad);
        setMaterial(sunDiscMat);
        
        addControl(billBoadControl);
    }
}
