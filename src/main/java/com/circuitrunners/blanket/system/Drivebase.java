/*
    Copyright 2015 CircuitRunners

    This file is part of Blanket.

    Blanket is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    Blanket is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with Blanket.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.circuitrunners.blanket.system;

import com.circuitrunners.blanket.input.Controller;
import edu.wpi.first.wpilibj.Joystick;

/**
 * Created by Akilan on 28.12.2015.
 */
public class Drivebase extends System {

    private int[] speedControllerPorts;
    private String driveType;
    private int[] joysticks;
    private String[] joystickTypes;
    private boolean encoders;


    public int[] getSpeedControllerPorts() {
        return speedControllerPorts;
    }

    public String getDriveType() {
        return driveType;
    }

    public Controller[] getJoysticks() {
        Controller[] controllers = new Controller[joysticks.length];
        for (int i = 0; i < joysticks.length; i++) {
            controllers[i] = new Controller(new Joystick(joysticks[i]));
        }
        return controllers;
    }

    public String[] getJoystickTypes() {
        return joystickTypes;
    }

    public boolean isEncoders() {
        return encoders;
    }

    @Override
    public String getName() {
        return "Drivebase";
    }
}
