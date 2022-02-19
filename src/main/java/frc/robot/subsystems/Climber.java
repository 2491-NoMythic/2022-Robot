// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static frc.robot.settings.Constants.Climber.*;

public class Climber extends SubsystemBase {

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

    private DoubleSolenoid rungLockSolenoid;
    private DoubleSolenoid armSolenoid;
    private WPI_TalonFX leftWinchMotor;
    private WPI_TalonFX rightWinchMotor;
    private DigitalInput topRightLimitSwitch;
    private DigitalInput bottomRightLimitSwitch;
    private DigitalInput topLeftLimitSwitch;
    private DigitalInput bottomLeftLimitSwitch;

    /** Creates a new climber. */
    public Climber() {

        rungLockSolenoid = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, RUNG_LOCK_FORWARD_CHANNEL,
                RUNG_LOCK_REVERSE_CHANNEL);
        armSolenoid = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, ARM_FORWARD_CHANNEL, ARM_REVERSE_CHANNEL);
        leftWinchMotor = new WPI_TalonFX(LEFT_WINCH_ID);
        rightWinchMotor = new WPI_TalonFX(RIGHT_WINCH_ID);

        topRightLimitSwitch = new DigitalInput(TOP_RIGHT_LIMIT_SWITCH);
        topLeftLimitSwitch = new DigitalInput(TOP_LEFT_LIMIT_SWITCH);
        bottomRightLimitSwitch = new DigitalInput(BOTTOM_RIGHT_LIMIT_SWITCH);
        bottomLeftLimitSwitch = new DigitalInput(BOTTOM_LEFT_LIMIT_SWITCH);

        rightWinchMotor.setInverted(InvertType.None);
        leftWinchMotor.setInverted(InvertType.None);

        rightWinchMotor.set(ControlMode.PercentOutput, 0);
        leftWinchMotor.set(ControlMode.PercentOutput, 0);

        toggleLock();
    }

    public void toggleLock() {
        switch (getLockState()) {
            case Locked:
                setLockState(RungLockState.Unlocked);
                break;
            case Unlocked:
                setLockState(RungLockState.Locked);
                break;

        }
    }

    public void setLockState(RungLockState lockState) {
        rungLockSolenoid.set(lockState.getLockStateValue());
    }

    public RungLockState getLockState() {
        switch (rungLockSolenoid.get()) {
            case kForward:
            case kOff:
                return RungLockState.Unlocked;
            case kReverse:
            default:
                return RungLockState.Locked;

        }
    }

    public boolean isLockFullyUnlocked() {
        // TODO sensor things. return bool if sensors say

        return false;
    }

    public boolean isLockFullyLocked() {
        // TODO sensor things. return bool if sensors say

        return false;
    }

    public void setArmDown() {
        armSolenoid.set(Value.kReverse);

    }

    public void setArmUp() {
        armSolenoid.set(Value.kForward);

    }

    public boolean isArmFullyDown() {
        // TODO sensor things. return bool if sensors say

        return false;
    }

    public boolean isArmFullyUp() {
        // TODO sensor things. return bool if sensors say

        return false;
    }

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
    public boolean climberOut(double speed) {
        if (speed < 0) {
            setMotorSpeed(0, 0);
            return false;
        }

        double leftSpeed = -speed;
        double rightSpeed = -speed;

        if (topLeftLimitSwitch.get()) {
            leftSpeed = 0;
        }
        if (topRightLimitSwitch.get()) {
            rightSpeed = 0;
        }
        setMotorSpeed(leftSpeed, rightSpeed);

        return leftSpeed != 0 || rightSpeed != 0;
    }

    /**
     * use motors to move the climber into extended position
     * 
     * @param speed 0-1 speed
     * @return whether motors are still running or not
     * @note method must be called repeatedly so robot can accuratley check sensors
     */
    public boolean climberIn(double speed) {
        if (speed < 0) {
            setMotorSpeed(0, 0);
            return false;
        }

        double leftSpeed = speed;
        double rightSpeed = speed;

        if (bottomLeftLimitSwitch.get()) {
            leftSpeed = 0;
        }
        if (bottomRightLimitSwitch.get()) {
            rightSpeed = 0;
        }
        setMotorSpeed(leftSpeed, rightSpeed);

        return leftSpeed != 0 || rightSpeed != 0;
    }

    public void stop() {
        setMotorSpeed(0, 0);
    }

    /*
     * TODO :
     * - ask about motors
     * - ask about sensors
     */
    @Override
    public void periodic() {
        // This method will be called once per scheduler run
    }
}
