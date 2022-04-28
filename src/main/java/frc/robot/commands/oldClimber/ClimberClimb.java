package frc.robot.commands.oldClimber;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.ArmExtendState;
import frc.robot.subsystems.OldClimber;
import static frc.robot.settings.Constants.Climber.*;

public class ClimberClimb extends CommandBase {
  private OldClimber climber;
  private ArmExtendState state;

 /**
   * Moves arms up and down.
   * <p>
   * NOTE: This command stops when the arms have finished moving. Use {@link Climb} instead.
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
        climber.setArmPostion(1);
        break;
      case DOWN:
        // climber.climberIn(CLARM_SPEED_IN);
        climber.setArmPostion(-.005);
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