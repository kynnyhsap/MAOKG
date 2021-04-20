# Лаброторна робота №5
## *Пашинник Андрій КП-81*

Варіант: **№16**



Код программи:
```Java
// Helicopter.java
import com.sun.j3d.loaders.Scene;
import com.sun.j3d.loaders.objectfile.ObjectFile;
import com.sun.j3d.utils.image.TextureLoader;
import com.sun.j3d.utils.universe.SimpleUniverse;
import com.sun.j3d.utils.universe.ViewingPlatform;

import javax.media.j3d.*;
import javax.swing.*;
import javax.vecmath.*;
import java.awt.*;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

public class Helicopter extends JFrame {

    private Canvas3D canvas;
    private SimpleUniverse universe;
    private final BranchGroup root;

    private final TransformGroup air = new TransformGroup();
    private final TransformGroup helicopter;

    private Map<String, Shape3D> shapeMap;

    public Helicopter() throws IOException {

        configureWindow();
        configureCanvas();
        configureUniverse();

        root = new BranchGroup();
        root.setCapability(BranchGroup.ALLOW_CHILDREN_EXTEND);


        addImageBackground("resource/image/war.jpg");
        addLightToUniverse();

        ChangeViewAngle();

        helicopter = getHelicopterGroup();
        air.addChild(helicopter);

        root.addChild(air);

        addAppearance();

        HelicopterAnimation helicopterAnimation = new HelicopterAnimation(this);
        canvas.addKeyListener(helicopterAnimation);

        root.compile();
        universe.addBranchGraph(root);
    }

    public static Scene getSceneFromFile(String location) throws IOException {
        ObjectFile file = new ObjectFile(ObjectFile.RESIZE);
        file.setFlags(ObjectFile.RESIZE | ObjectFile.TRIANGULATE | ObjectFile.STRIPIFY);
        return file.load(new FileReader(location));
    }

    public static void main(String[] args) {
        try {
            Helicopter window = new Helicopter();
            window.setVisible(true);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void configureWindow() {
        setTitle("Lab5");
        setSize(1920, 1050);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void configureCanvas() {
        canvas = new Canvas3D(SimpleUniverse.getPreferredConfiguration());
        canvas.setDoubleBufferEnable(true);
        canvas.setFocusable(true);
        add(canvas, BorderLayout.CENTER);
    }

    private void configureUniverse() {
        universe = new SimpleUniverse(canvas);
        universe.getViewingPlatform().setNominalViewingTransform();
    }

    private void addImageBackground(String imagePath) {
        TextureLoader t = new TextureLoader(imagePath, canvas);
        Background background = new Background(t.getImage());
        background.setImageScaleMode(Background.SCALE_FIT_ALL);
        BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 100.0);
        background.setApplicationBounds(bounds);
        root.addChild(background);
    }

    private void addLightToUniverse() {
        BoundingSphere bounds = new BoundingSphere();
        bounds.setRadius(1000);

        DirectionalLight directionalLight = new DirectionalLight(
                new Color3f(new Color(255, 255, 255)),
                new Vector3f(0, -0.5f, -0.5f));
        directionalLight.setInfluencingBounds(bounds);

        AmbientLight ambientLight = new AmbientLight(
                new Color3f(new Color(255, 255, 245)));
        ambientLight.setInfluencingBounds(bounds);

        root.addChild(directionalLight);
        root.addChild(ambientLight);
    }

    private TransformGroup getHelicopterGroup() throws IOException {
        Transform3D transform3D = new Transform3D();

        transform3D.setTranslation(new Vector3d(1, 0, 0));

        TransformGroup group = getModelGroup("resource/uh60.obj");
        group.setTransform(transform3D);

        return group;
    }

    private TransformGroup getModelGroup(String path) throws IOException {
        Scene scene = getSceneFromFile(path);
        shapeMap = scene.getNamedObjects();

        printModelElementsList(shapeMap);

        TransformGroup group = new TransformGroup();

        for (String shapeName : shapeMap.keySet()) {
            Shape3D shape = shapeMap.get(shapeName);

            scene.getSceneGroup().removeChild(shape);
            group.addChild(shape);
        }

        group.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

        return group;
    }

    private void printModelElementsList(Map<String, Shape3D> shapeMap) {
        for (String name : shapeMap.keySet()) {
            System.out.printf("Name: %s\n", name);
        }
    }

    private void addAppearance() {

        Appearance windows = new Appearance();
        windows.setMaterial(getMaterial(
                Color.BLACK,
                new Color(0x01234a)));
        shapeMap.get("window_l1").setAppearance(windows);
        shapeMap.get("window_l2").setAppearance(windows);
        shapeMap.get("window_r1").setAppearance(windows);
        shapeMap.get("window_r2").setAppearance(windows);

        Appearance rotors = new Appearance();
        rotors.setMaterial(getMaterial(
                Color.BLACK,
                Color.BLACK));
        shapeMap.get("main_rotor").setAppearance(rotors);
        shapeMap.get("rear_rotor").setAppearance(rotors);

        Appearance tyres = new Appearance();
        tyres.setMaterial(getMaterial(
                Color.BLACK,
                Color.BLACK));
        shapeMap.get("tyre_r").setAppearance(tyres);
        shapeMap.get("tyre_l").setAppearance(tyres);
        shapeMap.get("rear_tyre").setAppearance(tyres);

        Appearance doors = new Appearance();
        doors.setMaterial(getMaterial(
                Color.BLACK,
                Color.DARK_GRAY));
        shapeMap.get("door_l").setAppearance(doors);
        shapeMap.get("cabindoor_l").setAppearance(doors);
        shapeMap.get("door_r").setAppearance(doors);
        shapeMap.get("cabindoor_r").setAppearance(doors);

        Appearance body = new Appearance();
        body.setMaterial(getMaterial(
                Color.BLACK,
                Color.DARK_GRAY));
        shapeMap.get("fuselage").setAppearance(body);
        shapeMap.get("elevator").setAppearance(body);
    }

    Material getMaterial(
            Color emissiveColor,
            Color defaultColor) {
        Material material = new Material();
        material.setEmissiveColor(new Color3f(emissiveColor));
        material.setAmbientColor(new Color3f(defaultColor));
        material.setDiffuseColor(new Color3f(defaultColor));
        material.setSpecularColor(new Color3f(defaultColor));
        material.setShininess(64);
        material.setLightingEnable(true);
        return material;
    }

    private void ChangeViewAngle() {
        ViewingPlatform vp = universe.getViewingPlatform();
        TransformGroup vpGroup = vp.getMultiTransformGroup().getTransformGroup(0);
        Transform3D vpTranslation = new Transform3D();
        vpTranslation.setTranslation(new Vector3f(0, 0, 6));
        vpGroup.setTransform(vpTranslation);

    }

    public TransformGroup getHelicopterTransformGroup() {
        return helicopter;
    }

}

```


