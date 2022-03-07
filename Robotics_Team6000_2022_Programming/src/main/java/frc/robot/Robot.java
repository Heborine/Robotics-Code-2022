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

import frc.robot.subsystems.ArcadeDrivetrain;
import frc.robot.subsystems.AutonomousLimelight;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Intake;


public class Robot extends TimedRobot {
    public static ArcadeDrivetrain drivetrain;
    public static Shooter shooter;
    public static Climber climber;
    public static AutonomousLimelight limelight;
    public static Intake intake;
    public static double leftStickVal;
    public static double rightStickVal;

    public static XboxController XboxController0;
    public static XboxController XboxController1;

    @Override
    public void robotInit() {
        drivetrain = new ArcadeDrivetrain();
        shooter = new Shooter();
        climber = new Climber();
        limelight = new AutonomousLimelight();
        intake = new Intake();

        XboxController0 = new XboxController(RobotMap.XboxController0);
        XboxController1 = new XboxController(RobotMap.XboxController1);
    }

    @Override
    public void autonomousPeriodic() {
        limelight.autoPeriodic();
        drivetrain.driveRoute();
        shooter.Firing();
        Timer.delay(5);
        shooter.StopFiring();
    }

    @Override
    public void teleopPeriodic() {
        //if the start button is pressed, set the controls to tank drive - else, use arcadedrive - control swap
        //double x_axis = XboxController0.getLeftX();
        //double y_axis = XboxController0.getLeftY();
        if(XboxController0.getStartButtonPressed()){
            double y_axis_left = (XboxController0.getLeftY() * RobotMap.drivetrainPower);
            double y_axis_right = (XboxController0.getRightY() * RobotMap.drivetrainPower);
            if(XboxController0.getBButtonPressed()){
                drivetrain.drivetrain.tankDrive(0, 0);
            }
            else{
                while(XboxController0.getXButtonPressed()){
                    //reverse controls
                    drivetrain.drivetrain.tankDrive((-1 * y_axis_left), (-1 * y_axis_right));
                }
                drivetrain.drivetrain.tankDrive(y_axis_left, y_axis_right);
            }
        } 
        else {
            //full stop
            if(XboxController0.getBButtonPressed()){
                drivetrain.drivetrain.arcadeDrive(0, 0);
            }
            
            //turn differently (not moving forward or backward) when left stick pressed
            else if(XboxController0.getLeftStickButtonPressed()){
                drivetrain.drivetrain.arcadeDrive(0, x_axis);
            }
            //get controller left stick x and y to set speed and rotation
            else{
                while(XboxController0.getXButtonPressed()){
                    drivetrain.drivetrain.arcadeDrive((-1 * y_axis), x_axis);
                }
                drivetrain.drivetrain.arcadeDrive(y_axis, x_axis);
            }
        }
        //set shooters to right back trigger
        shooter.topMotor.set(XboxController0.getTriggerAxis(Hand.kRight) * RobotMap.shooterPower);
        shooter.bottomMotor.set(XboxController0.getTriggerAxis(Hand.kRight) * RobotMap.shooterPower);

        while (XboxController0.getBumperPressed(Hand.kRight)) {
            intake.intakeExtender.set(-1 * RobotMap.rollerExtendPower);
        }

        while (XboxController0.getBumperPressed(Hand.kLeft)){
            intake.intakeExtender.set(1 * RobotMap.rollerExtendPower);
        }

        while (XboxController0.getBumperReleased(Hand.kRight) & XboxController0.getBumperReleased(Hand.kLeft)) {
        intake.intakeExtender.set(0.0);
        }

        intake.intakeRoller.set(XboxController0.getTriggerAxis(Hand.kLeft) * RobotMap.intakeSpeed);

        while(XboxController0.getAButtonPressed()){
            climber.climberMotor.raiseClimber();
        }
        while(XboxController0.getYButtonPressed()){
            climber.climberMotor.lowerClimber();
        }
        //smoother movement and more adjustable
        //edit for arcade drive with power
        /*
        if(XboxController0.getX(Hand.kLeft) < 0){
            double x_axis = Math.pow(XboxController0.getX(Hand.kLeft), 2) * RobotMap.drivetrainPower * -1;
        }
        else{
            double x_axis = Math.pow(XboxController0.getX(Hand.kLeft), 2) * RobotMap.drivetrainPower;
        }
        if(XboxController0.getY(Hand.kLeft) < 0){
            double y_axis = Math.pow(XboxController0.getY(Hand.kLeft), 2) * RobotMap.drivetrainPower * -1;
        }
        else{
            double y_axis = Math.pow(XboxController0.getY(Hand.kLeft), 2) * RobotMap.drivetrainPower;
        }*/
        }
    }
//}
