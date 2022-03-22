// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Autos;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.oldClimber.ArmPneumaticTipping;
import frc.robot.commands.oldClimber.ArmPneumaticTipping.ArmTipState;
import frc.robot.commands.drivetrain.ForwardDistance;
import frc.robot.commands.intake.Direction;
import frc.robot.commands.intake.DoubleIntake;
import frc.robot.commands.intake.MoveArm;
import frc.robot.commands.intake.MoveArm.IntakeArmState;
import frc.robot.subsystems.OldClimber;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Intake;
// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class AutononomousDrive extends SequentialCommandGroup {

  /** Creates a new AutononomousDrive. Moves forward for 3 seconds at .25 power. */
  public AutononomousDrive(Drivetrain drivetrain, OldClimber climber, Intake intake) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
      new ForwardDistance(drivetrain, 2.3, -.25),
      new ArmPneumaticTipping(climber, ArmTipState.DOWN),
      new MoveArm(intake, IntakeArmState.armDown)



    );
  }
}
