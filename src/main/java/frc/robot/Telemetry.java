package frc.robot;

import edu.wpi.first.networktables.*;
import edu.wpi.first.wpilibj.simulation.DCMotorSim;
import edu.wpi.first.math.geometry.*;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Arm;

import edu.wpi.first.units.measure.*;
import static edu.wpi.first.units.Units.*;

public class Telemetry {
    private static Telemetry instance;

    private NetworkTableInstance tableInstance = NetworkTableInstance.getDefault();
    private NetworkTable table = tableInstance.getTable("1072");


    private NetworkTable armTable = table.getSubTable("Arm");
    private DoublePublisher armPosition = armTable.getDoubleTopic("Arm Angular Position").publish();
    private DoublePublisher armVelocity = armTable.getDoubleTopic("Arm Angular Velocity").publish();

    private NetworkTable intakeTable = table.getSubTable("Intake");
    private DoublePublisher intakeVelocityRight = intakeTable.getDoubleTopic("Intake Velocity Right").publish();
    private DoublePublisher intakeVelocityLeft = intakeTable.getDoubleTopic("Intake Velocity Left").publish();
    
    private Telemetry() 
    {
        
    }

    public void update() 
    {
        armPosition.set(Arm.getInstance().getCurrentAngle().in(Degrees));
        armVelocity.set(Arm.getInstance().getVelocity().in(DegreesPerSecond));
        intakeVelocityLeft.set(Intake.getInstance().getLeftVoltage().in(Volts));
        intakeVelocityRight.set(Intake.getInstance().getRightVoltage().in(Volts));
    }

    public static Telemetry getInstance ()
    {
        if (instance == null) instance = new Telemetry();
        return instance;
    }
}