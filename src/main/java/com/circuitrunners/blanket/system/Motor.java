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

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Motor extends Subsystem {
    public final SpeedController controller;

    public Motor(SpeedController controller) {
        this.controller = controller;
        set(0);
    }

    public void set(double speed) {
        controller.set(speed);
    }

    @Override
    protected void initDefaultCommand() {
        // TODO: actually init default command
    }
}
