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

public class Step1ClimbSetup extends SequentialCommandGroup {
    
    public Step1ClimbSetup(NewClimber climber, Intake intake) {
    
        addCommands(
          new MoveArm(intake, IntakeArmState.armDown),

      
            new ArmPneumaticTippingTraverse(climber, ArmTipState.OUT),

          new ParallelCommandGroup(
            new ClimberClimbTraverse(climber, ArmExtendState.UP),
            new ClimberClimbMid(climber, ArmExtendState.UP)
          )
         
        );
      }
}
