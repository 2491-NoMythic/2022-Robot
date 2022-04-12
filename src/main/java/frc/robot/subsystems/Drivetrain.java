package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.RemoteSensorSource;
import com.ctre.phoenix.motorcontrol.SensorTerm;
import com.ctre.phoenix.motorcontrol.StatusFrame;
import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.settings.Variables;
import com.ctre.phoenix.sensors.Pigeon2;
import com.ctre.phoenix.sensors.WPI_Pigeon2;

import static frc.robot.settings.Constants.Drivetrain.*;

public class Drivetrain extends SubsystemBase {
    private WPI_TalonFX leftLeadMotor;
    private WPI_TalonFX leftFollowMotor;
    private WPI_TalonFX rightLeadMotor;
    private WPI_TalonFX rightFollowMotor;
    // private DifferentialDrive bbDriveSystem;
    private WPI_Pigeon2 gyroBirib;
    private double speedManager;

    public Drivetrain() {

        leftLeadMotor = new WPI_TalonFX(LEFT_LEAD_ID);
        leftFollowMotor = new WPI_TalonFX(LEFT_FOLLOW_ID);
        rightLeadMotor = new WPI_TalonFX(RIGHT_LEAD_ID);
        rightFollowMotor = new WPI_TalonFX(RIGHT_FOLLOW_ID);
        leftLeadMotor.configFactoryDefault();
        leftFollowMotor.configFactoryDefault();
        rightLeadMotor.configFactoryDefault();
        rightFollowMotor.configFactoryDefault();

        leftFollowMotor.follow(leftLeadMotor);
        rightFollowMotor.follow(rightLeadMotor);

        leftLeadMotor.configOpenloopRamp(Variables.Drivetrain.ramp);
        rightLeadMotor.configOpenloopRamp(Variables.Drivetrain.ramp);

        gyroBirib = new WPI_Pigeon2(GYRO_ID);

        SetNormalSpeedMode();

        // making right motors go right
        rightLeadMotor.setInverted(InvertType.None);
        rightFollowMotor.setInverted(InvertType.None);
        leftLeadMotor.setInverted(InvertType.InvertMotorOutput);
        leftFollowMotor.setInverted(InvertType.InvertMotorOutput);

        leftLeadMotor.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);

        rightLeadMotor.configRemoteFeedbackFilter(leftLeadMotor.getDeviceID(),
                RemoteSensorSource.TalonFX_SelectedSensor, 0);
        rightLeadMotor.configRemoteFeedbackFilter(gyroBirib.getDeviceID(), RemoteSensorSource.Pigeon_Yaw, 1);

        rightLeadMotor.configSensorTerm(SensorTerm.Sum0, FeedbackDevice.IntegratedSensor);
        rightLeadMotor.configSensorTerm(SensorTerm.Sum1, FeedbackDevice.RemoteSensor0);

        rightLeadMotor.configSelectedFeedbackSensor(FeedbackDevice.SensorSum, DIST_SLOT, 0);
        rightLeadMotor.configSelectedFeedbackCoefficient(.5, DIST_SLOT, 0);

        rightLeadMotor.configSelectedFeedbackSensor(FeedbackDevice.RemoteSensor1, TURN_SLOT, 0);
        rightLeadMotor.configSelectedFeedbackCoefficient(1, TURN_SLOT, 0);

        rightLeadMotor.setStatusFramePeriod(StatusFrame.Status_12_Feedback1, 20);
        rightLeadMotor.setStatusFramePeriod(StatusFrame.Status_13_Base_PIDF0, 20);
        rightLeadMotor.setStatusFramePeriod(StatusFrame.Status_14_Turn_PIDF1, 20);
        leftLeadMotor.setStatusFramePeriod(StatusFrame.Status_2_Feedback0, 5);

        rightLeadMotor.config_kP(DIST_SLOT, DIST_KP);
        rightLeadMotor.config_kI(DIST_SLOT, DIST_KI);
        rightLeadMotor.config_kD(DIST_SLOT, DIST_KD);
        rightLeadMotor.config_kF(DIST_SLOT, DIST_KF);
        rightLeadMotor.config_IntegralZone(DIST_SLOT, DIST_IZ);
        rightLeadMotor.configAllowableClosedloopError(DIST_SLOT, DIST_ALLOWED_ERR_NATIVE_UNITS);

        rightLeadMotor.config_kP(TURN_SLOT, TURN_KP);
        rightLeadMotor.config_kI(TURN_SLOT, TURN_KI);
        rightLeadMotor.config_kD(TURN_SLOT, TURN_KD);
        rightLeadMotor.config_kF(TURN_SLOT, TURN_KF);
        rightLeadMotor.config_IntegralZone(TURN_SLOT, TURN_IZ);
        rightLeadMotor.configAllowableClosedloopError(TURN_SLOT, TURN_ALLOWED_ERR_NATIVE_UNITS);

