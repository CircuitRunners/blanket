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

package com.circuitrunners.blanket.command;

import com.circuitrunners.blanket.system.RobotSystem;
import edu.wpi.first.wpilibj.command.Command;

public class RobotSystemCommand extends Command {

    private final RobotSystem system;
    private final double speed;
    private final boolean timed;

    public RobotSystemCommand(RobotSystem system, double speed) {
        this.system = system;
        this.speed = speed;
        timed = false;
    }

    @Override
    protected void initialize() {
        // TODO: does anything need to be done here?
    }

    @Override
    protected void execute() {
        system.set(speed);
    }

    @Override
    protected boolean isFinished() {
        if (timed) {
            return isTimedOut();
        }
        return false;
    }

    @Override
    protected void end() {
        system.set(0);
    }

    @Override
    protected void interrupted() {
        system.set(0);
    }
}
