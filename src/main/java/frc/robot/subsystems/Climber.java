// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Climber extends SubsystemBase {
  private static Climber instance = null;
  /** Creates a new climber. */
  private Climber() {}
  
  /** Returns the singleton climber. */
  public static Climber getInstance() {
    if (instance == null){
      instance = new Climber(); 
    }
    return instance;
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
