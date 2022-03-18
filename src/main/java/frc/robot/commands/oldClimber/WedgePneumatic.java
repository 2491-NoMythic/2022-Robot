package frc.robot.commands.oldClimber;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.OldClimber;
import frc.robot.subsystems.OldClimber.RungLockState;

public class WedgePneumatic extends CommandBase {

    private OldClimber climber;
    private RungLockState targetState;

    /**
     * out makes solinoid out
     * in makes solinoid in
     * 
     * @param climber
     * @param WedgeState
     * @param wedgeOut
     */
    public WedgePneumatic(OldClimber climber, RungLockState pneumaticState) {
        this.climber = climber;
        addRequirements(climber);

        targetState = pneumaticState;
    }

    @Override
    public void initialize() {
        climber.setLockState(targetState);
    }

    @Override
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
