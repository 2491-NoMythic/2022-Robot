package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.settings.Constants;

public class Drivetrain extends SubsystemBase{
    private WPI_TalonFX driveLeftMotor1,driveLeftMotor2,driveRightMotor1,driveRightMotor2;
    public Drivetrain() {

		driveLeftMotor1 = new WPI_TalonFX(Constants.Drivetrain.driveLeftMotor1);
		driveLeftMotor2 = new WPI_TalonFX(Constants.Drivetrain.driveLeftMotor2);
		driveRightMotor1 = new WPI_TalonFX(Constants.Drivetrain.driveRightMotor1);
		driveRightMotor2 = new WPI_TalonFX(Constants.Drivetrain.driveRightMotor2);


        //Setting Followers
		driveLeftMotor2.follow(driveLeftMotor1);
		driveRightMotor2.follow(driveRightMotor1);

        
		//making right motors go right
		driveRightMotor1.setInverted(false);
		driveRightMotor2.setInverted(false);
		driveLeftMotor1.setInverted(false);
		driveLeftMotor2.setInverted(false);
    }
    public void setDrive(ControlMode mode, double speed){
        setDrive(mode, speed, speed);
    }

    /**
 * This command makes the motors move at the same rate, with default control mode PercentOutput(Left, Right). 
 */
    public void setDrive(double speed){
        setDrive(ControlMode.PercentOutput, speed, speed);
    }
/**
 * Controls motors with contorol PercentOutput. (Left, Right)
 * @param leftSpeed
 * @param rightSpeed
 */
    public void setDrive(double leftSpeed, double rightSpeed) {
        setDriveLeft(ControlMode.PercentOutput, leftSpeed);
        setDriveRight(ControlMode.PercentOutput, rightSpeed);
    }
/**
 * Controls motors with control mode. (Left, Right)
 *Also, never gonna give you up, never gonna let you down, never going to annoy Liam by typing this comment. :)
 */
    public void setDrive(ControlMode mode, double leftSpeed, double rightSpeed) {
        setDriveLeft(mode, leftSpeed);
        setDriveRight(mode, rightSpeed);
    }

    public void setDriveLeft(ControlMode mode,double speed) {
        driveLeftMotor1.set(mode, speed);
    }
    public void setDriveRight(ControlMode mode,double speed) {
        driveRightMotor1.set(mode, speed);
    }
    
}
