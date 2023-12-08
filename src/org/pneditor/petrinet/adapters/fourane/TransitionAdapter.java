package org.pneditor.petrinet.adapters.fourane;

import org.pneditor.petrinet.AbstractTransition;
import org.pneditor.petrinet.models.fourane.Transition;

import java.util.ArrayList;

/**
 * Using the Adapter design pattern, this class is a linking class between the implementation of Transition in {@link org.pneditor.petrinet.models.fourane.Transition} and the Editor interface
 */
public class TransitionAdapter extends AbstractTransition {
	private final Transition transition;
	
	/**
	 * This is the default constructor for the TransitionAdapter, it creates an instance of a Transition and set it as its attribute with its own lists
	 * @param label an optional parameter to identify the created TransitionAdapter object with a unique label
	 */
	public TransitionAdapter(String label) {
		super(label);
		this.transition = new Transition(new ArrayList<>(), new ArrayList<>());
	}
	
	/**
	 * @return the Transition object associated to this TransitionAdapter instance
	 */
	public Transition getTransition() {
		return transition;
	}
}
