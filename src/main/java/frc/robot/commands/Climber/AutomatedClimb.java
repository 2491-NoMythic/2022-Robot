// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Climber;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Climber;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class AutomatedClimb extends SequentialCommandGroup {
  /** Creates a new AutomatedClimb. */
  
  public AutomatedClimb(Climber climber) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    //TODO needs to work with drivetrain
    addCommands(new ClimberClimb(climber, true), new ClimberClimb(climber, false), new WedgePneumatic(climber, PneumaticState.In));
  }
}
