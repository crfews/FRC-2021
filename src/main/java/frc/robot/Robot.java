/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.Faults;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
//import com.revrobotics.ColorMatch;
//import com.revrobotics.ColorMatchResult;
//import com.revrobotics.ColorSensorV3;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoMode;
import edu.wpi.first.cameraserver.CameraServer;
//import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.TimedRobot;
//import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
//import edu.wpi.first.wpilibj.util.Color;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {

  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private static final String kOption1 = "Option 1";

  private String m_autoSelected;
  private SendableChooser<String> m_chooser = new SendableChooser<>();

  // Setting up classes:
  public static Systems systems = new Systems();
  public static Systems.InnerSystems innerSystems = new Systems.InnerSystems();
  public static Contants con = new Contants();
  public static Contants.IO io = new Contants.IO();
  public static Contants.IO.XController xboxController = new Contants.IO.XController();
  public static Contants.IO.ButtonBoard buttonBoard = new Contants.IO.ButtonBoard();
  public static Contants.Objects objects = new Contants.Objects();

  // public static Systems ballIntake = new Systems();

  // variables and constants
  // private boolean rdIndicator = false;
  int time = 0;

  private final Faults faults = new Faults();

  boolean i0 = true; boolean i1 = false; boolean i2 = false;
  boolean i3 = false; boolean i4 = false; boolean i5 = false;
  boolean i6 = false; boolean i7 = false; boolean i8 = false;
  boolean i9 = false; boolean i10 = false; boolean i11 = false;

  // color strings
  //private String gameData;
  //private String obcolorString;
  //private static Systems.ColorSys cSys = new Systems.ColorSys();

  // color sensing
  //private final ColorSensorV3 m_colorSensor = new ColorSensorV3(I2C.Port.kOnboard);

  //private final ColorMatch m_colorMatcher = new ColorMatch();
  // UwU
  // these colors need to be calibrated
 // private final Color kBlueTarget = ColorMatch.makeColor(0.143, 0.427, 0.429);
  //private final Color kGreenTarget = ColorMatch.makeColor(0.197, 0.561, 0.240);
  //private final Color kRedTarget = ColorMatch.makeColor(0.561, 0.232, 0.114);
  //private final Color kYellowTarget = ColorMatch.makeColor(0.361, 0.524, 0.113);

  // trying to do the suggested integer part...

  @Override
  public void robotInit() {
    UsbCamera m_usbCamera = new UsbCamera("USB Camera 0", 1);
    m_usbCamera.close();
    UsbCamera m_cameraserv = CameraServer.getInstance().startAutomaticCapture(1);
    m_cameraserv.setVideoMode(VideoMode.PixelFormat.kYUYV, 604, 480, 30);
    m_chooser.setDefaultOption("Option 1:", kOption1);
    m_chooser.addOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);

    // Config Talons
    innerSystems.configTalon(objects.lMaster, objects.rMaster, objects.lSlave, objects.rSlave);
    objects.lSlave.follow(objects.lMaster);
    objects.rSlave.follow(objects.rMaster);

    objects.lMaster.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);
    objects.lMaster.setSelectedSensorPosition(0);
    objects.rMaster.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);
    objects.rMaster.setSelectedSensorPosition(0);
    // objects.rMaster.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);
    // m_encoder.setDistancePerPulse(1./256.);

    //m_colorMatcher.addColorMatch(kBlueTarget);
   // m_colorMatcher.addColorMatch(kGreenTarget);
    //m_colorMatcher.addColorMatch(kRedTarget);
   // m_colorMatcher.addColorMatch(kYellowTarget);
    objects.rBallSpark.setInverted(true);
    objects.intakeSpark.setInverted(true);
    objects.lMaster.setInverted(true);
    objects.lSlave.setInverted(true);


  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for
   * items like diagnostics that you want ran during disabled, autonomous,
   * teleoperated and test.
   *
   * <p>
   * This runs after the mode specific periodic functions, but before LiveWindow
   * and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {

    // color detection code / color matching
    //Color detectedColor = m_colorSensor.getColor();

    //String colorString;
   // ColorMatchResult match = m_colorMatcher.matchClosestColor(detectedColor);

   /*
    if (match.color == kBlueTarget) {
      colorString = "Blue";
    } else if (match.color == kRedTarget) {
      colorString = "Red";
    } else if (match.color == kGreenTarget) {
      colorString = "Green";
    } else if (match.color == kYellowTarget) {
      colorString = "Yellow";
    } else {
      colorString = "Unknown";
    }

    
    
    SmartDashboard.putNumber("Red", detectedColor.red);
    SmartDashboard.putNumber("Green", detectedColor.green);
    SmartDashboard.putNumber("Blue", detectedColor.blue);
    SmartDashboard.putNumber("Confidence", match.confidence);
    SmartDashboard.putString("Detected Color", colorString);

    // read RGB values
    // double IR = m_colorSensor.getIR();
    SmartDashboard.putNumber("Red", detectedColor.red);
    SmartDashboard.putNumber("Green", detectedColor.green);
    SmartDashboard.putNumber("Blue", detectedColor.blue);
    */

    // SmartDashboard.putNumber("IR", IR);

    // encoder values
    SmartDashboard.putNumber("left Vel:", objects.lMaster.getSelectedSensorVelocity());
    SmartDashboard.putNumber("left Pos:", objects.lMaster.getSelectedSensorPosition());
    SmartDashboard.putNumber("left out %:", objects.lMaster.getMotorOutputPercent());
    SmartDashboard.putBoolean("Out of Phase", faults.SensorOutOfPhase);

  }

  /**
   * This autonomous (along with the chooser code above) shows how to select /*
   * between different autonomous modes using the dashboard. The sendable chooser
   * code works with the Java SmartDashboard. If you prefer the LabVIEW Dashboard,
   * remove all of the chooser code and uncomment the getString line to get the
   * auto name from the text box below the Gyro
   *
   * <p>
   * You can add additional auto modes by adding additional comparisons to the
   * switch structure below with additional strings. If using the SendableChooser
   * make sure to add them to the chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    m_autoSelected = m_chooser.getSelected();
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);

    System.out.println("Auto selected: " + m_autoSelected);
    objects.rMaster.setSelectedSensorPosition(0);
    objects.lMaster.setSelectedSensorPosition(0);

  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {

   /**
    //get position of the encoder

    int rEnc = objects.rMaster.getSelectedSensorPosition();
    int lEnc = objects.lMaster.getSelectedSensorPosition();

    
    int i = lEnc;

    
    if(i > -systems.feetToEnc(Math.PI*.5)) {
      
    
    objects.lMaster.set(.3);
    objects.rMaster.set(.3);
        
    }else{
      objects.lMaster.set(0);
    objects.rMaster.set(0);
    }
    System.out.println(i);
    System.out.println("r: " + rEnc);
    System.out.println("l: " +lEnc);

    //reset the encoder???

    if (buttonBoard.m_buttonboard.getRawButtonPressed(7) == true){
      objects.rMaster.setSelectedSensorPosition(0);
      objects.lMaster.setSelectedSensorPosition(0);

    }else{

    }
    */

