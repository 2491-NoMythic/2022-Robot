package frc.robot.commands.newClimber;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.NewClimber;
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
  public void initialize() {
    switch (state) {
      case UP:
        climber.setMidArmPostion(1.0);
        break;
      case DOWN:
        climber.setMidArmPostion(0.0);
        break;
    }
  }

  @Override
  public boolean isFinished() {
      switch (state){
        case UP:
         return climber.isMidClimberFullyUp();
        case DOWN:
          return climber.isMidClimberFullyDown();
        }
      return false;
  }

  @Override
  public void end(boolean interrupted) {
    climber.stopMid();
  }
}