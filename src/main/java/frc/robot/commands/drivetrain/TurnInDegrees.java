package frc.robot.commands.drivetrain;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;
import static frc.robot.settings.Variables.Drivetrain.Gyro.*;

public class TurnInDegrees extends CommandBase{
    private Drivetrain drivetrain;
  private double degrees;
  private PIDController pid;

  /**
   * Creates a new Rotate command.
   */
  public TurnInDegrees(Drivetrain drivetrain, double degrees) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.drivetrain = drivetrain;
    this.degrees = degrees;
    SmartDashboard.putNumber("kP", kP);
    SmartDashboard.putNumber("kI", kI);
    SmartDashboard.putNumber("kD", kD);
  }

  @Override
  public void initialize() {
    pid = new PIDController(SmartDashboard.getNumber("kP", kP), SmartDashboard.getNumber("kI", kI), SmartDashboard.getNumber("kD", kD));
    pid.setTolerance(1,10);
    pid.setSetpoint(drivetrain.getYaw() + degrees);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double turnrate = pid.calculate(drivetrain.getYaw());
    drivetrain.setDrive(turnrate*-1, turnrate);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return pid.atSetpoint();
  }

  
}
