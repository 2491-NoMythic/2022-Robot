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

 import frc.robot.commands.LightsSoftware;

import static frc.robot.settings.Constants.Ps4.*;

import org.opencv.core.Point;

import frc.robot.commands.PointAtCargo;
import frc.robot.commands.Autos.AutonomousAll;
import frc.robot.commands.Autos.AutononomousDrive;
import frc.robot.commands.oldClimber.ArmPneumaticTipping;
import frc.robot.commands.oldClimber.AutomatedClimb;
import frc.robot.commands.oldClimber.Climb;
import frc.robot.commands.drivetrain.BurnIn;
import frc.robot.subsystems.OldClimber;
import frc.robot.subsystems.NewClimber;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.LightsHardware;
import frc.robot.subsystems.Vision;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Pixy2SubSystem;
import frc.robot.commands.oldClimber.ClimberClimb;
import frc.robot.commands.intake.RunIntakeLeft;
import frc.robot.commands.intake.RunIntakeRight;
import frc.robot.commands.intake.MoveArm.IntakeArmState;
import frc.robot.commands.newClimber.ArmPneumaticTippingMid;
import frc.robot.commands.newClimber.ArmPneumaticTippingTraverse;
import frc.robot.commands.newClimber.ClimberClimbMid;
import frc.robot.commands.newClimber.ClimberClimbTraverse;
import frc.robot.settings.Variables;
import frc.robot.commands.intake.Direction;
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
import frc.robot.commands.drivetrain.TurnInDegrees;
import frc.robot.commands.intake.FilterCargo;
import frc.robot.commands.intake.RunIntake;
import frc.robot.commands.newClimber.OneButtonClimb;
import frc.robot.commands.oldClimber.ArmPneumaticTipping;
import frc.robot.commands.oldClimber.Automate.FullClimbPhase1;
import frc.robot.commands.oldClimber.ClimberClimb;
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

  private OldClimber oldClimber;
  private NewClimber newClimber;
  private final Drivetrain drivetrain;
  private final Vision vision;
  private final Intake intake;
  private final Pixy2SubSystem pixy;
  private final PointAtCargo pointAtCargo;
  private AutomatedClimb automatedClimb;
  private final Drive defaultDriveCommand;
  private SendableChooser<Command> autoChooser;
  // private final MoveArm intakeUpCommand;
  // private final MoveArm intakeDownCommand;
  private final RunIntake runIntakeCommand;
  private Climb runClimbCommand;
  private FilterCargo filterCargoCommand;
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

  // Change this to switch climbers in use. Rebuild/deploy necessary until changed to use preferences.
  private final ClimberType climberType = ClimberType.OLD;

  public enum ClimberType {
    OLD,
    NEW;
  }

   /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {

    autoChooser = new SendableChooser<>();

    drivetrain = new Drivetrain();
    vision = new Vision();
    intake = new Intake();

    ps4 = new PS4Controller(CONTROLLER_ID);
    lights = new LightsHardware();
    pixy = new Pixy2SubSystem();

    defaultDriveCommand = new Drive(drivetrain);
    pointAtCargo = new PointAtCargo(drivetrain, vision);
    //automatedClimb = new AutomatedClimb(climber);
    runIntakeCommand = new RunIntake(intake, ps4);
    filterCargoCommand = new FilterCargo(intake, pixy);
    intake.setDefaultCommand(runIntakeCommand);
    drivetrain.setDefaultCommand(defaultDriveCommand);

    pcmCompressor = new Compressor(PneumaticsModuleType.CTREPCM);
    pcmCompressor.enableDigital();
    pcmCompressor.enabled();

    configureSmartDashboard();
    
    switch (climberType) {
      case OLD:
        oldClimberInit();
        break;
      case NEW:
       newClimberInit();
        break;
    }
  }

  private void configureSmartDashboard() {
    SmartDashboard.putData("Test Vision", new PointAtCargo(drivetrain, vision));
    SmartDashboard.putData("filter cargo", new FilterCargo(intake, pixy));
    SmartDashboard.putData("drivetrain", drivetrain);
    SmartDashboard.putData("Burn In", new BurnIn(drivetrain));
    SmartDashboard.putData("forwardOneSecond", new ForwardDistance(drivetrain, 1, .25));
    SmartDashboard.putData("Choose Auto", autoChooser);
    SmartDashboard.putNumber("Ramp Rate", Variables.Drivetrain.ramp);
    SmartDashboard.putString("Things to remember",
        "The robot climbs backwards, Put the robot with the intake facing at the lower hub.");
    SmartDashboard.putData("climblights", new ClimbLights(lights));
    SmartDashboard.putData("rainbowlights", new RainbowLights(lights));
    SmartDashboard.putData("toggle lights", new LightsSoftware(lights, pixy));
    
    SmartDashboard.putNumber("GyroKp",Gyro.kP);
    SmartDashboard.putNumber("GyroKI",Gyro.kI);
    SmartDashboard.putNumber("GyroKD",Gyro.kD);
    SmartDashboard.putData("turn in degrees", new TurnInDegrees(drivetrain, 90));
    ShuffleboardLayout limelightLayout = Shuffleboard.getTab("SmartDashboard")
      .getLayout("Limelight", BuiltInLayouts.kList)
      .withSize(1, 2);
    limelightLayout.add(new DriveModeEnable(vision));
    limelightLayout.add(new VisionModeEnable(vision));
 
  }

  public void initTelemetry() {
  }

  public void initDisable() {
    drivetrain.coastMode();
  }
  
  public void initEnable() {
    drivetrain.brakeMode();
  }

  public void newClimberInit() {
  
    newClimber = new NewClimber();

    autoChooser.addOption("Taxi", new AutononomousDrive(drivetrain, newClimber, intake));
    autoChooser.setDefaultOption("Taxi And Ball", new AutonomousAll(drivetrain, oldClimber, intake));

    SmartDashboard.putData("MidArmExtend", new ClimberClimbMid(newClimber, ArmExtendState.UP));
    SmartDashboard.putData("MidArmRetract", new ClimberClimbMid(newClimber, ArmExtendState.DOWN));
    SmartDashboard.putData("TraverseArmExtend", new ClimberClimbTraverse(newClimber, ArmExtendState.UP));
    SmartDashboard.putData("TraverseArmRetract", new ClimberClimbTraverse(newClimber, ArmExtendState.DOWN));

    SmartDashboard.putData("MidArmTiltIn", new ArmPneumaticTippingMid(newClimber, ArmTipState.IN));
    SmartDashboard.putData("MidArmTiltOut", new ArmPneumaticTippingMid(newClimber, ArmTipState.OUT));
    SmartDashboard.putData("TraverseArmTiltIn", new ArmPneumaticTippingTraverse(newClimber, ArmTipState.IN));
    SmartDashboard.putData("TraverseArmTiltOut", new ArmPneumaticTippingTraverse(newClimber, ArmTipState.OUT));
  }

  public void oldClimberInit(){

   oldClimber = new OldClimber();

    autoChooser.addOption("Taxi", new AutononomousDrive(drivetrain, oldClimber, intake));
    autoChooser.setDefaultOption("Taxi And Ball", new AutonomousAll(drivetrain, oldClimber, intake));

    automatedClimb = new AutomatedClimb(oldClimber, drivetrain);
//runClimbCommand = new Climb(oldClimber, );

    //button bindings
    JoystickButton cargoFilterButton = new JoystickButton(ps4, INTAKEFILTER_BUTTON_ID);
    cargoFilterButton.whenHeld(filterCargoCommand);
    POVButton Phase1Button = new POVButton(ps4, PHASE_1_CLIMB_BUTTON_ID);
    POVButton RetractButton = new POVButton(ps4, RETRACT_ARM_BUTTON_ID);

    Climb armRetract = new Climb(oldClimber, frc.robot.commands.oldClimber.Climb.ArmExtendState.IN);
    FullClimbPhase1 phase1 = new FullClimbPhase1(oldClimber, intake);
    Phase1Button.whenPressed(phase1);
    RetractButton.whileHeld(armRetract);

    SmartDashboard.putData("ArmsExtend", new ClimberClimb(oldClimber, ArmExtendState.UP));
    SmartDashboard.putData("ArmsRetract", new ClimberClimb(oldClimber, ArmExtendState.DOWN));
    SmartDashboard.putData("ArmsTiltOut", new ArmPneumaticTipping(oldClimber, ArmTipState.OUT));
    SmartDashboard.putData("ArmsTiltIn", new  ArmPneumaticTipping(oldClimber, ArmTipState.IN));
    SmartDashboard.putData("Calibrate Climber", new CalibrateArmEncoders(oldClimber));
    SmartDashboard.putData("Phase1Climb", new FullClimbPhase1(oldClimber, intake));
  }


  
  public void initTeleop() {
    new LightsSoftware(lights, pixy);
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
