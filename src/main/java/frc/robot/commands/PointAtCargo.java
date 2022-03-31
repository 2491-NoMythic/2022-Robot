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
    double hPos = vision.getHorizontalPos();
    
    double abshPos = Math.abs(hPos);
    if (hPos!= 0 && abshPos>3) {
      // double throttle = hPos / 100;
      double throttle = (hPos / abshPos) * Math.log10(.2 * abshPos + 1) * .2 + .00001 * Math.pow(hPos,3);
      // \frac{x}{\operatorname{abs}\left(x\right)}\cdot\log\left(.5\operatorname{abs}\left(x\right)+1\right)\cdot.7
      drivetrain.setDrive(throttle, -throttle);
      SmartDashboard.putNumber("visionThrottle", throttle);
    } else {
      drivetrain.setDrive(0);
    }
    SmartDashboard.putNumber("hAngle", hPos);


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
