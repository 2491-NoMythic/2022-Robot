package frc.robot.commands.Climber;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Climber;

public class armPneumaticTipping extends CommandBase {

    Climber climber;
    ArmTipState targetState;

    enum ArmTipState {
        Up,
        Down
    }

    /**
     * if armUp = true, the solenoid is extended
     * if armUp = false, the solenoid is retracted
     * 
     * @param climber
     * @param armTipState
     */

    public armPneumaticTipping(Climber climber, ArmTipState armTipState) {
        this.climber = climber;

        addRequirements(climber);
        targetState = armTipState;
    }

    public void initialize() {
        // climber.setLockState(isArmUp);

        switch (targetState) {

            case Up:
                climber.setArmUp();
                break;

            case Down:
                climber.setArmDown();
                break;
        }

    }

    public boolean isFinished() {

        switch (targetState) {
            case Up:
                return climber.isArmFullyUp();
            case Down:
                return !climber.isArmFullyUp();
        }
        return false;
    }

}
