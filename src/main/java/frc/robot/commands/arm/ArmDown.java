package frc.robot.commands.arm;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Arm;
import frc.robot.Constants;
import frc.robot.Constants.*;

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
        Arm.getInstance().MoveToAngle(Arm.getInstance().getCurrentAngle().in(Degrees) - (Constants.Arm.ARM_CHANGE_ANGLE).in(Degrees));
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
