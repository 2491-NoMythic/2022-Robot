package frc.robot.commands.oldClimber;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.OldClimber;
import static frc.robot.settings.Constants.Climber.*;

public class Climb extends CommandBase {

  OldClimber climber;
  ArmExtendState state;
  
  public enum ArmExtendState {
    IN,
    OUT
  }

  /**
   * Moves arms up and down.
   * <p>
   * NOTE: This command does not stop automatically. Use {@link ClimberClimb} instead.
   * 
   * @param ArmExtendState
   *
   */
  public Climb(OldClimber climber, ArmExtendState armState) {
    this.climber = climber;
   
    addRequirements(climber);
    state = armState;
  }


  @Override
  public void execute() {

    switch (state) {

      case OUT:
        climber.climberOut(ARM_SPEED_OUT);
        // climber.setArmPostion(1.0);
        break;
      case IN:
        climber.climberIn(ARM_SPEED_IN);
        // climber.setArmPostion(0.005);
        break;
    }

  }


  @Override
  public boolean isFinished() {

      return false;
  }

  @Override
  public void end(boolean interrupted) {
    climber.stop();
  }

}