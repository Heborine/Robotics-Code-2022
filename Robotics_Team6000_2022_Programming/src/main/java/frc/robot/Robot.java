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
    public static boolean arcadeDriveActive = true;
    public static boolean intakeActive = false;

    @Override
    public void robotInit() {
        drivetrain = new DrivetrainMotors();
        shooter = new Shooter();
        climber = new Climber();
        //limelight = new AutonomousLimelight();
        intake = new Intake();

        XboxController0 = new XboxController(RobotMap.XboxController0);
        XboxController1 = new XboxController(RobotMap.XboxController1);
    }
/*
    @Override
    public void autonomousPeriodic() {
        //limelight.autoPeriodic();
        drivetrain.driveRoute();
        shooter.Firing();
        Timer.delay(5);
        shooter.StopFiring();
    }
*/
    @Override
    public void teleopPeriodic() {

        /*test one motor: */
        // drivetrain.drivetrain.arcadeDrive(y_axis, x_axis);

        //if the start button is pressed, set the controls to tank drive - else, use arcadedrive -- control swap

        /*
        delete asterisk on next line to comment out entire control scheme
        */
        if(XboxController0.getStartButtonPressed()) {
            if (arcadeDriveActive) { arcadeDriveActive = false; } else { arcadeDriveActive = true; }
        }
        // if there is a valid target by the limelight maybe input something here to have the controller react

        if(!arcadeDriveActive) {
            double left_stick = XboxController0.getLeftY();
            double right_stick = XboxController0.getRightY();
            double y_axis_left = (left_stick * RobotMap.drivetrainPower * (left_stick/Math.abs(left_stick)));
            double y_axis_right = (right_stick * RobotMap.drivetrainPower * (right_stick/Math.abs(right_stick)));
            if(XboxController0.getBButtonPressed()){
                //full stop
                drivetrain.drivetrain.tankDrive(0, 0);
            }
            else{
                //reverse controls
                while(XboxController0.getXButtonPressed()){
                    drivetrain.drivetrain.tankDrive(-y_axis_left, -y_axis_right);
                }
                drivetrain.drivetrain.tankDrive(y_axis_left, y_axis_right);
            }
        }
        else {
            double y_axis = 0;
            double x_axis = 0;
            double getLeftX = XboxController0.getLeftX();
            double getLeftY = XboxController0.getLeftY();
            //smoother movement and more adjustable
            //edit for arcade drive with power

            //set variables to: (controller input, squared) * (limit set in RobotMap.java) * ((controller input) / (absolute value(controller input)))
            //makes sure that variables are always positive
            if (getLeftX != 0){
                x_axis = Math.pow(getLeftX, 2) * RobotMap.drivetrainPower * (getLeftX/Math.abs(getLeftX));
            }
            if (getLeftY != 0){
                y_axis = Math.pow(getLeftY, 2) * RobotMap.drivetrainPower * (getLeftY/Math.abs(getLeftY));
            }
            // System.out.println(y_axis);
            
            //full stop
            if(XboxController0.getBButtonPressed()){
                drivetrain.drivetrain.arcadeDrive(0, 0);
            }
            
            //turn differently (not moving forward or backward) when left stick pressed
            else if(XboxController0.getLeftStickButtonPressed()){
                drivetrain.drivetrain.arcadeDrive(0, y_axis);
            }
            //get controller left stick x and y to set speed and rotation
            else{
                while(XboxController0.getXButtonPressed()){
                    drivetrain.drivetrain.arcadeDrive(x_axis, -y_axis);
                }
                drivetrain.drivetrain.arcadeDrive(x_axis, y_axis);
            }
        }
        //set shooters to right back trigger
        // while (XboxController0.)
        // three motors for shooting: top, bottom, and magazine
        shooter.topMotor.set(XboxController1.getRightTriggerAxis() * RobotMap.shooterPower);
        shooter.bottomMotor.set(XboxController1.getRightTriggerAxis() * RobotMap.shooterPower);
        shooter.bottomMotorMag.set(XboxController1.getRightTriggerAxis() * RobotMap.shooterPower);

        //intake extension
        while (XboxController1.getRightBumperPressed() && XboxController0.getLeftBumperReleased()) { intake.intakeExtender.set(-RobotMap.rollerExtendPower); }
        while (XboxController1.getLeftBumperPressed() && XboxController1.getRightBumperReleased()) { intake.intakeExtender.set(RobotMap.rollerExtendPower); }
        while (XboxController1.getRightBumperReleased() && XboxController0.getLeftBumperReleased()) { intake.intakeExtender.set(0.0); }

        if (XboxController1.getXButtonPressed()) {
            if (intakeActive){
                intakeActive = false;
                intake.intakeRoller.set(0); 
            }  
            else{
                intakeActive = true;
                intake.intakeRoller.set(RobotMap.intakeSpeed);
            } 
        }
        // if (intakeActive) {
        //     intake.intakeRoller.set(RobotMap.intakeSpeed);
        // }
        // else{
        //     intake.intakeRoller.set(0);
        // }

        // while(XboxController0.getAButtonPressed()){
        //     climber.raiseClimber();
        // }
        // while(XboxController0.getYButtonPressed()){
        //     climber.lowerClimber();
        // }
    }
}
// we can add a second controller portion if we want to split the subsystems potentially