```Java
// HelicopterAnimation.java
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.swing.*;
import javax.vecmath.Vector3f;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class HelicopterAnimation extends KeyAdapter implements ActionListener {

    private static final float DELTA_DISTANCE = 0.02f;
    private static final float DELTA_ANGLE = 0.05f;


    private final Helicopter helicopter;
    private final TransformGroup helicopteerTransformGroup;
    private final Transform3D transform3D = new Transform3D();

    private float xLoc = 0;
    private float yLoc = 0;

    private float xAngle = 0;
    private float yAngle = 0;
    private float zAngle = 0;


    private boolean resetXRotation = false;
    private boolean resetYRotation = false;
    private boolean resetZRotation = false;

    private boolean isRotatedPosY = false;
    private boolean isRotatedNegY = false;


    private boolean isPressedW = false;
    private boolean isPressedS = false;
    private boolean isPressedA = false;
    private boolean isPressedD = false;
    private boolean isPressedVKRight = false;
    private boolean isPressedVKLeft = false;
    private boolean isPressedVKUp = false;
    private boolean isPressedVKDown = false;

    public HelicopterAnimation(Helicopter helicopter) {
        this.helicopter = helicopter;

        this.helicopteerTransformGroup = helicopter.getHelicopterTransformGroup();
        this.helicopteerTransformGroup.getTransform(this.transform3D);

        Timer timer = new Timer(20, this);
        timer.start();
    }

    private void Move() {
        if (isPressedW) {
            yLoc += DELTA_DISTANCE;
        }

        if (isPressedS) {
            yLoc -= DELTA_DISTANCE;
        }

        if (isPressedA) {
            xLoc -= DELTA_DISTANCE;
        }

        if (isPressedD) {
            xLoc += DELTA_DISTANCE;
        }

        transform3D.setTranslation(new Vector3f(xLoc, yLoc, 0));

        if (isPressedVKRight) {
            Transform3D rotation = new Transform3D();
            rotation.rotZ(DELTA_ANGLE);
            zAngle += DELTA_ANGLE;
            transform3D.mul(rotation);

        }

        if (isPressedVKLeft) {
            Transform3D rotation = new Transform3D();
            rotation.rotZ(-DELTA_ANGLE);
            zAngle -= DELTA_ANGLE;
            transform3D.mul(rotation);
        }

        if (isPressedVKUp) {
            Transform3D rotation = new Transform3D();
            rotation.rotX(-DELTA_ANGLE);
            xAngle -= DELTA_ANGLE;
            transform3D.mul(rotation);
        }

        if (isPressedVKDown) {
            Transform3D rotation = new Transform3D();
            rotation.rotX(DELTA_ANGLE);
            xAngle += DELTA_ANGLE;
            transform3D.mul(rotation);
        }

        if (isRotatedPosY) {
            Transform3D rotation = new Transform3D();
            rotation.rotY(degree(20));
            transform3D.mul(rotation);

            yAngle += degree(20);

            isRotatedPosY = false;
        }

        if (isRotatedNegY) {
            Transform3D rotation = new Transform3D();
            rotation.rotY(degree(-20));
            transform3D.mul(rotation);

            yAngle += degree(-20);

            isRotatedNegY = false;
        }

        if (resetYRotation) {
            Transform3D rotation = new Transform3D();
            rotation.rotY(-yAngle);
            transform3D.mul(rotation);

            resetYRotation = false;
            yAngle = 0;
        }

        if (resetZRotation) {
            Transform3D rotation = new Transform3D();
            rotation.rotZ(-zAngle);
            transform3D.mul(rotation);

            resetZRotation = false;
            zAngle = 0;
        }

        if (resetXRotation) {
            Transform3D rotation = new Transform3D();
            rotation.rotX(-xAngle);
            transform3D.mul(rotation);

            resetXRotation = false;
            xAngle = 0;
        }

        helicopteerTransformGroup.setTransform(transform3D);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Move();
    }

    @Override
    public void keyPressed(KeyEvent ev) {
        switch (ev.getKeyCode()) {
            case 87: // W
                isPressedW = true;
                break;
            case 83: // S
                isPressedS = true;
                break;
            case 65: // A
                if (!isPressedA) {
                    isPressedA = true;
                    isRotatedNegY = true;
                }
                break;
            case 68: // D
                if (!isPressedD) {
                    isPressedD = true;
                    isRotatedPosY = true;
                }
                break;
            case KeyEvent.VK_LEFT:
                isPressedVKLeft = true;
                break;
            case KeyEvent.VK_RIGHT:
                isPressedVKRight = true;
                break;

            case KeyEvent.VK_UP:
                isPressedVKUp = true;
                break;
            case KeyEvent.VK_DOWN:
                isPressedVKDown = true;
                break;
            case 48: // 0
                resetXRotation = true;
                resetYRotation = true;
                resetZRotation = true;
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent ev) {
        switch (ev.getKeyCode()) {
            case 87: // W
                isPressedW = false;
                break;
            case 83: // S
                isPressedS = false;
                break;
            case 65: // A
                isPressedA = false;
                resetYRotation = true;
                break;
            case 68: // D
                isPressedD = false;
                resetYRotation = true;
                break;
            case KeyEvent.VK_RIGHT:
                isPressedVKRight = false;
                break;
            case KeyEvent.VK_LEFT:
                isPressedVKLeft = false;
                break;
            case KeyEvent.VK_UP:
                isPressedVKUp = false;
                break;
            case KeyEvent.VK_DOWN:
                isPressedVKDown = false;
                break;
        }
    }

    private float degree(float degrees) {
        return (float) (degrees * Math.PI / 180);
    }
}

```
