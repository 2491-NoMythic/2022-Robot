// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Limelight;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Vision;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class DriveModeEnable extends InstantCommand {
  private Vision vision;
  /**
   * Initializes the Limelight to be used as a driver camera.
   * <p>
   * 1. Turns off LEDs.
   * 2. Disables vision processing for higher FPS, and increases saturation.
   * @param vision
   */
  public DriveModeEnable(Vision vision) {
    this.vision = vision;
    addRequirements(vision);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    vision.toggleLights(false);
    vision.toggleVisionProcessing(false);
  }
}
