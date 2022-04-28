// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import static frc.robot.settings.Constants.NewClimberConstants.*;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatorCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class NewClimber extends SubsystemBase {

    private DoubleSolenoid midArmSolenoid;
    private DoubleSolenoid traverseArmSolenoid;
    private WPI_TalonFX midWinchMotor;
    private WPI_TalonFX midWinchFollowerMotor;
    private WPI_TalonFX traverseWinchMotor;
    private double prevCurrentLimit;

    /** Creates a new climber. */
    public NewClimber() {
        midArmSolenoid = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, MID_ARM_FORWARD_CHANNEL,
                MID_ARM_REVERSE_CHANNEL);
        traverseArmSolenoid = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, TRAVERSE_ARM_FORWARD_CHANNEL,
                TRAVERSE_ARM_REVERSE_CHANNEL);

        addChild("Mid Arm Solenoid", midArmSolenoid);
        addChild("Traverse Arm Solenoid", traverseArmSolenoid);

        midWinchMotor = new WPI_TalonFX(MID_WINCH_ID);
        midWinchFollowerMotor = new WPI_TalonFX(MID_WINCH_FOLLOWER_ID);
        traverseWinchMotor = new WPI_TalonFX(TRAVERSE_WINCH_ID);

        addChild("Mid Winch Motor", midWinchMotor);
        addChild("Mid Winch Follower", midWinchFollowerMotor);
        addChild("Traverse Winch Motor", traverseWinchMotor);

        midWinchMotor.configFactoryDefault();
        midWinchFollowerMotor.configFactoryDefault();
        traverseWinchMotor.configFactoryDefault();

        // TODO: configure motor inverts
        midWinchMotor.setInverted(InvertType.None);
        midWinchFollowerMotor.setInverted(InvertType.None);
        traverseWinchMotor.setInverted(InvertType.None);

        midWinchFollowerMotor.follow(midWinchMotor);

        traverseWinchMotor.setNeutralMode(NeutralMode.Brake);
        midWinchMotor.setNeutralMode(NeutralMode.Brake);
        midWinchFollowerMotor.setNeutralMode(NeutralMode.Brake);

        traverseWinchMotor.config_kD(0, TRAVERSE_CLIMBER_MOTOR_KD);
        traverseWinchMotor.config_kP(0, TRAVERSE_CLIMBER_MOTOR_KP);
        traverseWinchMotor.configAllowableClosedloopError(0, TRAVERSE_CLIMBER_MOTOR_ALLOWABLE_ERROR);
        midWinchMotor.config_kD(0, MID_CLIMBER_MOTOR_KD);
        midWinchMotor.config_kP(0, MID_CLIMBER_MOTOR_KP);
        midWinchMotor.configAllowableClosedloopError(0, MID_CLIMBER_MOTOR_ALLOWABLE_ERROR);
        Shuffleboard.getTab("Config").add("Climb Current Limit", 30);

        stop();
        resetEncoders();

        midWinchMotor.configReverseSoftLimitThreshold(0);
        traverseWinchMotor.configReverseSoftLimitThreshold(0);
        midWinchMotor.configForwardSoftLimitThreshold(MID_FORWARD_LIMIT_THRESHOLD);
        traverseWinchMotor.configForwardSoftLimitThreshold(TRAVERSE_FORWARD_LIMIT_THRESHOLD);

        setSoftlimitEnable(true);

    }

    public void setSoftlimitEnable(boolean enable) {
        midWinchMotor.configForwardSoftLimitEnable(enable);
        midWinchMotor.configReverseSoftLimitEnable(enable);

        traverseWinchMotor.configForwardSoftLimitEnable(enable);
        traverseWinchMotor.configReverseSoftLimitEnable(enable);
    }

    public void setMidArmIn() {
        midArmSolenoid.set(Value.kForward);
    }

    public void setTraverseArmIn() {
        traverseArmSolenoid.set(Value.kForward);
    }

    public void setMidArmOut() {
        midArmSolenoid.set(Value.kReverse);
    }

    public void setTraverseArmOut() {
        traverseArmSolenoid.set(Value.kReverse);
    }

    public void setMidArmPostion(double armLength) {
        midWinchMotor.set(ControlMode.Position, armLength * TRAVERSAL_ARM_LENGTH_TO_ENCODER_TICKS);
    }

    public void setTraverseArmPostion(double armLength) {
        traverseWinchMotor.set(ControlMode.Position, armLength * MID_ARM_LENGTH_TO_ENCODER_TICKS);
    }

    private void setTraverseMotorSpeed(double traverseSpeed) {
        traverseWinchMotor.set(ControlMode.PercentOutput, traverseSpeed);
    }

    private void setMidMotorSpeed(double midSpeed) {
        midWinchMotor.set(ControlMode.PercentOutput, midSpeed);
    }

    /**
     * use motors to move the climber into extended position
     * probably shouldn't be used - only set position to go up
     * 
     * @param speed 0-1 speed
     * @return whether motors are still running or not
     * @note method must be called repeatedly so robot can accuratley check sensors
     */
    public void midClimberArmUp(double speed) {
        if (speed < 0) {
            setMidMotorSpeed(0);
            return;
        }
        setMidMotorSpeed(speed);
    }

    /**
     * probably shouldn't be used - only set position to go up
     * 
     * @param speed
     */
    public void traverseClimberArmUp(double speed) {
        if (speed < 0) {
            setTraverseMotorSpeed(0);
            return;
        }
        setTraverseMotorSpeed(speed);
    }

    public void midClimberArmDown(double speed) {
        if (speed > 0) {
            setMidMotorSpeed(0);
            return;
        }
        setMidMotorSpeed(speed);
    }

    public void traverseClimberArmDown(double speed) {
        if (speed > 0) {
            setTraverseMotorSpeed(0);
            return;
        }
        setTraverseMotorSpeed(speed);
    }

    public boolean isMidClimberFullyOut() {
        return getMidArmPos() >= 1;
    }

    public boolean isTraverseClimberFullyOut() {
        return getTraverseArmPos() >= 1;
    }

    /**
     * use motors to move the climber into extended position
     * 
     * @param speed 0-1 speed
     * @return whether motors are still running or not
     * @note method must be called repeatedly so robot can accuratley check sensors
     */
    public void midclimberIn(double speed) {
        if (speed < 0) {
            setMidMotorSpeed(0);
            return;
        }
        setMidMotorSpeed(speed);
    }

    public void traverseclimberIn(double speed) {
        if (speed < 0) {
            setTraverseMotorSpeed(0);
            return;
        }
        setTraverseMotorSpeed(speed);
    }

    public boolean isMidClimberFullyIn() {
        return midWinchMotor.isRevLimitSwitchClosed() != 0;
    }

    public boolean isTraverseClimberFullyIn() {
        return traverseWinchMotor.isRevLimitSwitchClosed() != 0;
    }

    public void stopMid() {
        setMidMotorSpeed(0);
    }

    public void stopTraverse() {
        setTraverseMotorSpeed(0);
    }

    public void stop() {
        stopMid();
        stopTraverse();
    }

    public double[] getCurrent() {
        return new double[] {
                traverseWinchMotor.getStatorCurrent(),
                midWinchMotor.getStatorCurrent()
        };
    }

    public double getTraverseArmPos() {
        return traverseWinchMotor.getSelectedSensorPosition() * ENCODER_TICKS_TO_TRAVERSAL_ARMS_LENGTH;
    }

    public double getMidArmPos() {
        return midWinchMotor.getSelectedSensorPosition() * ENCODER_TICKS_TO_MID_ARMS_LENGTH;
    }

    @Override
    public void periodic() {
        // I don't think we want to limit the current - give it all she's got scottie
        double currentLimit = SmartDashboard.getNumber("Climb Current Limit", 30);
        if (prevCurrentLimit != currentLimit) {
            StatorCurrentLimitConfiguration cfg = new StatorCurrentLimitConfiguration(true, currentLimit, currentLimit,
                    0);
            traverseWinchMotor.configStatorCurrentLimit(cfg);
            midWinchMotor.configStatorCurrentLimit(cfg);
            prevCurrentLimit = currentLimit;
        }
        SmartDashboard.putNumberArray("Climber Current Indicator", getCurrent());
        SmartDashboard.putNumber("Traverse Arm Position", getTraverseArmPos());
        SmartDashboard.putNumber("Mid Arm Position", getMidArmPos());
    }

    /**
     * Sets the encoders to 0 no matter where the physical hardware is
     */
    public void resetEncoders() {
        traverseWinchMotor.setSelectedSensorPosition(0);
        midWinchMotor.setSelectedSensorPosition(0);
        midWinchFollowerMotor.setSelectedSensorPosition(0);
    }
}
