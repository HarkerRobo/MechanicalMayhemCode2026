package frc.robot.commands.arm;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Arm;
import frc.robot.Constants;
import frc.robot.Constants.*;
import frc.robot.Robot;

import edu.wpi.first.units.measure.*;
import static edu.wpi.first.units.Units.*;

public class ArmDown extends Command
{
    public ArmDown()
    {
        addRequirements(Arm.getInstance());
    }

    @Override
    public void initialize() {
    }
        

    @Override
    public void execute() {
        //Arm.getInstance().setVelocity(DegreesPerSecond.of(0.1));
        Arm.getInstance().setVoltage(Volts.of(2));
        //System.out.println("Running: True \nCurrent: " + Arm.getInstance().getCurrentAngle().in(Degrees) + "Change: " +  (Constants.Arm.ARM_CHANGE_ANGLE).in(Degrees));
        //Robot.getInstance().getArmMotorSim().setInputVoltage(-Constants.ARM_VOLTAGE.in(Volts));
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
