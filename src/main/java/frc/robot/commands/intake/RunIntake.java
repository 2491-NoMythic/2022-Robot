// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.intake;

import edu.wpi.first.wpilibj.PS4Controller;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intake;
import static frc.robot.settings.Constants.Intake.*;

public class RunIntake extends CommandBase {
  private PS4Controller ps4Controller;
  private Intake intake;
  /** Creates a new RunIntake. */
  public RunIntake(Intake intake, PS4Controller ps4Controller) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(intake);
    this.intake = intake;
    this.ps4Controller = ps4Controller;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (ps4Controller.getL2Button()) {
      intake.leftIntake(IN_SPEED);
    } else if (ps4Controller.getL1Button()) {
      intake.leftIntake(OUT_SPEED);
    } else {intake.leftIntake(0);}

    if (ps4Controller.getR2Button()) {
      intake.rightIntake(IN_SPEED);
    } else if (ps4Controller.getR1Button()) {
      intake.rightIntake(OUT_SPEED);
    } else {intake.rightIntake(0);}
    
    if (ps4Controller.getCircleButton()) {
      intake.setArmUp();
    } else if (ps4Controller.getCrossButton()) {
      intake.setArmDown();
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
