// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.FollowerType;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.RemoteSensorSource;
import com.ctre.phoenix.motorcontrol.SensorTerm;
import com.ctre.phoenix.motorcontrol.StatorCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.StatusFrame;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static frc.robot.settings.Constants.Climber.*;

public class OldClimber extends SubsystemBase {

    public enum RungLockState {
        Unlocked(Value.kForward),
        Locked(Value.kReverse);

        Value solenoidDirection;

        RungLockState(Value v) {
            solenoidDirection = v;
        }

        Value getLockStateValue() {
            return solenoidDirection;
        }
    }

    //private DoubleSolenoid rungLockSolenoid;
    private DoubleSolenoid armSolenoid;
    private WPI_TalonFX leftWinchMotor;
    private WPI_TalonFX rightWinchMotor;

    private double prevCurrentLimit = 0;

    /** Creates a new climber. */
    public OldClimber() {

        // rungLockSolenoid = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, RUNG_LOCK_FORWARD_CHANNEL,
                // RUNG_LOCK_REVERSE_CHANNEL);
        armSolenoid = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, ARM_FORWARD_CHANNEL, ARM_REVERSE_CHANNEL);
        leftWinchMotor = new WPI_TalonFX(LEFT_WINCH_ID);
        rightWinchMotor = new WPI_TalonFX(RIGHT_WINCH_ID);

        rightWinchMotor.configFactoryDefault();
        leftWinchMotor.configFactoryDefault();

        rightWinchMotor.setInverted(InvertType.InvertMotorOutput);
        rightWinchMotor.setSensorPhase(true);
        leftWinchMotor.setInverted(InvertType.None);

        rightWinchMotor.set(ControlMode.PercentOutput, 0);
        leftWinchMotor.set(ControlMode.PercentOutput, 0);

