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
import frc.robot.RobotMap;
import edu.wpi.first.wpilibj.Timer;

public class Intake implements Subsystem {
    public Spark intakeRoller;
    public Spark intakeExtender;

    public Intake() {
        intakeRoller = new Spark(RobotMap.intakeRoller);
        intakeRoller.setInverted(true);
        intakeRoller.setSafetyEnabled(false);
        intakeExtender = new Spark(RobotMap.intakeExtender);
    }

    public void extendIntake() {
        intakeExtender.set(RobotMap.intakeSpeed);
        Timer.delay(3);
        intakeExtender.set(0.0);
    }

    // @Override
    // public void initDefaultCommand() {
    // }
}
