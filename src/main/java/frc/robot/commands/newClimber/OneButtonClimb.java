package frc.robot.commands.newClimber;
import frc.robot.ArmExtendState;
import frc.robot.ArmTipState;
import frc.robot.commands.drivetrain.ForwardDistance;
import frc.robot.commands.intake.MoveArm;
import frc.robot.commands.intake.MoveArm.IntakeArmState;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.NewClimber;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
public class OneButtonClimb extends SequentialCommandGroup{

    public OneButtonClimb(Drivetrain drivetrain, Intake intake, NewClimber climber) {
        addCommands(
            new MoveArm(intake, IntakeArmState.armUp),
            new ArmPneumaticTippingTraverse(climber, ArmTipState.OUT),
            new ClimberClimbMid(climber, ArmExtendState.UP),
            new ArmPneumaticTippingMid(climber, ArmTipState.OUT),
            new ForwardDistance(drivetrain, 2.0, -1.0),
            new ArmPneumaticTippingMid(climber, ArmTipState.IN),
            new ClimberClimbMid(climber, ArmExtendState.DOWN),
            new ClimberClimbTraverse(climber, ArmExtendState.UP),
            new ArmPneumaticTippingTraverse(climber, ArmTipState.IN),
            new ClimberClimbTraverse(climber, ArmExtendState.DOWN),
            new ClimberClimbMid(climber, ArmExtendState.UP),
            new ClimberClimbMid(climber, ArmExtendState.DOWN),
            new ArmPneumaticTippingMid(climber, ArmTipState.IN)

        );
    }
}
