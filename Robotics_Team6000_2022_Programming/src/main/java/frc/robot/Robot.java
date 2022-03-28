//define control scheme -- arcade vs. tank


package frc.robot;


//import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.TimedRobot;
//import edu.wpi.first.wpilibj.GenericHID.Hand;
//import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.cameraserver.*;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;

import java.lang.System;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.networktables.NetworkTableInstance;

import frc.robot.subsystems.DrivetrainMotors;
//import frc.robot.subsystems.AutonomousLimelight;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Intake;


public class Robot extends TimedRobot {
    public static DrivetrainMotors drivetrain;
    public static Shooter shooter;
    public static Climber climber;
    //public static AutonomousLimelight limelight;
    public static Intake intake;
    public static double leftStickVal;
    public static double rightStickVal;

    public static XboxController XboxController0;
    public static XboxController XboxController1;
    public static boolean arcadeDriveActive = false;
    public static boolean intakeActive = false;
    // public static boolean magazineActive = false;
    public static boolean limelight_on = true;
    public static boolean verbose = true; //change this for amount of output. its a lot of output


    @Override
    public void robotInit() {
        drivetrain = new DrivetrainMotors();
        shooter = new Shooter();
        //climber = new Climber();
        //limelight = new AutonomousLimelight();
        intake = new Intake();

        XboxController0 = new XboxController(RobotMap.XboxController0);
        XboxController1 = new XboxController(RobotMap.XboxController1);

        System.out.println("Arcade Drive");
    }

    @Override
    public void autonomousPeriodic() {
        //limelight.autoPeriodic();
        //drivetrain.driveRoute();
        //shooter.Firing();
        //Timer.delay(5);
        //shooter.StopFiring();
        float KpAim = -0.1f;
        float KpDistance = -0.1f;
        float min_aim_command = 0.05f;

        double left_command = 0;
        double right_command = 0;
        
        double tx = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0);
        double ty = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ty").getDouble(0);
        
        if (limelight_on){
                double heading_error = -tx;
                double distance_error = -ty;
                double steering_adjust = 0.0f;
        
                if (tx > 1.0){
                    steering_adjust = KpAim*heading_error - min_aim_command;
                }
                else if (tx < -1.0){
                    steering_adjust = KpAim*heading_error + min_aim_command;
                }
        
                double distance_adjust = KpDistance * distance_error;
        
                left_command += steering_adjust + distance_adjust;
                right_command -= steering_adjust + distance_adjust;
        }
        //maybe adjust here
        drivetrain.drivetrain.tankDrive(left_command, right_command);

        if(left_command == 0 && right_command == 0){
            limelight_on = false;
            shooter.Firing();
            Timer.delay(1.5);
            shooter.StopFiring();
        }
    }

    @Override
    public void teleopPeriodic() {
        /*
        delete asterisk on next line to comment out entire control scheme
        */

        //if the start button is pressed, set the controls to tank drive - else, use arcadedrive -- control swap
        if(XboxController0.getStartButtonPressed()) {
            if (arcadeDriveActive){
                arcadeDriveActive = false;
                System.out.println("Tank drive");
            } else{
                arcadeDriveActive = true;
                System.out.println("Arcade drive");
            }
        }
        // if there is a valid target by the limelight maybe input something here to have the controller react

        //tank drive
        if(!arcadeDriveActive) {
            double getLeftY = XboxController0.getLeftY();
            double getRightY = XboxController0. getRightY();
            
            //simple acceleration curve
            double y_left = getLeftY * Math.abs(getLeftY) * RobotMap.drivetrainPower;
            double y_right = getRightY * Math.abs(getRightY) * RobotMap.drivetrainPower;
            y_left *= 0.9;
            if(XboxController0.getBButtonPressed()){
                //full stop
                drivetrain.drivetrain.tankDrive(0, 0);
            }
            else{
                //reverse controls
                drivetrain.drivetrain.tankDrive(-y_left, -y_right);
            }
        }
        //arcade drive:
        else {
            double getLeftX = XboxController0.getLeftX();
            double getLeftY = XboxController0.getLeftY();
            
            //simple acceleration curve
            //negative robot map to fix inverted control pattern
            double speed = getLeftY * Math.abs(getLeftY) * -RobotMap.drivetrainPower;
            double rotation = getLeftX * Math.abs(getLeftX) * RobotMap.drivetrainPower;
            
            //full stop
            if(XboxController0.getBButtonPressed()){
                drivetrain.drivetrain.arcadeDrive(0, 0);
            }
            //turn differently (not moving forward or backward) when left stick pressed
            else if(XboxController0.getLeftStickButtonPressed()){
                drivetrain.drivetrain.arcadeDrive(speed, 0);
            } 
            else {
                drivetrain.drivetrain.arcadeDrive(speed, rotation);
            }
        }

        //set shooters to right back trigger
        // three motors for shooting: top, bottom, and magazine
        shooter.topMotor.set(XboxController1.getRightTriggerAxis() * RobotMap.shooterPower);
        shooter.bottomMotor.set(-XboxController1.getRightTriggerAxis() * RobotMap.shooterPower);
        
        //magazine
        while(XboxController1.getYButton()) {
            shooter.magazine.set(RobotMap.magazinePower);
            if (verbose) System.out.println("Y pressed");
        }
        shooter.magazine.set(0);
        //reverse magazine
        while(XboxController1.getAButton()) {
            shooter.magazine.set(RobotMap.reverseMagazinePower);
            if (verbose) System.out.println("A pressed");
        }
        shooter.magazine.set(0);

        //intake extension/retraction
        while(XboxController1.getRightBumper()){
            intake.intakeExtender.set(-RobotMap.rollerExtendPower);
            if (verbose) System.out.println("Right Bumper pressed");
        }
        while(XboxController1.getLeftBumper()){
            intake.intakeExtender.set(RobotMap.rollerExtendPower);
            if (verbose) System.out.println("Left Bumper pressed");
        }
        intake.intakeExtender.set(0.0);
        
        //toggle intake
        if (XboxController1.getXButtonPressed()) {
            if (intakeActive){
                intakeActive = false;
                intake.intakeRoller.set(RobotMap.intakeSpeed);
            }  
            else{
                intakeActive = true;
                intake.intakeRoller.set(0); 
            } 
            if (verbose) System.out.println("X pressed");
        }

        // if(XboxController1.getAButtonPressed()){
        //     climber.raiseClimber();
        // }
        // if(XboxController1.getYButtonPressed()){
        //     climber.lowerClimber();
        // }
    }
}
