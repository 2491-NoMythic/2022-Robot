package frc.robot.subsystems;
import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.FakePixie;
public class LightsHardware extends SubsystemBase{
    private final AddressableLED adressableLedsLeft;
    //private final AddressableLED adressableLedsRight;
    private AddressableLEDBuffer ballsensorBufferLeft;
    //private AddressableLEDBuffer ballsensorBufferRight;
    private AddressableLEDBuffer decorBufferLeft;
    //private AddressableLEDBuffer decorBufferRight;
    private FakePixie pixie;
        public LightsHardware(){
      // Must be a PWM header, not MXP or DIO
      adressableLedsLeft = new AddressableLED(6);
      //adressableLedsRight = new AddressableLED(7);
      // Reuse buffer
      // Length is expensive to set, so only set it once, then just update data
      ballsensorBufferLeft = new AddressableLEDBuffer(5);
      adressableLedsLeft.setLength(ballsensorBufferLeft.getLength());
      //ballsensorBufferRight = new AddressableLEDBuffer(5);
      //adressableLedsRight.setLength(ballsensorBufferRight.getLength());
      decorBufferLeft = new AddressableLEDBuffer(5);
      adressableLedsLeft.setLength(decorBufferLeft.getLength());
      //decorBufferRight = new AddressableLEDBuffer(20);
      //adressableLedsRight.setLength(decorBufferRight.getLength());
      adressableLedsLeft.setData(ballsensorBufferLeft);
      //adressableLedsRight.setData(ballsensorBufferRight);
      adressableLedsLeft.setData(decorBufferLeft);
      //adressableLedsRight.setData(decorBufferRight);
      //I would just like to say that I had 'We don't talk about Bruno' stuck in my head while I was coding this.
      pixie = new FakePixie();
}
public void leftballindicator() {
    for (var leftlights = 0; leftlights < ballsensorBufferLeft.getLength(); leftlights++) {
      if (pixie.isLeftBallRed())  {
          ballsensorBufferLeft.setRGB(leftlights, 255, 0, 0);
      }else if (pixie.isLeftBallBlue()) {
          ballsensorBufferLeft.setRGB(leftlights, 0, 0, 255);
      }  
    }
  }
  //public void rightballindicator() {
    //  for (var rightlights = 0; rightlights < ballsensorBufferRight.getLength(); rightlights++) {
      //if (pixie.isRightBallRed())  
       //   ballsensorBufferRight.setRGB(rightlights, 255, 0, 0);
      //else if (pixie.isRightBallBlue())
        //  ballsensorBufferRight.setRGB(rightlights, 0, 0, 255);
// The redlinks are because I used placeholder code for the sensors.
        //}
    //}
public void prettylights(){
  for (var leftdecorlights = 6; leftdecorlights < decorBufferLeft.getLength(); leftdecorlights++){
      decorBufferLeft.setRGB(leftdecorlights, 50, 0, 50);
  //for (var rightdecorlights = 6; rightdecorlights < decorBufferRight.getLength(); rightdecorlights++){
      //decorBufferRight.setRGB(rightdecorlights, 50, 0, 50);
//}
}
}
}
