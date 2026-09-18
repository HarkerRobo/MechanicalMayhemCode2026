package frc.robot.commands.arm;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.*;

public class ArmUp extends Command
{

    public ArmUp()
    {
        addRequirements(Arm.getInstance());
    }

    @Override
    public void initialize()
    {
        
    }

    @Override
    public void execute()
    {
        Arm.getInstance().setVoltage(Constants.Arm.ARM_VOLTAGE);
    }
}
