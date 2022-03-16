// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.networktables.NetworkTablesJNI;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PS4Controller;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.commands.drivetrain.Drive;
import frc.robot.commands.drivetrain.ForwardDistance;
import frc.robot.commands.intake.MoveArm;
import frc.robot.commands.intake.RunIntake;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

 import frc.robot.commands.LightsSoftware;

import static frc.robot.settings.Constants.Ps4.*;

import org.opencv.core.Point;

import frc.robot.commands.PointAtCargo;
import frc.robot.commands.Autos.AutonomousAll;
import frc.robot.commands.Autos.AutononomousDrive;
import frc.robot.commands.climber.ArmPneumaticTipping;
import frc.robot.commands.climber.AutomatedClimb;
import frc.robot.commands.climber.Climb;
import frc.robot.commands.drivetrain.BurnIn;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Drivetrain;
 import frc.robot.subsystems.LightsHardware;
import frc.robot.subsystems.Vision;
import frc.robot.subsystems.Climber.RungLockState;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Pixy2SubSystem;
import frc.robot.commands.climber.ClimberClimb;
import frc.robot.commands.climber.ClimberClimb.ArmExtendState;
import frc.robot.commands.climber.WedgePneumatic;
import frc.robot.commands.intake.RunIntakeLeft;
import frc.robot.commands.intake.RunIntakeRight;
import frc.robot.commands.intake.MoveArm.IntakeArmState;
import frc.robot.settings.Variables;
import frc.robot.commands.intake.Direction;
import frc.robot.commands.climber.ArmPneumaticTipping.ArmTipState;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;

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
  private final Pixy2SubSystem pixy;
  private final PointAtCargo pointAtCargo;
  private final AutomatedClimb automatedClimb;
  private final Drive defaultDriveCommand;
  private final SendableChooser<Command> autoChooser;
  // private final MoveArm intakeUpCommand;
  // private final MoveArm intakeDownCommand;
  private final RunIntake runIntakeCommand;
  private final Climb runClimbCommand;
  // private final RunIntakeLeft intakeLeftInCommand;
  //private final RunIntakeLeft intakeLeftOutCommand;
  //private final RunIntakeLeft intakeLeftStopCommand;
  // private final RunIntakeRight intakeRightInCommand;
  //private final RunIntakeRight intakeRightOutCommand;
  //private final RunIntakeRight intakeRightStopCommand;
  private final PS4Controller ps4;
  private JoystickButton climb;

  private JoystickButton intakeUp;
  private JoystickButton intakeDown;

  private JoystickButton intakeLeftIn;
  private JoystickButton intakeLeftOut;
  private JoystickButton intakeRightIn;
  private JoystickButton intakeRightOut;

  private JoystickButton lightsToggle;

  private Compressor pcmCompressor;
  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    climber = new Climber();
    drivetrain = new Drivetrain();
    vision = new Vision();
    intake = new Intake();

    autoChooser = new SendableChooser<>();
    autoChooser.addOption("Taxi", new AutononomousDrive(drivetrain, climber, intake));
    autoChooser.setDefaultOption("Taxi And Ball", new AutonomousAll(drivetrain, climber, intake));

    ps4 = new PS4Controller(CONTROLLER_ID);
     lights = new LightsHardware();
    pixy = new Pixy2SubSystem();

    defaultDriveCommand = new Drive(drivetrain);
    automatedClimb = new AutomatedClimb(climber, drivetrain);
    pointAtCargo = new PointAtCargo(drivetrain, vision);
    //automatedClimb = new AutomatedClimb(climber);
    
    runIntakeCommand = new RunIntake(intake, ps4);
    runClimbCommand = new Climb(climber, ps4);
    intake.setDefaultCommand(runIntakeCommand);
    drivetrain.setDefaultCommand(defaultDriveCommand);
    
    pcmCompressor = new Compressor(PneumaticsModuleType.CTREPCM);
    pcmCompressor.enableDigital();
    pcmCompressor.enabled();

    configureButtonBindings();
    configureSmartDashboard();
  }

  private void configureSmartDashboard() {
    SmartDashboard.putData("Test Vision", new PointAtCargo(drivetrain, vision));
    SmartDashboard.putData("drivetrain", drivetrain);
    SmartDashboard.putData("Burn In", new BurnIn(drivetrain));
    SmartDashboard.putData("forwardOneSecond", new ForwardDistance(drivetrain, 1, .25));
    SmartDashboard.putData("Choose Auto", autoChooser);
    SmartDashboard.putNumber("Ramp Rate", Variables.Drivetrain.ramp);
    SmartDashboard.putData("ArmsExtend", new ClimberClimb(climber, ArmExtendState.OUT));
    SmartDashboard.putData("ArmsRetract", new ClimberClimb(climber, ArmExtendState.IN));
    //SmartDashboard.putData("armLock", new WedgePneumatic(climber, RungLockState.Locked));
    SmartDashboard.putData("ArmsTiltOut", new ArmPneumaticTipping(climber, ArmTipState.DOWN));
    SmartDashboard.putData("ArmsTiltIn", new  ArmPneumaticTipping(climber, ArmTipState.UP));
    SmartDashboard.putString("Things to remember",
     "The robot climbs backwards, Put the robot with the intake facing at the lower hub.");
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
    
    // climb = new JoystickButton(ps4, CLIMB_BUTTON_ID);
    // climb.whenPressed(automatedClimb, false);


    POVButton OutButton = new POVButton(ps4, OUT_ARM_BUTTON_ID);
    POVButton InButton = new POVButton(ps4, IN_ARM_BUTTON_ID);
    POVButton ExtendButton = new POVButton(ps4, EXTEND_ARM_BUTTON_ID);
    POVButton RetractButton = new POVButton(ps4, RETRACT_ARM_BUTTON_ID);
    
    ArmPneumaticTipping armsTiltOut = new ArmPneumaticTipping(climber, ArmTipState.DOWN);
    ArmPneumaticTipping armsTiltIn = new ArmPneumaticTipping(climber, ArmTipState.UP);
    ClimberClimb armExtend = new ClimberClimb(climber, ArmExtendState.OUT);
    ClimberClimb armRetract = new ClimberClimb(climber, ArmExtendState.IN);
    OutButton.whenPressed(armsTiltOut);
    InButton.whenPressed(armsTiltIn);
    ExtendButton.whenPressed(armExtend);
    RetractButton.whenPressed(armRetract);

  }

  public void initDisable() {
    drivetrain.coastMode();
  }

  public void initEnable() {
    drivetrain.brakeMode();
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {

    // moves us off tarmack
    return autoChooser.getSelected();
    // // An ExampleCommand will run in autonomous
    // return new ForwardDistance(drivetrain, 3.5, -.25);
  }
}
