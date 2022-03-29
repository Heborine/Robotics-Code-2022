package frc.robot.subsystems;

//not used

//import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import frc.robot.RobotMap;
//import edu.wpi.first.wpilibj.Spark;
//import edu.wpi.first.wpilibj.Timer;

public class Climber implements Subsystem{
    public Spark climberMotor;
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

    // @Override
    // protected void initDefaultCommand() {
    //     // todo Auto-generated method stub
        
    // }
}