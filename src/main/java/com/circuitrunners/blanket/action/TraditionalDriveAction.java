package com.circuitrunners.blanket.action;

import com.circuitrunners.blanket.input.Controller;
import com.circuitrunners.blanket.system.Drivebase;
import com.circuitrunners.blanket.system.IllegalSystemSetupException;
import edu.wpi.first.wpilibj.RobotDrive;

/**
 * Created by Akilan on 20.12.2015.
 */

public class TraditionalDriveAction extends Action {

    private Drivebase drivebase;
    private RobotDrive rd;
    private Controller[] joysticks;

    public TraditionalDriveAction(String name, Drivebase drivebase) {
        super(name, drivebase);
        this.drivebase = drivebase;
    }

    @Override
    public void initialize() throws IllegalSystemSetupException {
        int[] ports = drivebase.getSpeedControllerPorts();
        switch (ports.length) {
            case 2:
                rd = new RobotDrive(ports[0], ports[1]);
                break;
            case 4:
                rd = new RobotDrive(ports[0], ports[1], ports[2], ports[3]);
                break;
            default:
                throw new IllegalSystemSetupException("Incorrect number of ports setup in " + drivebase.getName() + " system.");
        }

        joysticks = drivebase.getJoysticks();
    }

    @Override
    public void execute() throws IllegalSystemSetupException {
        switch (drivebase.getDriveType()) {
            case "Arcade":
                switch (joysticks.length) {
                    case 1:
                        rd.arcadeDrive(joysticks[0].get().getY(), joysticks[0].get().getTwist());
                        break;
                    case 2:
                        rd.arcadeDrive(joysticks[0].get().getY(), joysticks[1].get().getTwist());
                        break;
                    default:
                        rd.arcadeDrive(joysticks[0].get().getY(), joysticks[0].get().getTwist());
                }
            case "Tank":
                if (joysticks.length >=2) {
                    rd.tankDrive(joysticks[0].get().getY(), joysticks[1].get().getY());
                } else {
                    throw new IllegalSystemSetupException("Invalid number of controllers specified.");
                }
            default:
                if(joysticks.length == 1) {
                    rd.arcadeDrive(joysticks[0].get().getY(), joysticks[0].get().getTwist());
                } else if (joysticks.length >= 2) {
                    rd.tankDrive(joysticks[0].get().getY(), joysticks[1].get().getY());
                } else {
                    throw new IllegalSystemSetupException("Invalid drive type and invalid number of controllers " +
                            "specified.");
                }
        }
    }

    @Override
    public void end() {
        rd.drive(0, 0);
    }

    @Override
    public void interrupted() {
        rd.drive(0, 0);
    }
}
