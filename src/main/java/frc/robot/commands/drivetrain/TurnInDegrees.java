package frc.robot.commands.drivetrain;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;
import edu.wpi.first.wpilibj.Timer;

import frc.robot.settings.Constants.Drivetrain.*;


public class TurnInDegrees extends CommandBase {
  private Drivetrain drivetrain;
  private double degrees;
  private int loopsToSettle = 5;
  private int withinThresholdLoops;
  private Timer timer;
  private double driveTime;


  /**
   * Creates a new Rotate command.
   */
  public TurnInDegrees(Drivetrain drivetrain, double degrees) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(drivetrain);
    this.drivetrain = drivetrain;
    this.degrees = degrees;
    timer = new Timer();
    driveTime =  1;//STOP_GYRO_TIME;
  }

  @Override
  public void initialize() {
    withinThresholdLoops = 0;
    drivetrain.turnToDegrees(degrees);
    timer.start();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (drivetrain.isAtTurnTarget()) {
      ++withinThresholdLoops;
    } else {
      withinThresholdLoops = 0;
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    drivetrain.stop();
    timer.reset();
        timer.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {

   
    if( withinThresholdLoops >= loopsToSettle)
    {
      return true;
    }
    else{
    return timer.get() >= driveTime;
    }
  }

}
