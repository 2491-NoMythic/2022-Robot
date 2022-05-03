package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.CargoState;

public class LightsHardware extends SubsystemBase {
  private final AddressableLED adressableLedsOnly;
  private AddressableLEDBuffer onlyBuffer;

  private static final int NUM_HORIZONTAL_LIGHTS = 20;
  private static final int NUM_TOTAL_LIGHTS = 120;
  private static final int NUM_ONE_SIDE_LIGHTS = NUM_TOTAL_LIGHTS / 2;
  private static final int NUM_VERTICAL_LIGHTS = NUM_ONE_SIDE_LIGHTS - NUM_HORIZONTAL_LIGHTS;

  public LightsHardware() {
    // Must be a PWM header, not MXP or DIO
    adressableLedsOnly = new AddressableLED(6);
    // Reuse buffer
    // Length is expensive to set, so only set it once, then just update data
    onlyBuffer = new AddressableLEDBuffer(NUM_TOTAL_LIGHTS);
    adressableLedsOnly.setLength(NUM_TOTAL_LIGHTS);
    adressableLedsOnly.setData(onlyBuffer);
    adressableLedsOnly.start();
    // I would just like to say that I had 'We don't talk about Bruno' stuck in my
    // head while I was coding this.
  }

  public void dataSetter() {
    adressableLedsOnly.setData(onlyBuffer);
  }

  private void setRGBMirrored(int i, int R, int G, int B) {
    onlyBuffer.setRGB(i, R, G, B);
    onlyBuffer.setRGB(NUM_TOTAL_LIGHTS-i-1, R, G, B);
  };
  public void prettyleftlights() {
    for (var leftdecorlights = 0; leftdecorlights < NUM_HORIZONTAL_LIGHTS; leftdecorlights++) { // Leds 0-29
      onlyBuffer.setRGB(leftdecorlights, 50, 0, 50);
    }
  }

  public void leftballindicator(CargoState ballColor) {
    for (var leftsensorlights = NUM_HORIZONTAL_LIGHTS; leftsensorlights < NUM_ONE_SIDE_LIGHTS; leftsensorlights++) { // Leds 30-59
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
    for (var rightsensorlights = NUM_ONE_SIDE_LIGHTS; rightsensorlights < NUM_ONE_SIDE_LIGHTS + NUM_VERTICAL_LIGHTS; rightsensorlights++) { // leds 60-89
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
    for (var rightdecorlights = NUM_ONE_SIDE_LIGHTS + NUM_VERTICAL_LIGHTS; rightdecorlights < NUM_TOTAL_LIGHTS; rightdecorlights++) { // Leds 90-129
      onlyBuffer.setRGB(rightdecorlights, 50, 0, 50);
    }
  }

  public void lightsout() {
    for (var allthelights = 0; allthelights < NUM_TOTAL_LIGHTS; allthelights++) {
      onlyBuffer.setRGB(allthelights, 0, 0, 0);
    }
  }

  public void climbinglights(double firstpixelvalue) {
    //each pixel
    int purple;
    for (var i = 0; i < NUM_ONE_SIDE_LIGHTS; i++) {
      // firstpixelvalue = Math.abs(i - firstpixelvalue);
      purple = Math.toIntExact(Math.round(127.0 * (Math.sin((Math.PI/10.0) * i - firstpixelvalue) + 127.0)));
      setRGBMirrored(i, purple, 0, purple);
    }
  }

  public void rainbowlights(int firstpixelhue){
    for (var i = 0; i < NUM_TOTAL_LIGHTS; i++) {
      final var hue = (firstpixelhue + (i * 180 / NUM_TOTAL_LIGHTS)) % 180;
      
      onlyBuffer.setHSV(i, hue, 255, 255);
    }
  }

  public void setOneLight(int index, int R, int G, int B) {
    if (index > NUM_TOTAL_LIGHTS) {
      index = NUM_TOTAL_LIGHTS;
    }
    onlyBuffer.setRGB(index, R, G, B);
  }

  public void setOneLight(int index, Color color) {
    if (index > NUM_TOTAL_LIGHTS) {
      index = NUM_TOTAL_LIGHTS;
    }
    onlyBuffer.setLED(index, color);
  }

  public void setLightBuffer(AddressableLEDBuffer buffer) {
    int max = NUM_TOTAL_LIGHTS;
    if (buffer.getLength() < NUM_TOTAL_LIGHTS) {
      max = buffer.getLength();
    }
    for (int i=0; i < max; i++) {
      onlyBuffer.setLED(i, buffer.getLED(i));
    }
    dataSetter();
  }
}
