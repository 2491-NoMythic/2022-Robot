package frc.robot.commands.Climber;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Climber;


enum PneumaticState {
  In,
  Out
}

public class WedgePneumatic extends CommandBase {

    Climber climber;
    PneumaticState state;
    

    /**
     * out makes solinoid out
     * in makes solinoid in
     * @param climber
     * @param PneumaticState
     * @param wedgeOut
     */
      public WedgePneumatic(Climber climber, PneumaticState pneumaticState) {
        this.climber = climber;
        addRequirements(climber);

        state = pneumaticState;
      }

      public void initialize(){
        
        switch(state){

          case Out:
          climber.toggleLock(true);
            break;
          case In:
          climber.toggleLock(false);
            break;
        }

      }

      public boolean isFinished(){

        return true;
      }
    
    
}
