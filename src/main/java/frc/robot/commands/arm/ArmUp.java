package frc.robot.commands.arm;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Arm;
import frc.robot.Constants;
import frc.robot.Constants.*;
import frc.robot.Robot;

import edu.wpi.first.units.measure.*;
import static edu.wpi.first.units.Units.*;

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
        Arm.getInstance().setVelocity(Constants.Arm.UP_VELOCITY);
    }
    
    @Override
    public boolean isFinished() {
        return false;
    }
    
}