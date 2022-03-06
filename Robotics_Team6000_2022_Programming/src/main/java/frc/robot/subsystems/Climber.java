package frc.robot.subsystems;

import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import java.lang.System;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.Timer;

public class Climber extends Subsystem{
    public Climber(){
        climberMotor = new Spark(RobotMap.climberMotor);
        climberMotor.setInverted(false);
        climberMotor.setSafetyEnabled(false);
    }
    public void raiseClimber(){
        climberMotor.set(RobotMap.climberPower);
    }
    public void lowerClimber(){
        climberMotor.set(RobotMap.climberPower * -1);
    }

    @Override
    protected void initDefaultCommand() {
        // TODO Auto-generated method stub
        
    }
}