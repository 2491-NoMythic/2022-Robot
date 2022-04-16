package frc.robot.commands.drivetrain;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

public class TurnInDegrees extends CommandBase {
  private Drivetrain drivetrain;
  private double degrees;

  /**
   * Creates a new Rotate command.
   */
  public TurnInDegrees(Drivetrain drivetrain, double degrees) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.drivetrain = drivetrain;
    this.degrees = degrees;
  }

  @Override
  public void initialize() {
    drivetrain.turnToDegrees(degrees);
  }

  // Called every time the scheduler runs while the command is scheduled.

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    drivetrain.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return drivetrain.isAtTurnTarget();
  }

}
