package frc.robot.commands.arm;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Arm;
import frc.robot.Constants;
import frc.robot.Constants.*;

import edu.wpi.first.units.measure.*;
import static edu.wpi.first.units.Units.*;

public class MoveToLevel extends Command
{
    private double target;
    public MoveToLevel(double target)
    {
        addRequirements(Arm.getInstance());
        this.target = target;
    }

    @Override
    public void initialize()
    {
        
    }

    @Override
    public void execute() {
        Arm.getInstance().MoveToAngle(this.target);
    }

    @Override
    public boolean isFinished() {
        return Arm.getInstance().isStalling();
    }
}
