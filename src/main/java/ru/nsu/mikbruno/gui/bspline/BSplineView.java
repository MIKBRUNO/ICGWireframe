package ru.nsu.mikbruno.gui.bspline;

import ru.nsu.mikbruno.interaction.observer.IntObservable;
import ru.nsu.mikbruno.interaction.observer.Observable;
import ru.nsu.mikbruno.interaction.observer.Observables;
import ru.nsu.mikbruno.interaction.observer.Observer;
import ru.nsu.mikbruno.util.Pair;
import ru.nsu.mikbruno.wireframe.*;
import ru.nsu.mikbruno.wireframe.chains.ArrayListChainObservable;
import ru.nsu.mikbruno.wireframe.chains.Chain;
import ru.nsu.mikbruno.wireframe.chains.PointUV;
import ru.nsu.mikbruno.wireframe.chains.PointUVImpl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Iterator;

public class BSplineView extends JPanel {
    private int panelWidth = 0;
    private int panelHeight = 0;
    private final double POINT_AREA_RADIUS = 0.1;
    private final double DEFAULT_UNITS_PER_WIDTH = 10.;
    private PointUVImpl viewportCenter = new PointUVImpl(0, 0);
    private double viewportWidth = 0;
    private double viewportHeight = 0;
    private final double zoomCoefficient = 1.;
    private final Chain<PointUV> chain;
    private final PointUVObservable currentPoint;
    private final Chain<PointUV> spline;

