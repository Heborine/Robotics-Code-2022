package frc.robot.subsystems;

//import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
//import edu.wpi.first.wpilibj.motorcontrol.VictorSP;
// import edu.wpi.first.wpilibj.Encoder;
import frc.robot.RobotMap;

public class DrivetrainMotors implements Subsystem{
    public Spark leftMotor;
    public Spark rightMotor;
    public DifferentialDrive drivetrain;
    // public Encoder leftEncoder;
    // public Encoder rightEncoder;

    //Constructor
    public DrivetrainMotors() {
        leftMotor = new Spark(RobotMap.leftDrivetrain);
        rightMotor = new Spark(RobotMap.rightDrivetrain);
        drivetrain = new DifferentialDrive(leftMotor, rightMotor);
        
        //set to true if the motor polarity needs to be flipped
        leftMotor.setInverted(false);
        rightMotor.setInverted(true);

        // leftEncoder = new Encoder(RobotMap.leftDrivetrainEncoderA, RobotMap.leftDrivetrainEncoderB, RobotMap.leftDrivetrainEncoder_Reverse, Encoder.EncodingType.k2X);
        // rightEncoder = new Encoder(RobotMap.rightDrivetrainEncoderA, RobotMap.rightDrivetrainEncoderB, RobotMap.rightDrivetrainEncoder_Reverse, Encoder.EncodingType.k2X);
    
    // Configures the encoder's distance-per-pulse
    // The robot moves forward 1 foot per encoder rotation
    // There are 256 pulses per encoder rotation
        /*
        leftEncoder.reset();
        leftEncoder.setDistancePerPulse(1./256.);
        leftEncoder.setMaxPeriod(RobotMap.drivetrainEncoder_MaxPeriod);
        leftEncoder.setMinRate(RobotMap.drivetrainEncoder_MinRate);
        leftEncoder.setDistancePerPulse(RobotMap.drivetrainEncoder_RadiansPerPulse);
        leftEncoder.setSamplesToAverage(7);


        rightEncoder.reset();
        rightEncoder.setDistancePerPulse(1./256.);
        rightEncoder.setMaxPeriod(RobotMap.drivetrainEncoder_MaxPeriod);
        rightEncoder.setMinRate(RobotMap.drivetrainEncoder_MinRate);
        rightEncoder.setDistancePerPulse(RobotMap.drivetrainEncoder_RadiansPerPulse);
        rightEncoder.setSamplesToAverage(7);
        */

        drivetrain.setSafetyEnabled(false);
    }
    public void driveRoute(){
        /*
        // Drives backward at half speed until the robot has moved 5 feet, then stops
        double distance = leftEncoder.getDistance();
        while(distance < 5){
            // Drives forward continuously at half speed, using the encoders to stabilize the heading
            drivetrain.arcadeDrive(-0.5, 0);
            distance = leftEncoder.getDistance();
        }
        */
        // stops after while statement
        drivetrain.arcadeDrive(0, 0);
    }

    public void turn(int xSpeed, int zRotation){
        drivetrain.arcadeDrive(xSpeed, zRotation);
    }
    
    public void movestraight(int xSpeed){
        drivetrain.arcadeDrive(xSpeed, 0.0);
    }

    public void pivot(int zRotation){
        drivetrain.arcadeDrive(0, zRotation);
    }

    public void brake(){
        drivetrain.arcadeDrive(0, 0);
    }

}
    /*
    public void turnright(int xSpeed, int zRotation){

    }

    public void turnleft(int xSpeed, int zRotation){

    }

    public void turnaround(int xSpeed, int zRotation){

    }

    public void pivotright(int zRotation){
        drivetrain.arcadeDrive(0, zRotation);
    }

    public void pivotleft(int zRotation){
        drivetrain.arcadeDrive(0, zRotation);
    }

    public void moveforward(int xSpeed){
        drivetrain.arcadeDrive(xSpeed, 0.0);
    }

    public void reverse(int xSpeed){
        drivetrain.arcadeDrive(xSpeed, 0.0);
    }

    public void stop(){

    }

    public void brake(){

    }
    */

    // @Override
    // protected void initDefaultCommand() {
    // }
    