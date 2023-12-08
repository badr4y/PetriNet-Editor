package org.pneditor.petrinet.adapters.fourane;

import org.pneditor.petrinet.AbstractPlace;
import org.pneditor.petrinet.models.fourane.Place;

/**
 * Using the Adapter design pattern, this class is a linking class between the implementation of Place in {@link org.pneditor.petrinet.models.fourane.Place} and the Editor interface
 */
public class PlaceAdapter extends AbstractPlace {
	private Place place;
	
	/**
	 * This is the default constructor for the PlaceAdapter, it creates an instance of a Place and set it as its attribute
	 * @param label an optional parameter to identify the created PlaceAdapter object with a unique label
	 */
	public PlaceAdapter(String label) {
		super(label);
		this.place = new Place();
	}
	
	/**
	 * @return the Place object associated to this PlaceAdapter instance
	 */
	public Place getPlace() {
		return place;
	}
	
	/**
	 * add a token to the PlaceAdapter object
	 */
	@Override
	public void addToken() {
		place.setTokens(place.getTokens() + 1);
	}
	
	/**
	 * removes a token to the PlaceAdapter object
	 */
	@Override
	public void removeToken() {
		place.setTokens(place.getTokens() - 1);
	}
	
	/**
	 * @return the number of tokens of the PlaceAdapter
	 */
	@Override
	public int getTokens() {
		return place.getTokens();
	}
	
	/**
	 * set the number of tokens of the PlaceAdapter to the instructed integer
	 * @param tokens given by the user of the petrinet
	 */
	@Override
	public void setTokens(int tokens) {
		place.changeTokens(tokens);
	}
}
