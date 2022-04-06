// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatorCurrentLimitConfiguration;
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

        rightWinchMotor.setInverted(InvertType.None);
        leftWinchMotor.setInverted(InvertType.None);

        rightWinchMotor.set(ControlMode.PercentOutput, 0);
        leftWinchMotor.set(ControlMode.PercentOutput, 0);

        rightWinchMotor.setNeutralMode(NeutralMode.Brake);
        leftWinchMotor.setNeutralMode(NeutralMode.Brake);

        rightWinchMotor.config_kD(0, CLIMBER_MOTOR_KD);
        rightWinchMotor.config_kP(0, CLIMBER_MOTOR_KP);
        rightWinchMotor.configAllowableClosedloopError(0, CLIMBER_MOTOR_ALLOWABLE_ERROR);
        leftWinchMotor.config_kD(0, CLIMBER_MOTOR_KD);
        leftWinchMotor.config_kP(0, CLIMBER_MOTOR_KP);
        leftWinchMotor.configAllowableClosedloopError(0, CLIMBER_MOTOR_ALLOWABLE_ERROR);
        SmartDashboard.putNumber("Climb Current Limit", 30);

        resetEncoders();
        // is this an alternative to magnetic encoders?
        rightWinchMotor.configReverseSoftLimitThreshold(0);
        leftWinchMotor.configReverseSoftLimitThreshold(0);

         rightWinchMotor.configForwardSoftLimitThreshold(FORWARD_LIMIT_THRESHOLD);
         leftWinchMotor.configForwardSoftLimitThreshold(FORWARD_LIMIT_THRESHOLD);
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
        rightWinchMotor.set(ControlMode.Position, armLength*ARM_LENGTHS_TO_ENCODER_TICKS);
        leftWinchMotor.set(ControlMode.Position, armLength*ARM_LENGTHS_TO_ENCODER_TICKS);
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
        rightWinchMotor.setSelectedSensorPosition(0);
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
