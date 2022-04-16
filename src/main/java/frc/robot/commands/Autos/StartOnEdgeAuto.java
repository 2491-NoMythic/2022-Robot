// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Autos;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.drivetrain.ForwardDistance;
import frc.robot.commands.drivetrain.GoForwardInInches;
import frc.robot.commands.drivetrain.TurnInDegrees;
import frc.robot.commands.intake.Direction;
import frc.robot.commands.intake.DoubleIntake;
import frc.robot.commands.intake.MoveArm;
import frc.robot.commands.intake.RunIntakeLeft;
import frc.robot.commands.intake.RunIntakeRight;
import frc.robot.commands.intake.MoveArm.IntakeArmState;
import frc.robot.commands.oldClimber.ArmPneumaticTipping;
import frc.robot.commands.oldClimber.CalibrateArmEncoders;
import frc.robot.commands.oldClimber.ArmPneumaticTipping.ArmTipState;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.OldClimber;

public class StartOnEdgeAuto extends SequentialCommandGroup {
  /** Creates a new AutonomousAll. */
  public StartOnEdgeAuto(Drivetrain drivetrain, OldClimber climber, Intake intake) {
    addCommands(
      new InstantCommand(drivetrain::brakeMode, drivetrain),
      new ArmPneumaticTipping(climber, ArmTipState.DOWN),

      new MoveArm(intake, IntakeArmState.armDown),
      new ParallelRaceGroup(
        new GoForwardInInches(drivetrain, .5, 60),
        new RunIntakeRight(intake, Direction.IN)
      ),
      new TurnInDegrees(drivetrain, 180),
      new GoForwardInInches(drivetrain, 0.5, 132),

      //should be up on hub

      new ParallelRaceGroup(
        new RunIntakeLeft(intake, Direction.OUT),
        new WaitCommand(1)
      ),

      new GoForwardInInches(drivetrain, .5, -35),
      new TurnInDegrees(drivetrain, -90),
      new GoForwardInInches(drivetrain, .5, 50),
      new TurnInDegrees(drivetrain, 90),
      new GoForwardInInches(drivetrain, .5, 35),
      new TurnInDegrees(drivetrain, -90),
      new ParallelRaceGroup(
          new GoForwardInInches(drivetrain, .5, 50),
          new DoubleIntake(intake, Direction.IN, Direction.IN)
      ),
      new ParallelCommandGroup(
        new TurnInDegrees(drivetrain, 90),
        new MoveArm(intake, IntakeArmState.armUp)
      ),
      new CalibrateArmEncoders(climber)
      // new MoveArm(intake, IntakeArmState.armDown)
    );
  }
}
