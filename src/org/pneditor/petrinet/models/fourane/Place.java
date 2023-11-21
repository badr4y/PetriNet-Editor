package org.pneditor.petrinet.models.fourane;

public class Place {
	private int tokens;

	public Place() {
		tokens =0;
	}

	/**
	 * Parametrized Constructor
	 * @param n assigns 0 if n is negative
	 */
	public Place(int n) {
        tokens = Math.max(n, 0);
	}

	/**
	 * changes the number of tokens of the place
	 * @param m changes it to 0 if m is negative
	 */
	public void changeTokens(int m) {
        tokens = Math.max(m, 0);
	}

	/**
	 * gets the number of tokens of the place
	 * @return
	 */
	public int getTokens() {
		return tokens;
	}

	/**
	 * sets the number of tokens of the place
	 * @param m
	 */
	public void setTokens(int m) {
		tokens = Math.max(m, 0);
	}
}
