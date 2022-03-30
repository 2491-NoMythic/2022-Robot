package frc.robot.commands.newClimber;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.NewClimber;
import static frc.robot.settings.Constants.Climber.*;

public class ClimberClimbTraverse extends CommandBase {
  NewClimber climber;
  frc.robot.commands.newClimber.ClimberClimbMid.ArmExtendState state;
  
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
  public ClimberClimbTraverse(NewClimber climber, frc.robot.commands.newClimber.ClimberClimbMid.ArmExtendState out) {
    this.climber = climber;
    addRequirements(climber);
    state = out;
  }


  @Override
  public void execute() {

    switch (state) {

      case OUT:
        climber.midClimberArmDown(MID_ARM_SPEED_OUT);
        break;
      case IN:
        climber.midClimberArmUp(MID_ARM_SPEED_IN);
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