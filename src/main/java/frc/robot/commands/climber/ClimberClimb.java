package frc.robot.commands.climber;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Climber;
import static frc.robot.settings.Constants.Climber.CLIMBER_SPEED;
import static frc.robot.settings.Constants.Climber.TESTING_CLIMBER_SPEED;

public class ClimberClimb extends CommandBase {

  Climber climber;
  ArmExtendState state;
  double currentSpeed;

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
    addRequirements(climber);

    state = armState;
    currentSpeed = CLIMBER_SPEED;
  }

  public ClimberClimb(Climber climber, ArmExtendState armState, boolean testingSpeed)
  {
    //new ClimberClimb(climber, armState);
    this.climber = climber;
    addRequirements(climber);

    state = armState;

    if (testingSpeed == true)
    {
      currentSpeed = TESTING_CLIMBER_SPEED;
    }
    else
    {
      currentSpeed = CLIMBER_SPEED;
    }
  }


  @Override
  public void execute() {

    switch (state) {

      case OUT:
        climber.climberOut(currentSpeed);
        break;
      case IN:
        climber.climberIn(currentSpeed);
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