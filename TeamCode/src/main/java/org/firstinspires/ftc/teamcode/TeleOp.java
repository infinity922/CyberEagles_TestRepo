package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp
public class TeleOp extends OpMode {
        private DcMotor grabArm, frontLeft, frontRight, backLeft, backRight, armOut, armIn = null;
        private Servo gripper, rHold, rOver, jewel;

        //variables to store motor values to prevent stuttering
        private double fl, fr, bl, br;

        @Override
        public void init(){
            grabArm = hardwareMap.dcMotor.get("grabArm");
            frontLeft = hardwareMap.dcMotor.get("frontLeft");
            frontRight = hardwareMap.dcMotor.get("frontRight");
            backLeft = hardwareMap.dcMotor.get("backLeft");
            backRight = hardwareMap.dcMotor.get("backRight");
            gripper = hardwareMap.servo.get("gripper");
            armOut = hardwareMap.dcMotor.get("armOut");
            armIn = hardwareMap.dcMotor.get("armIn");
            rHold = hardwareMap.servo.get("rHold");
            rOver = hardwareMap.servo.get("rOver");


            gripper.setPosition(0);

            //  grabArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            //  grabArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
            backLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        }
        @Override
        public void loop(){
            glyphControl();
            mecanumDrive();
            relicMechanism();
        }
        @Override
        public void stop(){
            grabArm.setPower(0);
            frontLeft.setPower(0);
            frontRight.setPower(0);
            backRight.setPower(0);
            backLeft.setPower(0);
        }
        private void relicMechanism(){
            if (gamepad2.left_bumper) armOut.setPower(.5);
            if (gamepad2.right_bumper) armIn.setPower(.5);
            if (gamepad2.left_bumper){
                if (gamepad2.a) rHold.setPosition(0);
                if (gamepad2.b) rHold.setPosition(1);
                if (gamepad2.x) rOver.setPosition(0);
                if (gamepad2.y) rOver.setPosition(1);
            }
        }
        private void glyphControl(){
            /*
             * think about using a stage system too for the glyph arm
             */
            //min Position = 0
            //max Position = 543

            // use this if encoders can be used
            //  grabArm.setTargetPosition((int)(-gamepad2.right_stick_y*543));
            //  grabArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            //  grabArm.setPower(.75);
            //refine this:
            grabArm.setPower(-gamepad2.right_stick_y/2);
            if (gamepad1.a) gripper.setPosition(1);
            if (gamepad1.b) gripper.setPosition(0);
            /*
             * think about using a stage system too for the glyph arm
             */
        }
        private void mecanumDrive(){
            //this part does front, back, left and right from gamepad1.left_stick
            fl = gamepad1.left_stick_y - gamepad1.left_stick_x;
            fr = gamepad1.left_stick_y + gamepad1.left_stick_x;
            bl = gamepad1.left_stick_y + gamepad1.left_stick_x;
            br = gamepad1.left_stick_y - gamepad1.left_stick_x;

            //this part does swiveling
            if (fl - gamepad1.right_stick_x > 1) {
                fl = 1;
            } else if (fl - gamepad1.right_stick_x < -1) {
                fl = -1;
            } else {
                fl = fl - gamepad1.right_stick_x;
            }

            if (bl - gamepad1.right_stick_x > 1) {
                bl = 1;
            } else if (bl - gamepad1.right_stick_x < -1) {
                bl = -1;
            } else {
                bl = bl - gamepad1.right_stick_x;
            }

            if (fr + gamepad1.right_stick_x > 1) {
                fr = 1;
            } else if (fr + gamepad1.right_stick_x < -1) {
                fr = -1;
            } else {
                fr = fr + gamepad1.right_stick_x;
            }

            if (br + gamepad1.right_stick_x > 1) {
                br = 1;
            } else if (br + gamepad1.right_stick_x < -1) {
                br = -1;
            } else {
                br = br + gamepad1.right_stick_x;
            }

            //this sends the variables to the motors
            frontLeft.setPower(fl);
            frontRight.setPower(fr);
            backLeft.setPower(bl);
            backRight.setPower(br);
        }
    }