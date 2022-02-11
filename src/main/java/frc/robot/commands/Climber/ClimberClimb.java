package frc.robot.commands.Climber;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Climber;

public class ClimberClimb extends CommandBase {

  Climber climber;
  boolean stillRunning;
  boolean isClimberExtending;

  /**
   * if climberExtending = true, the climber arm is extending 
   * if climberExtending = false, the clarm is retracting
   * @param climber
   * @param climberExtending
   */
  public ClimberClimb(Climber climber, boolean climberExtending ) {
    this.climber = climber;
    addRequirements(climber);

    isClimberExtending = climberExtending;
  }

  @Override
  public void initialize() {
    stillRunning = true;
  }

  @Override
  public void execute() {
    if (isClimberExtending == true) {
      stillRunning = climber.climberOut(.5);

    } else {
      stillRunning = climber.climberIn(.5);
    }
  }

  @Override
  public boolean isFinished() {
    return !stillRunning;
  }

  @Override
  public void end(boolean interrupted) {
    climber.stop();
  }

}