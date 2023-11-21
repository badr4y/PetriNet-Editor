package org.pneditor.petrinet.adapters.fourane;

import org.pneditor.petrinet.*;
import org.pneditor.petrinet.models.fourane.Arc;
import org.pneditor.petrinet.models.fourane.NullTransitionException;

import java.util.ArrayList;
import java.util.List;

public class PetriNetAdapter extends PetriNetInterface {

    private final List<ArcAdapter> arcs = new ArrayList<>();
    private final List<PlaceAdapter> places = new ArrayList<>();
    private final List<TransitionAdapter> transitions = new ArrayList<>();


    @Override
    public PlaceAdapter addPlace() {
        PlaceAdapter place = new PlaceAdapter(null);
        places.add(place);
        return place;
    }

    @Override
    public TransitionAdapter addTransition() {
        TransitionAdapter transition = new TransitionAdapter(null);
        transitions.add(transition);
        return transition;
    }

    @Override
    public ArcAdapter addRegularArc(AbstractNode source, AbstractNode destination) throws UnimplementedCaseException {
        ArcAdapter arc = null;
        if (source.isPlace()) {
            if (verifyArcExistence((PlaceAdapter) source, (TransitionAdapter) destination)){
                throw (new UnimplementedCaseException("arc with same destination and source already exists"));
            } else {
                arc = new ArcAdapter(ArcType.Regular,(PlaceAdapter) source, (TransitionAdapter) destination);
                ((TransitionAdapter) destination).getTransition().getEnteringArcs().add(arc.getArc());
                arcs.add(arc);
                return arc;
            }
        } else {
            if (verifyArcExistence((PlaceAdapter) destination, (TransitionAdapter) source)){
                throw (new UnimplementedCaseException("arc with same destination and source already exists"));
            } else {
                arc = new ArcAdapter(ArcType.Regular, (PlaceAdapter) destination, (TransitionAdapter) source);
                ((TransitionAdapter) source).getTransition().getExitingArcs().add(arc.getArc());
                arcs.add(arc);
                return arc;
            }
        }
    }

    public boolean verifyArcExistence(PlaceAdapter place, TransitionAdapter transition) {
        ArcAdapter testArc = new ArcAdapter(ArcType.Regular,place,transition);
        for (ArcAdapter arc : arcs) {
            if (testArc.equals(arc)){
                System.out.println("shouldnt be created");
                return true;
            }
        }
        return false;
    }

    @Override
    public ArcAdapter addInhibitoryArc(AbstractPlace place, AbstractTransition transition) throws UnimplementedCaseException {
        ArcAdapter arc;
        arc = new ArcAdapter(ArcType.Inhibitory, (PlaceAdapter) place, (TransitionAdapter) transition);
        ((TransitionAdapter) transition).getTransition().getEnteringArcs().add(arc.getArc());
        arcs.add(arc);
        return arc;
    }

    @Override
    public ArcAdapter addResetArc(AbstractPlace place, AbstractTransition transition) throws UnimplementedCaseException {
        ArcAdapter arc;
        arc = new ArcAdapter(ArcType.Reset, (PlaceAdapter) place, (TransitionAdapter) transition);
        ((TransitionAdapter) transition).getTransition().getEnteringArcs().add(arc.getArc());
        arcs.add(arc);
        return arc;
    }

    @Override
    public void removePlace(AbstractPlace place) {
        places.remove(place);
    }

    @Override
    public void removeTransition(AbstractTransition transition) {
        transitions.remove(transition);
    }

    @Override
    public void removeArc(AbstractArc arc) {
        arcs.remove(arc);
    }

    @Override
    public boolean isEnabled(AbstractTransition transition) {
        boolean firable = true;
        List<Arc> arcs = ((TransitionAdapter) transition).getTransition().getEnteringArcs();
        for (Arc enteringArc : arcs) {
            if (!enteringArc.firable()) {
                firable = false;
                break;
            }
        }
        return firable;
    }

    @Override
    public void fire(AbstractTransition transition) throws ResetArcMultiplicityException, NullTransitionException {
        ((TransitionAdapter) transition).getTransition().activate();
    }
}
