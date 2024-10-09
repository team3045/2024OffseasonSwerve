// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.constants.IntakeConstants;

public class Intake extends SubsystemBase {
  private TalonFX intakeMotorLeft = new TalonFX(IntakeConstants.leftID, IntakeConstants.canbus);
  private TalonFX intakeMotorRight = new TalonFX(IntakeConstants.rightID, IntakeConstants.canbus);

  private enum IntakeState{
    INTAKING,
    IDLE
  }

  private IntakeState state;

  /** Creates a new Intake. */
  public Intake() {
    intakeMotorLeft.getConfigurator().apply(
      new MotorOutputConfigs().withNeutralMode(NeutralModeValue.Brake));
    intakeMotorRight.getConfigurator().apply(
      new MotorOutputConfigs().withNeutralMode(NeutralModeValue.Brake));
    
    state = IntakeState.IDLE;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public Command runIntakeMotor(){
    return this.run(() -> {
      intakeMotorLeft.set(IntakeConstants.intakeSpeed);
      intakeMotorRight.set(IntakeConstants.intakeSpeed);
      state = IntakeState.INTAKING;
    });
  }


  public Command stop(){
    return this.run(() -> {
      intakeMotorLeft.stopMotor();
      intakeMotorRight.stopMotor();
      state = IntakeState.IDLE;
    });
  }

  //TRIGGERS
  public final Trigger isIntaking = new Trigger(() -> state == IntakeState.INTAKING);
}