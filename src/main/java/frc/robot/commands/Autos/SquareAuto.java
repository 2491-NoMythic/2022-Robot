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
import frc.robot.commands.intake.MoveArm.IntakeArmState;
import frc.robot.commands.newClimber.ArmPneumaticTippingMid;
import frc.robot.commands.newClimber.ArmPneumaticTippingTraverse;
import frc.robot.commands.oldClimber.ArmPneumaticTipping;
import frc.robot.subsystems.NewClimber;
import frc.robot.subsystems.OldClimber;
import frc.robot.commands.oldClimber.CalibrateArmEncoders;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Intake;
import frc.robot.ArmTipState;

public class SquareAuto extends SequentialCommandGroup {
  /** Creates a new AutonomousAll. */
  public SquareAuto(Drivetrain drivetrain, OldClimber climber, Intake intake) {
    addCommands(
      new InstantCommand(drivetrain::brakeMode, drivetrain),
      new InstantCommand(climber::setArmDown, climber),
      new WaitCommand(.1),

      new ParallelRaceGroup(
        new DoubleIntake(intake, Direction.OUT, Direction.OUT),
        new WaitCommand(.5)
      ),
      
    new GoForwardInInches(drivetrain, .25, -25),

    new ParallelCommandGroup(
      new TurnInDegrees(drivetrain, 180),
      new MoveArm(intake, IntakeArmState.armDown)
    ),


    new ParallelRaceGroup(
      new GoForwardInInches(drivetrain, .5, 45),
      new DoubleIntake(intake, Direction.IN, Direction.IN)
    ),

    
    new ParallelCommandGroup(
      new TurnInDegrees(drivetrain, -102.5),
      new MoveArm(intake, IntakeArmState.armUp)
    ),


    new GoForwardInInches(drivetrain, .5, 125),

    
    new ParallelCommandGroup(
      new TurnInDegrees(drivetrain, -90),
      new MoveArm(intake, IntakeArmState.armDown)
    ),

    new ParallelRaceGroup(
      new GoForwardInInches(drivetrain, .5, 62),
      new DoubleIntake(intake, Direction.IN, Direction.IN)
    ),

    new MoveArm(intake, IntakeArmState.armUp),
    new ParallelRaceGroup(
      new WaitCommand(.4),
      new DoubleIntake(intake, Direction.IN, Direction.IN)
  ),
  new CalibrateArmEncoders(climber)

    );



  }
    

  
}

