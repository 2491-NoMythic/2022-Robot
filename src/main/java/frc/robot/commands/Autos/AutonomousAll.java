// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Autos;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.drivetrain.ForwardDistance;
import frc.robot.commands.intake.Direction;
import frc.robot.commands.intake.DoubleIntake;
import frc.robot.commands.newClimber.ArmPneumaticTippingMid;
import frc.robot.commands.newClimber.ArmPneumaticTippingTraverse;
import frc.robot.commands.oldClimber.ArmPneumaticTipping;
import frc.robot.subsystems.NewClimber;
import frc.robot.subsystems.OldClimber;
import frc.robot.commands.oldClimber.CalibrateArmEncoders;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Intake;
import frc.robot.ArmTipState;

public class AutonomousAll extends SequentialCommandGroup {
  /** Creates a new AutonomousAll. */
  public AutonomousAll(Drivetrain drivetrain, OldClimber climber, Intake intake) {
    addCommands(
      new InstantCommand(drivetrain::brakeMode, drivetrain),
      new ParallelRaceGroup(
        new DoubleIntake(intake, Direction.OUT, Direction.OUT),
        new WaitCommand(2)
      ),
      new ForwardDistance(drivetrain, 2.3, -.25),
      new ArmPneumaticTipping(climber, ArmTipState.OUT),
      new CalibrateArmEncoders(climber)
      // new MoveArm(intake, IntakeArmState.armDown)
    );
  }
    
  public AutonomousAll(Drivetrain drivetrain, NewClimber climber, Intake intake) {
    addCommands(
      new InstantCommand(drivetrain::brakeMode, drivetrain),
      new ParallelRaceGroup(
        new DoubleIntake(intake, Direction.OUT, Direction.OUT),
        new WaitCommand(2)
      ),
      new ForwardDistance(drivetrain, 2.3, -.25),
      new ArmPneumaticTippingTraverse(climber, ArmTipState.IN),
      new ArmPneumaticTippingMid(climber, ArmTipState.IN)
      // new MoveArm(intake, IntakeArmState.armDown)

      //TODO: check arm tip state is correct way
    );
  }
}

