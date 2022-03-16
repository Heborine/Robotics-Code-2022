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

    @Override
    public void robotInit() {
        drivetrain = new DrivetrainMotors();
        shooter = new Shooter();
        climber = new Climber();
        //limelight = new AutonomousLimelight();
        intake = new Intake();

        XboxController0 = new XboxController(RobotMap.XboxController0);
        //XboxController1 = new XboxController(RobotMap.XboxController1);
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
        delete asterisk below to comment out entire control scheme
        */
        if(XboxController0.getStartButtonPressed()){
            if (arcadeDriveActive) { arcadeDriveActive = false; } else { arcadeDriveActive = true; }
        }
        if(!arcadeDriveActive) {
            double y_axis_left = (XboxController0.getLeftY() * RobotMap.drivetrainPower);
            double y_axis_right = (XboxController0.getRightY() * RobotMap.drivetrainPower);
            if(XboxController0.getBButtonPressed()){
                drivetrain.drivetrain.tankDrive(0, 0);
            }
            else{
                while(XboxController0.getXButtonPressed()){
                    //reverse controls
                    drivetrain.drivetrain.tankDrive(-y_axis_left, -y_axis_right);
                }
                drivetrain.drivetrain.tankDrive(y_axis_left, y_axis_right);
            }
        }
        else {
            //smoother movement and more adjustable
            //edit for arcade drive with power

            //set variables to: (controller input, squared) * (limit set in RobotMap.java) * ((controller input) / (absolute value(controller input)))
            //makes sure that variables are always positive
            double x_axis = Math.pow(XboxController0.getLeftX(), 2) * RobotMap.drivetrainPower * (XboxController0.getLeftX()/Math.abs(XboxController0.getLeftX()));
            double y_axis = Math.pow(XboxController0.getLeftY(), 2) * RobotMap.drivetrainPower * (XboxController0.getLeftY()/Math.abs(XboxController0.getLeftY()));

            System.out.println(y_axis);
            
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
        shooter.topMotor.set(XboxController0.getRightTriggerAxis() * RobotMap.shooterPower);
        shooter.bottomMotor.set(XboxController0.getRightTriggerAxis() * RobotMap.shooterPower);

        while (XboxController0.getRightBumperPressed()) {
            intake.intakeExtender.set(-1 * RobotMap.rollerExtendPower);
        }

        while (XboxController0.getLeftBumperPressed()){
            intake.intakeExtender.set(1 * RobotMap.rollerExtendPower);
        }

        while (XboxController0.getRightBumperReleased() & XboxController0.getLeftBumperReleased()) {
        intake.intakeExtender.set(0.0);
        }

        intake.intakeRoller.set(XboxController0.getLeftTriggerAxis() * RobotMap.intakeSpeed);

        while(XboxController0.getAButtonPressed()){
            climber.raiseClimber();
        }
        while(XboxController0.getYButtonPressed()){
            climber.lowerClimber();
        }
    }
}
