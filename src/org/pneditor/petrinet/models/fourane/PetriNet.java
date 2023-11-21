package org.pneditor.petrinet.models.fourane;

import java.util.List;

public class PetriNet implements IPetriNet {

    private List<Arc> arcs;
    private List<Place> places;
    private List<Transition> transitions;

    public PetriNet() {
    }

    /**
     * Parametrized Constructor
     * @param arcs
     * @param places
     * @param transitions
     */
    public PetriNet(List<Arc> arcs, List<Place> places, List<Transition> transitions) {
        this.arcs = arcs;
        this.transitions = transitions;
        this.places = places;
    }

    /**
     * adds an arc to the PetriNet
     * adds the arc and place linked to the arc to the PetriNet if they aren't already
     * @param arc
     */
    @Override
    public void addArc(Arc arc) {
            this.arcs.add(arc);
            if (arc.getTransition() != null && !this.transitions.contains(arc.getTransition())) {
                this.transitions.add(arc.getTransition());
            }

            if (arc.getPlace() != null && !this.places.contains(arc.getPlace())) {
                this.places.add(arc.getPlace());
            }
    }

    /**
     * adds a place to the PetriNet
     * @param place
     */
    @Override
    public void addPlace(Place place) {
        this.places.add(place);
    }

    /**
     * adds a transition to the PetriNet
     * @param transition
     */
    @Override
    public void addTransition(Transition transition) {
        this.transitions.add(transition);
    }

    /**
     * deletes and arc from the PetriNet
     * @param arc
     */
    @Override
    public void deleteArc(Arc arc) {
        this.arcs.remove(arc);
    }

    /**
     * deletes a transition from the PetriNet
     * @param transition
     */
    @Override
    public void deleteTransition(Transition transition) {
        this.transitions.remove(transition);
    }

    /**
     * deletes a place from the PetriNet
     * @param place
     */
    @Override
    public void deletePlace(Place place) {
        this.places.remove(place);
    }

    /**
     * fires a transition
     * displays in the terminal a message informing if the transition was fired
     * @param transition is distinguished by its index in the transitions list which represents the order in which it was added to the PetriNet
     */
    @Override
    public void fire(Transition transition) throws NullTransitionException {
        boolean response = transition.activate();
        if (response) {
            System.out.println("transition "+transitions.indexOf(transition)+" was fired");
        } else {
            System.out.println("transition "+transitions.indexOf(transition)+" is not firable");
        }
    }

    /**
     *
     * @throws NullTransitionException
     */
    @Override
    public void step() throws NullTransitionException {
        for (Transition transition : transitions) {
            fire(transition);
        }
    }

}