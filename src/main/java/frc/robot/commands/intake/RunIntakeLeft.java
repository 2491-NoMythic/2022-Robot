package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intake;
import static frc.robot.settings.Constants.Intake.*;

public class RunIntakeLeft extends CommandBase {
    private Intake intake;
    private Direction left;


    public RunIntakeLeft(Intake intake, Direction left) {
        this.intake = intake;
        this.left = left;
        addRequirements(intake);
    }
        
    @Override
    public void initialize() {
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
        intake.leftIntake(0);
    }

}
