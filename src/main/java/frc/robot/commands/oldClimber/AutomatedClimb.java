// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.oldClimber;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.ArmExtendState;
import frc.robot.ArmTipState;
import frc.robot.commands.drivetrain.ForwardDistance;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.OldClimber;
import frc.robot.subsystems.OldClimber.RungLockState;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class AutomatedClimb extends SequentialCommandGroup {
  /** Creates a new AutomatedClimb. */

  public AutomatedClimb(OldClimber climber, Drivetrain drivetrain) {
    // Add your commands in the addCommands() call, e.g.
    addCommands(
      new ClimberClimb(climber, ArmExtendState.UP),
      new ForwardDistance(drivetrain, 3, 0.3),
      new ClimberClimb(climber, ArmExtendState.DOWN), 
      new WedgePneumatic(climber, RungLockState.Locked),
      new ArmPneumaticTipping(climber, ArmTipState.OUT),
      new ClimberClimb(climber, ArmExtendState.UP),
      new ArmPneumaticTipping(climber, ArmTipState.IN),
      new WedgePneumatic(climber, RungLockState.Unlocked),
      new ClimberClimb(climber, ArmExtendState.DOWN)
    );
  }
}
