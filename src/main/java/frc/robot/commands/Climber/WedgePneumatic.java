package frc.robot.commands.Climber;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Climber;

public class WedgePneumatic extends CommandBase {

    Climber climber;
    boolean isWedgeOut;

  /**
   * if wedgeOut equals true, the solenoid will be extended
   * if wedgeOut equals false, the solenoud will be retracted 
   * @param climber
   * @param wedgeOut
   */
    public WedgePneumatic(Climber climber, boolean wedgeOut ) {
        this.climber = climber;
        
        addRequirements(climber);
        isWedgeOut = wedgeOut;
      }

      public void initialize(){
        climber.toggleBreak(isWedgeOut);

      }
    
    
}
