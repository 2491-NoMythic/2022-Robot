package frc.robot.settings;

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

        public static final double CLARM_SPEED_OUT = .5;
        public static final double CLARM_SPEED_IN = .7;
        public static final double MID_CLARM_SPEED_IN = .5;
        public static final double MID_CLARM_SPEED_OUT = .7;
        public static final double TRAVERSE_CLARM_SPEED_IN = .5;
        public static final double TRAVERSE_CLARM_SPEED_OUT = .7;
        // clarm means climber arm
        // this has the constants for both the new and old climbers together

        public static final double ENCODER_TICKS_TO_ARMS_LENGTH = (1 / 2048.0) * (1 / 23.73) * (1.25 * Math.PI)
                * (1 / 24.0);
        // nu> motor > spool > inches > arm lengths

        public static final double ARM_LENGTHS_TO_ENCODER_TICKS = ENCODER_TICKS_TO_ARMS_LENGTH / 1;
    }

    public final class Ps4 {
        private Ps4() {
        }

        public static final int CONTROLLER_ID = 1;
        public static final int CLIMB_BUTTON_ID = 5; // todo
        public static final int LIGHTS_BUTTON_ID = 13; // todo
        public static final int INTAKEUP_BUTTON_ID = 4;
        public static final int INTAKEDOWN_BUTTON_ID = 2;
        public static final int INTAKEFILTER_BUTTON_ID = 1;
        public static final int RIGHT_IN_BUTTON_ID = 8;
        public static final int RIGHT_OUT_BUTTON_ID = 6;
        public static final int LEFT_IN_BUTTON_ID = 7;
        public static final int LEFT_OUT_BUTTON_ID = 5;
        public static final int LIGHTS_OFF_BUTTON_ID = 14;
        public static final int OUT_ARM_BUTTON_ID = 270;
        public static final int IN_ARM_BUTTON_ID = 90;
        public static final int EXTEND_ARM_BUTTON_ID = 0;
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

        public static final double ENCODER_TICKS_TO_DISTANCE = (1.0 / 2048) * (8.0 / 1) * (2 * Math.PI);
        // Units, ChainRatio, WheelSize
        // 1/2048, 8:1, 2pi
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
