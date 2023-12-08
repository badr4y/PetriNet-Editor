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
	private boolean direction; //true if source is a place
	
	public PlaceAdapter getPlace() {
		return place;
	}
	
	public TransitionAdapter getTransition() {
		return transition;
	}
	
	public void setDirection(boolean direction) {
		this.direction = direction;
	}
	
	public ArcAdapter(ArcType type, PlaceAdapter place, TransitionAdapter transition) {
		switch(type) {
			case Regular:
				this.arc = new Arc(1, place.getPlace(), transition.getTransition());
				break;
			case Reset:
				this.arc = new ArcVideur(place.getPlace(), transition.getTransition());
				break;
			case Inhibitory:
				this.arc = new ArcZero(place.getPlace(), transition.getTransition());
				break;
		}
		this.place = place;
		this.transition = transition;
		this.arcType = type;
	}
	
	public Arc getArc() {
		return arc;
	}
	
	@Override
	public AbstractNode getSource() {
		if(direction) {
			return place;
		} else {
			return transition;
		}
	}
	
	public void unlinkArc() {
		if(this.isSourceAPlace()) {
			arc.getTransition().getEnteringArcs().remove(arc);
		} else {
			arc.getTransition().getExitingArcs().remove(arc);
		}
	}
	
	@Override
	public AbstractNode getDestination() {
		if(direction) {
			return transition;
		} else {
			return place;
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
