package frc.robot.commands.arm;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Arm;
import frc.robot.Constants;
import frc.robot.Constants.*;

import edu.wpi.first.units.measure.*;
import static edu.wpi.first.units.Units.*;

public class MoveToAngle extends Command
{
    private Angle target;

    public MoveToAngle(Angle target)
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
        Arm.getInstance().moveToAngle(target);
    }

    @Override
    public boolean isFinished() {
        return Math.abs(Arm.getInstance().getCurrentAngle().in(Degrees) - Arm.getInstance().getDesired().in(Degrees)) < Constants.Arm.ANGLE_TOLERANCE.in(Degrees);
    }
}
