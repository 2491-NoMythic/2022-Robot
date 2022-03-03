// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Autos;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.climber.ArmPneumaticTipping;
import frc.robot.commands.climber.ArmPneumaticTipping.ArmTipState;
import frc.robot.commands.drivetrain.ForwardDistance;
import frc.robot.commands.intake.Direction;
import frc.robot.commands.intake.DoubleIntake;
import frc.robot.commands.intake.MoveArm;
import frc.robot.commands.intake.MoveArm.IntakeArmState;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Intake;

public class AutonomousAll extends SequentialCommandGroup {
  /** Creates a new AutonomousAll. */
  public AutonomousAll(Drivetrain drivetrain, Climber climber, Intake intake) {
    addCommands(
      new InstantCommand(drivetrain::brakeMode, drivetrain),
      new ParallelRaceGroup(
        new DoubleIntake(intake, Direction.OUT, Direction.OUT),
        new WaitCommand(2)
      ),
      new ForwardDistance(drivetrain, 2.3, -.25),
      new ArmPneumaticTipping(climber, ArmTipState.DOWN),
      new MoveArm(intake, IntakeArmState.armDown)
    );
  }
}
