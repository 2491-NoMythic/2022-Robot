package frc.robot.commands.oldClimber;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.ArmExtendState;
import frc.robot.subsystems.OldClimber;
import static frc.robot.settings.Constants.Climber.*;

public class ClimberClimb extends CommandBase {
  private OldClimber climber;
  private ArmExtendState state;

  /**
   * Moves arm up and down
   * 
   * @param climber subsystem
   * @param armState move climber arms UP or DOWN
   */
  public ClimberClimb(OldClimber climber, ArmExtendState armState) {
    this.climber = climber;
    addRequirements(climber);
    state = armState;
  }

  @Override
  public void execute() {

    switch (state) {
      case UP:
        climber.climberOut(CLARM_SPEED_OUT);
        break;
      case DOWN:
        climber.climberIn(CLARM_SPEED_IN);
        break;
    }
  }

  @Override
  public boolean isFinished() {
      switch (state) {
        case UP:
          return climber.isClimberFullyOut();
        case DOWN:
          return climber.isClimberFullyIn();
      }
      return false;
  }

  @Override
  public void end(boolean interrupted) {
    climber.stop();
  }

}