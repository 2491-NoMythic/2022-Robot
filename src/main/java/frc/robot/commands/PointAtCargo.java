// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Vision;

public class PointAtCargo extends CommandBase {
  private Drivetrain drivetrain;
  private Vision vision;

  /** Creates a new PointAtCargo. */
  public PointAtCargo(Drivetrain drivetrain, Vision vision) {
    this.drivetrain = drivetrain;
    this.vision = vision;
    addRequirements(drivetrain);
    addRequirements(vision);

  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double hAngle = vision.getHorizontalAngle();
    
    double absHAngle = Math.abs(hAngle);
    if (hAngle!= 0 && absHAngle>3) {
      double throttle = (hAngle / absHAngle) * Math.log10(.2 * absHAngle + 1) * .2 + .00001 * Math.pow(hAngle,3);
      drivetrain.setDrive(throttle, -throttle);
      SmartDashboard.putNumber("visionThrottle", throttle);
    } else {
      drivetrain.setDrive(0);
    }
    SmartDashboard.putNumber("hAngle", hAngle);


  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    drivetrain.setDrive(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
