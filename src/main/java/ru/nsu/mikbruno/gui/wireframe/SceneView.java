package ru.nsu.mikbruno.gui.wireframe;

import ru.nsu.mikbruno.interaction.observer.DoubleObservable;
import ru.nsu.mikbruno.interaction.observer.IntObservable;
import ru.nsu.mikbruno.interaction.observer.Observables;
import ru.nsu.mikbruno.interaction.observer.Observer;
import ru.nsu.mikbruno.util.Pair;
import ru.nsu.mikbruno.wireframe.scenes.Scene;
import ru.nsu.mikbruno.wireframe.scenes.SceneNormalizer;
import ru.nsu.mikbruno.wireframe.scenes.SceneProducer;
import ru.nsu.mikbruno.wireframe.chains.ArrayListChainObservable;
import ru.nsu.mikbruno.wireframe.chains.Chain;
import ru.nsu.mikbruno.wireframe.chains.PointUV;
import ru.nsu.mikbruno.wireframe.homogenous.Axis;
import ru.nsu.mikbruno.wireframe.homogenous.Point3D;
import ru.nsu.mikbruno.wireframe.homogenous.RotateOperator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.Iterator;

public class SceneView extends JPanel {
    private final double SENSITIVITY = 0.01;
    private Scene scene;
    private Scene rotatedScene;
    private final DoubleObservable pitch = new DoubleObservable(0);
    private final DoubleObservable yaw = new DoubleObservable(0);
    private void normPitchYaw() {
        if (pitch.getValue() > Math.PI / 2) {
            pitch.setValue(Math.PI / 2);
        }
        else if (yaw.getValue() < -2*Math.PI || yaw.getValue() > 2*Math.PI) {
            yaw.setValue(0);
        }
    }

    public SceneView(Observables observables) {
        super();
        setBackground(Color.WHITE);
        setDoubleBuffered(true);

        ArrayListChainObservable<PointUV> chain = observables.getValue("chain");
        IntObservable splineSegments = observables.getValue("spline_segments");
        IntObservable generatrixCount = observables.getValue("generatricies");
        IntObservable circleSegments = observables.getValue("circle_segments");
        Observer sceneRotationObserver = o -> {
            normPitchYaw();
            rotatedScene = new RotateOperator(yaw.getValue(), Axis.Y).apply(scene);
            rotatedScene = new RotateOperator(pitch.getValue(), Axis.X).apply(rotatedScene);
            repaint();
        };
        Observer sceneMorphObserver = o -> {
            scene = SceneProducer.produce(
                    chain, splineSegments.getValue(),
                    generatrixCount.getValue(), circleSegments.getValue()
            );
            scene = SceneNormalizer.normalize(scene);
            sceneRotationObserver.update(o);
        };
        sceneMorphObserver.update(null);
        chain.addObserver(sceneMorphObserver);
        splineSegments.addObserver(sceneMorphObserver);
        generatrixCount.addObserver(sceneMorphObserver);
        circleSegments.addObserver(sceneMorphObserver);

        pitch.addObserver(sceneRotationObserver);
        yaw.addObserver(sceneRotationObserver);

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
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            super.mouseDragged(e);
            if (!moving) return;
            yaw.setValue(yaw.getValue() + SENSITIVITY*(e.getX() - oldX));
            pitch.setValue(pitch.getValue() + SENSITIVITY*(-e.getY() + oldY));
            oldX = e.getX();
            oldY = e.getY();
        }
    };

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        for (Chain<Point3D> chain : rotatedScene.getChains()) {
            for (Iterator<Pair<Point3D>> it = chain.getEdgesIterator(); it.hasNext(); ) {
                Pair<Point3D> pair = it.next();
                Point3D first = pair.first();
                Point3D second = pair.second();
                int w2 = getWidth() / 2;
                int h2 = getHeight() / 2;
                g.drawLine(
                        (int) Math.round(first.getX() * h2 + w2), (int) Math.round(first.getY() * h2 + h2),
                        (int) Math.round(second.getX() * h2 + w2), (int) Math.round(second.getY() * h2 + h2)
                );
            }
        }
    }
}
