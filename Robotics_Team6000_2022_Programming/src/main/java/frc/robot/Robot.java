//define control scheme -- arcade vs. tank
// Control Scheme Document so far: https://docs.google.com/document/d/1rWMHrpDTBKcG1Q6-gbv4yI6xAS-BwlAHAO730PDODtk/edit

package frc.robot;


//import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.TimedRobot;
//import edu.wpi.first.wpilibj.GenericHID.Hand;
//import edu.wpi.first.wpilibj.command.Scheduler;
// import edu.wpi.first.cameraserver.*;
// import edu.wpi.first.networktables.NetworkTable;
// import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;

import java.lang.System;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.networktables.NetworkTable;
// import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
// import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
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
    // public static boolean arcadeDriveActive = false;
    public static boolean intakeActive = false;
    // public static boolean magazineActive = false;
    public static boolean limelight_on = true;
    public static boolean verbose = true; //change this for amount of output. its a lot of output
    public static boolean taxi = true;

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
        if(taxi){
            taxi = false;
            drivetrain.drivetrain.tankDrive(-RobotMap.autonomousDrivePower, -RobotMap.autonomousDrivePower);
            Timer.delay(1.25);
            drivetrain.drivetrain.tankDrive(0, 0);
            shooter.Firing();
            Timer.delay(3);
            shooter.StopFiring();
        }
    }
        //limelight.autoPeriodic();
        //drivetrain.driveRoute();
        //shooter.Firing();
        //Timer.delay(5);
        //shooter.StopFiring();
    //     float KpAim = -0.1f;
    //     float KpDistance = -0.1f;
    //     float min_aim_command = 0.05f;

    //     double left_command = 0;
    //     double right_command = 0;
        
    //     double tx = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0);
    //     double ty = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ty").getDouble(0);
    //     // System.out.println("Limelight Values:");
    //     // System.out.println(NetworkTableInstance.getDefault().getTable("limelight").getEntry("ty").getDouble(0));
    //     if (limelight_on){
    //         double heading_error = -tx;
    //         double distance_error = -ty;
    //         double steering_adjust = 0.0f;
    
    //         if (tx > /*3.5*/ 1){
    //             steering_adjust = KpAim*heading_error - min_aim_command;
    //         }
    //         else if (tx < /*-3.5*/ -1){
    //             steering_adjust = KpAim*heading_error + min_aim_command;
    //         }
    //         /*else{
    //             if(ty > 24){
    //                 steering_adjust = KpAim*heading_error - min_aim_command;
    //             }
    //             else if(ty < 19){
    //                 steering_adjust = KpAim*heading_error + min_aim_command;
    //             }
    //         }*/
    
    //         double distance_adjust = KpDistance * distance_error;
    
    //         left_command += steering_adjust + distance_adjust;
    //         right_command -= steering_adjust + distance_adjust;

    //         left_command *= -1;
    //         right_command *= -1;
    // }
    //     //maybe adjust here
    //     System.out.println("left_command:");
    //     System.out.println(left_command);
    //     System.out.println("right_command:");
    //     System.out.println(right_command);

    //     drivetrain.drivetrain.tankDrive(left_command, right_command);


    //     if(left_command == 0 && right_command == 0){
    //         limelight_on = false;
    //         System.out.println("Firing");
    //         shooter.Firing();
    //         Timer.delay(3);
    //         shooter.StopFiring();
    //         // drivetrain.drivetrain.tankDrive(-0.5, -0.5);
    //         // Timer.delay(2);
    //         // drivetrain.drivetrain.tankDrive(0, 0);
    //     }
    // }

    @Override
    public void teleopPeriodic() {
        boolean arcadeDriveActive = false;
        // boolean intakeActive = false;    
        // boolean magazineActive = false;
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
            double getRightY = XboxController0.getRightY();
            
            //simple acceleration curve
            double y_left = getLeftY * Math.abs(getLeftY) * RobotMap.drivetrainPower;
            double y_right = getRightY * Math.abs(getRightY) * RobotMap.drivetrainPower;
            if(XboxController0.getBButtonPressed()){
                //full stop
                drivetrain.drivetrain.tankDrive(0, 0);
            }
            //turn in place
            else if(XboxController0.getLeftStickButtonPressed()){
                drivetrain.drivetrain.tankDrive(-y_left, y_left);
            }
            else if(XboxController0.getRightStickButtonPressed()){
                drivetrain.drivetrain.tankDrive(y_right, -y_right);
            }
            else{
                drivetrain.drivetrain.tankDrive(-y_left, -y_right);
            }

            //super speed
            while(XboxController0.getRightBumper()) {
                drivetrain.drivetrain.tankDrive(RobotMap.drivetrainPower, RobotMap.drivetrainPower);
            }
            while(XboxController0.getLeftBumper()) {
                drivetrain.drivetrain.tankDrive(-RobotMap.drivetrainPower, -RobotMap.drivetrainPower);
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

            //super speed
            while(XboxController0.getRightBumper()) {
                drivetrain.drivetrain.arcadeDrive(RobotMap.drivetrainPower, 0);
            }
            while(XboxController0.getLeftBumper()) {
                drivetrain.drivetrain.arcadeDrive(-RobotMap.drivetrainPower, 0);
            }
    
        }

        //set shooters to right back trigger
        // three motors for shooting: top, bottom, and magazine
        double right_trigger = XboxController1.getRightTriggerAxis();
        double left_trigger = XboxController1.getLeftTriggerAxis();
        //N likes the code below a LOT
        if(right_trigger > 0){
            shooter.topMotor.set(right_trigger * 0.65 * -RobotMap.shooterPower);
            shooter.bottomMotor.set(-right_trigger * RobotMap.shooterPower);
        }
        else{
            shooter.topMotor.set(left_trigger * RobotMap.shooterPower);
            shooter.bottomMotor.set(left_trigger * RobotMap.shooterPower);
        }

        //magazine
        if(XboxController1.getYButton()) {
            shooter.magazine.set(RobotMap.magazinePower);
            if (verbose) System.out.println("Y pressed");
        }
        //reverse magazine
        else if(XboxController1.getAButton()) {
            shooter.magazine.set(RobotMap.reverseMagazinePower);
            if (verbose) System.out.println("A pressed");
        }
        else{
            shooter.magazine.set(0);
            // if (verbose) System.out.println("A and Y not pressed");
        }

        //intake extension/retraction
        if(XboxController1.getRightBumper()){
            intake.intakeExtender.set(-RobotMap.rollerExtendPower);
            if (verbose) System.out.println("Right Bumper pressed");
        }
        if(XboxController1.getLeftBumper()){
            intake.intakeExtender.set(RobotMap.rollerExtendPower);
            if (verbose) System.out.println("Left Bumper pressed");
        }
        if(!XboxController1.getRightBumper() && (!XboxController1.getLeftBumper())){
            intake.intakeExtender.set(0.0);
        }
        //toggle intake
        if (XboxController1.getXButtonPressed()) {
            if (verbose) System.out.println("X pressed");
            if (intakeActive){
                intakeActive = false;
                intake.intakeRoller.set(0);
                if (verbose) System.out.println("Intake inactive");
            }
            else{
                intakeActive = true;
                intake.intakeRoller.set(RobotMap.intakeSpeed);
                if (verbose) System.out.println("Intake active");
            } 
        }

        // if(XboxController1.getAButtonPressed()){
        //     climber.raiseClimber();
        // }
        // if(XboxController1.getYButtonPressed()){
        //     climber.lowerClimber();
        // }

        //controller rumble by limelight
        /*float KpAim = -0.1f;
        float KpDistance = -0.1f;
        float min_aim_command = 0.05f;

        double left_command = 0;
        double right_command = 0;
        
        double tx = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0);
        double ty = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ty").getDouble(0);
    
        
    //    if (XboxController1.getAButtonPressed()) {
    //        NetworkTable limelighting = NetworkTableInstance.getDefault().getTable("limelight");
    //        limelighting.ledMode("ledMode", 1);
    //    }


        if (limelight_on){
                double heading_error = -tx;
                double distance_error = -ty;
                double steering_adjust = 0.0f;
        
                if (tx > 3.0){
                    steering_adjust = KpAim*heading_error - min_aim_command;
                }
                else if (tx < -3.0){
                    steering_adjust = KpAim*heading_error + min_aim_command;
                }
                else{
                    if(ty > 24){
                        steering_adjust = KpAim*heading_error - min_aim_command;
                    }
                    else if(ty < 19){
                        steering_adjust = KpAim*heading_error + min_aim_command;
                    }
                }
        
                double distance_adjust = KpDistance * distance_error;
        
                left_command += steering_adjust + distance_adjust;
                right_command -= steering_adjust + distance_adjust;
        }
        
        if(left_command == 0 && right_command == 0){
           XboxController0.setRumble(RumbleType.kLeftRumble, RobotMap.vibratePower);
           XboxController0.setRumble(RumbleType.kRightRumble, RobotMap.vibratePower);
            XboxController1.setRumble(RumbleType.kLeftRumble, RobotMap.vibratePower);
            XboxController1.setRumble(RumbleType.kRightRumble, RobotMap.vibratePower);
            if (verbose) System.out.println("Rumble");
        } 
        else{
           XboxController0.setRumble(RumbleType.kLeftRumble, 0);
            XboxController0.setRumble(RumbleType.kRightRumble, 0);
            XboxController1.setRumble(RumbleType.kLeftRumble, 0);
            XboxController1.setRumble(RumbleType.kRightRumble, 0);
            if (verbose) System.out.println("No rumble");
        }*/
    }
}
