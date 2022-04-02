package frc.robot.commands.oldClimber;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.OldClimber;
import static frc.robot.settings.Constants.Climber.*;

public class ClimberClimb extends CommandBase {

  OldClimber climber;
  ArmExtendState state;
  
  public enum ArmExtendState {
    IN,
    OUT
  }

 /**
   * Moves arms up and down.
   * <p>
   * NOTE: This command stops when the arms have finished moving. Use {@link Climb} instead.
   * 
   * @param ArmExtendState
   *
   */
  public ClimberClimb(OldClimber climber, ArmExtendState armState) {
    this.climber = climber;
   
    addRequirements(climber);
    state = armState;
  }


  @Override
  public void execute() {

    switch (state) {

      case OUT:
        // climber.climberOut(CLARM_SPEED_OUT);
        climber.setArmPostion(1.0);
        break;
      case IN:
        // climber.climberIn(CLARM_SPEED_IN);
        climber.setArmPostion(0.005);
        break;
    }

  }


  @Override
  public boolean isFinished() {
      switch (state){

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