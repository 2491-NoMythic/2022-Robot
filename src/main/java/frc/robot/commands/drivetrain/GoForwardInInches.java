package frc.robot.commands.drivetrain;
import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;


public class GoForwardInInches extends CommandBase{
  private Drivetrain drivetrain;
  private double power;
  private double ticksNeeded;
  private double initialTicks;
  /**
   * @param drivetrain
   * @param power (0-1)
   * @param inches
   */
  public GoForwardInInches(Drivetrain drivetrain, double power, double inches){
    addRequirements(drivetrain);
    this.drivetrain = drivetrain;
    this.power = Math.copySign(power, inches);
    ticksNeeded = drivetrain.convertInchesToTicks(inches);
  }

  @Override
  public void initialize() {
    initialTicks = drivetrain.getLeftEncoderValue();
    drivetrain.setDrive(ControlMode.PercentOutput, power, power);
  }

  @Override
  public void end(boolean interrupted) {
    drivetrain.setDrive(ControlMode.PercentOutput, 0, 0);
  }

  @Override
  public boolean isFinished() {
    if (ticksNeeded >= 0) {
      return drivetrain.getLeftEncoderValue() >= initialTicks + ticksNeeded;
    } else {
      return drivetrain.getLeftEncoderValue() <= initialTicks + ticksNeeded;
    }
  }
}