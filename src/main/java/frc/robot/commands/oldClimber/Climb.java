package frc.robot.commands.oldClimber;

import edu.wpi.first.wpilibj.PS4Controller;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.settings.Constants.Ps4;
import frc.robot.subsystems.OldClimber;
import static frc.robot.settings.Constants.Climber.*;

public class Climb extends CommandBase {
  private PS4Controller ps4controller;
  OldClimber climber;
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
  public Climb(OldClimber climber, PS4Controller ps4controller) {
    this.climber = climber;
    this.ps4controller = ps4controller;
    addRequirements(climber);
    
  }


  @Override
  public void execute() {
    // if (ps4controller.getPOV() == 0) { // raise arms
    //   if (climber.isClimberFullyOut()) {
    //     return;
    //   } else climber.climberOut(CLIMBER_SPEED_OUT);
    // }
    // if (ps4controller.getPOV() == 180) { // retract arms
    //   if (climber.isClimberFullyIn()) {
    //     return;
    //   } else climber.climberIn(CLIMBER_SPEED_IN);
    // }
    // switch (state) {
    //   case OUT:
    //     climber.climberOut(CLIMBER_SPEED);
    //     break;
    //   case IN:
    //     climber.climberIn(CLIMBER_SPEED);
    //     break;
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