// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Climber extends SubsystemBase {
  private static Climber instance = null;
  private Solenoid breakSolenoid;
  private DoubleSolenoid armSolenoid;
  private WPI_TalonFX leftWinchMotor, rightWinchMotor;
  /** Creates a new climber. */
  private Climber() {

    breakSolenoid = new Solenoid(PneumaticsModuleType.CTREPCM, 0);
    armSolenoid = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, 0, 0);
    leftWinchMotor = new WPI_TalonFX(0);
    rightWinchMotor = new WPI_TalonFX(0);
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
