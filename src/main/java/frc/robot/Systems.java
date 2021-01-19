package frc.robot;

//import java.util.Timer;
//import java.util.TimerTask;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
//import com.revrobotics.ColorSensorV3;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
//import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Spark;
//import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.Contants.IO.ButtonBoard;

public class Systems
{

    public static Contants constants = new Contants();
    public static ButtonBoard buttonBoard = new ButtonBoard();


    /**
     * sets encoder value of given talon to zero
     * @param controller Xbox controller
     * @param talon talon to set pos at zero
     */
    public void setEncZero(XboxController controller, WPI_TalonSRX talon) 
    {
        
        if (controller.getRawButton(1) == true ) 
        {
            talon.setSelectedSensorPosition(0);
            
        }
    }

    /**
     * This is a dead band. Input ranges between -.05 to .05 will be returned as 0.
     * @param rawNum The double value
     * @return Returns the raw value if it isn't in the Deadband range
     */
    public static double deadband(double rawNum){
        if (Math.abs(rawNum) < .05){
            return 0;
        }else{
            return rawNum;
        }
    }

    /**
     * InnerSystems
     */
    static class InnerSystems {
    
        /**
     * configures Talons to settings of choice. These changes can also be made via Phwonix Tuner.
     * @param lMaster 
     * @param lSlave
     * @param rSlave
     * @param rSlave
     * 
     */
        public void configTalon(WPI_TalonSRX lMaster, WPI_TalonSRX rMaster,WPI_TalonSRX lSlave,WPI_TalonSRX rSlave){
            lMaster.configFactoryDefault();
            lSlave.configFactoryDefault();
            rMaster.configFactoryDefault();
            rSlave.configFactoryDefault();
            
            lMaster.configNominalOutputForward(0);
            lMaster.configNominalOutputReverse(0);
            lMaster.configPeakOutputForward(1);
            lMaster.configPeakOutputReverse(-1);
            lMaster.setNeutralMode(NeutralMode.Brake);
        
                
            lSlave.configNominalOutputForward(0);
            lSlave.configNominalOutputReverse(0);
            lSlave.configPeakOutputForward(1);
            lSlave.configPeakOutputReverse(-1);
            lSlave.setNeutralMode(NeutralMode.Brake);
        
                
            rMaster.configNominalOutputForward(0);
            rMaster.configNominalOutputReverse(0);
            rMaster.configPeakOutputForward(1);
            rMaster.configPeakOutputReverse(-1);
            rMaster.setNeutralMode(NeutralMode.Brake);
        
                
            rSlave.configNominalOutputForward(0);
            rSlave.configNominalOutputReverse(0);
            rSlave.configPeakOutputForward(1);
            rSlave.configPeakOutputReverse(-1);
            rSlave.setNeutralMode(NeutralMode.Brake);
        }
        /**
         * used to get the color from the game; currently broken
         * 
         * 
         * 
         */
        public int gameData_Color()
        {
            String gameData = DriverStation.getInstance().getGameSpecificMessage();
            int a;
            if(gameData.length() > 0){
                if(gameData.charAt(0) == 'B')
                    {
                        a = 1;
                    }
                else if(gameData.charAt(0) == 'G')
                    {
                        a = 2;
                    }
                else if(gameData.charAt(0) == 'R')
                    {
                        a = 3;
                    }
                else if(gameData.charAt(0) == 'Y')
                    {
                        a = 4;
                    }
                else
                    {
                        a = 0;
                    }
            }else{
                a = 0;
            }
            return a;
        }


        
        
    }
   /**
     * Converts feet to Encoder values
     * @param ft input feet
     * 
     */
    public double feetToEnc(double ft){
        double pi = Math.PI;

        double mul = 360 * ft;   
        double hPI = pi * .5;
        double encC = mul/hPI;   // (360 * ft )/ (.5pi) ft = pi*.5
 
        return encC;
    }
    
    /**
     * Moves climb moter
     * @param spark spark motor
     * @param X input xbox controller
     */
    public void climb(Spark spark, XboxController x){
    
        if (x.getPOV() == 90){
            spark.set(1);
        }else if(x.getPOV() == 270){
            spark.set(-1);
        }else{
            spark.set(0);
        }
    }

    /**
     * This method, if state == true, will cube the raw value. This is ment to be used for joystick/Trigger
     * Axises wich vary from -1 to 1.
     * @param rawValue The input or raw value of the Axis
     * @param state Weather (true) or not (false) to run the method
     * @return Returns the cubed double value
     */
    public static double cubeDrive(double rawValue, boolean state)
    {
        if(state == true)
        {
            if (rawValue > 0)
                {
                    double rV2 = Math.pow(rawValue,3);
                    return rV2;
                }
            else if (rawValue < 0)
                {
                    double rV3 = (Math.pow(rawValue, 3));
                    return rV3;
                }
            else
                {
                    return 0;
                }
        }
        else
        {
            return rawValue;
        }
        
    }
    
