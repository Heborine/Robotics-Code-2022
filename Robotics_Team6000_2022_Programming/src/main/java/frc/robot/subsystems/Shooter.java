/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

//import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class Shooter extends Robot implements Subsystem {
  public Spark topMotor;
  public Spark bottomMotor;

  public Shooter() {
    topMotor = new Spark(RobotMap.topShooter);
    bottomMotor = new Spark(RobotMap.bottomShooter);

    // Ensures that motors keep running during Timer.delay()
    topMotor.setSafetyEnabled(false);
    bottomMotor.setSafetyEnabled(false);

  }

  public void Firing(){
    //needs trigger power
    //for autonomous - set variable dependent instead of 1
    shooter.topMotor.set(RobotMap.shooterPower);
    shooter.bottomMotor.set(RobotMap.shooterPower);
  }
  public void StopFiring(){
    shooter.topMotor.set(0);
    shooter.bottomMotor.set(0);
  }

// <<<<<<< HEAD
// <<<<<<< HEAD
// <<<<<<< HEAD
// <<<<<<< HEAD
// =======
// =======
// >>>>>>> parent of 29a9c82 (more info)
// =======
// >>>>>>> parent of 29a9c82 (more info)
  // useful info: https://docs.limelightvision.io/en/latest/cs_autorange.html
  // public void Checker(){
  //   @Override
  //   public void ShooterDistanceRender() {
  //         Update_Limelight_Tracking();
// =======
  // useful info: https://docs.limelightvision.io/en/latest/cs_autorange.html
  //public void Checker(){
  //  @Override
  //  public void ShooterDistanceRender() {
   //       Update_Limelight_Tracking();
// >>>>>>> parent of 29a9c82 (more info)

//         if (m_LimelightHasValidTarget){

//       }
//         else{

// // <<<<<<< HEAD
//       }
// =======
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
        // */}

// >>>>>>> parent of 29a9c82 (more info)

          
//     }

//   }

// >>>>>>> parent of 29a9c82 (more info)
  // @Override
  // public void initDefaultCommand() {
  // }

  
//}