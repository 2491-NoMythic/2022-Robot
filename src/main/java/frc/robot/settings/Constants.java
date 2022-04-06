package frc.robot.settings;

import edu.wpi.first.wpilibj2.command.FunctionalCommand;

/**
 * <p>
 * Robot system constants. Solenoids have a CHANNEL, motor controllers have an
 * ID
 * </p>
 * 
 * <p>
 * Import this class using <code>import static Constants.Drivetrain.*</code> to
 * use a constant without the
 * <code>Constants.Drivetrain</code> qualifier. The patter works with wildcards
 * or single static variables
 * </p>
 */
public final class Constants {
    private Constants() {
    }

    /**
     * climber substyem constants
     * TODO: link
     */
    public final class Climber {
        private Climber() {
        }

        public static final int RUNG_LOCK_FORWARD_CHANNEL = 0;
        public static final int RUNG_LOCK_REVERSE_CHANNEL = 0;

        public static final int ARM_FORWARD_CHANNEL = 2;
        public static final int ARM_REVERSE_CHANNEL = 3;
        public static final int MID_ARM_FORWARD_CHANNEL = 2;
        public static final int MID_ARM_REVERSE_CHANNEL = 3;
        public static final int TRAVERSE_ARM_FORWARD_CHANNEL = 0;
        public static final int TRAVERSE_ARM_REVERSE_CHANNEL = 0;

        public static final int LEFT_WINCH_ID = 5;
        public static final int RIGHT_WINCH_ID = 6;
        public static final int MID_WINCH_ID = 5;
        public static final int TRAVERSE_WINCH_ID = 6;

        public static final double TIME_FOR_MID_ARM_TO_GO_UP = 2;
        public static final double TIME_FOR_TRAVERSE_ARM_TO_GO_UP = 2;
        public static final double TIME_FOR_ARM_TO_GO_UP = 2;

        public static final double ARM_SPEED_OUT = .8;
        public static final double ARM_SPEED_IN = .8;
        public static final double MID_ARM_SPEED_IN = .5;
        public static final double MID_ARM_SPEED_OUT = .7;
        public static final double TRAVERSE_ARM_SPEED_IN = .5;
        public static final double TRAVERSE_ARM_SPEED_OUT = .7;
        public static final double ARM_SPEED_CALIBRATE = .3;
        // clarm means climber arm
        // this has the constants for both the new and old climbers together

        public static final double ENCODER_TICKS_TO_ARMS_LENGTH = 
            (1/2048.0)*(1/23.73)*(1.25*Math.PI)*(1/24.0);
        //nu> motor   >  spool  >    inches    > arm lengths

        public static final double ARM_LENGTHS_TO_ENCODER_TICKS = 1.0 / ENCODER_TICKS_TO_ARMS_LENGTH;

        public static final double CLIMBER_MOTOR_KP = 0.75;
        public static final double CLIMBER_MOTOR_KD = 0.05;
        public static final double CLIMBER_MOTOR_ALLOWABLE_ERROR = 300;
    }

    public final class Ps4 {
        private Ps4() {
        }

        public static final int CONTROLLER_ID = 1;
        public static final int CLIMB_BUTTON_ID = 5; // todo
        public static final int LIGHTS_BUTTON_ID = 13;
        public static final int INTAKEUP_BUTTON_ID = 4;
        public static final int INTAKEDOWN_BUTTON_ID = 2;
        public static final int INTAKEFILTER_BUTTON_ID = 1;
        public static final int RIGHT_IN_BUTTON_ID = 8;
        public static final int RIGHT_OUT_BUTTON_ID = 6;
        public static final int LEFT_IN_BUTTON_ID = 7;
        public static final int LEFT_OUT_BUTTON_ID = 5;
        public static final int SINGLE_BUTTON_CLIMB = 14;
        public static final int PHASE_1_CLIMB_BUTTON_ID = 270;
        public static final int RETRACT_ARM_BUTTON_ID = 180;

    }

    /**
     * drivetrain substyem constants
     * TODO: link
     */
    public final class Drivetrain {
        private Drivetrain() {
        }

        public static final int LEFT_LEAD_ID = 1;
        public static final int LEFT_FOLLOW_ID = 3;
        public static final int RIGHT_LEAD_ID = 2;
        public static final int RIGHT_FOLLOW_ID = 4;

        public static final double ENCODER_TICKS_TO_INCHES =
            (1.0 / 2048) * (1.0 / 8) * (4 * Math.PI); //TODO: find effective diameter of the wheels.
        //nu>  motor     >   wheel   >    inches

        public static final int GYRO_ID = 1;

        public static final double NORMAL_SPEED = 1;
        public static final double SLOW_SPEED = .5;
        public static final int SLOW_BUTTON_ID = 2;
        //TODO check this with drivers
            
        
    }

    /**
     * intake substyem constants
     * TODO: link
     */
    public final class Intake {
        private Intake() {
        }

        public static final int ARM_FORWARD_CHANNEL = 0;
        public static final int ARM_REVERSE_CHANNEL = 1;
        public static final int LEFT_MOTOR_ID = 7;
        public static final int RIGHT_MOTOR_ID = 8;
        public static final double IN_SPEED = .5;
        public static final double OUT_SPEED = -1;
    }

}
