package frc.robot.commands.drivetrain;
import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;
public class ForwardDistance extends CommandBase{
    private Drivetrain drivetrain;
    private Timer timer;
    private double driveTime;
    private double power;

    /**
     * Simple Movment Forward
     * @param base
     * @param driveTime
     * @param power
     */
  public ForwardDistance(Drivetrain base, double driveTime, double power){
    addRequirements(base);
    drivetrain = base;
    timer = new Timer();
    this.driveTime = driveTime;
    this.power = power;
  }
    @Override
    public void initialize() {
      drivetrain.setDrive(ControlMode.PercentOutput, power, power);
      timer.start();
    }
    @Override
    public void end(boolean interrupted) {
        timer.stop();
        drivetrain.setDrive(ControlMode.PercentOutput, 0, 0);
    }
    @Override
    public boolean isFinished() {
        return timer.get() >= driveTime;
    }
}
