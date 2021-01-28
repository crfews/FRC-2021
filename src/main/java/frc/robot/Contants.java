package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
//import com.revrobotics.ColorSensorV3;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.GenericHID.Hand;
//import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;


//NOTE: only put CONSTANTS in these classe

public class Contants {

    public static int kXController = 0;
    public static int kJoystickChannel = 1;
    public double rCannonSpark = 1;
    public double lCannonSpark = 1;
    public double beltSpark = .5;
    public double lift = 1;   //public double 
    public static double bmotorspeed = 0.75;


     static class IO {

    

         static class XController{
            //Xbox controller
            public  XboxController m_drivexbcont = new XboxController(kXController);

        }

        
        static class ButtonBoard{
            public final Joystick m_buttonboard = new Joystick(kJoystickChannel);
            public final int invertbutton = 1;
            //button used to invert the chassis
    

            
            //More buttons than you need
            public  Boolean button1(){
                return m_buttonboard.getRawButton(1);
            } 

        }
        
    }

     static class Objects {


        
        //Talons
        public  final WPI_TalonSRX lMaster = new WPI_TalonSRX(1);
        public  final WPI_TalonSRX lSlave = new WPI_TalonSRX(2);
        public  final WPI_TalonSRX rMaster = new WPI_TalonSRX(4);
        public  final WPI_TalonSRX rSlave = new WPI_TalonSRX(3);
        public  final DifferentialDrive m_autoDrive = new DifferentialDrive(lMaster, rMaster);

        DifferentialDrive drive = new DifferentialDrive(lMaster, rMaster);
        //Sparks
        public  final Spark lBallSpark = new Spark(0);  //cannon intake motor 1
        public  final Spark rBallSpark = new Spark(1);  //cannon intake motor 2
        public  final Spark intakeSpark = new Spark(2); //ball intake motor
        public  final Spark cwSpark = new Spark(3);     //color wheel motor
        public  final Spark lift = new Spark(4);

        //Solenoids
       //public final Solenoid soleSole = new Solenoid(0);
       public final Solenoid soleA = new Solenoid(0);
       public final DoubleSolenoid soleB = new DoubleSolenoid(2,3);

       //Color Sensor
      // public final ColorSensorV3 colorsensor = new ColorSensorV3(I2C.Port.kOnboard);
    }

}