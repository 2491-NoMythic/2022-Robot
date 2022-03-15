package frc.robot.commands.climber;

import edu.wpi.first.wpilibj.PS4Controller;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.settings.Constants.Ps4;
import frc.robot.subsystems.Climber;
import static frc.robot.settings.Constants.Climber.*;

public class ClimberClimb extends CommandBase {
  private PS4Controller ps4controller;
  Climber climber;
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
  public ClimberClimb(Climber climber, ArmExtendState armState) {
    this.climber = climber;
    this.ps4controller = ps4controller;
    addRequirements(climber);
    state = armState;
  }


  @Override
  public void execute() {

    switch (state) {

      case OUT:
        climber.climberOut(CLIMBER_SPEED_UP);
        break;
      case IN:
        climber.climberIn(CLIMBER_SPEED_DOWN);
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