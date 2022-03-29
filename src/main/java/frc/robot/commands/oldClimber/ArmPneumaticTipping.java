package frc.robot.commands.oldClimber;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.OldClimber;
import frc.robot.settings.Constants;
import frc.robot.settings.Constants.Ps4;

public class ArmPneumaticTipping extends CommandBase {

    OldClimber climber;
    Timer timer;
    ArmTipState targetState;

    public enum ArmTipState {
        UP,
        DOWN
    }

    /**
     * 
     * @param climber
     * @param armTipState
     */

    public ArmPneumaticTipping(OldClimber climber, ArmTipState armTipState) {
        this.climber = climber;
        timer = new Timer();
        addRequirements(climber);
        targetState = armTipState;
    }

    @Override
    public void initialize() {
        timer.reset();
        timer.start();
        switch (targetState) {

            case UP:
                climber.setArmUp();
                break;

            case DOWN:
                climber.setArmDown();
                break;
        }

    }

    @Override
    public boolean isFinished() {
        return timer.get() >= Constants.Climber.TIME_FOR_ARM_TO_GO_UP;
    }

}
