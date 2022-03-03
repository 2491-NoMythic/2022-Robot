package frc.robot.commands.intake;

import static frc.robot.settings.Constants.Intake.*;

public enum Direction{
    IN,
    OUT,
    STOP;

    public double getSpeed(){
        switch (this){
            case IN:
                return IN_SPEED;
            case OUT:
                return OUT_SPEED;
            case STOP:
                return 0;
        }
        return 0;
    }
}
