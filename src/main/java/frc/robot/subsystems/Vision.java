// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

/** Add your docs here. */
public class Vision {
    NetworkTableEntry tv, tx, ty, ta, ts, tl, tshort, tlong, thor, tvert, getpipe;
    public Vision() {
      
        NetworkTableInstance inst = NetworkTableInstance.getDefault();
        NetworkTable visionTable = inst.getTable("limelight");

        // NetworkTableInstance.getdefault().getTable("limelight").getEntry("<variablename>").getDouble(0);
        tv = visionTable.getEntry("tv");
        tx = visionTable.getEntry("tx");
        ty = visionTable.getEntry("ty");
        ta = visionTable.getEntry("ta");
        ts = visionTable.getEntry("ts");
        tl = visionTable.getEntry("tl");
        tshort = visionTable.getEntry("tshort");
        tlong = visionTable.getEntry("tlong");
        thor = visionTable.getEntry("thor");
        tvert = visionTable.getEntry("tvert");
        getpipe = visionTable.getEntry("getpipe");
    }


public double GetAngleFromCargo(){
    
    double angle = tx.getDouble(0);
    return angle;
}

    

    




}
