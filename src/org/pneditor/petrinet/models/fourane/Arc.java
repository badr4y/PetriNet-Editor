package org.pneditor.petrinet.models.fourane;

public class Arc {
    private int weight;
    protected Place place;
    protected Transition transition;

    public Arc() {
    }

    /**
     * Parametrized constructor
     * @param weight
     * @param place
     * @param transition
     */
    public Arc(int weight, Place place, Transition transition) {
        this.weight = Math.max(weight, 0);
        this.transition = transition;
        this.place= place;
    }

    /**
     * Gets the transition linked to this arc
     * @return
     */
    public Transition getTransition() {
        return transition;
    }

    /**
     * Gets the weight of this arc
     * @return
     */
    public int getWeight() {
        return weight;
    }

    /**
     * Gets the place linked to this arc
     * @return
     */
    public Place getPlace() {
        return place;
    }

    /**
     * Changes the weight of this arc
     * @param n
     */
    public void changeWeight(int n) {
        this.weight = n;
    }

    /**
     * Checks if this arc is drawable
     * @return
     */
    public boolean firable() {
        return place.getTokens() >= weight;
    }

    /**
     * Adds tokens to the place linked to the arc depending on the weight of the arc itself
     */
    public void addTokens() {
        place.setTokens(place.getTokens()+ weight);
    }

    /**
     * retrieves tokens to the place linked to the arc depending on the weight of the arc itself
     */
    public void retrieveTokens() {
        place.setTokens(Math.max(0,place.getTokens()- weight));
    }


}
