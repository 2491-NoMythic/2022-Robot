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

        public static final int LEFT_WINCH_ID = 5;
        public static final int RIGHT_WINCH_ID = 6;

        public static final double TIME_FOR_ARM_TO_GO_UP = 2;

        public static final double CLIMBER_SPEED = .2;
        public static final double TESTING_CLIMBER_SPEED = .1;
    }

    public final class Ps4 {
        private Ps4() {
        }

        public static final int CONTROLLER_ID = 1;
        public static final int CLIMB_BUTTON_ID = 5;    // todo
        public static final int LIGHTS_BUTTON_ID = 6;   // todo
        public static final int INTAKEUP_BUTTON_ID = 4;
        public static final int INTAKEDOWN_BUTTON_ID = 2;
        
        public static final int RIGHT_IN_BUTTON_ID = 8;
        public static final int RIGHT_OUT_BUTTON_ID = 6;
        public static final int LEFT_IN_BUTTON_ID = 7;
        public static final int LEFT_OUT_BUTTON_ID = 5;

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
