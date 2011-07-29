package com.idflood.sky.examples;

import com.idflood.sky.DynamicSky;
import com.jme3.app.SimpleApplication;
import com.jme3.light.DirectionalLight;
import com.jme3.light.PointLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Plane;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.queue.RenderQueue.ShadowMode;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Quad;
import com.jme3.shadow.BasicShadowRenderer;
/**
 * test
 * @author normenhansen
 */
public class Main extends SimpleApplication {
    DynamicSky sky = null;
    BasicShadowRenderer bsr = null;
    public static void main(String[] args) {
        Main app = new Main();
        app.start();
    }

    @Override
    public void simpleInitApp() {
        Box b = new Box(Vector3f.ZERO, 1, 1, 1);
        Geometry geom = new Geometry("Box", b);

        Material mat = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        mat.setFloat("Shininess", 12);
        mat.setBoolean("UseMaterialColors", true);
        mat.setColor("Ambient",  ColorRGBA.Gray);
        mat.setColor("Diffuse",  ColorRGBA.White);
        mat.setColor("Specular", ColorRGBA.Gray);
        geom.setMaterial(mat);
        geom.rotate(0.2f, 0.5f, 0.1f);
        geom.setShadowMode(ShadowMode.CastAndReceive);
        rootNode.attachChild(geom);
        
        Quad pl = new Quad(1, 1);
        Geometry plane = new Geometry("plane", pl);
        plane.setMaterial(mat);
        plane.setShadowMode(ShadowMode.Receive);
        plane.rotate(FastMath.DEG_TO_RAD * -90.0f, 0, 0);
        plane.setLocalScale(20f);
        plane.setLocalTranslation(-10f, -1f, 10f);
        rootNode.attachChild(plane);
        
        sky = new DynamicSky(assetManager, viewPort, rootNode);
        rootNode.attachChild(sky);
        
        // http://jmonkeyengine.org/wiki/doku.php/jme3:advanced:light_and_shadow
        // todo: try the other shadow renderer
        
        rootNode.setShadowMode(ShadowMode.Off);
        bsr = new BasicShadowRenderer(assetManager, 1024);
        bsr.setDirection(new Vector3f(-1, -1, -1).normalizeLocal());
        viewPort.addProcessor(bsr);
    }

    @Override
    public void simpleUpdate(float tpf) {
        sky.updateTime();
        bsr.setDirection(sky.getSunDirection().normalizeLocal().mult(-1));
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
}
