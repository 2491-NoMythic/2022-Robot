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
    CANSparkMax intakeMotor;
    private DoubleSolenoid armDoubleSolenoid;

    public Intake() {
        intakeMotor = new CANSparkMax(AXLE_ID, MotorType.kBrushless);
        intakeMotor.setInverted(false);
        armDoubleSolenoid = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, ARM_FORWARD_CHANNEL, ARM_REVERSE_CHANNEL);
    }

    public void runIntake(double speed){
        intakeMotor.set(speed);
    }
    public void setArmUp(){
        armDoubleSolenoid.set(Value.kForward);
    }
    public void setArmDown(){
        armDoubleSolenoid.set(Value.kReverse);
    }
    public double getSensors(){
        //stub
        double value = .2491;
        return value;
    }
    public boolean isArmUp(){
        //stub
        boolean state = true;
        return state;
    }
    public CargoState[] getcCargoState(){
        //stub, get ball colors, and positions?
        CargoState[] state = {CargoState.Red, CargoState.Empty};
        return state;
    }
    public void setIntakeLights(CargoState[] state){
        //stub, set lights
    }
}
