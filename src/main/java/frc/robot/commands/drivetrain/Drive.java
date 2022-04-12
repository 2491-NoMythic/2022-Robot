package frc.robot.commands.drivetrain;


import java.security.PrivilegedAction;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;
import static frc.robot.settings.Constants.Drivetrain.*;

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
        drivetrain.brakeMode();
    }

    @Override
    public void execute()
    {
        double speedManager = drivetrain.GetSpeedManager();
       SmartDashboard.putNumber("Slider", speedManager);
        var robotTurnSpeed = driveStick.getZ();
        currentLeftSpeed = driveStick.getY() * -1 + robotTurnSpeed;
        currentRightSpeed = driveStick.getY() * -1 - robotTurnSpeed;
        drivetrain.setDrive(currentLeftSpeed * speedManager, currentRightSpeed * speedManager);

        if(driveStick.getRawButton(SLOW_BUTTON_ID))
        {
            drivetrain.SetSlowSpeedMode();
        }
        else
        {
            drivetrain.SetNormalSpeedMode();
        }
    }



}



