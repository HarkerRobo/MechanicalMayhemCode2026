// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix6.CANBus;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.signals.*;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
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
    public static final CANBus CAN_SUPERSTRUCTURE = new CANBus("Superstructure");
    public static final CANBus CAN_CHAIN = new CANBus("rio");

    public static final double ROBOT_HEIGHT = 0.89535; //parallel to the intake face (TODO)
    public static final double ROBOT_WIDTH = 0.84455; //TODO

    public static final Pose2d ZEROING_POSE = new Pose2d(3.581, 4.20288, new Rotation2d(Degrees.of(180))); //TODO

    public static final double TRANSLATION_SLOW_MULTIPLIER = 0.35;
    public static final double ROTATION_SLOW_MULTIPLIER = 0.5;

    public static final Voltage MAX_VOLTAGE = Volts.of(12.0);
    public static final Voltage ARM_VOLTAGE = Volts.of(5.0);

    public static class OperatorConstants 
    {
        public static final int kDriverControllerPort = 0;
    }

    public static class Arm 
    {
        public static final int MASTER_ID = 0;
        public static final int FOLLOWER_ID = 0;
        public static final InvertedValue INVERTED = InvertedValue.CounterClockwise_Positive;
        public static final MotorAlignmentValue MOTOR_ALIGNMENT = MotorAlignmentValue.Opposed; //TODO

        public static final Current STALLING_CURRENT = Amps.of(0);
        public static final double GEAR_RATIO = 0;
        public static final Current SUPPLY_CURRENT_LIMIT = Amps.of(0);
        public static final Current STATOR_CURRENT_LIMIT = Amps.of(0);

        public static final double KP = 0;
        public static final double KI = 0;
        public static final double KD = 0;
        public static final double KA = 0;
        public static final double KS = 0;
        public static final double KV = 0;

        public static final Voltage ARM_UP_VOLTAGE = Volts.of(5); //TODO
        public static final Voltage ARM_DOWN_VOLTAGE = Volts.of(-5); //TODO
        public static final Voltage ARM_DOWN_VOLTAGE_STAY = Volts.of(1); //TODO
        
    }

    public static class Intake
    {
        public static final AngularVelocity INTAKE_VELOCITY = RotationsPerSecond.of(-30.0); // rot/s

        public static final int LEFT_ID = 0;
        public static final int RIGHT_ID = 0;

        public static final double RIGHT_GEAR_RATIO = 48;
        public static final double LEFT_GEAR_RATIO = 48;

        public static final double KP = 0;
        public static final double KI = 0;
        public static final double KD = 0;

        public static final double KA = 0;
        public static final double KS = 0;
        public static final double KV = 0;

        public static final Current STALLING_CURRENT = Amps.of(30.0);

        public static final InvertedValue LEFT_INVERTED = InvertedValue.CounterClockwise_Positive;
        public static final InvertedValue RIGHT_INVERTED = InvertedValue.CounterClockwise_Positive;
        public static final MotorAlignmentValue MOTOR_ALIGNMENT = MotorAlignmentValue.Opposed;
    }
}

