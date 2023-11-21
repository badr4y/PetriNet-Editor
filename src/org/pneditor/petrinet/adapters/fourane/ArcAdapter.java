package org.pneditor.petrinet.adapters.fourane;

import org.pneditor.petrinet.AbstractArc;
import org.pneditor.petrinet.AbstractNode;
import org.pneditor.petrinet.ResetArcMultiplicityException;
import org.pneditor.petrinet.models.fourane.Arc;
import org.pneditor.petrinet.models.fourane.ArcVideur;
import org.pneditor.petrinet.models.fourane.ArcZero;

public class ArcAdapter extends AbstractArc {

    private Arc arc;
    private final PlaceAdapter place;
    private final TransitionAdapter transition;
    private final ArcType arcType;

    public PlaceAdapter getPlace() {
        return place;
    }

    public TransitionAdapter getTransition() {
        return transition;
    }

    public ArcAdapter(ArcType type, PlaceAdapter place, TransitionAdapter transition) {
        switch (type) {
            case Regular -> this.arc = new Arc(1,place.getPlace(),transition.getTransition());
            case Reset -> this.arc = new ArcVideur(place.getPlace(),transition.getTransition());
            case Inhibitory -> this.arc = new ArcZero(place.getPlace(),transition.getTransition());
        }
        this.place = place;
        this.transition = transition;
        this.arcType= type;
    }

    public Arc getArc() {
        return arc;
    }

    @Override
    public AbstractNode getSource() {
        if (arc.getTransition().getEnteringArcs().contains(arc)){
            return this.place;
        } else {
            return this.transition;
        }
    }

    @Override
    public AbstractNode getDestination() {
        if (arc.getTransition().getExitingArcs().contains(arc)){
            return this.place;
        } else {
            return this.transition;
        }
    }

    @Override
    public boolean equals(Object o){
        ArcAdapter arc = (ArcAdapter) o;
        if (this.getSource() == arc.getSource() && this.getDestination() == arc.getDestination()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean isReset() {
        return arcType == ArcType.Reset;
    }

    @Override
    public boolean isRegular() {
        return arcType == ArcType.Regular;
    }

    @Override
    public boolean isInhibitory() {
        return arcType == ArcType.Inhibitory;
    }

    @Override
    public int getMultiplicity() throws ResetArcMultiplicityException {
        return arc.getWeight();
    }

    @Override
    public void setMultiplicity(int multiplicity) {
        arc.changeWeight(multiplicity);
    }
}
