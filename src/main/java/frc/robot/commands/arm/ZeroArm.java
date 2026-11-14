package frc.robot.commands.arm;

import static edu.wpi.first.units.Units.*;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.Constants;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Intake;

public class ZeroArm extends Command{
    
    public ZeroArm()
    {
        addRequirements(Arm.getInstance());
    }

    public void initialize()
    {

    }

    public void execute()
    {
        Arm.getInstance().setVelocity(Constants.Arm.DOWN_VELOCITY);
    }

    public boolean isFinished()
    {
        return Arm.getInstance().isStalling();
    }

    public void end(boolean interrupted)
    {
        Arm.getInstance().setAngle(Constants.Arm.ZERO_ARM);
    }
}