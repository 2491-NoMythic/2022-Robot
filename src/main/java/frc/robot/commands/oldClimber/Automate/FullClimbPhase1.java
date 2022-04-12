// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.oldClimber.Automate;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.intake.Direction;
import frc.robot.commands.intake.MoveArm;
import frc.robot.commands.intake.MoveArm.IntakeArmState;
import frc.robot.commands.oldClimber.ArmPneumaticTipping;
import frc.robot.commands.oldClimber.ArmPneumaticTipping.ArmTipState;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.OldClimber;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class FullClimbPhase1 extends SequentialCommandGroup {
  /**
   * Climb Phase 1:
   * <p>
   * 1. Tilts the arms back while extending them.
   * <p>
   * 2. Tilts the arms forward.
   */
  public FullClimbPhase1(OldClimber climber, Intake intake) {

    addCommands(
      new MoveArm(intake, IntakeArmState.armDown),
      new TiltBackAndExtend(climber),
      new ArmPneumaticTipping(climber, ArmTipState.UP)
    );
  }
}
