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

package com.circuitrunners.blanket.action;

import edu.wpi.first.wpilibj.RobotState;
import edu.wpi.first.wpilibj.Timer;

import java.util.concurrent.Callable;

public abstract class Action implements Callable {

    private final String name;
    private boolean isActive = false;
    private double timeout = -1;
    private double startTime;
    private boolean isCancelled = false;
    private boolean isFinished = false;

    private System system;

    public Action(String name, System system) {
        this.name = name;
        this.system = system;
    }

    public Action(String name, System system, double timeout) {
        this(name, system);
        if(timeout < 0) {
            throw new IllegalArgumentException("Timeout must not be negative. Action: " + name + "Given: " + timeout);
        } else {
            this.timeout = timeout;
        }
    }

    public final String getName() { return name; }

    public System getSystem() { return system; }

    public boolean isActive() {
        return isActive;
    }

    public void setActiveState(boolean actionActive) {
        isActive = actionActive;
    }

    public abstract void initialize() throws Exception;

    public abstract void execute() throws Exception;

    public abstract void end() throws Exception;

    public abstract void interrupted() throws Exception;

    public final boolean isInterupted() {
        return Thread.interrupted();
    }

    public final void finish() {
        isFinished = true;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public final void cancel() {
        this.isCancelled = true;
    }

    public final boolean isCancelled() { return isCancelled || Thread.interrupted(); }

    public final boolean isTimedOut() {
        if (timeout != -1) return Timer.getFPGATimestamp() - timeout < startTime; else return false;
    }

    private void startTiming() {
        startTime = Timer.getFPGATimestamp();
    }

    @Override
    public final Object call() throws Exception {
        if (isActive) {
            if (RobotState.isDisabled()) cancel();
            if (isCancelled()) return null;
            startTiming();
            initialize();
            while (!isFinished() && !isTimedOut()) {
                if (isCancelled()) {
                    interrupted();
                    return isCancelled();
                } else execute();
            }
            end();
        } else {
            throw new InactiveActionException();
        }
        return null;
    }
}
