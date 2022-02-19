package frc.robot.commands.Climber;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Climber.RungLockState;

public class WedgePneumatic extends CommandBase {

    private Climber climber;
    private RungLockState targetState;

    /**
     * out makes solinoid out
     * in makes solinoid in
     * 
     * @param climber
     * @param WedgeState
     * @param wedgeOut
     */
    public WedgePneumatic(Climber climber, RungLockState pneumaticState) {
        this.climber = climber;
        addRequirements(climber);

        targetState = pneumaticState;
    }

    public void initialize() {
        climber.setLockState(targetState);
    }

    public boolean isFinished() {

        switch (targetState) {
            case Locked:
                return climber.isLockFullyLocked();
            case Unlocked:
                return climber.isLockFullyUnlocked();
        }
        return false;
    }

}
