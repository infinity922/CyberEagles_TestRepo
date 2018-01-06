package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp
public class TeleOp extends OpMode {

    //hardware variables
    private DcMotor frontLeft;
    private DcMotor frontRight;
    private DcMotor backLeft;
    private DcMotor backRight;
//    private DcMotor armOut, armIn, grabber = null;
//    private Servo gripper, rGrab, rPivot = null;

    //variables to store motor values to prevent stuttering
    private double fl;
    private double fr;
    private double bl;
    private double br;

    @Override
    public void init() {
        //find hardware on HWMap
 //       armOut = hardwareMap.get(DcMotor.class, "armOut");
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        frontRight = hardwareMap.dcMotor.get("frontRight");
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        backRight = hardwareMap.get(DcMotor.class, "backRight");
 /*       armIn = hardwareMap.get(DcMotor.class, "armIn");
        grabber = hardwareMap.get(DcMotor.class, "grabber");
        gripper= hardwareMap.get(Servo.class, "gripper");
        rGrab = hardwareMap.get(Servo.class, "rGrab");
        rPivot = hardwareMap.get(Servo.class, "rPivot");*/

        //set necessary motors to REVERSE
        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);

        //set grabber to RUN_USING_ENCODER
    //    grabber.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    //    grabber.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        //init servo positions

     //   gripper.setPosition(0);
       // rGrab.setPosition(0);
        //rPivot.setPosition(0);
    }

    @Override
    public void loop() {

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
/**
        //run grabber arm to either position using button
        //1220 ticks/rev on andymark motors?
        if (gamepad2.y) {
            grabber.setTargetPosition(90);
            grabber.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            grabber.setPower(.5);
        }

        if (gamepad2.x) {
            grabber.setTargetPosition(0);
            grabber.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            grabber.setPower(.5);
        }

        //CREATE A RESET FUNCTION FOR THE SERVOS
        telemetry.addData("rGrab: ", rGrab.getPosition());
        telemetry.addData("rPivot: ", rPivot.getPosition());
        telemetry.addData("gripper: ", gripper.getPosition());

        //to slowly close the glyph grippers when a is pressed until the touch sensor is pressed
        //example
        // to open the glyph arms incrementally while b is pressed
   /*         for(double i=0; gamepad2.b; i+=.02){
            if ((leftGrab.getPosition()!= 1) | (rightGrab.getPosition()!=1)) {
                double leftGrabPos = leftGrab.getPosition() + i;
                double rightGrabPos = rightGrab.getPosition() + i;
                leftGrab.setPosition(leftGrabPos);
                rightGrab.setPosition(rightGrabPos);
            }
            i=0;
            if(!gamepad2.b) break;
            } */
/**
   if (gamepad2.a){
       if (rPivot.getPosition() !=1){
           double rPivotPos = rPivot.getPosition();
           rPivot.setPosition((.02 + rPivotPos));
   }    }
   if (gamepad2.b){
        if (rPivot.getPosition() !=0){
            double rGrabPos = rGrab.getPosition();
            rGrab.setPosition((rGrabPos -.02));
   }}
   if (gamepad2.x){
       if (rGrab.getPosition() !=1) {
           double rGrabPos = rGrab.getPosition();
           rGrab.setPosition((rGrabPos+.02));
       }
   }
   if (gamepad2.y){
       if (rGrab.getPosition() !=0){
           double rGrabPos = rGrab.getPosition();
           rGrab.setPosition(rGrabPos-.02);
       }
   }
   //armIn.setPower(-gamepad2.left_stick_y/2);
   //armOut.setPower(-gamepad2.right_stick_y/2);
   grabber.setPower(-gamepad2.left_stick_y/2);
   int gPos = grabber.getCurrentPosition();
   telemetry.addData("Grabber Pos", gPos);*/
    }
}
