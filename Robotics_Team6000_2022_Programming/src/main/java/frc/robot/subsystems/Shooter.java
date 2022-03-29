/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

//import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj.Timer;

import edu.wpi.first.wpilibj.motorcontrol.Spark;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class Shooter extends Robot implements Subsystem {
  public Spark topMotor;
  public Spark bottomMotor;
  public Spark magazine;

  public Shooter() {
    topMotor = new Spark(RobotMap.topShooter);
    bottomMotor = new Spark(RobotMap.bottomShooter);
    magazine = new Spark(RobotMap.magazine);

    // Ensures that motors keep running during Timer.delay()
    topMotor.setSafetyEnabled(false);
    bottomMotor.setSafetyEnabled(false);
    magazine.setSafetyEnabled(false);

  }

  public void Firing(){
    //needs trigger power
    System.out.println("Firing");
    shooter.topMotor.set(RobotMap.shooterPower);
    shooter.bottomMotor.set(-RobotMap.shooterPower);
    Timer.delay(1);
    shooter.magazine.set(RobotMap.magazinePower);
    Timer.delay(1);
  }
  public void StopFiring(){
    shooter.topMotor.set(0);
    shooter.bottomMotor.set(0);
    shooter.magazine.set(0);
  }
}
  // useful info: https://docs.limelightvision.io/en/latest/cs_autorange.html
  // public void Checker(){
  //   @Override
  //   public void ShooterDistanceRender() {
  //         Update_Limelight_Tracking();

  // useful info: https://docs.limelightvision.io/en/latest/cs_autorange.html
  //public void Checker(){
  //  @Override
  //  public void ShooterDistanceRender() {
   //       Update_Limelight_Tracking();
//         if (m_LimelightHasValidTarget){
//       }
//         else{
//       }
    //  }
/*
    public void Update_Limelight_Tracking_Shooter()
    {
          // tune the values
          final double Motor_K = 0.26;                    
          final double DESIRED_TARGET_AREA = 13.0;        
          final double MAX_DRIVE = 0.7;                  
          double tv = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tv").getDouble(0);
          double tx = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0);
          double ty = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ty").getDouble(0);
          double ta = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ta").getDouble(0);
          if (tv < 1.0)
          {
            m_LimelightHasValidTarget = false;
            m_LimelightMotorCommand = 0.0;
            m_LimelightMotorCommand = 0.0;
            return;
          }
          m_LimelightHasValidTarget = true;
          if m_LimelightHasValidTarget = true{
            double drive_cmd = (DESIRED_TARGET_AREA - ta) * DRIVE_K;
          }
          if (drive_cmd > MAX_DRIVE)
          {
            drive_cmd = MAX_DRIVE;
          }
          m_LimelightDriveCommand = drive_cmd;
        // *///}          
//     }
//   }
  // @Override
  // public void initDefaultCommand() {
  // }  
//}