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

        public static final int BREAK_CHANNEL = 0;
        public static final int ARM_FORWARD_CHANNEL = 0;
        public static final int ARM_REVERSE_CHANNEL = 0;
        public static final int LEFT_WINCH_ID = 0;
        public static final int RIGHT_WINCH_ID = 0;
    }

    /**
     * drivetrain substyem constants
     * TODO: link
     */
    public final class Drivetrain {
        private Drivetrain() {}
        public static final int LEFT_LEAD_ID= 1;
        public static final int LEFT_FOLLOW_ID= 3;
        public static final int RIGHT_LEAD_ID= 2;
        public static final int RIGHT_FOLLOW_ID= 4;
        
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
