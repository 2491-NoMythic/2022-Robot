package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.CargoState;

public class LightsHardware extends SubsystemBase {
  private final AddressableLED adressableLedsOnly;
  private AddressableLEDBuffer onlyBuffer;

  public LightsHardware() {
    // Must be a PWM header, not MXP or DIO
    adressableLedsOnly = new AddressableLED(6);
    // Reuse buffer
    // Length is expensive to set, so only set it once, then just update data
    onlyBuffer = new AddressableLEDBuffer(120);
    adressableLedsOnly.setLength(onlyBuffer.getLength());
    adressableLedsOnly.setData(onlyBuffer);
    adressableLedsOnly.start();
    // I would just like to say that I had 'We don't talk about Bruno' stuck in my
    // head while I was coding this.
  }

  public void dataSetter() {
    adressableLedsOnly.setData(onlyBuffer);
  }

  public void prettyleftlights() {
    for (var leftdecorlights = 0; leftdecorlights < onlyBuffer.getLength(); leftdecorlights++) {
      onlyBuffer.setRGB(leftdecorlights, 50, 0, 50);
    }
  }

  public void leftballindicator(CargoState ballColor) {
    for (var leftsensorlights = 30; leftsensorlights < onlyBuffer.getLength(); leftsensorlights++) {
      if (ballColor == CargoState.Red) {
        onlyBuffer.setRGB(leftsensorlights, 255, 0, 0);
      } else if (ballColor == CargoState.Blue) {
        onlyBuffer.setRGB(leftsensorlights, 0, 0, 255);
      } else {
        onlyBuffer.setRGB(leftsensorlights, 0, 0, 0);
      }
    }
  }

  public void rightballindicator(CargoState ballColor) {
    for (var rightsensorlights = 60; rightsensorlights < onlyBuffer.getLength(); rightsensorlights++) {
      if (ballColor == CargoState.Red) {
        onlyBuffer.setRGB(rightsensorlights, 255, 0, 0);
      } else if (ballColor == CargoState.Blue) {
        onlyBuffer.setRGB(rightsensorlights, 0, 0, 255);
      } else {
        onlyBuffer.setRGB(rightsensorlights, 0, 0, 0);
      }
    }
  }

  public void prettyrightlights() {
    for (var rightdecorlights = 90; rightdecorlights < onlyBuffer.getLength(); rightdecorlights++) {
      onlyBuffer.setRGB(rightdecorlights, 50, 0, 50);
    }
  }

  public void lightsout() {
    for (var allthelights = 0; allthelights < onlyBuffer.getLength(); allthelights++) {
      onlyBuffer.setRGB(allthelights, 0, 0, 0);
    }
  }

  public void climbinglights(int firstpixelvalue) {
    //each pixel
    for (var i = 0; i < onlyBuffer.getLength(); i++) {
      final var value = 10 + (firstpixelvalue + ( i* 205 / onlyBuffer.getLength())) % 205;
      
      onlyBuffer.setHSV(i, 150, 200, value);
    }
  }

  public void throbinglights(int firstpixelvalue){
    for (var i = 0; i < onlyBuffer.getLength(); i++) {
      
      
      onlyBuffer.setHSV(i, 150, 200, firstpixelvalue);
    }
  }
}
