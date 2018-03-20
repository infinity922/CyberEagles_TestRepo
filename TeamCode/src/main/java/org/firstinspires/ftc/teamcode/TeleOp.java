package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp
public class TeleOp extends OpMode {
        private DcMotor liftnTilt, frontLeft, frontRight, backLeft, backRight, armOut, leftWheel, rightWheel = null;
        private Servo align;

        //variables to store motor values to prevent stuttering
        private double fl, fr, bl, br, step;
        private double holdPos= 0.5;
        private double overPos= 0;
        private final int TOP = 2735;
        public final double IN = .26;
        public final double OUT = .71;
        private ElapsedTime runtime = new ElapsedTime();

        private boolean lpressed,lreleased,rpressed,rreleased, upressed, urel, dpressed, drel= false;
        private boolean leftBumper = false;

        @Override
        public void init(){
            liftnTilt = hardwareMap.dcMotor.get("lift");
            frontLeft = hardwareMap.dcMotor.get("frontLeft");
            frontRight = hardwareMap.dcMotor.get("frontRight");
            backLeft = hardwareMap.dcMotor.get("backLeft");
            backRight = hardwareMap.dcMotor.get("backRight");
            //gripper = hardwareMap.servo.get("gripper");
            //armOut = hardwareMap.dcMotor.get("armOut");
            rightWheel = hardwareMap.dcMotor.get("rightWheel");
            leftWheel = hardwareMap.dcMotor.get("leftWheel");
            //rHold = hardwareMap.servo.get("rHold");
            //rOver = hardwareMap.servo.get("rOver");
            //lArm = hardwareMap.servo.get("lArm");
            //rArm = hardwareMap.servo.get("rArm");
            align = hardwareMap.servo.get("align");

            //run the arm motor with encoders

            //set proper directions for drive
            frontLeft.setDirection(DcMotorSimple.Direction.FORWARD);
            frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
            backLeft.setDirection(DcMotorSimple.Direction.REVERSE);
            liftnTilt.setDirection(DcMotorSimple.Direction.FORWARD);
            rightWheel.setDirection(DcMotorSimple.Direction.FORWARD);
            leftWheel.setDirection(DcMotorSimple.Direction.REVERSE);
            liftnTilt.setMode(DcMotor.RunMode.RUN_USING_ENCODER);






        }
        @Override
        public void loop() {
            glyphControl();
            mecanumDrive();
            //relicMechanism();
            telemetry.addData("servo pos", align.getPosition());
            telemetry.update();

            if (gamepad1.right_bumper) {
                align.setPosition(OUT);

            } else {
                align.setPosition(IN);
            }
        }
        public void stop(){
            liftnTilt.setPower(0);
            frontLeft.setPower(0);
            frontRight.setPower(0);
            backRight.setPower(0);
            backLeft.setPower(0);
            leftWheel.setPower(0);
            rightWheel.setPower(0);
            //armOut.setPower(0);
        }
        /*private void relicMechanism(){
            //this does the in and out motion of the linear slides
            if (gamepad2.left_bumper) { armOut.setPower(gamepad2.left_stick_y);}
            if (!gamepad2.left_bumper) {armOut.setPower(0);}
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
        }}*/
        private void glyphControl(){

            if (gamepad1.right_bumper) {
                align.setPosition(OUT);

            } else {
                align.setPosition(IN);
            }

            //Blame Curtis for the next bit of code...
            if (!gamepad2.a){
                liftnTilt.setPower(-gamepad2.left_stick_y);
            }else{
                liftnTilt.setPower(-gamepad2.left_stick_y*.7);
            }

            //Stop blaming Curtis, he didn't do it...

            if (gamepad2.x){
                leftWheel.setPower(-gamepad2.right_trigger);
                rightWheel.setPower(-gamepad2.right_trigger);
            }else {
                leftWheel.setPower(gamepad2.right_trigger);
                rightWheel.setPower(gamepad2.right_trigger);
            }

            /*if (gamepad2.a) gripper.setPosition(1);
            if (gamepad2.b) gripper.setPosition(0);

            double lArmPos = gamepad2.left_trigger;
            double rArmPos = gamepad2.left_trigger;
            if (rArmPos < .2) rArmPos = .2;
            if (lArmPos < .3) lArmPos = .3;
            if (rArmPos > .7) rArmPos=.7;
            if (lArmPos > .7) lArmPos=.7;
            lArm.setPosition(lArmPos);
            rArm.setPosition(rArmPos);*/

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

            if (gamepad1.left_bumper){
                leftBumper = true;
            }
            if (!gamepad1.left_bumper){
                if (leftBumper){
                    leftBumper = false;
                    onwards();
                }
            }else{leftBumper = false;}

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
        private void onwards(){
            runtime.reset();
            while (runtime.milliseconds()<200){
                br = .25;
                bl = .25;
                fr = .25;
                fl = .25;
                updateDrive();
            }
            stopDrive();
        }

        private void updateDrive() {
            backLeft.setPower(bl);
            backRight.setPower(br);
            frontLeft.setPower(fl);
            frontRight.setPower(fr);


        }
        private void stopDrive(){
            frontLeft.setPower(0);
            frontRight.setPower(0);
            backLeft.setPower(0);
            backRight.setPower(0);
        }
    }