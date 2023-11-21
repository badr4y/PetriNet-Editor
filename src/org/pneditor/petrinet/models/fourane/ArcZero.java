package org.pneditor.petrinet.models.fourane;

public class ArcZero extends Arc {

	public ArcZero(){}

	/**
	 * Parametrized Constructor
	 * @param place
	 * @param transition
	 */
	public ArcZero(Place place, Transition transition) {
		this.place=place;
		this.transition=transition;
	}

	/**
	 * overrides the drawable method implemented in Arc
	 * Checks if this arc is drawable
	 * @return
	 */
	@Override
	public boolean firable() {
		return place.getTokens()==0;
	}

	/**
	 * overrides the retrieveTokens method implemented in Arc
	 * doesn't change the number of tokens of the place linked to this arc
	 */
	@Override
	public void retrieveTokens() {
	}
}
