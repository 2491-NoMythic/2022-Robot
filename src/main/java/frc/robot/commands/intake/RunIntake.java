package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intake;
import static frc.robot.settings.Constants.Intake.*;

public class RunIntake extends CommandBase {
    private Intake intake;
    private Direction left;
    private Direction right;

    public enum Direction{
        IN,
        OUT,
        STOP,
    }

    public RunIntake(Intake intake, Direction left, Direction right) {
        this.intake = intake;
        this.left = left;
        this.right = right;
        addRequirements(intake);
    }
        
    @Override
    public void initialize() {
        intake.rightIntake(convertSpeed(right));
        intake.leftIntake(convertSpeed(left));
    }

    private static double convertSpeed(Direction d){
        switch (d){
            case IN:
            return IN_SPEED;
            case OUT:
            return OUT_SPEED;
            case STOP:
            return 0;
        }
        return 0;
    }

    @Override
    public void end(boolean interrupted) {
        intake.rightIntake(0);
        intake.leftIntake(0);
    }

}
