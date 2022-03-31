package frc.robot.commands.newClimber;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.NewClimber;
import frc.robot.settings.Constants;
import frc.robot.ArmTipState;

public class ArmPneumaticTippingTraverse extends CommandBase {

    private NewClimber climber;
    private Timer timer;
    private ArmTipState targetState;

    /**
     * 
     * @param climber
     * @param traverseArmTipState
     */

    public ArmPneumaticTippingTraverse(NewClimber climber, ArmTipState traverseArmTipState) {
        this.climber = climber;
        timer = new Timer();
        addRequirements(climber);
        targetState = traverseArmTipState;
    }

    @Override
    public void initialize() {
        timer.reset();
        timer.start();
        switch (targetState) {

            case OUT:
                climber.setTraverseArmOut();
                break;

            case IN:
                climber.setTraverseArmIn();
                break;
        }

    }

    @Override
    public boolean isFinished() {
        return timer.get() >= Constants.Climber.TIME_FOR_TRAVERSE_ARM_TO_GO_UP;
    }

}
