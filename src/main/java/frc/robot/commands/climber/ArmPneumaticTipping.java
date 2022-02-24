package frc.robot.commands.climber;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Climber;
import frc.robot.settings.Constants;

public class ArmPneumaticTipping extends CommandBase {

    Climber climber;
    Timer timer;
    ArmTipState targetState;

    enum ArmTipState {
        UP,
        DOWN
    }

    /**
     * if armUp = true, the solenoid is extended
     * if armUp = false, the solenoid is retracted
     * 
     * @param climber
     * @param armTipState
     */

    public ArmPneumaticTipping(Climber climber, ArmTipState armTipState) {
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
