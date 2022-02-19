package frc.robot.commands.Climber;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Climber;

public class ClimberClimb extends CommandBase {

  Climber climber;
  boolean stillRunning;
  ArmExtendState state;

  enum ArmExtendState {
    In,
    Out
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

    switch(state){

      case Out:
      stillRunning = climber.climberOut(.5);
        break;
      case In:
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