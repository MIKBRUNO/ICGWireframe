package ru.nsu.mikbruno.wireframe;

import ru.nsu.mikbruno.interaction.observer.IntObservable;
import ru.nsu.mikbruno.interaction.observer.Observables;
import ru.nsu.mikbruno.interaction.observer.Observer;
import ru.nsu.mikbruno.interaction.observer.ValueObservable;
import ru.nsu.mikbruno.wireframe.chains.ArrayListChainObservable;
import ru.nsu.mikbruno.wireframe.chains.PointUV;
import ru.nsu.mikbruno.wireframe.scenes.Scene;
import ru.nsu.mikbruno.wireframe.scenes.SceneProducer;

public class SceneProduction {
    public SceneProduction(Observables observables) {
        ValueObservable<Scene> scene = observables.getValue("scene");
        ArrayListChainObservable<PointUV> chain = observables.getValue("chain");
        IntObservable splineSegments = observables.getValue("spline_segments");
        IntObservable generatrixCount = observables.getValue("generatricies");
        IntObservable circleSegments = observables.getValue("circle_segments");
        Observer sceneMorphObserver = o -> {
            scene.setValue(SceneProducer.produce(
                    chain, splineSegments.getValue(),
                    generatrixCount.getValue(), circleSegments.getValue()
            ));
        };
        sceneMorphObserver.update(null);
        chain.addObserver(sceneMorphObserver);
        splineSegments.addObserver(sceneMorphObserver);
        generatrixCount.addObserver(sceneMorphObserver);
        circleSegments.addObserver(sceneMorphObserver);
    }
}
