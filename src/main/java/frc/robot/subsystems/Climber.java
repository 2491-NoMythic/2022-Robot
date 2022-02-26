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

    /** Creates a new climber. */
    public Climber() {

        // rungLockSolenoid = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, RUNG_LOCK_FORWARD_CHANNEL,
                // RUNG_LOCK_REVERSE_CHANNEL);
        armSolenoid = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, ARM_FORWARD_CHANNEL, ARM_REVERSE_CHANNEL);
        leftWinchMotor = new WPI_TalonFX(LEFT_WINCH_ID);
        rightWinchMotor = new WPI_TalonFX(RIGHT_WINCH_ID);


        rightWinchMotor.setInverted(InvertType.None);
        leftWinchMotor.setInverted(InvertType.None);

        rightWinchMotor.set(ControlMode.PercentOutput, 0);
        leftWinchMotor.set(ControlMode.PercentOutput, 0);

        toggleLock();
    }

    public void toggleLock() {
        // switch (getLockState()) {
        //     case Locked:
        //         setLockState(RungLockState.Unlocked);
        //         break;
        //     case Unlocked:
        //         setLockState(RungLockState.Locked);
        //         break;

        // }
    }

    public void setLockState(RungLockState lockState) {
        // rungLockSolenoid.set(lockState.getLockStateValue());
    }

    public RungLockState getLockState() {
    //     switch (rungLockSolenoid.get()) {
    //         case kForward:
    //         case kOff:
    //             return RungLockState.Unlocked;
    //         case kReverse:
    //         default:
    //             return RungLockState.Locked;

    //     }
        return RungLockState.Unlocked;
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
    public void climberOut(double speed) {
        if (speed < 0) {
            setMotorSpeed(0, 0);
            return;
        }

        double leftSpeed = -speed;
        double rightSpeed = -speed;
        setMotorSpeed(leftSpeed, rightSpeed);
    }

    public boolean isClimberFullyOut()
    {
        // if not open(closed)
        return leftWinchMotor.isRevLimitSwitchClosed() != 0 && rightWinchMotor.isRevLimitSwitchClosed() != 0;
    }
    /**
     * use motors to move the climber into extended position
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

        double leftSpeed = speed;
        double rightSpeed = speed;

        setMotorSpeed(leftSpeed, rightSpeed);
    }

public boolean isClimberFullyIn(){
    return leftWinchMotor.isFwdLimitSwitchClosed() != 0 && rightWinchMotor.isFwdLimitSwitchClosed() != 0;
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
