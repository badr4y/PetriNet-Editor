package org.pneditor.petrinet.adapters.fourane;

import org.pneditor.petrinet.AbstractPlace;
import org.pneditor.petrinet.models.fourane.Place;

public class PlaceAdapter extends AbstractPlace {
    private Place place;

    public PlaceAdapter(String label) {
        super(label);
        this.place = new Place();
    }

    public Place getPlace() {
        return place;
    }

    @Override
    public void addToken() {
        place.setTokens(place.getTokens()+1);
    }

    @Override
    public void removeToken() {
        place.setTokens(place.getTokens()-1);
    }

    @Override
    public int getTokens() {
        return place.getTokens();
    }

    @Override
    public void setTokens(int tokens) {
        place.changeTokens(tokens);
    }
}
