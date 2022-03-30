package frc.robot.commands.newClimber;
import frc.robot.commands.drivetrain.ForwardDistance;
import frc.robot.commands.intake.MoveArm;
import frc.robot.commands.intake.MoveArm.IntakeArmState;
import frc.robot.commands.newClimber.ArmPneumaticTippingMid.MidArmTipState;
import frc.robot.commands.newClimber.ArmPneumaticTippingTraverse.TraverseArmTipState;
import frc.robot.commands.newClimber.ClimberClimbMid.ArmExtendState;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.NewClimber;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
public class OneButtonClimb extends SequentialCommandGroup{

    public OneButtonClimb(Drivetrain drivetrain, Intake intake, NewClimber climber) {
        addCommands(
            new MoveArm(intake, IntakeArmState.armUp),
            new ArmPneumaticTippingTraverse(climber, TraverseArmTipState.OUT),
            new ClimberClimbMid(climber, ArmExtendState.OUT),
            new ArmPneumaticTippingMid(climber, MidArmTipState.OUT),
            new ForwardDistance(drivetrain, 2.0, -1.0),
            new ArmPneumaticTippingMid(climber, MidArmTipState.IN),
            new ClimberClimbMid(climber, ArmExtendState.IN),
            new ClimberClimbTraverse(climber, ArmExtendState.OUT),
            new ArmPneumaticTippingTraverse(climber, TraverseArmTipState.IN),
            new ClimberClimbTraverse(climber, ArmExtendState.IN),
            new ClimberClimbMid(climber, ArmExtendState.OUT),
            new ClimberClimbMid(climber, ArmExtendState.IN),
            new ArmPneumaticTippingMid(climber, MidArmTipState.IN)

        );
    }
}
