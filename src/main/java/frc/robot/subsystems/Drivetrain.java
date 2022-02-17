package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static frc.robot.settings.Constants.Drivetrain.*;

public class Drivetrain extends SubsystemBase{
    private WPI_TalonFX leftLeadMotor;
    private WPI_TalonFX leftFollowMotor;
    private WPI_TalonFX rightLeadMotor;
    private WPI_TalonFX rightFollowMotor;
    private DifferentialDrive bbDriveSystem;
    private MotorControllerGroup leftMotors;
    private MotorControllerGroup rightMotors;
    public Drivetrain() {

		leftLeadMotor = new WPI_TalonFX(LEFT_LEAD_ID);
		leftFollowMotor = new WPI_TalonFX(LEFT_FOLLOW_ID);
		rightLeadMotor = new WPI_TalonFX(RIGHT_LEAD_ID);
		rightFollowMotor = new WPI_TalonFX(RIGHT_FOLLOW_ID);
        leftMotors = new MotorControllerGroup(leftLeadMotor, leftFollowMotor);
        rightMotors = new MotorControllerGroup(rightLeadMotor, rightFollowMotor);
        addChild("Left Lead", leftLeadMotor);
        addChild("Left Follow", leftFollowMotor);
        addChild("Right Lead", rightLeadMotor);
        addChild("Right Follow", rightFollowMotor);

        
		//making right motors go right
		rightLeadMotor.setInverted(InvertType.None);
		rightFollowMotor.setInverted(InvertType.None);
		leftLeadMotor.setInverted(InvertType.InvertMotorOutput);
		leftFollowMotor.setInverted(InvertType.InvertMotorOutput);
        bbDriveSystem = new DifferentialDrive(leftMotors, rightMotors);
         bbDriveSystem.setDeadband(0.04);
        addChild("Diff Drive", bbDriveSystem);
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
        leftLeadMotor.set(mode, speed);
    }
    public void setDriveRight(ControlMode mode,double speed) {
        rightLeadMotor.set(mode, speed);
    }
    public void curvatureDrive(double xSpeed, double zRotation, boolean allowTurnInPlace){
         bbDriveSystem.curvatureDrive(xSpeed, zRotation, allowTurnInPlace);
    }

    @Override
    public void periodic() {
         bbDriveSystem.feed();
    }
}
