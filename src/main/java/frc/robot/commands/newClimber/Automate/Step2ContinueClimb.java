package frc.robot.commands.newClimber.Automate;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.ArmExtendState;
import frc.robot.ArmTipState;
import frc.robot.commands.intake.MoveArm;
import frc.robot.commands.intake.MoveArm.IntakeArmState;
import frc.robot.commands.newClimber.ArmPneumaticTippingMid;
import frc.robot.commands.newClimber.ArmPneumaticTippingTraverse;
import frc.robot.commands.newClimber.ClimberClimbMid;
import frc.robot.commands.newClimber.ClimberClimbTraverse;
import frc.robot.commands.oldClimber.ArmPneumaticTipping;
import frc.robot.commands.oldClimber.ClimberClimb;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.NewClimber;

public class Step2ContinueClimb extends SequentialCommandGroup {
    
    public Step2ContinueClimb(NewClimber climber, Intake intake) {
    
        addCommands(
        
        new ParallelCommandGroup(
          new ArmPneumaticTippingMid(climber, ArmTipState.OUT),
          new ClimberClimbMid(climber, ArmExtendState.UP)
        ),

        new ArmPneumaticTippingTraverse(climber, ArmTipState.IN),
        
        new ParallelRaceGroup(
          new ArmPneumaticTippingMid(climber, ArmTipState.IN),
          new ClimberClimbMid(climber, ArmExtendState.UP)
        )
         
        );
      }
}
