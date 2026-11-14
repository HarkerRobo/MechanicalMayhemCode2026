package frc.robot;

import edu.wpi.first.networktables.*;
import edu.wpi.first.wpilibj.simulation.DCMotorSim;
import edu.wpi.first.wpilibj2.command.Command;
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
    private DoublePublisher armPosition = armTable.getDoubleTopic("Position").publish();
    private DoublePublisher armVelocity = armTable.getDoubleTopic("Velocity").publish();
    private DoublePublisher armVoltage = armTable.getDoubleTopic("Voltage").publish();
    private StringPublisher armCommand = armTable.getStringTopic("Command").publish();
    private DoublePublisher armDesired = armTable.getDoubleTopic("Desired Angle").publish();


    private NetworkTable intakeTable = table.getSubTable("Intake");
    private DoublePublisher intakePositionRight = intakeTable.getDoubleTopic("Right Position").publish();
    private DoublePublisher intakePositionLeft = intakeTable.getDoubleTopic("Left Position").publish();
    private DoublePublisher intakeVelocityRight = intakeTable.getDoubleTopic("Right Velocity").publish();
    private DoublePublisher intakeVelocityLeft = intakeTable.getDoubleTopic("Left Velocity").publish();
    private DoublePublisher intakeVoltageRight = intakeTable.getDoubleTopic("Right Voltage").publish();
    private DoublePublisher intakeVoltageLeft = intakeTable.getDoubleTopic("Left Voltage").publish();
    private StringPublisher intakeCommand = intakeTable.getStringTopic("Command").publish();
    
    private Telemetry() 
    {
        
    }

    public void update() 
    {
        Command armCommand = Arm.getInstance().getCurrentCommand();
        this.armCommand.set(armCommand == null ? "" : armCommand.getName());
        armPosition.set(Arm.getInstance().getCurrentAngle().in(Degrees));
        armVelocity.set(Arm.getInstance().getVelocity().in(DegreesPerSecond));
        armVoltage.set(Arm.getInstance().getVoltage().in(Volts));
        armDesired.set(Arm.getInstance().getDesired().in(Degrees));

        Command intakeCommand = Intake.getInstance().getCurrentCommand();
        this.intakeCommand.set(intakeCommand == null ? "" : intakeCommand.getName());
        intakePositionLeft.set(Intake.getInstance().getLeftPosition().in(Degrees));
        intakePositionRight.set(Intake.getInstance().getRightPosition().in(Degrees));
        intakeVelocityLeft.set(Intake.getInstance().getLeftVelocity().in(DegreesPerSecond));
        intakeVelocityRight.set(Intake.getInstance().getRightVelocity().in(DegreesPerSecond));
        intakeVoltageLeft.set(Intake.getInstance().getLeftVoltage().in(Volts));
        intakeVoltageRight.set(Intake.getInstance().getRightVoltage().in(Volts));
    }

    public static Telemetry getInstance ()
    {
        if (instance == null) instance = new Telemetry();
        return instance;
    }
}