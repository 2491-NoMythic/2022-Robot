package frc.robot.commands.climber;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Climber;

public class ClimberClimb extends CommandBase {

  Climber climber;
  boolean stillRunning;
  ArmExtendState state;

  public enum ArmExtendState {
    IN,
    OUT
  }

  /**
   * 
   * Moves arm up and down
   * 
   * @param ArmExtendState
   *
   */
  public ClimberClimb(Climber climber, ArmExtendState armState) {
    this.climber = climber;
    addRequirements(climber);

    state = armState;
  }

  @Override
  public void initialize() {
    stillRunning = true;
  }

  @Override
  public void execute() {

    switch (state) {

      case OUT:
        stillRunning = climber.climberOut(.5);
        break;
      case IN:
        stillRunning = climber.climberIn(.5);
        break;
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