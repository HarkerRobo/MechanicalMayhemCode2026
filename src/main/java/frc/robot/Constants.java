// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix6.CANBus;
import com.ctre.phoenix6.configs.CANcoderConfiguration;
import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.configs.Pigeon2Configuration;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.signals.*;
import com.ctre.phoenix6.sim.ChassisReference;
import com.ctre.phoenix6.swerve.SwerveDrivetrainConstants;
import com.ctre.phoenix6.swerve.SwerveModuleConstants.ClosedLoopOutputType;
import com.ctre.phoenix6.swerve.SwerveModuleConstants.DriveMotorArrangement;
import com.ctre.phoenix6.swerve.SwerveModuleConstants.SteerFeedbackType;
import com.ctre.phoenix6.swerve.SwerveModuleConstants.SteerMotorArrangement;
import com.ctre.phoenix6.swerve.SwerveModuleConstantsFactory;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.units.VoltageUnit;
import edu.wpi.first.units.measure.*;
import static edu.wpi.first.units.Units.*;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
* <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants
{
    //public static final CANBus CAN_SUPERSTRUCTURE = new CANBus("Superstructure");
    public static final CANBus CAN_CHAIN = new CANBus("rio");

    public static final double ROBOT_HEIGHT = 0.89535; //parallel to the intake face (TODO)
    public static final double ROBOT_WIDTH = 0.84455; //TODO

    public static final Pose2d ZEROING_POSE = new Pose2d(3.581, 4.20288, new Rotation2d(Degrees.of(180))); //TODO

    public static final double TRANSLATION_SLOW_MULTIPLIER = 0.35;
    public static final double ROTATION_SLOW_MULTIPLIER = 0.5;

    public static final Voltage MAX_VOLTAGE = Volts.of(12.0);
    public static final Voltage ARM_VOLTAGE = Volts.of(5.0);

    public static final double BLOCK_HEIGHT = 0; //TODO // 6" foam cubes

    public static class OperatorConstants 
    {
        public static final int kDriverControllerPort = 0;
    }

    public static class Swerve {
        //TODO very bad numbers
        public static final double AUTOALIGN_STEER_KP = 13.0;
        public static final double AUTOALIGN_STEER_KI = 0.0;
        public static final double AUTOALIGN_STEER_KD = 0.0;
        
        public static final double AUTOALIGN_DRIVE_KP = 6.0;
        public static final double AUTOALIGN_DRIVE_KI = 0.0;
        public static final double AUTOALIGN_DRIVE_KD = 0.0;

        public static final Slot0Configs steerGains = new Slot0Configs()
        .withKP(100).withKI(0).withKD(0.5)
        .withKS(-0.010835).withKV(-1.2157).withKA(0.065145) //ks and kv are negative fix later
        .withStaticFeedforwardSign(StaticFeedforwardSignValue.UseClosedLoopSign);
        // When using closed-loop control, the drive motor uses the control
        // output type specified by SwerveModuleConstants.DriveMotorClosedLoopOutput
        public static final Slot0Configs driveGains = new Slot0Configs()
            .withKP(0.1).withKI(0).withKD(0)
            .withKS(0).withKV(0.124);

        // The closed-loop output type to use for the steer motors;
        // This affects the PID/FF gains for the steer motors
        public static final ClosedLoopOutputType kSteerClosedLoopOutput = ClosedLoopOutputType.Voltage;
        // The closed-loop output type to use for the drive motors;
        // This affects the PID/FF gains for the drive motors
        public static final ClosedLoopOutputType kDriveClosedLoopOutput = ClosedLoopOutputType.Voltage;

        // The type of motor used for the drive motor
        public static final DriveMotorArrangement kDriveMotorType = DriveMotorArrangement.TalonFX_Integrated;
        // The type of motor used for the steer motor
        public static final SteerMotorArrangement kSteerMotorType = SteerMotorArrangement.TalonFX_Integrated;

        // The remote sensor feedback type to use for the steer motors;
        // When not Pro-licensed, Fused*/Sync* automatically fall back to Remote*
        public static final SteerFeedbackType kSteerFeedbackType = SteerFeedbackType.FusedCANcoder;

        // The stator current at which the wheels start to slip;
        // This needs to be tuned to your individual robot
        public static final Current kSlipCurrent = Amps.of(120);

        // Initial configs for the drive and steer motors and the azimuth encoder; these cannot be null.
        // Some configs will be overwritten; check the `with*InitialConfigs()` API documentation.
        public static final TalonFXConfiguration driveInitialConfigs = new TalonFXConfiguration()
            .withCurrentLimits(
                new CurrentLimitsConfigs()
                    // Default supply current limit is 70 A, but it can be lowered to avoid brownouts.
                    // Supply current limits can be larger than the breaker current rating.
                    .withSupplyCurrentLimit(Amps.of(70))
                    .withSupplyCurrentLimitEnable(true)
            );
        public static final TalonFXConfiguration steerInitialConfigs = new TalonFXConfiguration()
            .withCurrentLimits(
                new CurrentLimitsConfigs()
                    // Swerve azimuth does not require much torque output, so we can set a relatively low
                    // stator current limit to help avoid brownouts without impacting performance.
                    .withStatorCurrentLimit(Amps.of(60))
                    .withStatorCurrentLimitEnable(true)
            );
        public static final CANcoderConfiguration encoderInitialConfigs = new CANcoderConfiguration();
        // Configs for the Pigeon 2; leave this null to skip applying Pigeon 2 configs
        public static final Pigeon2Configuration pigeonConfigs = null;

        // CAN bus that the devices are located on;
        // All swerve devices must share the same CAN bus
        public static final CANBus kCANBus = new CANBus("", "./logs/example.hoot");

        // Measured robot speed (m/s) at 12 V applied output;
        // This is NOT the desired max robot speed - see MaxSpeed in RobotContainer instead;
        // This needs to be tuned to your individual robot
        public static final LinearVelocity kSpeedAt12Volts = MetersPerSecond.of(8.78);

        // Every 1 rotation of the azimuth results in kCoupleRatio drive motor turns;
        // This may need to be tuned to your individual robot
        public static final double kCoupleRatio = 4.5;

        public static final double kDriveGearRatio = 7.03125;
        public static final double kSteerGearRatio = 26.09090909090909;
        public static final Distance kWheelRadius = Inches.of(2);

        public static final boolean kInvertLeftSide = false;
        public static final boolean kInvertRightSide = true;

        public static final int kPigeonId = 3;

        // These are only used for simulation
        public static final MomentOfInertia kSteerInertia = KilogramSquareMeters.of(0.01);
        public static final MomentOfInertia kDriveInertia = KilogramSquareMeters.of(0.035);
        // Simulated voltage necessary to overcome friction
        public static final Voltage kSteerFrictionVoltage = Volts.of(0.2);
        public static final Voltage kDriveFrictionVoltage = Volts.of(0.2);

        public static final SwerveDrivetrainConstants DrivetrainConstants = new SwerveDrivetrainConstants()
                .withCANBusName(kCANBus.getName())
                .withPigeon2Id(kPigeonId)
                .withPigeon2Configs(pigeonConfigs);

        public static final SwerveModuleConstantsFactory<TalonFXConfiguration, TalonFXConfiguration, CANcoderConfiguration> ConstantCreator =
            new SwerveModuleConstantsFactory<TalonFXConfiguration, TalonFXConfiguration, CANcoderConfiguration>()
                .withDriveMotorGearRatio(kDriveGearRatio)
                .withSteerMotorGearRatio(kSteerGearRatio)
                .withCouplingGearRatio(kCoupleRatio)
                .withWheelRadius(kWheelRadius)
                .withSteerMotorGains(steerGains)
                .withDriveMotorGains(driveGains)
                .withSteerMotorClosedLoopOutput(kSteerClosedLoopOutput)
                .withDriveMotorClosedLoopOutput(kDriveClosedLoopOutput)
                .withSlipCurrent(kSlipCurrent)
                .withSpeedAt12Volts(kSpeedAt12Volts)
                .withDriveMotorType(kDriveMotorType)
                .withSteerMotorType(kSteerMotorType)
                .withFeedbackSource(kSteerFeedbackType)
                .withDriveMotorInitialConfigs(driveInitialConfigs)
                .withSteerMotorInitialConfigs(steerInitialConfigs)
                .withEncoderInitialConfigs(encoderInitialConfigs)
                .withSteerInertia(kSteerInertia)
                .withDriveInertia(kDriveInertia)
                .withSteerFrictionVoltage(kSteerFrictionVoltage)
                .withDriveFrictionVoltage(kDriveFrictionVoltage);


        // Front Left
        public static final int kFrontLeftDriveMotorId = 6;
        public static final int kFrontLeftSteerMotorId = 1;
        public static final int kFrontLeftEncoderId = 14;
        public static final Angle kFrontLeftEncoderOffset = Rotations.of(-0.244873046875);
        public static final boolean kFrontLeftSteerMotorInverted = false;
        public static final boolean kFrontLeftEncoderInverted = false;

        public static final Distance kFrontLeftXPos = Inches.of(5);
        public static final Distance kFrontLeftYPos = Inches.of(7.75);

        // Front Right
        public static final int kFrontRightDriveMotorId = 4;
        public static final int kFrontRightSteerMotorId = 5;
        public static final int kFrontRightEncoderId = 13;
        public static final Angle kFrontRightEncoderOffset = Rotations.of(0.06982421875);
        public static final boolean kFrontRightSteerMotorInverted = false;
        public static final boolean kFrontRightEncoderInverted = false;

        public static final Distance kFrontRightXPos = Inches.of(5);
        public static final Distance kFrontRightYPos = Inches.of(-7.75);

        // Back Left
        public static final int kBackLeftDriveMotorId = 7;
        public static final int kBackLeftSteerMotorId = 2;
        public static final int kBackLeftEncoderId = 11;
        public static final Angle kBackLeftEncoderOffset = Rotations.of(-0.259033203125);
        public static final boolean kBackLeftSteerMotorInverted = false;
        public static final boolean kBackLeftEncoderInverted = false;

        public static final Distance kBackLeftXPos = Inches.of(-5);
        public static final Distance kBackLeftYPos = Inches.of(7.75);

        // Back Right
        public static final int kBackRightDriveMotorId = 8;
        public static final int kBackRightSteerMotorId = 10;
        public static final int kBackRightEncoderId = 12;
        public static final Angle kBackRightEncoderOffset = Rotations.of(-0.0068359375);
        public static final boolean kBackRightSteerMotorInverted = false;
        public static final boolean kBackRightEncoderInverted = false;

        public static final Distance kBackRightXPos = Inches.of(-5);
        public static final Distance kBackRightYPos = Inches.of(-7.75);

    }

    public static class Arm 
    {
        public static final Distance ARM_LENGTH = Inches.of(17.170); //TODO fake number
        public static final Mass ARM_MASS = Kilograms.of(6.577); //TODO slightly fake number
        
        public static final Angle MAX_ANGLE = Degrees.of(202); //TODO slightly fake number
        public static final Angle MIN_ANGLE = Radians.of(0);
        public static final Angle STARTING_SIM_ANGLE = Degrees.of(45); //TODO very fake number

        public static final Velocity<VoltageUnit> QUASI_RAMP_RATE = Volts.of(0.5).per(Second);
        public static final Voltage DYNAMIC_STEP_VOLTAGE = Volts.of(0.35);
        public static final Voltage ZEROING_VOLTAGE = Volts.of(-1.0); // TODO tune

        public static final int MASTER_ID = 15;
        public static final int FOLLOWER_ID = 16;
        public static final InvertedValue INVERTED = InvertedValue.CounterClockwise_Positive;
        public static final MotorAlignmentValue MOTOR_ALIGNMENT = MotorAlignmentValue.Opposed; //TODO
        public static final ChassisReference MECHANICAL_ORIENTATION = ChassisReference.CounterClockwise_Positive; // TODO

        public static final Current STALLING_CURRENT = Amps.of(10);
        public static final double GEAR_RATIO = 48.0;
        public static final Current SUPPLY_CURRENT_LIMIT = Amps.of(60);
        public static final Current STATOR_CURRENT_LIMIT = Amps.of(80);

        public static final double KP = 61.202;
        public static final double KI = 0;
        public static final double KD = 21.094;
        public static final double KA = 0.092;
        public static final double KS = 0.003;
        public static final double KV = 5.73;
        public static final double KG = 0.245;

        public static final Voltage DEFAULT_VOLTAGE = Volts.of(0); //TODO
        public static final AngularVelocity UP_VELOCITY = DegreesPerSecond.of(50);
        public static final AngularVelocity DOWN_VELOCITY = DegreesPerSecond.of(-50.0);
        

        public static final AngularVelocity CRUISE_VELOCITY = DegreesPerSecond.of(90); // TODO 
        public static final AngularAcceleration MAX_ACCELERATION = DegreesPerSecondPerSecond.of(180); // TODO 

        public static final Angle GROUND_ANGLE = Degrees.of(0); //TODO
        public static final Angle ZERO_ARM = Degrees.of(10); //TODO when the intake is touching swerve modules  
        public static final Angle FIRST_SHELF_ANGLE = Degrees.of(20); // TODO
        public static final Angle STACK_ANGLE = Degrees.of(15); //TODO stacking on the first shelf 
        public static final Angle TOP_SHELF_ANGLE = Degrees.of(30); // TODO
        public static final Angle ANGLE_TOLERANCE = Degrees.of(0.5);
    }

    public static class Intake
    {
        public static final AngularVelocity INTAKE_VELOCITY = RotationsPerSecond.of(-10.0); // rot/s
        public static final AngularVelocity INTAKE_REVERSE_VELOCITY = RotationsPerSecond.of(10.0); // TODO may be different rot/s

        public static final int LEFT_ID = 17;
        public static final int RIGHT_ID = 19;

        public static final double RIGHT_GEAR_RATIO = 18.0/14.0;
        public static final double LEFT_GEAR_RATIO = 18.0/14.0;

        public static final double KP = 1;
        public static final double KI = 0;
        public static final double KD = 0;

        public static final double KA = 1;
        public static final double KS = 1;
        public static final double KV = 1;

        
        public static final Velocity<VoltageUnit> QUASI_RAMP_RATE = Volts.of(0.5).per(Second);
        public static final Voltage DYNAMIC_STEP_VOLTAGE = Volts.of(0.35);

        public static final Current STALLING_CURRENT = Amps.of(30.0);
        public static final Current SUPPLY_CURRENT_LIMIT = Amps.of(60);
        public static final Current STATOR_CURRENT_LIMIT = Amps.of(80);

        public static final InvertedValue LEFT_INVERTED = InvertedValue.CounterClockwise_Positive;
        public static final MotorAlignmentValue MOTOR_ALIGNMENT = MotorAlignmentValue.Opposed;

        public static final ChassisReference MECHANICAL_ORIENTATION = ChassisReference.CounterClockwise_Positive;

        public static final MomentOfInertia INTAKE_MOI = KilogramSquareMeters.of(0.001); //TODO

    }
}

