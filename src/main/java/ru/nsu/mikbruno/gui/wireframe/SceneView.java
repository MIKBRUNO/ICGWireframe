package ru.nsu.mikbruno.gui.wireframe;

import ru.nsu.mikbruno.interaction.observer.*;
import ru.nsu.mikbruno.util.Pair;
import ru.nsu.mikbruno.wireframe.homogenous.*;
import ru.nsu.mikbruno.wireframe.scenes.BoundingBox;
import ru.nsu.mikbruno.wireframe.scenes.Scene;
import ru.nsu.mikbruno.wireframe.chains.Chain;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.Iterator;

public class SceneView extends JPanel {
    private final double SPATIAL_SENSITIVITY = 0.01;
    private final double ZOOM_SENSITIVITY = 0.5;
    private final DoubleObservable pitch;
    private final DoubleObservable yaw;
    private final DoubleObservable zoom;
    private Scene scene;
    private Operator operator;
    private void normPitchYaw() {
        if (pitch.getValue() > 2*Math.PI) {
            pitch.setValue(pitch.getValue() - 2*Math.PI);
        }
        else if (pitch.getValue() < -2*Math.PI) {
            pitch.setValue(pitch.getValue() + 2*Math.PI);
        }
        if (yaw.getValue() > 2*Math.PI) {
            yaw.setValue(yaw.getValue() - 2*Math.PI);
        }
        else if (yaw.getValue() < -2*Math.PI) {
            yaw.setValue(yaw.getValue() + 2*Math.PI);
        }
    }

    public SceneView(Observables observables) {
        super();
        setBackground(Color.WHITE);
        setDoubleBuffered(true);

        pitch = observables.getValue("pitch");
        yaw = observables.getValue("yaw");
        zoom = observables.getValue("zoom");
        ValueObservable<Operator> operatorObservable = observables.getValue("operator");
        ValueObservable<Scene> sceneObservable = observables.getValue("scene");
        scene = sceneObservable.getValue();
        operator = operatorObservable.getValue();

        operatorObservable.addObserver(o -> {
            operator = operatorObservable.getValue();
            repaint();
        });
        sceneObservable.addObserver(o -> {
            scene = sceneObservable.getValue();
            repaint();
        });

        addMouseListener(mouseAdapter);
        addMouseMotionListener(mouseAdapter);
        addMouseWheelListener(mouseAdapter);
    }

    private final MouseAdapter mouseAdapter = new MouseAdapter() {
        private int oldX, oldY;
        private boolean moving = false;

        @Override
        public void mousePressed(MouseEvent e) {
            super.mousePressed(e);
            oldX = e.getX();
            oldY = e.getY();
            moving = true;
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            super.mouseReleased(e);
            moving = false;
        }

        @Override
        public void mouseWheelMoved(MouseWheelEvent e) {
            super.mouseWheelMoved(e);
            final double MIN = 0.5;
            final double MAX = 9;
            double zoomK = zoom.getValue();
            zoomK -= ZOOM_SENSITIVITY * e.getWheelRotation();
            if (zoomK > MAX) zoomK = MAX;
            if (zoomK < MIN) zoomK = MIN;
            zoom.setValue(zoomK);
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            super.mouseDragged(e);
            if (!moving) return;
            yaw.setValue(yaw.getValue() - SPATIAL_SENSITIVITY *(e.getX() - oldX));
            pitch.setValue(pitch.getValue() + SPATIAL_SENSITIVITY *(e.getY() - oldY));
            normPitchYaw();
            oldX = e.getX();
            oldY = e.getY();
        }
    };

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Scene renderScene = operator.apply(scene);
        BoundingBox box = new BoundingBox(renderScene);
        for (Chain<Point3D> chain : renderScene.getChains()) {
            for (Iterator<Pair<Point3D>> it = chain.getEdgesIterator(); it.hasNext(); ) {
                Pair<Point3D> pair = it.next();
                Point3D first = pair.first();
                Point3D second = pair.second();
                double depth = (first.getZ() + second.getZ())/2;
                depth = (depth - box.getMinZ()) / box.getDepth();
                depth = Math.pow(depth, .9);
                int color = (int) (depth * 245);
                int w2 = getWidth() / 2;
                int h2 = getHeight() / 2;
                int scale = getHeight() * 2;
                g.setColor(new Color(color, color, color));
                g.drawLine(
                        (int) Math.round(first.getX() * scale + w2), (int) Math.round(first.getY() * scale + h2),
                        (int) Math.round(second.getX() * scale + w2), (int) Math.round(second.getY() * scale + h2)
                );
            }
        }

        Point3D x = new Point3DImpl(-1,0,0);
        Point3D y = new Point3DImpl(0,-1,0);
        Point3D z = new Point3DImpl(0,0,-1);
        RotateOperator yawOp = new RotateOperator(yaw.getValue(), Axis.Y);
        Operator rotation = new RotateOperator(pitch.getValue(), Axis.X).apply(yawOp);
        x = rotation.apply(x);
        y = rotation.apply(y);
        z = rotation.apply(z);
        int len = 100;
        g.setColor(Color.RED);
        g.drawLine(len / 2, len / 2, (int)(len * x.getX() / 4) + len / 2, (int)(len * x.getY() / 4) + len / 2);
        g.setColor(Color.GREEN);
        g.drawLine(len / 2, len / 2, (int)(len * y.getX() / 4) + len / 2, (int)(len * y.getY() / 4) + len / 2);
        g.setColor(Color.BLUE);
        g.drawLine(len / 2, len / 2, (int)(len * z.getX() / 4) + len / 2, (int)(len * z.getY() / 4) + len / 2);
    }
}
