package org.pneditor.petrinet.models.fourane;

import java.util.ArrayList;
import java.util.List;

public class Transition {
	private List<Arc> enteringArcs;
	private List<Arc> exitingArcs;

	public Transition() {
	}

	/**
	 * Parametrized Constructor
	 * @param enteringArcs
	 * @param exitingArcs
	 */
	public Transition(ArrayList<Arc> enteringArcs, ArrayList<Arc> exitingArcs) {
		 this.enteringArcs = enteringArcs;
	     this.exitingArcs = exitingArcs;
	}

	/**
	 * gets the lists of entering arcs
	 * @return
	 */
	public List<Arc> getEnteringArcs() {
		return enteringArcs;
	}

	/**
	 * gets the list of exiting arcs
	 * @return
	 */
	public List<Arc> getExitingArcs() {
		return exitingArcs;
	}

	/**
	 * sets the lists of entering arcs
	 * @param enteringArcs
	 */
	public void setEnteringArcs(ArrayList<Arc> enteringArcs) {
		this.enteringArcs = enteringArcs;
	}

	/**
	 * sets the list of exiting arcs
	 * @param exitingArcs
	 */
	public void setExitingArcs(ArrayList<Arc> exitingArcs) {
		this.exitingArcs = exitingArcs;
	}

	/**
	 * activates a transition
	 */
	public boolean activate() throws NullTransitionException {
		if (this.enteringArcs == null || this.exitingArcs==null) {
			throw new NullTransitionException();
		}
		boolean firable = true;

        for (Arc enteringArc : enteringArcs) {
            if (!enteringArc.firable()) {
                firable = false;
                break;
            }
        }
		if (firable) {
            for (Arc enteringArc : enteringArcs) {
                enteringArc.retrieveTokens();
            }
            for (Arc exitingArc : exitingArcs) {
                exitingArc.addTokens();
            }
		}
		return firable;
	}
}