    public BSplineView(Observables observables) {
        super();
        setBackground(Color.BLACK);
        setDoubleBuffered(true);
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                updatePanelSize();
            }
        });
        addMouseMotionListener(mouseListener);
        addMouseWheelListener(mouseListener);
        addMouseListener(mouseListener);

        this.chain = observables.<ArrayListChainObservable<PointUV>>getValue("chain");
        IntObservable splineSegments = observables.getValue("splineSegments");
        spline = BSplineProducer.produce(this.chain, splineSegments.getValue());
        Observer splineObserver = o -> {
            spline.setPoints(BSplineProducer.produce(this.chain, splineSegments.getValue()).getPoints());
            repaint();
        };
        observables.<Observable>getValue("chain").addObserver(splineObserver);
        splineSegments.addObserver(splineObserver);
        currentPoint = observables.<PointUVObservable>getValue("current point");
        currentPoint.addObserver(splineObserver);
    }

    public void normalizeView() {
        viewportCenter.setUV(0, 0);
        viewportWidth = 0;
        viewportHeight = 0;
        updatePanelSize();
        repaint();
    }

    private int convertPosU(double u) {
        return (int) Math.round(
                (u - viewportCenter.getU() + viewportWidth / 2)
                        * (panelWidth / viewportWidth)
        );
    }

    private int convertPosV(double v) {
        return (int) Math.round(
                (v - viewportCenter.getV() + viewportHeight / 2)
                        * (panelHeight / viewportHeight)
        );
    }

    private int convertWidth(double w) {
        return (int) Math.round(w * (panelWidth / viewportWidth));
    }

    private int convertHeight(double h) {
        return (int) Math.round(h * (panelHeight / viewportHeight));
    }

    private double deconvertPosU(int x) {
        return x * (viewportWidth / panelWidth) + viewportCenter.getU() - viewportWidth / 2;
    }

    private double deconvertPosV(int y) {
        return y * (viewportHeight / panelHeight) + viewportCenter.getV() - viewportHeight / 2;
    }

    private double deconvertWidth(int w) {
        return w * (viewportWidth / panelWidth);
    }

    private double deconvertHeight(int h) {
        return h * (viewportHeight / panelHeight);
    }

    private void updatePanelSize() {
        if (viewportWidth == 0) {
            panelWidth = getWidth();
            panelHeight = getHeight();
            viewportWidth = DEFAULT_UNITS_PER_WIDTH;
            viewportHeight = DEFAULT_UNITS_PER_WIDTH * panelHeight / panelWidth;
        }
        int newPanelWidth = getWidth();
        int newPanelHeight = getHeight();
        viewportWidth = deconvertWidth(newPanelWidth);
        viewportHeight = deconvertHeight(newPanelHeight);
        panelWidth = newPanelWidth;
        panelHeight = newPanelHeight;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        updatePanelSize();
        g.setColor(Color.DARK_GRAY);
        int vaxis = convertPosU(0);
        int uaxis = convertPosV(0);
        if (vaxis > 0 && vaxis < panelWidth) {
            g.drawLine(vaxis, 0, vaxis, panelHeight);
            double grid = Math.ceil(deconvertPosV(0));
            while (convertPosV(grid) < panelHeight) {
                g.drawLine(vaxis, convertPosV(grid), vaxis + 3, convertPosV(grid));
                grid += 1;
            }
        }
        if (uaxis > 0 && uaxis < panelHeight) {
            g.drawLine(0, uaxis, panelWidth, uaxis);
            double grid = Math.ceil(deconvertPosU(0));
            while (convertPosU(grid) < panelWidth) {
                g.drawLine(convertPosU(grid), uaxis, convertPosU(grid), uaxis + 3);
                grid += 1;
            }
        }
        g.setColor(Color.LIGHT_GRAY);
        for (Iterator<Pair<PointUV>> it = chain.getEdgesIterator(); it.hasNext(); ) {
            Pair<PointUV> p = it.next();
            if (p.first() == currentPoint.getPoint()) {
                g.setColor(Color.GREEN);
            }
            g.drawOval(
                    convertPosU(p.first().getU() - POINT_AREA_RADIUS),
                    convertPosV(p.first().getV() - POINT_AREA_RADIUS),
                    convertWidth(POINT_AREA_RADIUS*2), convertHeight(POINT_AREA_RADIUS*2)
            );
            g.setColor(Color.LIGHT_GRAY);
            if (p.second() == currentPoint.getPoint()) {
                g.setColor(Color.GREEN);
            }
            g.drawOval(
                    convertPosU(p.second().getU() - POINT_AREA_RADIUS),
                    convertPosV(p.second().getV() - POINT_AREA_RADIUS),
                    convertWidth(POINT_AREA_RADIUS*2), convertHeight(POINT_AREA_RADIUS*2)
            );
            g.setColor(Color.LIGHT_GRAY);
            g.drawLine(
                    convertPosU(p.first().getU()), convertPosV(p.first().getV()),
                    convertPosU(p.second().getU()), convertPosV(p.second().getV())
            );
        }
        for (Iterator<Pair<PointUV>> it = spline.getEdgesIterator(); it.hasNext(); ) {
            Pair<PointUV> p = it.next();
            g.setColor(Color.RED);
            g.drawLine(
                    convertPosU(p.first().getU()), convertPosV(p.first().getV()),
                    convertPosU(p.second().getU()), convertPosV(p.second().getV())
            );
        }
    }

    private final MouseAdapter mouseListener = new MouseAdapter() {
        private int oldX = getX();
        private int oldY = getY();
        private boolean moving = false;

        @Override
        public void mouseClicked(MouseEvent e) {
            super.mouseClicked(e);
            if (e.getButton() == MouseEvent.BUTTON1) {
                chain.addPoint(new PointUVImpl(deconvertPosU(e.getX()), deconvertPosV(e.getY())));

                return;
            }
            if (e.getButton() != MouseEvent.BUTTON3 || chain.getPoints().size() <= 4) return;
            for (Iterator<PointUV> it = chain.getPointsIterator(); it.hasNext(); ) {
                PointUV point = it.next();
                double distU = deconvertPosU(oldX) - point.getU();
                double distV = deconvertPosV(oldY) - point.getV();
                double dist = distU*distU + distV*distV;
                if (dist > POINT_AREA_RADIUS * POINT_AREA_RADIUS) {
                    continue;
                }
                chain.removePoint(point);
                currentPoint.setPoint(null);

                return;
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
            super.mousePressed(e);
            oldX = e.getX();
            oldY = e.getY();
            moving = true;

            for (Iterator<PointUV> it = chain.getPointsIterator(); it.hasNext(); ) {
                PointUV point = it.next();
                double distU = deconvertPosU(oldX) - point.getU();
                double distV = deconvertPosV(oldY) - point.getV();
                double dist = distU*distU + distV*distV;
                if (dist > POINT_AREA_RADIUS * POINT_AREA_RADIUS) {
                    continue;
                }
                currentPoint.setPoint(point);
                return;
            }
            currentPoint.setPoint(null);
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            super.mouseReleased(e);
            moving = false;
        }

        @Override
        public void mouseWheelMoved(MouseWheelEvent e) {
            super.mouseWheelMoved(e);
            double newViewportWidth = viewportWidth + zoomCoefficient * e.getWheelRotation();
            double newViewportHeight = viewportHeight + zoomCoefficient * e.getWheelRotation() * viewportHeight / viewportWidth;
            if (newViewportWidth > panelWidth || newViewportWidth < 1) {
                return;
            }
            double u = viewportCenter.getU();
            int x = e.getX() + (int) Math.round((convertPosU(u) - e.getX()) * (newViewportWidth / viewportWidth));
            double v = viewportCenter.getV();
            int y = e.getY() + (int) Math.round((convertPosV(v) - e.getY()) * (newViewportHeight / viewportHeight));
            viewportCenter = new PointUVImpl(deconvertPosU(x), deconvertPosV(y));
            viewportWidth = newViewportWidth;
            viewportHeight = newViewportHeight;
            repaint();
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            super.mouseMoved(e);
            if (!moving) return;
            if ((e.getModifiersEx() & MouseEvent.BUTTON3_DOWN_MASK) == MouseEvent.BUTTON3_DOWN_MASK) {
                double u = viewportCenter.getU() - ((e.getX() - oldX) * viewportWidth) / panelWidth;
                double v = viewportCenter.getV() - ((e.getY() - oldY) * viewportHeight) / panelHeight;
                viewportCenter = new PointUVImpl(u, v);
                repaint();
            }
            else if ((e.getModifiersEx() & MouseEvent.BUTTON1_DOWN_MASK) == MouseEvent.BUTTON1_DOWN_MASK) {
                currentPoint.setUV(
                        currentPoint.getU() + deconvertWidth(e.getX() - oldX),
                        currentPoint.getV() + deconvertHeight(e.getY() - oldY)
                );
            }
            oldX = e.getX();
            oldY = e.getY();
        }
    };
}
