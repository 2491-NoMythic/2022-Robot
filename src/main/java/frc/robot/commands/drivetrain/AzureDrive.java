package frc.robot.commands.drivetrain;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
//All Hail the Glorious Shotgun Princess
import frc.robot.subsystems.Drivetrain;

public class AzureDrive extends CommandBase {
    private Drivetrain drivetrain;
    double turnSpeed, lastLeftSpeed, lastRightSpeed; 
    
    double currentLeftSpeed = 0;
    double currentRightSpeed = 0;
    Joystick driveStick;
    

    public AzureDrive(Drivetrain drivetrain, Joystick driveStick)
    {
      
        this.drivetrain = drivetrain;
        this.driveStick = driveStick;
        addRequirements(drivetrain);
    }

    @Override
    public void execute()
    {
       // double robotTurnSpeed = driveStick.getZ();
        //currentLeftSpeed = driveStick.getY() - robotTurnSpeed;
        //currentRightSpeed = driveStick.getY() + robotTurnSpeed;
        //drivetrain.setDrive(currentLeftSpeed, currentRightSpeed);

        
        double drive_SpeedManager = 1-(driveStick.getRawAxis(3)+1)/+2; 
        if (driveStick.getRawButtonPressed(1))
        {
          drive_SpeedManager = drive_SpeedManager/2;
        }
        else if (driveStick.getRawButtonReleased(1))
        {
          drive_SpeedManager = drive_SpeedManager*2;
        }
          SmartDashboard.putNumber("Slider", drive_SpeedManager);
        //SmartDashboard.
        drivetrain.curvatureDrive(driveStick.getY()*drive_SpeedManager, driveStick.getZ()*drive_SpeedManager, driveStick.getRawButton(1));
      }
}



