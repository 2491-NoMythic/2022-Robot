package frc.robot.commands.drivetrain;
import java.util.HashSet;
import java.util.Set;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.Command;
//All Hail the Glorious Shotgun Princess
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.subsystems.Drivetrain;

public class Drive implements Command {
    private Drivetrain drivetrain;
    double turnSpeed, lastLeftSpeed, lastRightSpeed; 
    
    double currentLeftSpeed = 0;
    double currentRightSpeed = 0;

    @Override
    public Set<Subsystem> getRequirements() {
        Set<Subsystem> requirements = new HashSet<Subsystem>();
        requirements.add(drivetrain);
        return requirements;
    }

    public Drive(Drivetrain drivetrain)
    {
        this.drivetrain = drivetrain;
    }

    public void execute()
    {
        drivetrain.setDrive(currentLeftSpeed, currentRightSpeed);
    }



}



