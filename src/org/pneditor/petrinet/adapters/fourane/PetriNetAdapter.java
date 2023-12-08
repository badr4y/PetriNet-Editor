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
		if(source.isPlace()) {
			arc = new ArcAdapter(ArcType.Regular, (PlaceAdapter) source, (TransitionAdapter) destination);
			((TransitionAdapter) destination).getTransition().getEnteringArcs().add(arc.getArc());
			arc.setDirection(true);
		} else {
			arc = new ArcAdapter(ArcType.Regular, (PlaceAdapter) destination, (TransitionAdapter) source);
			((TransitionAdapter) source).getTransition().getExitingArcs().add(arc.getArc());
			arc.setDirection(false);
		}
		arcs.add(arc);
		return arc;
	}
	
	@Override
	public ArcAdapter addInhibitoryArc(AbstractPlace place, AbstractTransition transition) throws UnimplementedCaseException {
		ArcAdapter arc;
		arc = new ArcAdapter(ArcType.Inhibitory, (PlaceAdapter) place, (TransitionAdapter) transition);
		((TransitionAdapter) transition).getTransition().getEnteringArcs().add(arc.getArc());
		arc.setDirection(true);
		arcs.add(arc);
		return arc;
	}
	
	@Override
	public ArcAdapter addResetArc(AbstractPlace place, AbstractTransition transition) throws UnimplementedCaseException {
		ArcAdapter arc;
		arc = new ArcAdapter(ArcType.Reset, (PlaceAdapter) place, (TransitionAdapter) transition);
		((TransitionAdapter) transition).getTransition().getEnteringArcs().add(arc.getArc());
		arc.setDirection(true);
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
		((ArcAdapter) arc).unlinkArc();
		this.arcs.remove(arc);
	}
	
	@Override
	public boolean isEnabled(AbstractTransition transition) {
		boolean firable = true;
		List<Arc> arcs = ((TransitionAdapter) transition).getTransition().getEnteringArcs();
		for(Arc enteringArc : arcs) {
//            Class<?> objectClass = enteringArc.getClass();
//            System.out.println("Class of the object: " + objectClass.getName());
			if(!enteringArc.firable()) {
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
