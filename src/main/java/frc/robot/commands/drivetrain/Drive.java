package frc.robot.commands.drivetrain;
import java.util.HashSet;
import java.util.Set;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
//All Hail the Glorious Shotgun Princess
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.subsystems.Drivetrain;

public class Drive implements Command {
    private Drivetrain drivetrain;
    double turnSpeed;
    double lastLeftSpeed;
    double lastRightSpeed; 
    
    double currentLeftSpeed = 0;
    double currentRightSpeed = 0;
    Joystick driveStick = new Joystick(0);

    @Override
    public Set<Subsystem> getRequirements() {
        Set<Subsystem> requirements = new HashSet<>();
        requirements.add(drivetrain);
        return requirements;
    }

    public Drive(Drivetrain drivetrain)
    {
        this.drivetrain = drivetrain;
    }

    @Override
    public void execute()
    {
        double speedManager = 1-(driveStick.getRawAxis(3)+1)/2;
        SmartDashboard.putNumber("Slider", speedManager);
        var robotTurnSpeed = driveStick.getZ();
        currentLeftSpeed = driveStick.getY() * -1 - robotTurnSpeed;
        currentRightSpeed = driveStick.getY() * -1 + robotTurnSpeed;
        drivetrain.setDrive(currentLeftSpeed * speedManager, currentRightSpeed * speedManager);
    }



}



