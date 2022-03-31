// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.intake;

import static frc.robot.settings.Constants.Intake.OUT_SPEED;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.CargoState;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Pixy2SubSystem;

public class FilterCargo extends CommandBase {
  /** Creates a new FilterCargo. */
  private Intake intake;
  private Pixy2SubSystem pixy;
  private Alliance alliance;
  private CargoState right;
  private CargoState left;

  public FilterCargo(Intake intake, Pixy2SubSystem pixy) {
    addRequirements(intake);
    addRequirements(pixy);
    this.intake = intake;
    this.pixy = pixy;

  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    right = pixy.getRightCargoState();
    left = pixy.getLeftCargoState();
    alliance = DriverStation.getAlliance();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (alliance == Alliance.Red){
      if (right == CargoState.Blue){
        intake.rightIntake(OUT_SPEED);
      }
      if (left == CargoState.Blue){
        intake.leftIntake(OUT_SPEED);
      }
    } else if (alliance == Alliance.Blue){
      if (right == CargoState.Red){
        intake.rightIntake(OUT_SPEED);
      }
      if (left == CargoState.Red){
        intake.leftIntake(OUT_SPEED);
      }
    }

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    intake.leftIntake(0);
    intake.rightIntake(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
