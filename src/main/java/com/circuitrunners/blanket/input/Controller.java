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
public class Controller extends Input {

    GenericHID controller;

    public Controller(GenericHID controller) {
        this.controller = controller;
    }

    public GenericHID get() {
        return controller;
    }

    @Override
    public String getId() {
        return "Controller";
    }

    public class Button extends Input {
        private int buttonNumber;
        private PRESS_TYPE pressType;
        private boolean lastValue;
        private boolean value;

        public Button(int buttonNumber) {
            this(buttonNumber, PRESS_TYPE.PRESS);
        }

        public Button(int buttonNumber, PRESS_TYPE pressType) {
            this.buttonNumber = buttonNumber;
            this.pressType = pressType;
        }

        public Boolean get() {
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

        @Override
        public String getId() {
            return "Button";
        }

        public PRESS_TYPE getPressType() {
            return pressType;
        }
    }

    enum PRESS_TYPE {
        PRESS, TOGGLE, RELEASE
    }

    public class Axis extends Input {
        private int axisNumber;
        private NUMBER_COMPARISON_TYPE comparisonType;
        private double comparisonValue;
        private double lastValue;
        private double value;

        public Axis(int axisNumber) {
            this(axisNumber, null, 0);
        }

        public Axis(int axisNumber, NUMBER_COMPARISON_TYPE comparisonType, double comparisonValue) {
            this.axisNumber = axisNumber;
            this.comparisonType = comparisonType;
            if (comparisonValue >= -1 && comparisonValue <= 1) {
                this.comparisonValue = comparisonValue;
            } else {
                throw new IllegalArgumentException("Comparison value must be in the interval [-1,1]");
            }
        }

        public Object get() {
            lastValue = value;
            value = controller.getRawAxis(axisNumber);
            if (comparisonType == null) {
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

        @Override
        public String getId() {
            return "Axis";
        }

        public NUMBER_COMPARISON_TYPE getComparisonType() {
            return comparisonType;
        }

    }

    public class POV extends Input {
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

        @Override
        public Object get() {
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

        @Override
        public String getId() {
            return "POV";
        }

    }

    enum POV_OFFSET_DIRECTION {
        CLOCKWISE, COUNTER_CLOCKWISE
    }
}
