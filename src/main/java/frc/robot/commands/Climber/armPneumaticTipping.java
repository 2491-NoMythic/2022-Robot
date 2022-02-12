package frc.robot.commands.Climber;
import com.fasterxml.jackson.databind.node.BooleanNode;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Climber;

public class armPneumaticTipping extends CommandBase {

    Climber climber;
    ArmTipState state;

    enum ArmTipState {
      Up,
      Down
    }
/**
 * if armUp = true, the solenoid is extended
 * if armUp = false, the solenoid is retracted
 * @param climber
 * @param armTipState
 */

      public armPneumaticTipping(Climber climber, ArmTipState armTipState ) {
        this.climber = climber;
        
        addRequirements(climber);
        state = armTipState;
      }

      public void initialize(){
        climber.toggleLock(isArmUp);

        switch(state){

          case Up:
          climber.toggleLock(activateBreak);
        }

      }

      public boolean isFinished(){

        return true;
      }
    
    
}
