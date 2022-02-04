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

        public static final int BREAK_CHANNEL = 0;

        public static final int ARM_FORWARD_CHANNEL = 0;
        public static final int ARM_REVERSE_CHANNEL = 0;

        public static final int LEFT_WINCH_ID = 0;
        public static final int RIGHT_WINCH_ID = 0;

        public static final int TOP_RIGHT_LIMIT_SWITCH = 0;
        public static final int BOTTOM_RIGHT_LIMIT_SWITCH = 0;
        public static final int TOP_LEFT_LIMIT_SWITCH = 0;
        public static final int BOTTOM_LEFT_LIMIT_SWITCH = 0;
    }

    /**
     * drivetrain substyem constants
     * TODO: link
     */
    public final class Drivetrain {
        public final static int driveLeftMotor1= 2491;
        public final static int driveLeftMotor2= 2491;
        public final static int driveRightMotor1= 2491;
        public final static int driveRightMotor2= 2491;
    }

    /**
     * intake substyem constants
     * TODO: link
     */
    public final class Intake {
        private Intake() {
        }

    }
}
