// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Autos;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.ArmTipState;
import frc.robot.commands.drivetrain.ForwardDistance;
import frc.robot.commands.drivetrain.GoForwardInInches;
import frc.robot.commands.drivetrain.TurnInDegrees;
import frc.robot.commands.intake.Direction;
import frc.robot.commands.intake.DoubleIntake;
import frc.robot.commands.intake.FilterCargo;
import frc.robot.commands.intake.MoveArm;
import frc.robot.commands.intake.MoveArm.IntakeArmState;
import frc.robot.commands.oldClimber.ArmPneumaticTipping;
import frc.robot.commands.oldClimber.CalibrateArmEncoders;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.OldClimber;
import frc.robot.subsystems.Pixy2SubSystem;
import io.github.pseudoresonance.pixy2api.Pixy2;

public class TwoBallAuto extends ParallelRaceGroup {
  /** Creates a new AutonomousAll. */
  public TwoBallAuto(Drivetrain drivetrain, OldClimber climber, Intake intake, Pixy2SubSystem pixy) {
    addCommands(
  new SequentialCommandGroup(

      new InstantCommand(drivetrain::brakeMode, drivetrain),

        new DoubleIntake(intake, Direction.OUT, Direction.OUT),
 
      new TurnInDegrees(drivetrain, 180),
      new MoveArm(intake, IntakeArmState.armDown),

      new ParallelRaceGroup(
        new GoForwardInInches(drivetrain, .5, 350),
        new DoubleIntake(intake, Direction.IN, Direction.IN)
      ),
      new ParallelCommandGroup(
        new TurnInDegrees(drivetrain, 180),
        new MoveArm(intake, IntakeArmState.armUp)
      ),
      new GoForwardInInches(drivetrain, .5, 180),

      new ParallelRaceGroup(
        new DoubleIntake(intake, Direction.OUT, Direction.OUT),
        new WaitCommand(2)
      ),
      new ArmPneumaticTipping(climber, ArmTipState.OUT),
      new ParallelCommandGroup(
        new GoForwardInInches(drivetrain, .5, -350),
        new MoveArm(intake, IntakeArmState.armUp),
        new CalibrateArmEncoders(climber)
      )
      )
    );
    new FilterCargo(intake, pixy);
  }
}
