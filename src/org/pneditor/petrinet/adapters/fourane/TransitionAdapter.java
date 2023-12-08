package org.pneditor.petrinet.adapters.fourane;

import org.pneditor.petrinet.AbstractTransition;
import org.pneditor.petrinet.models.fourane.Transition;

import java.util.ArrayList;

public class TransitionAdapter extends AbstractTransition {
	private final Transition transition;
	
	public TransitionAdapter(String label) {
		super(label);
		this.transition = new Transition(new ArrayList<>(), new ArrayList<>());
	}
	
	public Transition getTransition() {
		return transition;
	}
}
