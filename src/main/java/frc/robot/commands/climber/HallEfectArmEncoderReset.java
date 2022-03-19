// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.climber;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Climber;

public class HallEfectArmEncoderReset extends CommandBase {
  /** Creates a new HallEfectArmEncoderReset. */
  Climber climber;
  public HallEfectArmEncoderReset(Climber climber) {
    this.climber = climber;
  }

  @Override
  public void execute() {
    climber.climberIn(.1);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    climber.stop();
    climber.resetEncoders();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {


    

    return climber.isClimberFullyIn();
  }
}
