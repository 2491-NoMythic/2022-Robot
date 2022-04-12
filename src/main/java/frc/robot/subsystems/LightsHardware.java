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
    for (var leftdecorlights = 0; leftdecorlights < 30; leftdecorlights++) { // Leds 0-29
      onlyBuffer.setRGB(leftdecorlights, 50, 0, 50);
    }
  }

  public void leftballindicator(CargoState ballColor) {
    for (var leftsensorlights = 30; leftsensorlights < 60; leftsensorlights++) { // Leds 30-59
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
    for (var rightsensorlights = 60; rightsensorlights < 90; rightsensorlights++) { // leds 60-89
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
    for (var rightdecorlights = 90; rightdecorlights < onlyBuffer.getLength(); rightdecorlights++) { // Leds 90-129
      onlyBuffer.setRGB(rightdecorlights, 50, 0, 50);
    }
  }

  public void lightsout() {
    for (var allthelights = 0; allthelights < onlyBuffer.getLength(); allthelights++) {
      onlyBuffer.setRGB(allthelights, 0, 0, 0);
    }
  }

  public void climbinglights(double firstpixelvalue) {
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

  public void rainbowlights(int firstpixelhue){
    for (var i = 0; i < onlyBuffer.getLength(); i++) {
      final var hue = (firstpixelhue + (i * 180 / onlyBuffer.getLength())) % 180;
      
      onlyBuffer.setHSV(i, hue, 255, 255);
    }
  }

  public void setOneLight(int index, int R, int G, int B) {
    if (index > onlyBuffer.getLength()) {
      index = onlyBuffer.getLength();
    }
    onlyBuffer.setRGB(index, R, G, B);
  }

  public void setOneLight(int index, Color color) {
    if (index > onlyBuffer.getLength()) {
      index = onlyBuffer.getLength();
    }
    onlyBuffer.setLED(index, color);
  }

  public void setLightBuffer(AddressableLEDBuffer buffer) {
    int max = onlyBuffer.getLength();
    if (buffer.getLength() < onlyBuffer.getLength()) {
      max = buffer.getLength();
    }
    for (int i=0; i < max; i++) {
      onlyBuffer.setLED(i, buffer.getLED(i));
    }
    dataSetter();
  }
}
