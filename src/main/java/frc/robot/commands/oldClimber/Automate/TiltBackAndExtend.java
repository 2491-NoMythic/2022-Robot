// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.oldClimber.Automate;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.OldClimber;

public class TiltBackAndExtend extends CommandBase {
  OldClimber climber;

  /** 
   * Tilts the arms back while simultaniously extending them. 
   * 
   * @return When the arms have extended fully.
   */
  public TiltBackAndExtend(OldClimber climber) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.climber = climber;
    addRequirements(climber);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    climber.setArmDown();
    climber.setArmPostion(1.0);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {}

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    climber.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return climber.isClimberFullyOut();
  }
}
