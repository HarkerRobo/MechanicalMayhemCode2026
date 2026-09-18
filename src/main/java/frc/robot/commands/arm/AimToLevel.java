package frc.robot.commands.arm;

import static edu.wpi.first.units.Units.Degrees;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.*;

public class AimToLevel extends Command
{
    private double pitch;

    public AimToLevel(int level)
    {
        this.level = level;
        addRequirements(Arm.getInstance());
    }

    @Override
    public void initialize()
    {
        
    }

    @Override
    public void execute()
    {
        Arm.getInstance().moveToPosition(Degrees.of(pitch));
    }
}
