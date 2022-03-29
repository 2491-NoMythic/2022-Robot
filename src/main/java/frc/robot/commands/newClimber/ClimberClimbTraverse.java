package frc.robot.commands.newClimber;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.NewClimber;
import static frc.robot.settings.Constants.Climber.*;

public class ClimberClimbTraverse extends CommandBase {
  NewClimber climber;
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
  public ClimberClimbTraverse(NewClimber climber, ArmExtendState armState) {
    this.climber = climber;
    addRequirements(climber);
    state = armState;
  }


  @Override
  public void execute() {

    switch (state) {

      case OUT:
        climber.midClimberArmDown(MID_CLARM_SPEED_OUT);
        break;
      case IN:
        climber.midClimberArmUp(MID_CLARM_SPEED_IN);
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
    climber.stopMid();
  }

}