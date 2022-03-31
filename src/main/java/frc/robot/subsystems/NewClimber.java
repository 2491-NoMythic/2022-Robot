// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static frc.robot.settings.Constants.Climber.*;

public class NewClimber extends SubsystemBase {

    private DoubleSolenoid midArmSolenoid;
    private DoubleSolenoid traverseArmSolenoid;
    private WPI_TalonFX midWinchMotor;
    private WPI_TalonFX traverseWinchMotor;

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

    public boolean isArmFullyDown() {
        // TODO sensor things. return bool if sensors say

        return false;
    }

    public boolean isArmFullyUp() {
        // TODO sensor things. return bool if sensors say

        return false;
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

    public boolean isClimberFullyOut()
    {
        // if not open(closed)
        //return midWinchMotor.isFwdLimitSwitchClosed() != 0 && traverseWinchMotor.isFwdLimitSwitchClosed() != 0;
        return false;
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

    public boolean isClimberFullyIn(){
        return midWinchMotor.isRevLimitSwitchClosed() != 0 && traverseWinchMotor.isRevLimitSwitchClosed() != 0;
    }

    public void stopMid() {
        setMidMotorSpeed(0);
    
    }

    public void stopTraverse() {
        setTraverseMotorSpeed(0);
    
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
    }
}
