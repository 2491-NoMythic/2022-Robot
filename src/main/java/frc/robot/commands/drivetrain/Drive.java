package frc.robot.commands.drivetrain;


import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

public class Drive extends CommandBase {
    private Drivetrain drivetrain;
    double turnSpeed;
    double lastLeftSpeed;
    double lastRightSpeed; 
    
    double currentLeftSpeed = 0;
    double currentRightSpeed = 0;
    Joystick driveStick = new Joystick(0);

    public Drive(Drivetrain drivetrain)
    {
        addRequirements(drivetrain);
        this.drivetrain = drivetrain;
    }

    @Override
    public void initialize() {
        drivetrain.coastMode();
    }

    @Override
    public void execute()
    {
        double speedManager = 1-(driveStick.getRawAxis(3)+1)/2;
        SmartDashboard.putNumber("Slider", speedManager);
        var robotTurnSpeed = driveStick.getZ();
        currentLeftSpeed = driveStick.getY() * -1 + robotTurnSpeed;
        currentRightSpeed = driveStick.getY() * -1 - robotTurnSpeed;
        drivetrain.setDrive(currentLeftSpeed * speedManager, currentRightSpeed * speedManager);
    }



}



