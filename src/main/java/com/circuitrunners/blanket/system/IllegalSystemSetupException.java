package com.circuitrunners.blanket.system;

/**
 * Created by Akilan on 03.01.2016.
 */
public class IllegalSystemSetupException extends Exception {

    public IllegalSystemSetupException(System system) {
        this("The system: " + system.getName() + " is setup incorrectly");
    }

    public IllegalSystemSetupException(String message) {
        super(message);
    }
}
