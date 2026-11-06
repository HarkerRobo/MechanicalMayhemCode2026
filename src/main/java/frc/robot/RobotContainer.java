// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.OperatorConstants;

import java.util.ArrayList;

import com.pathplanner.lib.auto.NamedCommands;
import com.pathplanner.lib.util.FlippingUtil;
import edu.wpi.first.hal.SimDevice.Direction;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine;
import frc.robot.commands.arm.*;
import frc.robot.commands.intake.*;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Intake;

/**
 * 
 */
public class RobotContainer {    
    // Replace with CommandPS4Controller or CommandJoystick if needed
    public final CommandXboxController driver =
        new CommandXboxController(OperatorConstants.kDriverControllerPort);

    public static int INTAKE_INDEX = 0;
    public static int ARM_INDEX = 1;

    //public final CommandSwerveDrivetrain drivetrain = Modules.createDrivetrain();

    public enum SubsystemStatus {Enabled, Simulated, Disabled};

    /** The container for the robot. Contains subsystems, OI devices, and commands. */
    public RobotContainer() {

        // Configure the trigger bindings
        // NamedCommands.registerCommand("Zero Drivetrain", drivetrain.runOnce(() -> {
        //         if (DriverStation.getAlliance().orElse(DriverStation.Alliance.Blue) == DriverStation.Alliance.Red)
        //         {
        //             drivetrain.seedFieldCentric(Rotation2d.k180deg);
        //         }
        //         else
        //         {
        //             drivetrain.seedFieldCentric();
        //         }
        //     })
        //         .andThen(drivetrain.runOnce(() -> drivetrain.resetPose(
        //                     (DriverStation.getAlliance().get() == DriverStation.Alliance.Red) ? 
        //                     FlippingUtil.flipFieldPose(Constants.ZEROING_POSE) : Constants.ZEROING_POSE))));
        NamedCommands.registerCommand("FirstShelf", new MoveToAngle(Constants.Arm.FIRST_SHELF_ANGLE).asProxy());
        NamedCommands.registerCommand("Stack", new MoveToAngle(Constants.Arm.STACK_ANGLE).asProxy());
        NamedCommands.registerCommand("TopShelf", new MoveToAngle(Constants.Arm.TOP_SHELF_ANGLE).asProxy());
        NamedCommands.registerCommand("Zero Arm", new ZeroArm().asProxy());

        NamedCommands.registerCommand("StartIntake", new StartRunIntake().asProxy());
        NamedCommands.registerCommand("ReverseIntake", new ReverseIntake().asProxy());        

        
        configureBindings();
    }

    /**
     * 
     */
    private void configureBindings()
    {      
        // Uncomment once drivetrain code has been generated!
        // Note that X is defined as forward according to WPILib convention,
        // and Y is defined as to the left according to WPILib convention.
        // drivetrain.setDefaultCommand(
        //     // Drivetrain will execute this command periodically
        //     drivetrain.applyRequest(() -> 
        //         drive.withVelocityX(-driver.getLeftY() * MaxSpeed.in(MetersPerSecond) * (isSlow ? Constants.TRANSLATION_SLOW_MULTIPLIER : 1.0)/*)*/) // Drive forward with negative Y (forward)
        //         .withVelocityY(-driver.getLeftX() * MaxSpeed.in(MetersPerSecond) * (isSlow ? Constants.TRANSLATION_SLOW_MULTIPLIER : 1.0)/*)*/) // Drive left with negative X (left)
        //         .withRotationalRate(-driver.getRightX() * MaxAngularRate.in(RadiansPerSecond) * (isSlow ? Constants.ROTATION_SLOW_MULTIPLIER : 1.0)) // Drive counterclockwise with negative X (left)
        //     ).withName("SwerveManual"));
        
        Arm.getInstance().setDefaultCommand(new ArmDefault()); 
        Intake.getInstance().setDefaultCommand(new StartDefaultIntake());

        // Deploy
        driver.leftTrigger().onTrue(
            new MoveToAngle(Constants.Arm.GROUND_ANGLE)
                .alongWith(new StartRunIntake()));

        // Score
        driver.rightTrigger().onTrue(new ReverseIntake());

        // Stow
        driver.leftBumper().onTrue(new MoveToAngle(Constants.Arm.ZERO_ARM));

        driver.x().onTrue(new MoveToAngle(Constants.Arm.FIRST_SHELF_ANGLE));
        driver.y().onTrue(new MoveToAngle(Constants.Arm.TOP_SHELF_ANGLE));
        driver.b().onTrue(new MoveToAngle(Constants.Arm.STACK_ANGLE));

        driver.povUp().whileTrue(new ArmUp().andThen(new StartRunIntake()));
        driver.povDown().whileTrue(new ArmDown().andThen(new StartRunIntake()));
        //driver.povLeft().onTrue(new ZeroDrive()); //zero drive which is todo 
        driver.povRight().onTrue(new ZeroArm());
        
        // driver.button(1).whileTrue(Arm.getInstance().getRoutine().dynamic(SysIdRoutine.Direction.kForward)); 
        // driver.button(2).whileTrue(Arm.getInstance().getRoutine().dynamic(SysIdRoutine.Direction.kReverse));
        // driver.button(3).whileTrue(Arm.getInstance().getRoutine().quasistatic(SysIdRoutine.Direction.kForward)); 
        // driver.button(4).whileTrue(Arm.getInstance().getRoutine().quasistatic(SysIdRoutine.Direction.kReverse));                                              
    }
                    
    /*public Command getAutonomousCommand()
    {        
        return autonChooser.getSelected();
    }

    public SubsystemStatus getStatus(int subsystem)
    {
        return modeChoosers.get(subsystem).getSelected();
    }*/
}
