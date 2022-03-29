// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intake;

public class DoubleIntake extends CommandBase {

    Intake intake;
    Direction left, right;

    public DoubleIntake(Intake intake, Direction left, Direction right) {
        addRequirements(intake);
        this.intake = intake;
        this.left = left;
        this.right = right;
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        intake.leftIntake(left.getSpeed());
        intake.rightIntake(right.getSpeed());
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        intake.leftIntake(0);
        intake.rightIntake(0);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }
}
