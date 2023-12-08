package org.pneditor.petrinet.adapters.fourane;

import org.pneditor.petrinet.AbstractArc;
import org.pneditor.petrinet.AbstractNode;
import org.pneditor.petrinet.ResetArcMultiplicityException;
import org.pneditor.petrinet.models.fourane.Arc;
import org.pneditor.petrinet.models.fourane.ArcVideur;
import org.pneditor.petrinet.models.fourane.ArcZero;

/**
 * Using the Adapter design pattern, this class is a linking class between the implementation of Arc, ArcVideur and ArcZero in {@link org.pneditor.petrinet.models.fourane.Arc}, {@link org.pneditor.petrinet.models.fourane.ArcVideur} and {@link org.pneditor.petrinet.models.fourane.ArcZero} and the Editor interface
 */
public class ArcAdapter extends AbstractArc {
	
	private Arc arc;
	private final PlaceAdapter place;
	private final TransitionAdapter transition;
	private final ArcType arcType;
	
	/**
	 * This boolean attribute refers to the direction of the arc, it is true if the source of the arc is a Place and false if it's a Transition
	 */
	private boolean direction;
	/**
	 * @return the Place object associated to this ArcAdapter instance
	 */
	public PlaceAdapter getPlace() {
		return place;
	}
	
	/**
	 * @return the Transition object associated to this ArcAdapter instance
	 */
	public TransitionAdapter getTransition() {
		return transition;
	}
	
	/**
	 * sets the direction of the Arc
	 * @param direction instructed by the Editor Interface
	 */
	public void setDirection(boolean direction) {
		this.direction = direction;
	}
	
	/**
	 * This is the default constructor for the ArcAdapter, it creates an instance of an Arc, an ArcVideur or an ArcZero and sets it as its attribute
	 * @param type defines the Type of the Arc created whether it's an Arc, an ArcVideur or an ArcZero
	 * @param place The Place linked to the Arc as either a source or a destination
	 * @param transition The Transition linked to the Arc as either a source or a destination
	 */
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
	
	/**
	 * @return the Arc object associated to this ArcAdapter instance
	 */
	public Arc getArc() {
		return arc;
	}
	
	/**
	 * @return either the place or the transition that constitutes the ArcAdapter's source using its direction attribute
	 */
	@Override
	public AbstractNode getSource() {
		if(direction) {
			return place;
		} else {
			return transition;
		}
	}
	
	/**
	 * removes the Arc associated to this ArcAdapter from the lists of its associated Transition
	 */
	public void unlinkArc() {
		if(this.isSourceAPlace()) {
			arc.getTransition().getEnteringArcs().remove(arc);
		} else {
			arc.getTransition().getExitingArcs().remove(arc);
		}
	}
	
	/**
	 * @return either the place or the transition that constitutes the ArcAdapter's destination using its direction attribute
	 */
	@Override
	public AbstractNode getDestination() {
		if(direction) {
			return transition;
		} else {
			return place;
		}
	}
	
	/**
	 * @return a boolean indicating whether the arc type is reset (Videur)
	 */
	@Override
	public boolean isReset() {
		return arcType == ArcType.Reset;
	}
	
	/**
	 * @return a boolean indicating whether the arc type is regular
	 */
	@Override
	public boolean isRegular() {
		return arcType == ArcType.Regular;
	}
	
	/**
	 * @return a boolean indicating whether the arc type is inhibitory (Zero)
	 */
	@Override
	public boolean isInhibitory() {
		return arcType == ArcType.Inhibitory;
	}
	
	/**
	 * @return the multiplicity of the arc, throwing a ResetArcMultiplicityException if it's a reset arc
	 * @throws ResetArcMultiplicityException If the arc type is reset
	 */
	@Override
	public int getMultiplicity() throws ResetArcMultiplicityException {
		return arc.getWeight();
	}
	
	/**
	 * Sets the multiplicity of the arc.
	 * @param multiplicity The new multiplicity to set
	 */
	@Override
	public void setMultiplicity(int multiplicity) {
		arc.changeWeight(multiplicity);
	}
}
