// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.util.ArrayList;
import java.util.function.Supplier;

import com.ctre.phoenix6.swerve.SwerveModule.DriveRequestType;
import com.ctre.phoenix6.swerve.SwerveRequest;
import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;
import com.pathplanner.lib.util.FlippingUtil;
import edu.wpi.first.hal.SimDevice.Direction;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.LinearVelocity;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.RobotModeTriggers;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine;
import frc.robot.commands.arm.*;
import frc.robot.commands.intake.*;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.drivetrain.*;
import frc.robot.Constants.*;
import static edu.wpi.first.units.Units.*;

/**
 * 
 */
public class RobotContainer {
    // Replace with CommandPS4Controller or Commanddriver if needed
    public final CommandXboxController driver =
        new CommandXboxController(OperatorConstants.kDriverControllerPort);

    public static int INTAKE_INDEX = 0;
    public static int ARM_INDEX = 1;
    public static boolean DO_SYID_CONTROLS = false;

    public final CommandSwerveDrivetrain drivetrain = Modules.createDrivetrain();

    private SendableChooser<Command> autonChooser;

    public LinearVelocity MaxSpeed = Swerve.kSpeedAt12Volts.times(1.0); // kSpeedAt12Volts desired top speed
    private AngularVelocity MaxAngularRate = RotationsPerSecond.of(0.75);

    private final SwerveRequest.FieldCentric drive = new SwerveRequest.FieldCentric()
        .withDeadband(MaxSpeed.in(MetersPerSecond) * 0.1).withRotationalDeadband(MaxAngularRate.in(RadiansPerSecond) * 0.1) // Add a 10% deadband
        .withDriveRequestType(DriveRequestType.OpenLoopVoltage);

    private final SwerveRequest.SwerveDriveBrake brake = new SwerveRequest.SwerveDriveBrake();

        
    private Supplier<Command> stow;
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
        
        NamedCommands.registerCommand("FirstShelf", new MoveToAngle(Constants.Arm.FIRST_SHELF_ANGLE).withTimeout(1.5));
        NamedCommands.registerCommand("Stack", new MoveToAngle(Constants.Arm.STACK_ANGLE).withTimeout(1.5));
        NamedCommands.registerCommand("TopShelf", new MoveToAngle(Constants.Arm.TOP_SHELF_ANGLE).withTimeout(1.5));
        NamedCommands.registerCommand("Zero Arm", new ZeroArm().withTimeout(3.0));

        NamedCommands.registerCommand("StartIntake", new StartRunIntake());
        NamedCommands.registerCommand("ReverseIntake", new StartReverseIntake());

        stow = ()->
            new StartDefaultIntake()
            .withName("Stow"); // must stay a supplier
        
        NamedCommands.registerCommand("Stow", stow.get());

        configureBindings();

        drivetrain.registerTelemetry(Telemetry.getInstance()::telemeterize);

        autonChooser = AutoBuilder.buildAutoChooser(); //TODO add a default auton??
        SmartDashboard.putData("Auton Chooser", autonChooser);
    }

    /**
     * 
     */
    private void configureBindings()
    {      
        // Uncomment once drivetrain code has been generated!
        // Note that X is defined as forward according to WPILib convention,
        // and Y is defined as to the left according to WPILib convention.
       drivetrain.setDefaultCommand(
            // Drivetrain will execute this command periodically
            drivetrain.applyRequest(() ->
                drive.withVelocityX(-driver.getLeftY() *  MaxSpeed.in(MetersPerSecond)) // Drive forward with negative Y (forward)
                    .withVelocityY(-driver.getLeftX() *  MaxSpeed.in(MetersPerSecond)) // Drive left with negative X (left)
                    .withRotationalRate(-driver.getRightX() * MaxAngularRate.in(RadiansPerSecond)) // Drive counterclockwise with negative X (left)
            )
        );
        // Idle while the robot is disabled. This ensures the configured
        // neutral mode is applied to the drive motors while disabled.
        final var idle = new SwerveRequest.Idle();
        RobotModeTriggers.disabled().whileTrue(
            drivetrain.applyRequest(() -> idle).ignoringDisable(true)
        );

       
        // Run SysId routines when holding back/start and X/Y.
        // Note that each routine should be run exactly once in a single log.
        
        if (DO_SYID_CONTROLS)
        {
            //driver.button(1).whileTrue(Arm.getInstance().getRoutine().dynamic(SysIdRoutine.Direction.kForward)); 
            //driver.button(2).whileTrue(Arm.getInstance().getRoutine().dynamic(SysIdRoutine.Direction.kReverse));
            //driver.button(3).whileTrue(Arm.getInstance().getRoutine().quasistatic(SysIdRoutine.Direction.kForward)); 
            //driver.button(4).whileTrue(Arm.getInstance().getRoutine().quasistatic(SysIdRoutine.Direction.kReverse)); 

            driver.button(1).whileTrue(Intake.getInstance().getRoutine().dynamic(SysIdRoutine.Direction.kForward)); 
            driver.button(2).whileTrue(Intake.getInstance().getRoutine().dynamic(SysIdRoutine.Direction.kReverse));
            driver.button(3).whileTrue(Intake.getInstance().getRoutine().quasistatic(SysIdRoutine.Direction.kForward)); 
            driver.button(4).whileTrue(Intake.getInstance().getRoutine().quasistatic(SysIdRoutine.Direction.kReverse)); 
        }
        else
        {
            Arm.getInstance().setDefaultCommand(new ArmDefault()); 
            //Intake.getInstance().setDefaultCommand(new StartDefaultIntake());

            // Deploy
            // driver.leftTrigger().onTrue(
            //     new MoveToAngle(Constants.Arm.GROUND_ANGLE)
            //         .alongWith(new StartRunIntake()).withName("Intake/Ground"));
            driver.b().onTrue(
                new StartRunIntake());
            driver.b().onFalse(stow.get());

            // Score
            driver.a().onTrue(new StartReverseIntake().withName("Score"));
            driver.a().onFalse(stow.get());
            //driver.rightTrigger().onFalse(new StartDefaultIntake());

            // Stow
            //driver.leftBumper().onTrue(new MoveToAngle(Constants.Arm.ZERO_ARM));

            //driver.x().onTrue(new MoveToAngle(Constants.Arm.FIRST_SHELF_ANGLE).withName("Stow/First Shelf"));
            //driver.y().onTrue(new MoveToAngle(Constants.Arm.TOP_SHELF_ANGLE).withName("Top Shelf/Stack"));
            //driver.b().onTrue(new MoveToAngle(Constants.Arm.STACK_ANGLE));

            driver.povUp().whileTrue(new ArmUp().alongWith(new StartRunIntake())
                                     .finallyDo(() -> stow.get().schedule()).withName("Intake Up Manual"));
            driver.povDown().whileTrue(new ArmDown().alongWith(new StartRunIntake())
                                       .finallyDo(() -> stow.get().schedule()).withName("Intake Down Manual"));
            driver.start().onTrue(drivetrain.runOnce(() -> drivetrain.seedFieldCentric()));
            driver.x().whileTrue(drivetrain.applyRequest(() -> brake));
            //driver.povLeft().onTrue(new ZeroDrive().withName("Zero Drive")); //zero drive which is todo 
            driver.povRight().onTrue(new ZeroArm().withName("Zero A").withTimeout(3.0)); // zeros intake to stow position
        }
    }
                    
    public Command getAutonomousCommand()
    {        
        return autonChooser.getSelected();
    }

}