package frc.robot.simulation;

import edu.wpi.first.math.geometry.Translation3d;
import frc.robot.subsystems.drivetrain.Drivetrain;

/*
    Should store positions for the blocks. eventually.
*/
public class SimulationState {
    public static Translation3d boxes[] = new Translation3d[19];
    
    public static void initBoxes()
    {
        for (int i=0;i<5;i++)
        {
            for (int j=0;j<3;j++)
            {
                boxes[5*j + i] = new Translation3d(0, -12+6*i, 6*j);
            }
           
        }

        for (int i=0;i<2;i++)
        {
            for (int j=0;j<2;j++)
            {
                boxes[5*j + i] = new Translation3d(-6+12*i, 0, 6*j);
            }
           
        }
    }

    public static void update()
    {

    }
}


