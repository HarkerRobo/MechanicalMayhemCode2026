package frc.robot.commands.arm;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.*;
import frc.robot.Constants.*;

public class ArmDown extends Command
{
    public ArmDown()
    {
        addRequirements.getInstance();
    }

    @Override
    public void initialize()
    {
        Arm.getInstance().setVoltage(Constants.Arm.ARM_VOLTAGE);
    }
}
