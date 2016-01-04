package com.circuitrunners.blanket;

import com.circuitrunners.blanket.system.Motor;
import edu.wpi.first.wpilibj.DigitalSource;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class Main {
    // semi-pseudocode for proof of concept
    public void main(String[] args) {
        String[] strings = {"Motor", "Joystick", ""}; // replace with json getter
        int[] substrings = {1}; // replace with json getter
        Class<? extends Subsystem>[] classTypes = new Class[]{Motor.class}; // replace with class registry
        for (String string : strings) {
            for (Class classType : classTypes) {
                if (string.equals(classType.getSimpleName())) {
                    Class parameter;
                    switch (string) {
                        case "Motor":
                            parameter = SpeedController.class;
                            break;
                        default:
                            parameter = DigitalSource.class;
                            break;
                    }
                    try {
                        Constructor constructor = classType.getConstructor(parameter);
                        constructor.newInstance(substrings[0]);
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }
        }
    }
}
