// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Autos;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.ArmTipState;
import frc.robot.commands.drivetrain.ForwardDistance;
import frc.robot.commands.drivetrain.GoForwardInInches;
import frc.robot.commands.drivetrain.TurnInDegrees;
import frc.robot.commands.intake.Direction;
import frc.robot.commands.intake.DoubleIntake;
import frc.robot.commands.intake.MoveArm;
import frc.robot.commands.intake.RunIntakeLeft;
import frc.robot.commands.intake.MoveArm.IntakeArmState;
import frc.robot.commands.oldClimber.ArmPneumaticTipping;
import frc.robot.commands.oldClimber.CalibrateArmEncoders;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.OldClimber;

public class TriangleAuto extends SequentialCommandGroup{
  /** Creates a new TriangleAuto. 
   * Starts up against the hub
   * Spits the ball
   * Grabs opponent ball
   * Goes to the hub
   * Gets the other opponenet's ball 
  */




  public TriangleAuto(Drivetrain drivetrain, OldClimber climber, Intake intake) {
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

    new WaitCommand(.2),

    new ParallelCommandGroup(
      new GoForwardInInches(drivetrain, .5, -57),
      new MoveArm(intake, IntakeArmState.armUp)
    ),
    new MoveArm(intake, IntakeArmState.armDown),

    new ParallelRaceGroup(
      new TurnInDegrees(drivetrain, -100),
      new DoubleIntake(intake, Direction.IN, Direction.IN)
    ),

    
    //new WaitCommand(45),

    new ParallelRaceGroup(
      new GoForwardInInches(drivetrain, .4, 100),
      new DoubleIntake(intake, Direction.IN, Direction.IN)
    ),

    
    new ParallelRaceGroup(
      new GoForwardInInches(drivetrain, .2, 15),
      new DoubleIntake(intake, Direction.IN, Direction.IN)
    ),
    

    new ParallelRaceGroup(
      new WaitCommand(.2),
      new DoubleIntake(intake, Direction.IN, Direction.IN)
    ),

    new GoForwardInInches(drivetrain, .25, -2.2),


    new MoveArm(intake, IntakeArmState.armUp),
    
    new ParallelRaceGroup(
        new TurnInDegrees(drivetrain, -90),
        new DoubleIntake(intake, Direction.IN, Direction.IN)
    ),
    new CalibrateArmEncoders(climber)
    );

    //150
    //turn 130
  }
}
