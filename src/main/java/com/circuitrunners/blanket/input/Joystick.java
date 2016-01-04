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

package com.circuitrunners.blanket.input;

import edu.wpi.first.wpilibj.GenericHID;

/**
 * Created by Akilan on 29.12.2015.
 */
public class Joystick {

    GenericHID controller;

    public Joystick(GenericHID controller) {
        this.controller = controller;
    }

    public boolean get(Button button) {
        return button.getValue();
    }

    public Object get(Axis axis) {
        return axis.getValue();
    }

    public Object get(POV pov) {
        return pov.getValue();
    }

    class Button {
        private int buttonNumber;
        private PRESS_TYPE pressType = PRESS_TYPE.PRESS;
        private boolean lastValue;
        private boolean value;

        public Button(int buttonNumber, PRESS_TYPE pressType) {
            this.buttonNumber = buttonNumber;
            this.pressType = pressType;
        }

        public boolean getValue() {
            lastValue = value;
            value = controller.getRawButton(buttonNumber);
            switch (pressType) {
                case PRESS:
                    return value == true;
                case TOGGLE:
                    return lastValue != value;
                case RELEASE:
                    return value == false;
            }
            return value;
        }
    }

    enum PRESS_TYPE {
        PRESS, TOGGLE, RELEASE
    }

    class Axis {
        private int axisNumber;
        private AXIS_COMPARISON_TYPE comparisonType;
        private double comparisonValue = -2;
        private double lastValue;
        private double value;

        public Axis(int axisNumber, AXIS_COMPARISON_TYPE comparisonType, double comparisonValue) {
            this(axisNumber);
            this.comparisonType = comparisonType;
            if (comparisonValue >= -1 && comparisonValue <= 1) {
                this.comparisonValue = comparisonValue;
            } else {
                throw new IllegalArgumentException("Comparison value must be in the interval [-1,1]");
            }
        }

        public Axis(int axisNumber) {
            this.axisNumber = axisNumber;
        }

        public Object getValue() {
            lastValue = value;
            value = controller.getRawAxis(axisNumber);
            if (comparisonValue != -2) {
                switch (comparisonType) {
                    case GREATER:
                        return value > comparisonValue;
                    case LESSER:
                        return value < comparisonValue;
                    case GREATER_EQUAL:
                        return value >= comparisonValue;
                    case LESSER_EQUAL:
                        return value <= comparisonValue;
                    case EQUAL:
                        return value == comparisonValue;
                    case NOT_EQUAL:
                        return value != comparisonValue;
                    case CHANGE:
                        return value != lastValue;
                }
            }
            return value;
        }

    }

    enum AXIS_COMPARISON_TYPE {
        GREATER, LESSER, GREATER_EQUAL, LESSER_EQUAL, EQUAL, NOT_EQUAL, CHANGE
    }

    class POV {
        private POV_OFFSET_DIRECTION offsetType;
        private int offsetValue = -1;
        private int lastValue;
        private int value;

        public POV(POV_OFFSET_DIRECTION offsetType, int offsetValue) {
            this.offsetType = offsetType;
            if (offsetValue >= 0 && offsetValue <= 360 && offsetValue % 45 == 0) {
                this.offsetValue = offsetValue;
            } else {
                throw new IllegalArgumentException("Offset value must be in the interval [0,360] " +
                        "in 45 degree intervals");
            }
        }

        public POV() {}

        public Object getValue() {
            lastValue = value;
            value = controller.getPOV();
            if (offsetValue != -1) {
                switch (offsetType) {
                    case CLOCKWISE:
                        int offset = lastValue + offsetValue;
                        offset = offset > 360 ? 360 - offset : offset;
                        return value == offset;
                    case COUNTER_CLOCKWISE:
                        offset = lastValue - offsetValue;
                        offset += offset < 0 ? 360 : 0;
                        return value == offset;
                }
            }
            return value;
        }

    }

    enum POV_OFFSET_DIRECTION {
        CLOCKWISE, COUNTER_CLOCKWISE
    }
}
