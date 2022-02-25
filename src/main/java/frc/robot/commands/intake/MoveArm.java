// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.intake;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intake;


public class MoveArm extends CommandBase {

  public enum IntakeArmState{
    armUp,
    armDown
  }
 
  private Intake intake;
  private IntakeArmState state;
  
/**
 * State is used for the state the arm will move in
 * @param intake
 * @param state
 */
  public MoveArm(Intake intake, IntakeArmState state) {
    this.intake = intake;
    addRequirements(intake);
    this.state = state;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    
    switch(state)
    {
      case armUp:
        intake.setArmUp();
      case armDown:
        intake.setArmDown();
    
    }
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {}

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return true;
  }
}
