// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.oldClimber;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.OldClimber;
import static frc.robot.settings.Constants.Climber.*;


public class calibrateClimber extends CommandBase {
  OldClimber climber;

  /** Creates a new calibrateClimber. */
  public calibrateClimber(OldClimber climber) {
    this.climber = climber;
    
    addRequirements(climber);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    climber.climberIn(CLARM_SPEED_CALIBRATE);

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
