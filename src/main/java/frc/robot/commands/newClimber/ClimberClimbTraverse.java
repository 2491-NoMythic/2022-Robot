package frc.robot.commands.newClimber;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.NewClimber;
import static frc.robot.settings.Constants.Climber.*;
import frc.robot.ArmExtendState;

public class ClimberClimbTraverse extends CommandBase {
  private NewClimber climber;
  private ArmExtendState state;
  
  /**
   * 
   * Moves arm up and down
   * 
   * @param climber subsystem
   * @param armExtendState choose moving arm up or down
   */
  public ClimberClimbTraverse(NewClimber climber, ArmExtendState out) {
    this.climber = climber;
    addRequirements(climber);
    state = out;
  }

  @Override
  public void execute() {

    switch (state) {
      case UP:
        climber.midClimberArmDown(MID_ARM_SPEED_OUT);
        break;
      case DOWN:
        climber.midClimberArmUp(MID_ARM_SPEED_IN);
        break;
    }
  }

  @Override
  public boolean isFinished() {
      switch (state){

      case UP:
        return climber.isClimberFullyOut();
      case DOWN:
        return climber.isClimberFullyIn();
      }
      return false;
  }

  @Override
  public void end(boolean interrupted) {
    climber.stopMid();
  }

}