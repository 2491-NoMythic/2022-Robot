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
import frc.robot.ArmTipState;


public class OpponentCargo extends SequentialCommandGroup {
  /** Creates a new AutonomousAll. */
  public OpponentCargo(Drivetrain drivetrain, OldClimber climber, Intake intake) {
    addCommands(

        new InstantCommand(drivetrain::brakeMode, drivetrain),
        new ArmPneumaticTipping(climber, ArmTipState.OUT),

        new WaitCommand(0.3),

        new ParallelRaceGroup(
            new DoubleIntake(intake, Direction.OUT, Direction.OUT),
            new WaitCommand(1)),

        new ForwardDistance(drivetrain, .3, -.25),
        new TurnInDegrees(drivetrain, 150),
        new MoveArm(intake, IntakeArmState.armDown),
        new WaitCommand(0.4),

        new ParallelRaceGroup(
            new ForwardDistance(drivetrain, 2, .25),
            new DoubleIntake(intake, Direction.IN, Direction.IN)),
        new ParallelCommandGroup(
            // new TurnInDegrees(drivetrain, 180),
            new MoveArm(intake, IntakeArmState.armUp)

        ),
        // new ForwardDistance(drivetrain, 3, .25),

        // new ParallelRaceGroup(
        // new DoubleIntake(intake, Direction.OUT, Direction.OUT),
        // new WaitCommand(1)
        // ),

        new ParallelCommandGroup(
            // new ForwardDistance(drivetrain, 2.3, -.25),
            new MoveArm(intake, IntakeArmState.armUp),
            new CalibrateArmEncoders(climber)));
  }
}