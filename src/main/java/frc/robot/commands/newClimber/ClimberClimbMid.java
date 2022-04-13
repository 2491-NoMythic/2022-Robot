package frc.robot.commands.newClimber;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.NewClimber;
import static frc.robot.settings.Constants.NewClimber.*;
import frc.robot.ArmExtendState;

public class ClimberClimbMid extends CommandBase {
  private NewClimber climber;
  private ArmExtendState state;
 
  /**
   * 
   * Moves arm up and down
   * 
   * @param ArmExtendState
   *
   */
  public ClimberClimbMid(NewClimber climber, ArmExtendState armState) {
    this.climber = climber;
    addRequirements(climber);
    state = armState;
  }

  @Override
  public void execute() {
    switch (state) {
      case DOWN:
        climber.setMidArmPostion(1.0);
        break;
      case UP:
        climber.setMidArmPostion(0.0);
        break;
    }
  }

  @Override
  public boolean isFinished() {
      switch (state){
        case UP:

         return climber.isMidClimberFullyOut();

        case DOWN:
          return climber.isMidClimberFullyIn();
        }
      return false;
  }

  @Override
  public void end(boolean interrupted) {
    climber.stopMid();
  }

}