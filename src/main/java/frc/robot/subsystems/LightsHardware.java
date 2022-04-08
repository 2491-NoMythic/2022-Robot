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
    // each pixel
    for (var i = 0; i < onlyBuffer.getLength(); i++) {
      // double sinpurple = Math.sin(i);
      // int purple = Math.round((sinpurple + Math.PI)*(128/Math.PI));
      int purple = Math.toIntExact(Math.round(127.0 * (Math.sin((Math.PI / 10.0) * i + firstpixelvalue) + 127.0)));
      // int value = (firstpixelvalue + i % 30)*8;
      onlyBuffer.setRGB(i, purple, 0, purple);
    }
  }

  public void fadechase(int firstpixelvalue) {
    //each pixel
    int purple;
    for (var i = 0; i < onlyBuffer.getLength(); i++) {
      if (i < (onlyBuffer.getLength()/2)) {// left leds
        // firstpixelvalue = Math.abs(i - firstpixelvalue);
        purple = Math.toIntExact(Math.round(127.0 * (Math.sin((Math.PI/10.0) * i - firstpixelvalue) + 127.0)));
      } else {// right leds
        purple = Math.toIntExact(Math.round(127.0 * (Math.sin((Math.PI/10.0) * i + firstpixelvalue) + 127.0)));

      }
      onlyBuffer.setRGB(i, purple, 0, purple);
    }
  }
  public void throbinglights(int firstpixelvalue){
    for (var i = 0; i < onlyBuffer.getLength(); i++) {
      
      
      onlyBuffer.setHSV(i, 150, 200, firstpixelvalue);
    }
  }
}
