package frc.robot.commands;

public class CommandExamples {
    //Examples: https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
    //new RunCommand(()-> Robot.climber.goToPosition(position), Robot.climber);
    //new InstantCommand(m_hatchSubsystem::grabHatch, m_hatchSubsystem)
    //new StartEndCommand(() -> m_shooter.shooterSpeed(0.5), () -> m_shooter.shooterSpeed(0.0), m_shooter))
    //new ConditionalCommand(commandOnTrue, commandOnFalse, m_limitSwitch::get)
    //new PrintCommand("This message will be printed!")
    //new WaitCommand(5)
    //new WaitUntilCommand(m_limitSwitch::get)
    //new PerpetualCommand(commandToRunForever)
    //new ScheduleCommand(commandToSchedule) , only used to fork off a command group

    //FunctionalCommand
/*     new FunctionalCommand(
        // onInit Reset encoders on command start
        m_robotDrive::resetEncoders,
        // onExecute Start driving forward at the start of the command
        () -> m_robotDrive.arcadeDrive(kAutoDriveSpeed, 0),
        // onEnd Stop driving at the end of the command
        interrupted -> m_robotDrive.arcadeDrive(0, 0),
        // isFinished End the command when the robot's driven distance exceeds the desired value
        () -> m_robotDrive.getAverageEncoderDistance() >= kAutoDriveDistanceInches,
        // requirements Require the drive subsystem
        m_robotDrive
    ) */

    //Decorators
    //.withTimeout(5)
    //.withInterrupt decorator adds a condition on which the command will be interrupted:
    //.andThen() adds a method to run after a command ends
    //.beforeStarting adds a method to be executed before the command starts:
    //.alongWith adds a method to be executed in a parrellel command group with the command
    //.raceWith makes a parrellel race group that ends when any of the commands end
    //.deadlineWith makes a parrellel deadline group and when the first method ends all the methods end
    //.withName adds a name to a command
    //.perpetually removes the end condition

}
