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
import static frc.robot.settings.Constants.NewClimber.*;

public class NewClimber extends SubsystemBase {

    private DoubleSolenoid midArmSolenoid;
    private DoubleSolenoid traverseArmSolenoid;
    private WPI_TalonFX midWinchMotor;
    private WPI_TalonFX traverseWinchMotor;
    private double prevCurrentLimit;

    /** Creates a new climber. */
    public NewClimber() {

        // rungLockSolenoid = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, RUNG_LOCK_FORWARD_CHANNEL,
                // RUNG_LOCK_REVERSE_CHANNEL);
        midArmSolenoid = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, MID_ARM_FORWARD_CHANNEL, MID_ARM_REVERSE_CHANNEL);
        traverseArmSolenoid = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, TRAVERSE_ARM_FORWARD_CHANNEL, TRAVERSE_ARM_REVERSE_CHANNEL);
        midWinchMotor = new WPI_TalonFX(MID_WINCH_ID);
        traverseWinchMotor = new WPI_TalonFX(TRAVERSE_WINCH_ID);


        traverseWinchMotor.setInverted(InvertType.None);
        midWinchMotor.setInverted(InvertType.None);

        traverseWinchMotor.set(ControlMode.PercentOutput, 0);
        midWinchMotor.set(ControlMode.PercentOutput, 0);

        traverseWinchMotor.setNeutralMode(NeutralMode.Brake);
        midWinchMotor.setNeutralMode(NeutralMode.Brake);
        //negative percent output values bring climber in, positive bring it out.

        
        traverseWinchMotor.config_kD(0, CLIMBER_MOTOR_KD);
        traverseWinchMotor.config_kP(0, CLIMBER_MOTOR_KP);
        traverseWinchMotor.configAllowableClosedloopError(0, CLIMBER_MOTOR_ALLOWABLE_ERROR);
        midWinchMotor.config_kD(0, CLIMBER_MOTOR_KD);
        midWinchMotor.config_kP(0, CLIMBER_MOTOR_KP);
        midWinchMotor.configAllowableClosedloopError(0, CLIMBER_MOTOR_ALLOWABLE_ERROR);
        SmartDashboard.putNumber("Climb Current Limit", 30);

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

    public void setMidArmPostion (double armLength) {
        midWinchMotor.set(ControlMode.Position, armLength*ARM_LENGTHS_TO_ENCODER_TICKS);
    }

    public void setTraverseArmPostion (double armLength) {
        traverseWinchMotor.set(ControlMode.Position, armLength*ARM_LENGTHS_TO_ENCODER_TICKS);
    }

    private void setTraverseMotorSpeed(double traverseSpeed) {
        traverseWinchMotor.set(ControlMode.PercentOutput, traverseSpeed);
    }

    private void setMidMotorSpeed(double midSpeed) {
        midWinchMotor.set(ControlMode.PercentOutput, midSpeed);
    }

    /**
     * use motors to move the climber into extended position
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

    public boolean isMidClimberFullyOut()
    {
        if(getMidArmPos() >= 1)
        {
          return true;
        }
        else
        {
          return false;
        }
    }

    public boolean isTraverseClimberFullyOut()
    {
        if(getTraverseArmPos() >= 1)
        {
          return true;
        }
        else
        {
          return false;
        }
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

    public boolean isMidClimberFullyIn(){
        return midWinchMotor.isRevLimitSwitchClosed() != 0;
    }

    public boolean isTraverseClimberFullyIn(){
        return traverseWinchMotor.isRevLimitSwitchClosed() != 0;
    }

    public void stopMid() {
        setMidMotorSpeed(0);
    
    }

    public void stopTraverse() {
        setTraverseMotorSpeed(0);
    
    }

    public double[] getCurrent(){
        return new double[] {
            traverseWinchMotor.getStatorCurrent(),
            midWinchMotor.getStatorCurrent()
        };
    }

    public double getTraverseArmPos() {
        return traverseWinchMotor.getSelectedSensorPosition() * ENCODER_TICKS_TO_ARMS_LENGTH;
    }
    
    public double getMidArmPos() {
        return midWinchMotor.getSelectedSensorPosition() * ENCODER_TICKS_TO_ARMS_LENGTH;
    }

    @Override
    public void periodic() {
        double currentLimit = SmartDashboard.getNumber("Climb Current Limit", 30);
        if (prevCurrentLimit != currentLimit) {
            StatorCurrentLimitConfiguration cfg = new StatorCurrentLimitConfiguration(true, currentLimit, currentLimit, 0);
            traverseWinchMotor.configStatorCurrentLimit(cfg);
            midWinchMotor.configStatorCurrentLimit(cfg);
            prevCurrentLimit = currentLimit;
        }
        SmartDashboard.putNumberArray("Climber Current Indicator", getCurrent());
        SmartDashboard.putNumber("Left Arm Position", getTraverseArmPos());
        SmartDashboard.putNumber("Right Arm Position", getMidArmPos());
        
        // This method will be called once per scheduler run
    }
}