        rightWinchMotor.setNeutralMode(NeutralMode.Brake);
        leftWinchMotor.setNeutralMode(NeutralMode.Brake);
        leftWinchMotor.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);

        rightWinchMotor.configRemoteFeedbackFilter(
            leftWinchMotor.getDeviceID(), RemoteSensorSource.TalonFX_SelectedSensor, 0);

        rightWinchMotor.configSensorTerm(SensorTerm.Sum0, FeedbackDevice.RemoteSensor0);
        rightWinchMotor.configSensorTerm(SensorTerm.Sum1, FeedbackDevice.IntegratedSensor);

        rightWinchMotor.configSensorTerm(SensorTerm.Diff0, FeedbackDevice.RemoteSensor0);
        rightWinchMotor.configSensorTerm(SensorTerm.Diff1, FeedbackDevice.IntegratedSensor);

        rightWinchMotor.configSelectedFeedbackSensor(FeedbackDevice.SensorSum, SUM_SLOT, 0);
        rightWinchMotor.configSelectedFeedbackCoefficient(0.5, 0, 0);

        rightWinchMotor.configSelectedFeedbackSensor(FeedbackDevice.SensorDifference, DIFF_SLOT, 0);
        rightWinchMotor.configSelectedFeedbackCoefficient(1, 0, 0);

        rightWinchMotor.setStatusFramePeriod(StatusFrame.Status_12_Feedback1, 20);
        rightWinchMotor.setStatusFramePeriod(StatusFrame.Status_13_Base_PIDF0, 20);
        rightWinchMotor.setStatusFramePeriod(StatusFrame.Status_14_Turn_PIDF1, 20);
        rightWinchMotor.setStatusFramePeriod(StatusFrame.Status_2_Feedback0, 5);

        rightWinchMotor.config_kP(SUM_SLOT, SUM_KP);
        rightWinchMotor.config_kI(SUM_SLOT, SUM_KI);
        rightWinchMotor.config_kD(SUM_SLOT, SUM_KD);
        rightWinchMotor.config_kF(SUM_SLOT, SUM_KF);
        rightWinchMotor.config_IntegralZone(SUM_SLOT, SUM_IZ);
        rightWinchMotor.configClosedLoopPeakOutput(SUM_SLOT, 1);
        rightWinchMotor.configAllowableClosedloopError(0, SUM_ALLOWED_ERR_NATIVE_UNITS);

        int closedLoopTimeMs = 1;
        rightWinchMotor.configClosedLoopPeakOutput(0, closedLoopTimeMs);
        rightWinchMotor.configClosedLoopPeakOutput(1, closedLoopTimeMs);

        rightWinchMotor.configAuxPIDPolarity(false);
        leftWinchMotor.follow(rightWinchMotor, FollowerType.AuxOutput1);

        SmartDashboard.putNumber("Climb Current Limit", 30);

        resetEncoders();
        // is this an alternative to magnetic encoders?
        // rightWinchMotor.configForwardSoftLimitThreshold(ENCODER_TICKS_TO_ARMS_LENGTH_DIVIDED_BY_ONE);
        // leftWinchMotor.configForwardSoftLimitThreshold(ENCODER_TICKS_TO_ARMS_LENGTH_DIVIDED_BY_ONE);
    }

    public void setArmDown() {
        armSolenoid.set(Value.kForward);

    }
    
     public void setArmUp() {
        armSolenoid.set(Value.kReverse);

    }

    /**
     * @param armLength double 0-1.
     * 0 is fully retracted, 1 is fully extended.
     */
    public void setArmPostion (double armLength) {
        rightWinchMotor.set(ControlMode.Position, armLength*ARM_LENGTHS_TO_ENCODER_TICKS, DemandType.AuxPID, 0);
        leftWinchMotor.follow(rightWinchMotor, FollowerType.AuxOutput1);
    }

    public boolean isArmFullyDown() {
        // TODO sensor things. return bool if sensors say

        return false;
    }

    public boolean isArmFullyUp() {
        // TODO sensor things. return bool if sensors say

        return false;
    }

    /**
     * negative percent output values bring climber in, positive bring it out.
     */
    private void setMotorSpeed(double leftSpeed, double rightSpeed) {
        rightWinchMotor.set(ControlMode.PercentOutput, leftSpeed);
        leftWinchMotor.set(ControlMode.PercentOutput, rightSpeed);
    }


    /**
     * use motors to move the climber into extended position
     * 
     * @param speed 0-1 speed
     * @return whether motors are still running or not
     * @note method must be called repeatedly so robot can accuratley check sensors
     */
    public void climberOut(double speed) {
        if (speed < 0) {
            setMotorSpeed(0, 0);
            return;
        }

        double leftSpeed = speed;
        double rightSpeed = speed;
        setMotorSpeed(leftSpeed, rightSpeed);
    }

    public boolean isClimberFullyOut()
    {
        // if not open(closed)
        return leftWinchMotor.isFwdLimitSwitchClosed() != 0 && rightWinchMotor.isFwdLimitSwitchClosed() != 0;
    }
    /**
     * use motors to move the climber into retracted position
     * 
     * @param speed 0-1 speed
     * @return whether motors are still running or not
     * @note method must be called repeatedly so robot can accuratley check sensors
     */
    public void climberIn(double speed) {
        if (speed < 0) {
            setMotorSpeed(0, 0);
            return;
        }

        double leftSpeed = -speed; // negative speed in order to retract the arms.
        double rightSpeed = -speed;

        setMotorSpeed(leftSpeed, rightSpeed);
    }

    public boolean isClimberFullyIn(){
        return leftWinchMotor.isRevLimitSwitchClosed() != 0 && rightWinchMotor.isRevLimitSwitchClosed() != 0;
    }

    /** 
     * Stops the climber winch motors 
     */
    public void stop() {
        setMotorSpeed(0, 0);
    }

    /**
     * Get the left arm position as a percentage of the total arm range. (0 is fully retracted, 1 is fully extended)
     */
    public double getLeftArmPos() {
        return leftWinchMotor.getSelectedSensorPosition() * ENCODER_TICKS_TO_ARMS_LENGTH;
    }

    /**
     * Get the right arm position as a percentage of the total arm range. (0 is fully retracted, 1 is fully extended)
     */
    public double getRightArmPos() {
        return rightWinchMotor.getSelectedSensorPosition() * ENCODER_TICKS_TO_ARMS_LENGTH;
    }

    /** 
     * Sets the encoders to 0 no matter where the physical hardware is 
     */ 
    public void resetEncoders(){
        leftWinchMotor.setSelectedSensorPosition(0);
        rightWinchMotor.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, SUM_SLOT, 0);
        rightWinchMotor.setSelectedSensorPosition(0);
        rightWinchMotor.configSelectedFeedbackSensor(FeedbackDevice.SensorSum, SUM_SLOT, 0);
    }

    public double[] getCurrent(){
        return new double[] {
            leftWinchMotor.getStatorCurrent(),
            rightWinchMotor.getStatorCurrent()
        };
    }

    @Override
    public void periodic() {
        double currentLimit = SmartDashboard.getNumber("Climb Current Limit", 30);
        if (prevCurrentLimit != currentLimit) {
            StatorCurrentLimitConfiguration cfg = new StatorCurrentLimitConfiguration(true, currentLimit, currentLimit, 0);
            leftWinchMotor.configStatorCurrentLimit(cfg);
            rightWinchMotor.configStatorCurrentLimit(cfg);
            prevCurrentLimit = currentLimit;
        }
        SmartDashboard.putNumberArray("Climber Voltage Indicator", getCurrent());
        SmartDashboard.putNumber("Left Arm Position", getLeftArmPos());
        SmartDashboard.putNumber("Right Arm Position", getRightArmPos());
        // This method will be called once per scheduler run
    }
}
