package frc.robot.commands.drivetrain;
import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;


public class GoForwardInInches extends CommandBase{
    private Drivetrain drivetrain;
    private double power;
    private double ticksNeeded;
    private double ticksTraveled;

    public GoForwardInInches(Drivetrain drivetrain, double power, double inches){
        addRequirements(drivetrain);
        this.drivetrain = drivetrain;
        this.power = power;
        ticksNeeded = drivetrain.convertInchesToTicks(inches);
    }

    @Override
    public void initialize() {
      ticksTraveled = drivetrain.getLeftEncoderValue();
      //TODO check that the encoder is reset each time the command runs 
      drivetrain.setDrive(ControlMode.PercentOutput, power, power);      
    }
    @Override
    public void end(boolean interrupted) {
        drivetrain.setDrive(ControlMode.PercentOutput, 0, 0);
    }
    @Override
    public boolean isFinished() {
      return drivetrain.getLeftEncoderValue() + ticksNeeded == ticksTraveled;
    }
}