switch (m_autoSelected) {
      case kCustomAuto:
        // Put custom auto code here
        break;
      case kDefaultAuto:
      
        break;
      case kOption1:

      if (i0 == true){
        if(systems.linearMotion(objects.lMaster, objects.rMaster, 5, .3, 1) == false){
          systems.linearMotion(objects.lMaster, objects.rMaster, 5, .3, 1);
        }else{
          i0 = false;
          i1 = true;
        }
      }

      if(i1 == true){
        if(systems.rotation(objects.lMaster, objects.rMaster, 90, -1) == false){
          systems.rotation(objects.lMaster, objects.rMaster, 90, -1);
        }else{
          if(systems.linearMotion(objects.lMaster, objects.rMaster, 5, .3, 1) == false){
            systems.linearMotion(objects.lMaster, objects.rMaster, 5, .3, 1);
          }else{
            i1 = false;
            i2 = true;
          }
        }
      }

      if(i2 == true){
        if(systems.rotation(objects.lMaster, objects.rMaster, 90, 1) == false){
          systems.rotation(objects.lMaster, objects.rMaster, 90, 1);
        }else{
          if(systems.linearMotion(objects.lMaster, objects.rMaster, 15, .3, 1) == false){
            systems.linearMotion(objects.lMaster, objects.rMaster, 15, .3, 1);
          }else{
            i2 = false;
            i3 = true;
          }
        }
      }

      if(i3 == true){
        if(systems.rotation(objects.lMaster, objects.rMaster, 90, 1) == false){
          systems.rotation(objects.lMaster, objects.rMaster, 90, 1);
        }else{
          if(systems.linearMotion(objects.lMaster, objects.rMaster, 5, .3, 1) == false){
            systems.linearMotion(objects.lMaster, objects.rMaster, 5, .3, 1);
          }else{
            i3 = false;
            i4 = true;
          }
        }
      }
      if(i4 == true){
        if(systems.rotation(objects.lMaster, objects.rMaster, 90, -1) == false){
          systems.rotation(objects.lMaster, objects.rMaster, 90, -1);
        }else{
          if(systems.linearMotion(objects.lMaster, objects.rMaster, 5, .3, 1) == false){
            systems.linearMotion(objects.lMaster, objects.rMaster, 5, .3, 1);
          }else{
            i4 = false;
            i5 = true;
          }
        }
      }
      if(i5 == true){
        if(systems.rotation(objects.lMaster, objects.rMaster, 90, -1) == false){
          systems.rotation(objects.lMaster, objects.rMaster, 90, -1);
        }else{
          if(systems.linearMotion(objects.lMaster, objects.rMaster, 5, .3, 1) == false){
            systems.linearMotion(objects.lMaster, objects.rMaster, 5, .3, 1);
          }else{
            i5 = false;
            i6 = true;
          }
        }
      }
      if(i6 == true){
        if(systems.rotation(objects.lMaster, objects.rMaster, 90, -1) == false){
          systems.rotation(objects.lMaster, objects.rMaster, 90, -1);
        }else{
          if(systems.linearMotion(objects.lMaster, objects.rMaster, 5, .3, 1) == false){
            systems.linearMotion(objects.lMaster, objects.rMaster, 5, .3, 1);
          }else{
            i6 = false;
            i7 = true;
          }
        }
      }
      if(i7 == true){
        if(systems.rotation(objects.lMaster, objects.rMaster, 90, -1) == false){
          systems.rotation(objects.lMaster, objects.rMaster, 90, -1);
        }else{
          if(systems.linearMotion(objects.lMaster, objects.rMaster, 5, .3, 1) == false){
            systems.linearMotion(objects.lMaster, objects.rMaster, 5, .3, 1);
          }else{
            i7 = false;
            i8 = true;
          }
        }
      }
      if(i8 == true){
        if(systems.rotation(objects.lMaster, objects.rMaster, 90, 1) == false){
          systems.rotation(objects.lMaster, objects.rMaster, 90, 1);
        }else{
          if(systems.linearMotion(objects.lMaster, objects.rMaster, 15, .3, 1) == false){
            systems.linearMotion(objects.lMaster, objects.rMaster, 15, .3, 1);
          }else{
            i8 = false;
            i9 = true;
          }
        }
      }
      if(i9 == true){
        if(systems.rotation(objects.lMaster, objects.rMaster, 90, 1) == false){
          systems.rotation(objects.lMaster, objects.rMaster, 90, 1);
        }else{
          if(systems.linearMotion(objects.lMaster, objects.rMaster, 5, .3, 1) == false){
            systems.linearMotion(objects.lMaster, objects.rMaster, 5, .3, 1);
          }else{
            i9 = false;
            i10 = true;
          }
        }
      }
      if(i10 == true){
        if(systems.rotation(objects.lMaster, objects.rMaster, 90, -1) == false){
          systems.rotation(objects.lMaster, objects.rMaster, 90, -1);
        }else{
          if(systems.linearMotion(objects.lMaster, objects.rMaster, 5, .3, 1) == false){
            systems.linearMotion(objects.lMaster, objects.rMaster, 5, .3, 1);
          }else{
            i10 = false;
            i11 = true;
          }
        }
      }
      if(i11 == true){
        objects.lMaster.set(0);
        objects.rMaster.set(0);
      }

        break;


    }
    
  
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    time++;

    // the color of the wheel; B = 1; G = 2; R = 3; Y = 4; none = 0;
    //int gameColor = innerSystems.gameData_Color();
    objects.lMaster.getFaults(faults);


      systems.driveTeleop(objects.rMaster, objects.lMaster, objects.rSlave, objects.lSlave,
          xboxController.m_drivexbcont, buttonBoard.m_buttonboard);
    systems.climb(objects.cwSpark, xboxController.m_drivexbcont);

  
    systems.soloSolControl(xboxController.m_drivexbcont, objects.soleA);
    systems.cannon(xboxController.m_drivexbcont, objects.lBallSpark, objects.rBallSpark, objects.intakeSpark);
    systems.lift(xboxController.m_drivexbcont, objects.lift);
    //System.out.println(m_colorSensor.getRawColor());


                                                      
  }

  @Override
  public void testPeriodic() {
    //System.out.println(m_colorSensor.getColor());
    //System.out.println(m_colorSensor.getBlue() ++ m_colorSensor.getGreen() ++ m_colorSensor.getRed(), )

    // System.out.println(m_controller.getYChannel());

  }
}
