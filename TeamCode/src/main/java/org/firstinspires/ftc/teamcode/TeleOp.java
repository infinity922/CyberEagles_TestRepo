package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp
public class TeleOp extends OpMode {
        private DcMotor grabArm, frontLeft, frontRight, backLeft, backRight, armOut, armIn = null;
        private Servo gripper, rHold, rOver, jewel, lArm, rArm;

        //variables to store motor values to prevent stuttering
        private double fl, fr, bl, br, step;
        private double holdPos= 0.5;
        private double overPos= 0;
        int extra = 0;
        private boolean lpressed,lreleased,rpressed,rreleased, upressed, urel, dpressed, drel= false;

        @Override
        public void init(){
            grabArm = hardwareMap.dcMotor.get("grabber");
            frontLeft = hardwareMap.dcMotor.get("frontLeft");
            frontRight = hardwareMap.dcMotor.get("frontRight");
            backLeft = hardwareMap.dcMotor.get("backLeft");
            backRight = hardwareMap.dcMotor.get("backRight");
            gripper = hardwareMap.servo.get("gripper");
            armOut = hardwareMap.dcMotor.get("armOut");
            armIn = hardwareMap.dcMotor.get("armIn");
            rHold = hardwareMap.servo.get("rHold");
            rOver = hardwareMap.servo.get("rOver");
            lArm = hardwareMap.servo.get("lArm");
            rArm = hardwareMap.servo.get("rArm");

            //run the arm motor with encoders
            grabArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            grabArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            //set proper directions for drive
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
            armIn.setPower(0);
            armOut.setPower(0);
        }
        private void relicMechanism(){
            //this does the in and out motion of the linear slides
            if (gamepad2.left_bumper) {armIn.setPower(-gamepad2.left_stick_y); armOut.setPower(gamepad2.left_stick_y);}
            if (!gamepad2.left_bumper) {armOut.setPower(0);armIn.setPower(0);}
            //This controls the servos at the end of the linear slides
            if (gamepad2.left_bumper){
                if (gamepad2.a) {
                    if (holdPos >0){holdPos -= .02;rHold.setPosition(holdPos);}}
                if (gamepad2.b){
                    if (holdPos <1){holdPos += .02;rHold.setPosition(holdPos);}}
                if (gamepad2.x) {rOver.setPosition(0);}
                else {rOver.setPosition(0.45);}
            telemetry.addData("overPos", overPos);
            telemetry.update();
        }}
        private void glyphControl(){
            /*
             * think about using a stage system too for the glyph arm
             */
            //min Position = 0
            //max Position = 543

            if (gamepad2.dpad_up) upressed = true;
            if (upressed && (!gamepad2.dpad_up))urel = true;
            if (gamepad2.dpad_down) dpressed = true;
            if (dpressed && (!gamepad2.dpad_down)) drel = true;

            //alternate stepping method

            if (gamepad2.dpad_left) lpressed = true;
            if (lpressed && (!gamepad2.dpad_left)) lreleased = true;
            if (gamepad2.dpad_right) rpressed = true;
            if (rpressed && (!gamepad2.dpad_right)) rreleased = true;

            if ((upressed && urel) && (step<3)) {step++;upressed = false; urel= false;}
            if ((dpressed && drel) && (step>0)){step--;dpressed = false; drel= false;}
            if (lpressed && lreleased) {lpressed = false; lreleased = false; extra = 0;}
            if (rpressed && rreleased) {rpressed = false; rreleased = false; extra = 100;}
            double target = ((step/3)*543);
            int itarget = ((int)target + extra);
            if (itarget > 543) itarget = 543;


            grabArm.setTargetPosition(itarget);
            // use this if encoders can be used
             //grabArm.setTargetPosition((int)(-gamepad2.left_stick_y*543));
              grabArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
              grabArm.setPower(1);
            //refine this:
            //grabArm.setPower(-gamepad2.right_stick_y/2);
            if (gamepad2.a) gripper.setPosition(1);
            if (gamepad2.b) gripper.setPosition(0);

            double lArmPos = gamepad2.left_trigger;
            double rArmPos = gamepad2.left_trigger;
            if (rArmPos < .2) rArmPos = .2;
            if (lArmPos < .3) lArmPos = .3;
            if (rArmPos > .7) rArmPos=.7;
            if (lArmPos > .7) lArmPos=.7;
            lArm.setPosition(lArmPos);
            rArm.setPosition(rArmPos);
        }
        private void mecanumDrive(){
            //this part does front, back, left and right from gamepad1.left_stick
            fl = gamepad1.left_stick_y + gamepad1.left_stick_x;
            fr = gamepad1.left_stick_y - gamepad1.left_stick_x;
            bl = gamepad1.left_stick_y - gamepad1.left_stick_x;
            br = gamepad1.left_stick_y + gamepad1.left_stick_x;

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
            //with a slow mode
            if (gamepad1.right_trigger!=0){
                frontLeft.setPower((fl/2)*(gamepad1.right_trigger+1));
                frontRight.setPower((fr/2)*(gamepad1.right_trigger+1));
                backLeft.setPower((bl/2)*(gamepad1.right_trigger+1));
                backRight.setPower((br/2)*(gamepad1.right_trigger+1));
            }else {
                frontLeft.setPower(fl/2);
                frontRight.setPower(fr/2);
                backLeft.setPower(bl/2);
                backRight.setPower(br/2);
            }
        }
    }