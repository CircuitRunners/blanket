package com.circuitrunners.blanket.action;

/**
 * Created by Akilan on 03.01.2016.
 */
public class InactiveActionException extends Exception {

    public InactiveActionException() {
        this("This action is not active and did not run.");
    }

    public InactiveActionException(String message) {
        super(message);
    }
}
