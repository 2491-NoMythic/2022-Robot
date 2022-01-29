package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Drivetrain extends SubsystemBase{
    
    public Drivetrain() {

    }
    public void setDrive(ControlMode mode, double speed){
        setDrive(mode, speed, speed);
    }

    /**
 * This command makes the motors move at the same rate, with default control mode PercentOutput. 
 */
    public void setDrive(double speed){
        setDrive(ControlMode.PercentOutput, speed, speed);
    }

    public void setDrive(double leftSpeed, double rightSpeed) {
        setDriveLeft(ControlMode.PercentOutput, leftSpeed);
        setDriveRight(ControlMode.PercentOutput, rightSpeed);
    }
/**
 * Controls motors with control mode
 *Also, never gonna give you up, never gonna let you down, never going to annoy Liam by typing this comment. :)
 */
    public void setDrive(ControlMode mode, double leftSpeed, double rightSpeed) {
        setDriveLeft(mode, leftSpeed);
        setDriveRight(mode, rightSpeed);
    }

    public void setDriveLeft(ControlMode mode,double speed) {
    // moves left motor
    }
    public void setDriveRight(ControlMode mode,double speed) {
        // moves right motor
    }
    
}
