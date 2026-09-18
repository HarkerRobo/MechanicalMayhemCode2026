package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.RobotContainer;
import frc.robot.RobotContainer.SubsystemStatus;
import edu.wpi.first.units.measure.*;
import static edu.wpi.first.units.Units.*;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.controls.VoltageOut;

public class Arm extends SubsystemBase {

    public static Arm instance;

    private TalonFX master;
    private TalonFX follower;

    private Arm ()
    {
        master = new TalonFX(Constants.Arm.MASTER_ID, Constants.CAN_SUPERSTRUCTURE);
        follower = new TalonFX(Constants.Arm.FOLLOWER_ID, Constants.CAN_SUPERSTRUCTURE);

        config();
    }

    private void config()
    {
        master.clearStickyFaults();
        follower.clearStickyFaults();

        TalonFXConfiguration config = new TalonFXConfiguration();

        if (isSimulated())
        {
            config.CurrentLimits.StatorCurrentLimit = Constants.Arm.STATOR_CURRENT_LIMIT.in(Amps);
            config.CurrentLimits.StatorCurrentLimitEnable = true;
            
            config.CurrentLimits.SupplyCurrentLimit = Constants.Arm.SUPPLY_CURRENT_LIMIT.in(Amps);
            config.CurrentLimits.SupplyCurrentLimitEnable = true;
        }

        config.Feedback.SensorToMechanismRatio = Constants.Arm.GEAR_RATIO;

        config.MotorOutput.NeutralMode = NeutralModeValue.Coast;

        config.Voltage.PeakForwardVoltage = Constants.MAX_VOLTAGE.in(Volts);
        config.Voltage.PeakReverseVoltage = -Constants.MAX_VOLTAGE.in(Volts);
        
        config.Slot0.kP = Constants.Arm.KP;
        config.Slot0.kI = Constants.Arm.KI;
        config.Slot0.kD = Constants.Arm.KD;
        config.Slot0.kS = Constants.Arm.KS;
        config.Slot0.kV = Constants.Arm.KV;
        config.Slot0.kA = Constants.Arm.KA;
        config.MotorOutput.Inverted = Constants.Arm.INVERTED;

        master.getConfigurator().apply(config);
        follower.setControl(new Follower(Constants.Arm.MASTER_ID, Constants.Arm.MOTOR_ALIGNMENT));
    }

    public static Arm getInstance()
    {
        if (instance == null) instance = new Arm();
            return instance;
    }
   
    /**
     * Returns the right motor voltage.
     */
    public Voltage getVoltage()
    {
        return master.getMotorVoltage().getValue();
    }
    
    /**
     * Returns the right motor velocity.
     * Represents the measured angular speed of the intake.
     */
    public AngularVelocity getVelocity()
    {
        return master.getVelocity().getValue();
    }

    /**
     * Returns the right stator current.
     * Indicates the electrical load on the motor.
     */
    public Current getStatorCurrent()
    {
        return master.getStatorCurrent().getValue();
    }
    
    
    public boolean isStalling()
    {
        return Math.abs(master.getStatorCurrent().getValueAsDouble()) >= Constants.Arm.STALLING_CURRENT.in(Amps);
    }
    
    /**
    * Pushes a specific voltage into the motor.
    * Blocked if disabled
    */
    public void setVoltage (Voltage voltage)
    {
        if (isDisabled())
        {
            System.out.println("Quashing input to Intake");
            return;
        }
        master.setControl(new VoltageOut(voltage));
    }

    /**
     * Moves to angle
     * @param angle the angle to move the Arm's pitch to that degree
     */
    public void MoveToAngle(double angle)
    {
        this.desiredPosition = desiredPosition;
        motor.setControl(new PositionVoltage(desiredPosition));
    }

    /**
    * Commands to hold a target velocity.
    * Automatically does feedforward and PID.
    */
    public void setVelocity (AngularVelocity velocity)
    {
    }


    /**
     * Returns true if the subsystem is running in simulation.
     * Uses RobotContainer status to determine the mode.
     */
    private boolean isSimulated ()
    {
        return Robot.instance.robotContainer.getStatus(RobotContainer.INTAKE_INDEX) == SubsystemStatus.Simulated;
    }
    

    /**
     * Returns true if the subsystem is disabled.
     * Prevents motor commands from being applied.
     */
    private boolean isDisabled ()
    {
        return Robot.instance.robotContainer.getStatus(RobotContainer.INTAKE_INDEX) == SubsystemStatus.Disabled;
    }

}
    
