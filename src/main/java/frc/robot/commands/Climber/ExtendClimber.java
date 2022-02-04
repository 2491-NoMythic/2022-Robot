package frc.robot.commands.Climber;

import java.util.HashSet;
import java.util.Set;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.subsystems.Climber;

public class ExtendClimber implements Command {

  Climber climber;
  boolean stillRunning;

  @Override
  public Set<Subsystem> getRequirements() {
    Set<Subsystem> requirements = new HashSet<Subsystem>();
    requirements.add(climber);
    return requirements;
  }

  public ExtendClimber(Climber climber) {
    this.climber = climber;
  }

  @Override
  public void initialize() {
    stillRunning = true;
  }

  @Override
  public void execute() {
    stillRunning = climber.climberOut(.5);
  }

  @Override
  public boolean isFinished() {
    return !stillRunning;
  }

  @Override
  public void end(boolean interrupted) {
    climber.stop();
  }

}