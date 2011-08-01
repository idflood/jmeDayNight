package com.idflood.sky.utils;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.material.RenderState.BlendMode;
import com.jme3.math.Quaternion;
import com.jme3.renderer.queue.RenderQueue.Bucket;
import com.jme3.scene.Geometry;
import com.jme3.scene.control.BillboardControl;
import com.jme3.scene.shape.Quad;

public class SkyBillboardItem extends Geometry{
    private BillboardControl billBoadControl = new BillboardControl();
    private Quad quad;
    Material sunDiscMat;
    
    public SkyBillboardItem(AssetManager assetManager, String name, String texture, Float scale){
        super(name);
        
        quad = new Quad(scale, scale);
        
        sunDiscMat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        sunDiscMat.setTexture("ColorMap", assetManager.loadTexture(texture));
        sunDiscMat.getAdditionalRenderState().setBlendMode(BlendMode.Alpha);
        sunDiscMat.getAdditionalRenderState().setDepthWrite(false);
        
        setQueueBucket(Bucket.Transparent);
        setCullHint(CullHint.Never);
        
        setMesh(quad);
        setMaterial(sunDiscMat);
        
        addControl(billBoadControl);
    }
    
    public Material getMaterial(){
        return sunDiscMat;
    }
    
    public void setRotation(Float rotation){
        setRotationEnabled();
        this.rotate(new Quaternion().fromAngles(0, 0, rotation));
    }
    
    public void removeBillboardController(){
        removeControl(billBoadControl);
    }
    
    protected void setRotationEnabled(){
        billBoadControl.setAlignment(BillboardControl.Alignment.AxialZ);
    }
    
    protected void setRotationDisabled(){
        billBoadControl.setAlignment(BillboardControl.Alignment.Screen);
    }
}
