package com.idflood.sky.items;

import com.idflood.sky.utils.SkyBillboardItem;
import com.jme3.asset.AssetManager;
import com.jme3.material.RenderState.BlendMode;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.ViewPort;
import com.jme3.renderer.queue.RenderQueue.Bucket;
import com.jme3.scene.Node;

public class DynamicStars extends Node{
    private ViewPort viewPort = null;
    private AssetManager assetManager = null;
    
    private SkyBillboardItem[] stars;
    
    private int stars_count = 200;
    
    public DynamicStars(AssetManager assetManager, ViewPort viewPort, Float scaling){
        this.assetManager = assetManager;
        this.viewPort = viewPort;
        
        stars = new SkyBillboardItem[stars_count];
        for(int i = 0; i < stars_count; i++){
            SkyBillboardItem item = new SkyBillboardItem(assetManager, "star_" + i, "Textures/star.png", 420f);
            stars[i] = item;
            
            //item.setRotation((float) Math.random() * 100f);
            //item.setLocalTranslation(scaling - 50, 0, 0);
            item.setLocalTranslation(getPointOnSphere().mult(scaling - 30f));
            item.getMaterial().getAdditionalRenderState().setBlendMode(BlendMode.Additive);
            item.removeBillboardController();
            item.lookAt(Vector3f.ZERO, Vector3f.UNIT_Y);
            attachChild(item);
            /*Node container = new Node();
            container.setQueueBucket(Bucket.Sky);
            container.setCullHint(CullHint.Never);
            container.attachChild(item);
            container.rotate(new Quaternion().fromAngles(FastMath.RAD_TO_DEG * 360, FastMath.RAD_TO_DEG * 360, FastMath.RAD_TO_DEG * 360));
            //container.setLocalRotation(new Quaternion().fromAngles(FastMath.RAD_TO_DEG * 360, FastMath.RAD_TO_DEG * 360, FastMath.RAD_TO_DEG * 360));
            attachChild(container);*/
        }
        
        setQueueBucket(Bucket.Sky);
        setCullHint(CullHint.Never);
    }
    
    protected Vector3f getPointOnSphere(){
        Float dz = 1f;
        if(Math.random() < 0.5){
            dz = -1f;
        }
        Float theta = (float) Math.random() * 360f;
        Float phi = (float) Math.random() * FastMath.HALF_PI;
        return new Vector3f(
                FastMath.cos(FastMath.sqrt(phi)) * FastMath.cos(theta),
                FastMath.cos(FastMath.sqrt(phi)) * FastMath.sin(theta),
                FastMath.sin(FastMath.sqrt(phi)) * dz
        );
    }
}
