package frc.robot;

import edu.wpi.first.networktables.*;
import edu.wpi.first.math.geometry.*;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Arm;

public class Telemetry {
    private static Telemetry instance;

    private NetworkTableInstance tableInstance = NetworkTableInstance.getDefault();
    private NetworkTable table = tableInstance.getTable("1072");

    private NetworkTable simTable = table.getSubTable("Sim");
    private NetworkTable armSimTable = simTable.getSubTable("Arm");
    private StructPublisher armSimPose3d = armSimTable.getStructTopic("arm pose", Pose3d.struct).publish();

    private NetworkTable armTable = table.getSubTable("Arm Table");
    private DoublePublisher armPosition = armTable.getDoubleTopic("Arm Sim Position").publish();
    private DoublePublisher armVelocity = armTable.getDoubleTopic("Simulation Angular Velocity").publish();

    private NetworkTable intakeTable = table.getSubTable("Intake");
    private DoublePublisher intakeVelocityRight = intakeTable.getDoubleTopic("Simulation Intake Velocity Right").publish();
    private DoublePublisher intakeVelocityLeft = intakeTable.getDoubleTopic("Simulation Intake Velocity Left").publish();
    
    private Telemetry() 
    {
        
    }

    public void update() 
    {
        
    }

    public void updateArmSim(double angularPosition, double angularVelocity)
    {
        armPosition.set(angularPosition);
        armVelocity.set(angularVelocity);
    }

    public void updateIntakeSim(double leftVelocity, double rightVelocity)
    {
        intakeVelocityRight.set(leftVelocity);
        intakeVelocityLeft.set(rightVelocity); 
    }

    public static Telemetry getInstance ()
    {
        if (instance == null) instance = new Telemetry();
        return instance;
    }
}