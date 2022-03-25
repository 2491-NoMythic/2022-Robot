package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.LightsHardware;
import frc.robot.subsystems.Pixy2SubSystem;
import frc.robot.FakePixie;
public class LightsSoftware extends CommandBase{
   private LightsHardware lights;
   private Pixy2SubSystem pixie;
   private FakePixie testPixie;
   public LightsSoftware(LightsHardware lights, Pixy2SubSystem pixie){
      this.lights=lights;
      this.pixie=pixie;
   }

   @Override
   public void execute() {
      // Fill the buffer with the indicator
      lights.prettyleftlights();
      lights.leftballindicator(pixie.getLeftCargoState());
      lights.rightballindicator(pixie.getRightCargoState());
      lights.prettyrightlights();
      lights.dataSetter();
   }     
   @Override
   public void end(boolean interrupted) {
      lights.lightsout();
      lights.dataSetter();
   }
}