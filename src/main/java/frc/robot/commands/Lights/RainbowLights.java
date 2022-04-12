// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Lights;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.LightsHardware;

public class RainbowLights extends CommandBase {
  /** Creates a new RainbowLights. */
  LightsHardware lights;
  int startingPixel;
  public RainbowLights(LightsHardware lights) {
    this.lights = lights;
    addRequirements(lights);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    lights.rainbowlights(startingPixel);
    lights.dataSetter();
    startingPixel = (startingPixel + 3) % 180; // mod stops the number from getting too big.
    //Loops the starting pixel past the last one
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    lights.lightsout();
    lights.dataSetter();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
