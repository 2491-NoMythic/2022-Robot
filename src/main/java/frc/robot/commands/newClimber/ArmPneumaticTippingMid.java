package frc.robot.commands.newClimber;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.NewClimber;
import frc.robot.settings.Constants;

public class ArmPneumaticTippingMid extends CommandBase {

    NewClimber climber;
    Timer timer;
    MidArmTipState targetState;

    public enum MidArmTipState {
        OUT,
        IN
    }

    /**
     * 
     * @param climber
     * @param midArmTipState
     */

    public ArmPneumaticTippingMid(NewClimber climber, MidArmTipState midArmTipState) {
        this.climber = climber;
        timer = new Timer();
        addRequirements(climber);
        targetState = midArmTipState;
    }

    @Override
    public void initialize() {
        timer.reset();
        timer.start();
        switch (targetState) {

            case OUT:
                climber.setMidArmOut();
                break;

            case IN:
                climber.setMidArmIn();
                break;
        }

    }

    @Override
    public boolean isFinished() {
        return timer.get() >= Constants.Climber.TIME_FOR_MID_ARM_TO_GO_UP;
    }

}
