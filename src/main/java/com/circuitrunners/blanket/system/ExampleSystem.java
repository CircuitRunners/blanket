package com.circuitrunners.blanket.system;

/**
 * Created by Akilan on 28.12.2015.
 */
public class ExampleSystem extends System {

    private int[] speedControllerPorts;
    private int[] upperLimitPorts;
    private String upperLimitLogic;
    private int[] lowerLimitPorts;
    private String lowerLimitLogic;

    public int[] getSpeedControllerPorts() {
        return speedControllerPorts;
    }

    public int[] getUpperLimitPorts() {
        return upperLimitPorts;
    }

    public String getUpperLimitLogic() {
        return upperLimitLogic;
    }

    public int[] getLowerLimitPorts() {
        return lowerLimitPorts;
    }

    public String getLowerLimitLogic() {
        return lowerLimitLogic;
    }

    @Override
    public String getName() {
        return "Example System";
    }
}
