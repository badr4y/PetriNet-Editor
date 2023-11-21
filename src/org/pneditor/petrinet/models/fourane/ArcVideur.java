package org.pneditor.petrinet.models.fourane;

import org.pneditor.petrinet.models.fourane.Arc;

public class ArcVideur extends Arc {

    public ArcVideur() {
    }

    /**
     * Paraemetrized Arc
     * @param place
     * @param transition
     */
    public ArcVideur(Place place, Transition transition) {
        this.place = place;
        this.transition = transition;
    }


    /**
     * overrides the drawable method implemented in Arc
     * Checks if the arc_videur is drawable
     * @return
     */
    @Override
    public boolean firable() {
        return place.getTokens() > 0;
    }

    /**
     * overrides the retrieveTokens method implemented in Arc
     * changes the number of the tokens of the place linked to the arc to 0
     */
    @Override
    public void retrieveTokens() {
        place.setTokens(0);
    }
}
