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
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static frc.robot.settings.Constants.Climber.*;

public class Climber extends SubsystemBase {
  private static Climber instance = null;
  private Solenoid breakSolenoid;
  private DoubleSolenoid armSolenoid;
  private WPI_TalonFX leftWinchMotor, rightWinchMotor;
  private DigitalInput topRightLimitSwitch, bottomRightLimitSwitch, topLeftLimitSwitch, bottomLeftLimitSwitch;
    
  /** Creates a new climber. */
  private Climber() {

    breakSolenoid = new Solenoid(PneumaticsModuleType.CTREPCM, BREAK_CHANNEL);
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

    toggleBreak(true);
  }

  
  /** Returns the singleton climber. */
  public static Climber getInstance() {
    if (instance == null){
      instance = new Climber(); 
    }
    return instance;
  }

  public void toggleBreak(){
    toggleBreak(!isBreakOn());
  }

  public void toggleBreak(boolean activateBreak){
    breakSolenoid.set(activateBreak);

  }

  public boolean isBreakOn(){
    return breakSolenoid.get();
  }
  
public void setArmIn(){
  armSolenoid.set(Value.kReverse);

} 

public void setArmOut (){
  armSolenoid.set(Value.kForward);

}

private void setMotorSpeed(double leftSpeed, double rightSpeed){
  rightWinchMotor.set(ControlMode.PercentOutput, leftSpeed);
  leftWinchMotor.set(ControlMode.PercentOutput, rightSpeed);
}

/**
 * use motors to move the climber into extended position
 * @param speed 0-1 speed
 * @return whether motors are still running or not
 * @note method must be called repeatedly so robot can accuratley check sensors
 */
public boolean climberOut(double speed){ 
  if (speed < 0) {
    setMotorSpeed(0, 0);
    return false;
  }

  double leftSpeed = speed;
  double rightSpeed = speed;
  
  if (topLeftLimitSwitch.get()){
    leftSpeed = 0;
  }
  if (topRightLimitSwitch.get()){
    rightSpeed = 0;
  }
  setMotorSpeed(leftSpeed, rightSpeed);

  return leftSpeed != 0 || rightSpeed != 0;
}

/**
 * use motors to move the climber into extended position
 * @param speed 0-1 speed
 * @return whether motors are still running or not
 * @note method must be called repeatedly so robot can accuratley check sensors
 */
public boolean climberIn(double speed){ 
  if (speed < 0) {
    setMotorSpeed(0, 0);
    return false;
  }

  double leftSpeed = -speed;
  double rightSpeed = -speed;
  
  if (bottomLeftLimitSwitch.get()){
    leftSpeed = 0;
  }
  if (bottomRightLimitSwitch.get()){
    rightSpeed = 0;
  }
  setMotorSpeed(leftSpeed, rightSpeed);

  return leftSpeed != 0 || rightSpeed != 0;
}

  /* TODO :
    - ask about motors
    - add a pnumatics control hub
    - ask about sensers  
  */
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