        int closedLoopTimeoutMS = 1;
        rightLeadMotor.configClosedLoopPeriod(DIST_SLOT, closedLoopTimeoutMS);
        rightLeadMotor.configClosedLoopPeriod(TURN_SLOT, closedLoopTimeoutMS);

        rightLeadMotor.configAuxPIDPolarity(false);

        rightLeadMotor.selectProfileSlot(DIST_SLOT, 0);
        rightLeadMotor.selectProfileSlot(TURN_SLOT, 0);

        gyroBirib.reset();
        resetEncoders();
        // bbDriveSystem = new DifferentialDrive(leftMotors, rightMotors);
        // bbDriveSystem.setDeadband(0.04);
        // addChild("Diff Drive", bbDriveSystem);

        // LeftSideLead();

    }

    public void setDrive(ControlMode mode, double speed) {
        setDrive(mode, speed, speed);
    }

    /**
     * This command makes the motors move at the same rate, with default control
     * mode PercentOutput(Left, Right).
     */
    public void setDrive(double speed) {
        setDrive(ControlMode.PercentOutput, speed, speed);
    }

    /**
     * Controls motors with contorol PercentOutput. (Left, Right)
     * 
     * @param leftSpeed
     * @param rightSpeed
     */
    public void setDrive(double leftSpeed, double rightSpeed) {
        setDriveLeft(ControlMode.PercentOutput, leftSpeed);
        setDriveRight(ControlMode.PercentOutput, rightSpeed);
    }

    /**
     * Controls motors with control mode. (Left, Right)
     * Also, never gonna give you up, never gonna let you down, never going to annoy
     * Liam by typing this comment. :)
     */
    public void setDrive(ControlMode mode, double leftSpeed, double rightSpeed) {
        setDriveLeft(mode, leftSpeed);
        setDriveRight(mode, rightSpeed);
    }

    public void setDriveLeft(ControlMode mode, double speed) {
        leftLeadMotor.set(mode, speed);
    }

    public void setDriveRight(ControlMode mode, double speed) {
        rightLeadMotor.set(mode, speed);
    }

    public void setDriveVoltage(double leftOutputVolts, double rightOutputVolts) {
        rightLeadMotor.setVoltage(leftOutputVolts);
        leftLeadMotor.setVoltage(rightOutputVolts);

    }

    public void curvatureDrive(double xSpeed, double zRotation, boolean allowTurnInPlace) {
        // bbDriveSystem.curvatureDrive(xSpeed, zRotation, allowTurnInPlace);
    }

    @Override
    public void periodic() {
        // bbDriveSystem.feed();
        Variables.Drivetrain.ramp = SmartDashboard.getNumber("Ramp Rate", Variables.Drivetrain.ramp);
        SmartDashboard.putNumber("gyro", getYaw());
        leftLeadMotor.configOpenloopRamp(Variables.Drivetrain.ramp);
        rightLeadMotor.configOpenloopRamp(Variables.Drivetrain.ramp);

    }

    private void setNeutralMode(NeutralMode mode) {
        rightLeadMotor.setNeutralMode(mode);
        leftLeadMotor.setNeutralMode(mode);
        rightFollowMotor.setNeutralMode(mode);
        leftFollowMotor.setNeutralMode(mode);
    }

    public void brakeMode() {
        setNeutralMode(NeutralMode.Brake);
    }

    public void coastMode() {
        setNeutralMode(NeutralMode.Coast);
    }

    public void resetEncoders() {
        leftLeadMotor.setSelectedSensorPosition(0);
        rightLeadMotor.getSensorCollection().setIntegratedSensorPosition(0, 0);
        leftFollowMotor.setSelectedSensorPosition(0);
        rightFollowMotor.setSelectedSensorPosition(0);
    }

    public double getLeftEncoderValue() {
        return leftLeadMotor.getSelectedSensorPosition();
    }

    public double getRightEncoderValue() {
        return rightLeadMotor.getSelectedSensorPosition();
    }

    public double convertInchesToTicks(double inches) {
        return inches * ENCODER_TICKS_TO_INCHES;
    }

    public double getYaw() {
        return gyroBirib.getYaw();
    }

    public void LeftSideLead() {
        // TODO CHECK THIS
        rightLeadMotor.follow(leftLeadMotor);
        rightLeadMotor.setInverted(InvertType.OpposeMaster);

    }

    public void SetSlowSpeedMode() {
        speedManager = SLOW_SPEED;
    }

    public void SetNormalSpeedMode() {
        speedManager = NORMAL_SPEED;
    }

    public double GetSpeedManager() {
        return speedManager;
    }
}
