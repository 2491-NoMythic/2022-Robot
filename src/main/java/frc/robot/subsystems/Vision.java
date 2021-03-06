// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

// this is how to change the limelight settings:
// NetworkTableInstance.getDefault().getTable("limelight").getEntry("<variablename>").setNumber(<value>);
/** Add your docs here. */
public class Vision extends SubsystemBase {

    NetworkTableEntry tv, tx, ty, ta, tshort, tlong, thor, tvert, getpipe;
    NetworkTableInstance inst;
    NetworkTable visionTable;
    public Vision() {
        inst = NetworkTableInstance.getDefault();
        visionTable = inst.getTable("limelight");

        // NetworkTableInstance.getdefault().getTable("limelight").getEntry("<variablename>").getDouble(0);
        tv = visionTable.getEntry("tv"); // if there are any detections (1 or 0)
        tx = visionTable.getEntry("tx"); // horizontal offset from crosshair to target (-27 to 27 degrees)
        ty = visionTable.getEntry("ty"); // vertical offset from crosshair to target (-20.5 to 20.5 degrees)
        ta = visionTable.getEntry("ta"); // target area (0% to 100% of image frame)
        tshort = visionTable.getEntry("tshort"); // sidelength of shortest side of detection
        tlong = visionTable.getEntry("tlong"); // sidelength of longest side of detection
        thor = visionTable.getEntry("thor"); // horizontal sidelength of detection (0-320 pixels)
        tvert = visionTable.getEntry("tvert"); // vertical sidelength of detection (0-320 pixels)
        getpipe = visionTable.getEntry("getpipe"); // get current pipeline number

        // turn off lights and processing by default
        toggleLights(false);
        toggleVisionProcessing(false);
    }

    /**
     * horizontal offset from crosshair to target 
     * @return (-27 to 27 degrees)
     */
    public double getHorizontalPos(){
        double angle = tx.getDouble(0);
        return angle;
    }

    /**
     * Turn lights on or off.
     * @param enabled true if lights should be turned on.
     */
    public void toggleLights(boolean enabled) {
        if (enabled) {
            NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(3); // lights on
        } else {
            NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(1); // lights off
        }
    }

    /**
     * Enable/Disable vision processing. When disabled, frames can be delivered faster and exposure is increased. Best for when being used as a driver camera.
     * @param enabled true if vision processing should be enabled.
     */
    public void toggleVisionProcessing(boolean enabled) {
        if (enabled) {
            NetworkTableInstance.getDefault().getTable("limelight").getEntry("camMode").setNumber(2); // enable vision processing.
        } else {
            NetworkTableInstance.getDefault().getTable("limelight").getEntry("camMode").setNumber(1); // disable vision processing.
        }
    }




}
