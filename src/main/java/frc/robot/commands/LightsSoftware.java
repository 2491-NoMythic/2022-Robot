package frc.robot.commands;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.LightsHardware;
public class LightsSoftware extends CommandBase{
private LightsHardware lights;
   public LightsSoftware(LightsHardware lights){
this.lights=lights;
   }
    @Override
    public void execute() {
        // Fill the buffer with the indicator
        lights.leftballindicator();
        lights.rightballindicator();
        lights.prettylights();
      }     
}