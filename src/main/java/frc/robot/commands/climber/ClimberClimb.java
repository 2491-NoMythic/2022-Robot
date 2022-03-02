package frc.robot.commands.climber;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Climber;

public class ClimberClimb extends CommandBase {

  Climber climber;
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
  public void execute() {

    switch (state) {

      case OUT:
        climber.climberOut(.5);
        break;
      case IN:
        climber.climberIn(.5);
        break;
    }
  }

  @Override
  public boolean isFinished() {
    switch (state) {

      case OUT:
        return climber.isClimberFullyOut();
      case IN:
        return climber.isClimberFullyIn();
      }
      return false;
  }

  @Override
  public void end(boolean interrupted) {
    climber.stop();
  }

}