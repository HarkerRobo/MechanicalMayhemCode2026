// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.OperatorConstants;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {    
  
    private SendableChooser<Command> autonChooser;
    public ArrayList<SendableChooser<SubsystemStatus>> modeChoosers = new ArrayList<>();

    // Replace with CommandPS4Controller or CommandJoystick if needed
    public final CommandXboxController driver =
    new CommandXboxController(OperatorConstants.kDriverControllerPort);

    public static int INTAKE_INDEX = 0;
    public static int ARM_INDEX = 1;

    public enum SubsystemStatus {Enabled, Simulated, Disabled};

    /** The container for the robot. Contains subsystems, OI devices, and commands. */
    public RobotContainer() {
        // Configure the trigger bindings
        configureBindings();
    }

    /**
     * Use this method to define your trigger->command mappings. Triggers can be created via the
     * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary
     * predicate, or via the named factories in {@link
     * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for {@link
     * CommandXboxController Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
     * PS4} controllers or {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
     * joysticks}.
     */
    private void configureBindings()
    {        
                                                
    }
                    
    public Command getAutonomousCommand()
    {        
        return autonChooser.getSelected();
    }

    public SubsystemStatus getStatus(int subsystem)
    {
        return modeChoosers.get(subsystem).getSelected();
    }
}
