package org.pneditor.petrinet.adapters.fourane;

import org.pneditor.petrinet.*;
import org.pneditor.petrinet.models.fourane.Arc;
import org.pneditor.petrinet.models.fourane.NullTransitionException;

import java.util.ArrayList;
import java.util.List;

/**
 * This class serves as an adapter to link the Editor interface with the implementation of
 * a Petri Net using the Adapter design pattern. It adapts the underlying implementation of
 * {@link org.pneditor.petrinet.models.fourane.Arc}, {@link PlaceAdapter}, {@link TransitionAdapter},
 * and other related classes to the Editor interface.
 */
public class PetriNetAdapter extends PetriNetInterface {
	
	private final List<ArcAdapter> arcs = new ArrayList<>();
	private final List<PlaceAdapter> places = new ArrayList<>();
	private final List<TransitionAdapter> transitions = new ArrayList<>();
	
	/**
	 * Adds a new place to the Petri Net and returns the corresponding PlaceAdapter.
	 * @return The newly created PlaceAdapter instance
	 */
	@Override
	public PlaceAdapter addPlace() {
		PlaceAdapter place = new PlaceAdapter(null);
		places.add(place);
		return place;
	}
	
	/**
	 * Adds a new transition to the Petri Net and returns the corresponding TransitionAdapter.
	 * @return The newly created TransitionAdapter instance
	 */
	@Override
	public TransitionAdapter addTransition() {
		TransitionAdapter transition = new TransitionAdapter(null);
		transitions.add(transition);
		return transition;
	}
	
	/**
	 * Adds a regular arc between the given source and destination nodes and returns the corresponding ArcAdapter.
	 * @param source The source node (either a Place or a Transition)
	 * @param destination The destination node (either a Place or a Transition)
	 * @return The newly created ArcAdapter instance
	 * @throws UnimplementedCaseException If an unimplemented case is encountered
	 */
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
	
	/**
	 * Adds an inhibitory arc between the given place and transition and returns the corresponding ArcAdapter.
	 * @param place The Place node
	 * @param transition The Transition node
	 * @return The newly created ArcAdapter instance
	 * @throws UnimplementedCaseException If an unimplemented case is encountered
	 */
	@Override
	public ArcAdapter addInhibitoryArc(AbstractPlace place, AbstractTransition transition) throws UnimplementedCaseException {
		ArcAdapter arc;
		arc = new ArcAdapter(ArcType.Inhibitory, (PlaceAdapter) place, (TransitionAdapter) transition);
		((TransitionAdapter) transition).getTransition().getEnteringArcs().add(arc.getArc());
		arc.setDirection(true);
		arcs.add(arc);
		return arc;
	}
	
	/**
	 * Adds a reset arc between the given place and transition and returns the corresponding ArcAdapter.
	 * @param place The Place node
	 * @param transition The Transition node
	 * @return The newly created ArcAdapter instance
	 * @throws UnimplementedCaseException If an unimplemented case is encountered
	 */
	@Override
	public ArcAdapter addResetArc(AbstractPlace place, AbstractTransition transition) throws UnimplementedCaseException {
		ArcAdapter arc;
		arc = new ArcAdapter(ArcType.Reset, (PlaceAdapter) place, (TransitionAdapter) transition);
		((TransitionAdapter) transition).getTransition().getEnteringArcs().add(arc.getArc());
		arc.setDirection(true);
		arcs.add(arc);
		return arc;
	}
	
	/**
	 * Removes the specified place from the Petri Net.
	 * @param place The Place to be removed
	 */
	@Override
	public void removePlace(AbstractPlace place) {
		places.remove(place);
	}
	
	/**
	 * Removes the specified transition from the Petri Net.
	 * @param transition The Transition to be removed
	 */
	@Override
	public void removeTransition(AbstractTransition transition) {
		transitions.remove(transition);
	}
	
	/**
	 * Removes the specified arc from the Petri Net.
	 * @param arc The Arc to be removed
	 */
	@Override
	public void removeArc(AbstractArc arc) {
		((ArcAdapter) arc).unlinkArc();
		this.arcs.remove(arc);
	}
	
	/**
	 * Checks if the given transition is enabled, i.e., all its entering arcs are firable.
	 * @param transition The Transition to check for enablement
	 * @return true if the transition is enabled, false otherwise
	 */
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
	
	/**
	 * Fires the given transition, activating it in the Petri Net.
	 * @param transition The Transition to be fired
	 * @throws ResetArcMultiplicityException If a reset arc with incorrect multiplicity is encountered
	 * @throws NullTransitionException If a null transition is encountered
	 */
	@Override
	public void fire(AbstractTransition transition) throws ResetArcMultiplicityException, NullTransitionException {
		((TransitionAdapter) transition).getTransition().activate();
	}
}
