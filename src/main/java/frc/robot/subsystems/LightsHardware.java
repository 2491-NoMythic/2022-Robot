package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.FakePixie;

public class LightsHardware extends SubsystemBase {
  private final AddressableLED adressableLedsOnly;
  private AddressableLEDBuffer onlyBuffer;
  private FakePixie pixie;

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
    pixie = new FakePixie();
  }

  public void dataSetter() {
    adressableLedsOnly.setData(onlyBuffer);
  }

  public void prettyleftlights() {
    // System.out.println("Light it up");
    for (var leftdecorlights = 0; leftdecorlights < onlyBuffer.getLength(); leftdecorlights++) {
      onlyBuffer.setRGB(leftdecorlights, 50, 0, 50);
    }
  }

  public void leftballindicator() {
    // System.out.println("Left side go");
    for (var leftsensorlights = 30; leftsensorlights < onlyBuffer.getLength(); leftsensorlights++) {
      if (pixie.isLeftBallRed()) {
        onlyBuffer.setRGB(leftsensorlights, 255, 0, 0);
      } else if (pixie.isLeftBallBlue()) {
        onlyBuffer.setRGB(leftsensorlights, 0, 0, 255);
      } else {
        onlyBuffer.setRGB(leftsensorlights, 0, 0, 0);
      }
    }
  }

  public void rightballindicator() {
    // System.out.println("Right side go");
    for (var rightsensorlights = 60; rightsensorlights < onlyBuffer.getLength(); rightsensorlights++) {
      if (pixie.isRightBallRed()) {
        onlyBuffer.setRGB(rightsensorlights, 255, 0, 0);
      } else if (pixie.isRightBallBlue()) {
        onlyBuffer.setRGB(rightsensorlights, 0, 0, 255);
      } else {
        onlyBuffer.setRGB(rightsensorlights, 0, 0, 0);
        // The redlinks are because I used placeholder code for the sensors.
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
}