    public static boolean inv;
    public static int i2 = 0;
    
    
    /**
     * Toggles invert. NEEDS TO BE LOOKED AT AND FIXED
     * ISSUE: currently set up as input = true, then invert. This is a problem as you need inhuman speed to click the button in under a few milliseconds
     * FIX: add a button for the false statement.
     * @return Returns the cubed double value
     */
    public static void toggle(Joystick j){
        
        if (j.getRawButtonPressed(buttonBoard.invertbutton) == true){
            if (i2 % 2 == 0){
                inv = true;
                i2++;
    
            }else{
                inv = false;
                i2++;
            }
        }

    }
    /**
     * This Method is used as an arcade dive for four WPI_TalsonSRX Motor controllers. Also inverts the control if needed
     * @param rMaster Right Front Motor
     * @param lMaster Left Front Motor
     * @param rSlave Right Back Motor (make sure that you've set it to follow RMaster in roboInit)
     * @param lSlave Left Back Motor (make sure that you've set it to follow lMaster in roboInit)
     * @param teemo Xbox Controller
     * @param inverse If true, the robot orientation is flipped
     */
    public void driveTeleop(WPI_TalonSRX rMaster, WPI_TalonSRX lMaster, WPI_TalonSRX rSlave, WPI_TalonSRX lSlave, XboxController teemo) {
        //remember to change this channel!!!
        double invert = 1;
        if (inv == true)
            invert = invert*-1;

        Double speed = (cubeDrive(deadband(teemo.getY(Hand.kLeft)),true)*invert);
        Double turn = -(cubeDrive(deadband(teemo.getX(Hand.kRight)),true)*invert); 
       //teemo is the xbox thing, remember :)
        Double left = speed + turn;
        Double right = speed - turn;
       
        lMaster.set(left);
       // lSlave.set(left*invert);
        rMaster.set(right);
       // rSlave.set(right*invert);

    
    }

    /**
     * The Method is ment to control the entire intake and shooting system.
     * @param x Xbox controller
     * @param l Left cannon spark
     * @param r Right cannon spark
     * @param b Belt motor (controls intake)
     * 
     */
    public void cannon(XboxController x, Spark l, Spark r, Spark b)
    {
        if (x.getTriggerAxis(Hand.kRight) > .5)
        {
            if(x.getBumper(Hand.kRight) == true)
            {
                l.set(1);
                r.set(1);
                b.set(.5);
            }
            else if (x.getBumper(Hand.kRight) == false && (x.getBumper(Hand.kLeft) == false))
            {
                l.set(1);
                r.set(1);
                b.set(0);
            }else if((x.getBumper(Hand.kLeft) == true)){
                l.set(1);
                r.set(1);
                b.set(-.05);
            }
        }
        else
        {
            if(x.getBumper(Hand.kRight) == true)
            {
                l.set(-.35);
                r.set(-.35);
                b.set(.5);
            }
            else if (x.getBumper(Hand.kRight) == false && x.getBumper(Hand.kLeft) == false)
            {
                l.set(0);
                r.set(0);
                b.set(0);
            }else if (x.getBumper(Hand.kLeft) == true){
                l.set(-.1);
                r.set(-.1);
                b.set(-.5);
            }
        }
        
    }

    public void lift(XboxController x, Spark liftMotor){
        if(x.getBackButton() == true){
            liftMotor.set(1);
        }else if (x.getStartButton() == true){
            liftMotor.set(-1);
        }else{
            liftMotor.set(0);
        }
    }

    /**
     * This function sets the velocity of the left chassis motor to zero equal if both are different.
     * @param leftV disc
     * @param leftTalonSRX left TalonSRX
     * @param rightTalonSRX right TalonSRX
     * 
     */
    public void leftrealign(WPI_TalonSRX leftTalonSRX, WPI_TalonSRX rightTalonSRX, int leftV)
    {
        leftV = leftTalonSRX.getSelectedSensorVelocity();
        if (leftV != rightTalonSRX.getSelectedSensorVelocity())
        {
            leftTalonSRX.set(0);
        }
        else
        {

        }
    }
    /**
     * This function sets the velocity of the right chassis motor to zero equal if both are different.
     * @param leftTalonSRX the left TalonSRX
     * @param rightTalonSRX the right TalonSRX
     * @param rightV the velocity of the right motor controller
     */
    public void rightrealight(WPI_TalonSRX leftTalonSRX, WPI_TalonSRX rightTalonSRX, int rightV)
    {
        rightV = rightTalonSRX.getSelectedSensorVelocity();    
        if (rightV != leftTalonSRX.getSelectedSensorVelocity());
        {
            rightTalonSRX.set(0);
        }
    }

    /** This function raises or lowers the double solenoids.
    *  @param x the state of the D - Pad; up raises it and down lowers it
    *  @param sole the double solenoid that is being raised or lowered
    */
    public void soloSolControl(XboxController x, Solenoid sole){

        if ( x.getPOV() == 0){
            sole.set(true);
        }if (x.getPOV() == 180) {
            sole.set(false);
        }
        
    }


    /**
     * This method enables the "A" and "B" buttons on the Xbox controller to control the solenoids refrenced.
     * NOTE: this is set up for two "DoubleSolenoid"s, not two "Solenoid"s
     * @param x xbox controller
     * @param soleA this is a double solenoid
     * @param soleB this is lso a double solenoid
     * @deprecated use soloSolControl() instead
     */
    public void soleControl(XboxController x, DoubleSolenoid soleA, DoubleSolenoid soleB){
        if (x.getAButton() == true && x.getBButton() == false) 
        {
            soleA.set(Value.kForward);
            soleB.set(Value.kForward);
        }
        if (x.getBButton() == true && x.getAButton() == false)
        {
            soleA.set(Value.kReverse);
            soleB.set(Value.kReverse);
        }
        if (x.getAButtonReleased() == true || x.getBButtonReleased() == true || (x.getAButton() == true && x.getBButton() == true))
        {
            soleA.set(Value.kOff);
            soleB.set(Value.kOff);
        }else
        {
            soleA.set(Value.kOff);
            soleB.set(Value.kOff);
        }
    }

}

