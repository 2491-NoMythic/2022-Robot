package frc.robot.commands.Climber;
import com.fasterxml.jackson.databind.node.BooleanNode;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Climber;

public class armPneumaticTipping extends CommandBase {

    Climber climber;
    boolean isArmUp;
/**
 * if armUp = true, the solenoid is extended
 * if armUp = false, the solenoid is retracted
 * @param climber
 * @param armUp
 */

      public armPneumaticTipping(Climber climber, boolean armUp ) {
        this.climber = climber;
        
        addRequirements(climber);
        isArmUp = armUp;
      }

      public void initialize(){
        climber.toggleBreak(isArmUp);

      }

      public boolean isFinished(){

        return true;
      }
    
    
}
