package frc.robot.commands.newClimber;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.NewClimber;
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
        climber.setTraverseArmPostion(1.0);
        break;
      case DOWN:
        climber.setTraverseArmPostion(0.0);
        break;
    }
  }

  @Override
  public boolean isFinished() {
      switch (state) {
        case UP:
          return climber.isTraverseClimberFullyOut();
        case DOWN:
          return climber.isTraverseClimberFullyIn();
      }
      return false;
  }

  @Override
  public void end(boolean interrupted) {
    climber.stopMid();
  }

}