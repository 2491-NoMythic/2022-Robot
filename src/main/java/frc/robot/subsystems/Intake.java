package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static frc.robot.settings.Constants.Intake.*;
public class Intake extends SubsystemBase {
    public enum CargoState {
        Empty,
        Red,
        Blue,
        }
    CANSparkMax leftIntakeMotor;
    CANSparkMax rightIntakeMotor;
    private DoubleSolenoid armDoubleSolenoid;

    public Intake() {
        leftIntakeMotor = new CANSparkMax(LEFT_MOTOR_ID, MotorType.kBrushless);
        rightIntakeMotor = new CANSparkMax(RIGHT_MOTOR_ID, MotorType.kBrushless); 
        leftIntakeMotor.setInverted(false);
        leftIntakeMotor.setInverted(false); 
        armDoubleSolenoid = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, ARM_FORWARD_CHANNEL, ARM_REVERSE_CHANNEL);
        rightIntakeMotor.follow(leftIntakeMotor);
        //TODO make motors operate independantly. 
    }

    public void runIntake(double speed){
        leftIntakeMotor.set(speed);
    }
    public void setArmUp(){
        armDoubleSolenoid.set(Value.kForward);
    }
    public void setArmDown(){
        armDoubleSolenoid.set(Value.kReverse);
    }
    public double getSensors(){
        //stub
        //TODO put sensors in here. 
        double value = .2491;
        return value;
    }
    public CargoState[] getCargoState(){
        //stub, get ball colors, and positions?
        CargoState[] state = {CargoState.Red, CargoState.Empty};
        return state;
    }
    public void setIntakeLights(CargoState[] state){
        //stub, set lights
    }
}