package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.*;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by nightf on 10/03/18.
 */
@com.qualcomm.robotcore.eventloop.opmode.TeleOp
public class AutoStuff extends OpMode {
    private DcMotor fL, fR, bR, bL, leftWheel, rightWheel;
    private DcMotor glyphArm;
    private double fr, fl, br, bl;
    private double extra, orbit, direction, strafe;
    double finalorbit = 0, finaldirection = 0, orbitstart = 0, directionstart = 0, orbitEnd = 0, directionEnd = 0;
    private ElapsedTime runtime = new ElapsedTime();
    private ElapsedTime orbitTime = new ElapsedTime(), directionTime = new ElapsedTime();
    private Servo jewel;
    private ColorSensor csensor;
    private double jpos = .5, fpos = .5;

    @Override
    public void init() {
        //init code
        fR = hardwareMap.dcMotor.get("frontRight");
        fL = hardwareMap.dcMotor.get("frontLeft");
        bR = hardwareMap.dcMotor.get("backRight");
        bL = hardwareMap.dcMotor.get("backLeft");
        glyphArm = hardwareMap.dcMotor.get("lift");
        rightWheel = hardwareMap.dcMotor.get("rightWheel");
        leftWheel = hardwareMap.dcMotor.get("leftWheel");
        rightWheel.setDirection(DcMotorSimple.Direction.FORWARD);
        leftWheel.setDirection(DcMotorSimple.Direction.REVERSE);
        fL.setDirection(DcMotorSimple.Direction.FORWARD);
        fR.setDirection(DcMotorSimple.Direction.REVERSE);
        bL.setDirection(DcMotorSimple.Direction.REVERSE);
        jewel = hardwareMap.servo.get("jewel");
        csensor = hardwareMap.colorSensor.get("csensor");

        glyphArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        glyphArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        jewel.setPosition(jpos);

    }

    public void loop() {
        if (gamepad1.left_stick_y > 0 && jpos <= 1) {
            jpos += .02;
            jewel.setPosition(jpos);
        } else if (gamepad1.left_stick_y < 0 && jpos >= 0) {
            jpos -= .02;
            jewel.setPosition(jpos);
        }
        stopDrive();
        orbitEnd = orbitTime.seconds();
        finalorbit += (orbitEnd - orbitstart);
        telemetry.addData("jpos: ",jpos);

        telemetry.addData("Blue: ",csensor.blue());
        telemetry.addData("Red: ",csensor.red());
        if (csensor.blue()>csensor.red())telemetry.addData("Colour: ", "Blue");
        else telemetry.addData("Colour: ", "Red");


        if (gamepad1.left_bumper){
            orbit = 1;
            orbitstart = orbitTime.seconds();
            while (gamepad1.left_bumper){
                setDrive(0,orbit,0);
            }
            stopDrive();
            orbitEnd = orbitTime.seconds();
            finalorbit += (orbitEnd-orbitstart);
        }
        telemetry.addData("orbitTime: ",finalorbit);

        if (gamepad1.a){
            direction = -1;
            directionstart = directionTime.seconds();
            while (gamepad1.a){
                setDrive(0,0,direction);
            }
            stopDrive();
            directionEnd = directionTime.seconds();
            finaldirection += (directionEnd-directionstart);
        }
        if (gamepad1.b){
            direction = 1;
            directionstart = directionTime.seconds();
            while (gamepad1.b){
                setDrive(0,0,direction);
            }
            stopDrive();
            directionEnd = directionTime.seconds();
            finaldirection += (directionEnd-directionstart);
        }
        telemetry.addData("direction Time: ",finaldirection);
        telemetry.update();
    }
    public void stop(){
        fR.setPower(0);
        fL.setPower(0);
        bR.setPower(0);
        bL.setPower(0);
        rightWheel.setPower(0);
        leftWheel.setPower(0);
        glyphArm.setPower(0);
    }
    private void setDrive(double strafe, double orbit, double direction){
        fl = 0;
        fr = 0;
        bl = 0;
        br = 0;
        fl = fl + strafe;
        fr = fr - strafe;
        bl = bl - strafe;
        br = br + strafe;
        fl = fl + orbit;
        fr = fr - orbit;
        bl = bl + orbit;
        br = br - orbit;
        fr = fr + direction;
        fl = fl + direction;
        bl = bl + direction;
        br = br + direction;
            bL.setPower(bl / 3);
            bR.setPower(br / 3);
            fL.setPower(fl / 3);
            fR.setPower(fr / 3);
        }
    private void stopDrive(){
            fR.setPower(0);
            fL.setPower(0);
            bL.setPower(0);
            bR.setPower(0);}
    }
