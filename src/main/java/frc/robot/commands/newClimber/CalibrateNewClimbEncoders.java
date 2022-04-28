// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.newClimber;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.NewClimber;
import static frc.robot.settings.Constants.NewClimberConstants.*;

public class CalibrateNewClimbEncoders extends CommandBase {
    private NewClimber newClimber;

    /**
     * Moves the newClimber arms down to the bottom hall-effect sensors, then zeroes
     * out the encoders.
     */
    public CalibrateNewClimbEncoders(NewClimber newClimber) {
        this.newClimber = newClimber;
        addRequirements(newClimber);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        newClimber.traverseClimberArmDown(ARM_SPEED_CALIBRATE);
        newClimber.midClimberArmDown(ARM_SPEED_CALIBRATE);
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        newClimber.stopMid();
        newClimber.stopTraverse();
        if (!interrupted) {
            newClimber.resetEncoders();
        }
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return newClimber.isMidClimberFullyIn() && newClimber.isTraverseClimberFullyIn();
    }
}
