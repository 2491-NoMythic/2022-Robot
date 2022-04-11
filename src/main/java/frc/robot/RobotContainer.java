// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import static frc.robot.settings.Constants.Ps4.CONTROLLER_ID;
import static frc.robot.settings.Constants.Ps4.INTAKEFILTER_BUTTON_ID;
import static frc.robot.settings.Constants.Ps4.LIGHTS_BUTTON_ID;
import static frc.robot.settings.Constants.Ps4.PHASE_1_CLIMB_BUTTON_ID;
import static frc.robot.settings.Constants.Ps4.RETRACT_ARM_BUTTON_ID;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.PS4Controller;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardLayout;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;
import frc.robot.commands.LightsSoftware;
import frc.robot.commands.PointAtCargo;
import frc.robot.commands.Autos.AutonomousAll;
import frc.robot.commands.Autos.AutononomousDrive;
import frc.robot.commands.Lights.ClimbLights;
import frc.robot.commands.Lights.RainbowLights;
import frc.robot.commands.Limelight.VisionModeEnable;
import frc.robot.commands.Limelight.DriveModeEnable;
import frc.robot.commands.drivetrain.BurnIn;
import frc.robot.commands.drivetrain.Drive;
import frc.robot.commands.drivetrain.ForwardDistance;
import frc.robot.commands.intake.FilterCargo;
import frc.robot.commands.intake.RunIntake;
import frc.robot.commands.newClimber.OneButtonClimb;
import frc.robot.commands.oldClimber.ArmPneumaticTipping;
import frc.robot.commands.oldClimber.ArmPneumaticTipping.ArmTipState;
import frc.robot.commands.oldClimber.Automate.FullClimbPhase1;
import frc.robot.commands.oldClimber.ClimberClimb;
import frc.robot.commands.oldClimber.ClimberClimb.ArmExtendState;
import frc.robot.commands.oldClimber.CalibrateArmEncoders;
import frc.robot.commands.oldClimber.Climb;
import frc.robot.settings.Variables;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.LightsHardware;
import frc.robot.subsystems.OldClimber;
import frc.robot.subsystems.Pixy2SubSystem;
import frc.robot.subsystems.Vision;
import frc.robot.settings.Variables.Drivetrain.Gyro;
import frc.robot.settings.Variables.Drivetrain.Gyro.*;

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
  private final OldClimber climber;
  private final Drivetrain drivetrain;
  private final Vision vision;
  private final Intake intake;
  private final Pixy2SubSystem pixy;
  private final Drive defaultDriveCommand;
  private final SendableChooser<Command> autoChooser;
  private final RunIntake runIntakeCommand;
  private final FilterCargo filterCargoCommand;
  // private final Climb runClimbCommand;
  private OneButtonClimb runOneButtonClimb;
  // private final RunIntakeLeft intakeLeftInCommand;
  // private final RunIntakeLeft intakeLeftOutCommand;
  // private final RunIntakeLeft intakeLeftStopCommand;
  // private final RunIntakeRight intakeRightInCommand;
  // private final RunIntakeRight intakeRightOutCommand;
  // private final RunIntakeRight intakeRightStopCommand;
  private final PS4Controller ps4;
  private JoystickButton lightsToggle;
  private JoystickButton onebClimber;
  private Compressor pcmCompressor;

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    climber = new OldClimber();
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

    runIntakeCommand = new RunIntake(intake, ps4);
    filterCargoCommand = new FilterCargo(intake, pixy);
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
    SmartDashboard.putData("filter cargo", new FilterCargo(intake, pixy));
    SmartDashboard.putData("drivetrain", drivetrain);
    SmartDashboard.putData("Burn In", new BurnIn(drivetrain));
    SmartDashboard.putData("forwardOneSecond", new ForwardDistance(drivetrain, 1, .25));
    SmartDashboard.putData("Choose Auto", autoChooser);
    SmartDashboard.putNumber("Ramp Rate", Variables.Drivetrain.ramp);
    SmartDashboard.putData("ArmsExtend", new ClimberClimb(climber, ArmExtendState.OUT));
    SmartDashboard.putData("ArmsRetract", new ClimberClimb(climber, ArmExtendState.IN));
    SmartDashboard.putData("ArmsTiltOut", new ArmPneumaticTipping(climber, ArmTipState.DOWN));
    SmartDashboard.putData("ArmsTiltIn", new ArmPneumaticTipping(climber, ArmTipState.UP));
    SmartDashboard.putData("Calibrate Climber", new CalibrateArmEncoders(climber));
    SmartDashboard.putData("Phase1Climb", new FullClimbPhase1(climber));
    SmartDashboard.putString("Things to remember",
        "The robot climbs backwards, Put the robot with the intake facing at the lower hub.");
    SmartDashboard.putData("climblights", new ClimbLights(lights));
    SmartDashboard.putData("rainbowlights", new RainbowLights(lights));
    
    SmartDashboard.putNumber("GyroKp",Gyro.kP);
    SmartDashboard.putNumber("GyroKI",Gyro.kI);
    SmartDashboard.putNumber("GyroKD",Gyro.kD);

    ShuffleboardLayout limelightLayout = Shuffleboard.getTab("SmartDashboard")
      .getLayout("Limelight", BuiltInLayouts.kList)
      .withSize(1, 2);
    limelightLayout.add(new DriveModeEnable(vision));
    limelightLayout.add(new VisionModeEnable(vision));
 
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
    lightsToggle.toggleWhenPressed(new LightsSoftware(lights, pixy));

    // climb = new JoystickButton(ps4, CLIMB_BUTTON_ID);
    // climb.whenPressed(automatedClimb, false);
    // onebClimber = new JoystickButton(ps4, SINGLE_BUTTON_CLIMB);
    // onebClimber.whenPressed(runOneButtonClimb);
    JoystickButton cargoFilterButton = new JoystickButton(ps4, INTAKEFILTER_BUTTON_ID);
    cargoFilterButton.whenHeld(filterCargoCommand);
    POVButton Phase1Button = new POVButton(ps4, PHASE_1_CLIMB_BUTTON_ID);
    POVButton RetractButton = new POVButton(ps4, RETRACT_ARM_BUTTON_ID);

    Climb armRetract = new Climb(climber, frc.robot.commands.oldClimber.Climb.ArmExtendState.IN);
    FullClimbPhase1 phase1 = new FullClimbPhase1(climber);
    Phase1Button.whenPressed(phase1);
    RetractButton.whileHeld(armRetract);
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
    // return new ForwardDistance(drivetrain, 3.5, -.25);
  }
}
