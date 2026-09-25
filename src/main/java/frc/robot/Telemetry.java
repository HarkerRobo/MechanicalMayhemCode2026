package frc.robot;

import edu.wpi.first.networktables.*;

public class Telemetry {
    private static Telemetry instance;

    private NetworkTableInstance tableInstance = NetworkTableInstance.getDefault();
    private NetworkTable table = tableInstance.getTable("1072");

    private NetworkTable armSimTable = table.getSubTable("Arm Simulation");
    private DoublePublisher armSimPosition = armSimTable.getDoubleTopic("Angular Position").publish();

    private Telemetry() {

    }

    public void update() {

    }

    public static Telemetry getInstance ()
    {
        if (instance == null) instance = new Telemetry();
        return instance;
    }
}