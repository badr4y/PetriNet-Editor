package org.pneditor.petrinet.models.fourane;

/**
 * This exception is called when a transition created with a default constructor and its entering and exiting arcs lists are not instantiated yet is activated
 */
public class NullTransitionException extends Exception {
    public NullTransitionException() {
    }
}
