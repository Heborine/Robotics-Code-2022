/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/


//various variables:
//spark inputs, misc

package frc.robot;

public class RobotMap {
  // Drivetrain
  public static int leftDrivetrain = 1;
  public static int rightDrivetrain = 0;

  public static int leftDrivetrainEncoderA = 0;
  public static int leftDrivetrainEncoderB = 1;
  public static int rightDrivetrainEncoderA = 2;
  public static int rightDrivetrainEncoderB = 3;

  public static double drivetrainEncoder_RadiansPerPulse = 2*3.14/512; //Check, assume encoders have 512 pulse/rev
  public static double drivetrainEncoder_MaxPeriod = 1.0;
  public static double drivetrainEncoder_MinRate = 6*3.14;

  public static boolean leftDrivetrainEncoder_Reverse = false;
  public static boolean rightDrivetrainEncoder_Reverse = true;
  
  public static double drivetrainPower = 0.85;
  public static double fineDrivetrainPower = 0.8;


  // Shooter
  public static int topShooter = 4;
  public static int bottomShooter = 2;

  public static double shooterPower = 0.75;


  // Intake
  public static int intakeRoller = 7;
  public static int intakeExtender = 8;

  public static double intakeSpeed = 0.5;
  public static double intakeSpeedAdjusted = 0.5;

  public static double rollerExtendPower = 0.3;

  // Climber
  public static int climberMotor = 9;

  public static double climberPower = 1.0;


  // Controller Ports
  public static int XboxController0 = 0;
  public static int XboxController1 = 1;


  // Miscellany
  public static boolean collectMode = false;


  // For Testing
  public static boolean activateSensor = true;
  public static boolean rotationMode = false;
  public static boolean autoMode = false;


  // public static boolean toggleOn1 = false;
  // public static boolean togglePressed1 = false;
  // public static boolean toggleOn2 = false;
  // public static boolean togglePressed2 = false;

  // public static boolean fullPower = true;
  // public static boolean power83 = false;
  // public static boolean threeFourthsPower = false;
}