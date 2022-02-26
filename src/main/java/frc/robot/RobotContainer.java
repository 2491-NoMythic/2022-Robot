// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.commands.drivetrain.Drive;
import frc.robot.commands.drivetrain.ForwardDistance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import frc.robot.commands.LightsSoftware;

import static frc.robot.settings.Constants.Ps4.*;

import frc.robot.commands.PointAtCargo;
import frc.robot.commands.climber.AutomatedClimb;
import frc.robot.commands.drivetrain.BurnIn;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.LightsHardware;
import frc.robot.subsystems.Vision;
import frc.robot.subsystems.Climber.RungLockState;
import frc.robot.subsystems.Intake;
import frc.robot.commands.climber.ClimberClimb;
import frc.robot.commands.climber.ClimberClimb.ArmExtendState;
import frc.robot.commands.climber.WedgePneumatic;
import frc.robot.commands.intake.RunIntakeLeft;
import frc.robot.commands.intake.RunIntakeRight;
import frc.robot.commands.intake.Direction;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in
 * the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of
 * the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final LightsHardware lights;

  private final Climber climber;
  private final Drivetrain drivetrain;
  private final Vision vision;
  private final Intake intake;

  private final AutomatedClimb automatedClimb;
  private final Drive defaultDriveCommand;
  private final PointAtCargo pointAtCargo;

  private final Joystick ps4;
  private JoystickButton climb;
  private JoystickButton lightsToggle;

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    climber = new Climber();
    drivetrain = new Drivetrain();
    vision = new Vision();
    intake = new Intake();
    lights = new LightsHardware();

    defaultDriveCommand = new Drive(drivetrain);
    automatedClimb = new AutomatedClimb(climber);
    pointAtCargo = new PointAtCargo(drivetrain, vision);

    ps4 = new Joystick(CONTROLLER_ID);

    drivetrain.setDefaultCommand(defaultDriveCommand);

    configureButtonBindings();
    configureSmartDashboard();
  }

  private void configureSmartDashboard() {
    SmartDashboard.putData("Burn In", new BurnIn(drivetrain));
    SmartDashboard.putData("climbUp", new ClimberClimb(climber, ArmExtendState.OUT));
    SmartDashboard.putData("climbDown", new ClimberClimb(climber, ArmExtendState.IN));
    SmartDashboard.putData("armLock", new WedgePneumatic(climber, RungLockState.Locked));
    SmartDashboard.putData("forwardOneSecond", new ForwardDistance(drivetrain, 1, .25));
    SmartDashboard.putData("RightIntakeOut", new RunIntakeRight(intake, Direction.OUT));
    SmartDashboard.putData("intakeIn", new RunIntakeLeft(intake, Direction.OUT));
  }

  public void initTelemetry() {
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be
   * created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing
   * it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    lightsToggle = new JoystickButton(ps4, LIGHTS_BUTTON_ID);
    lightsToggle.whenPressed(new LightsSoftware(lights));
    
    climb = new JoystickButton(ps4, CLIMB_BUTTON_ID);
    climb.whenPressed(automatedClimb, false);
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return null;
  }
}
