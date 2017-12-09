package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp
public class TeleOp extends OpMode {

    //hardware variables
    private DcMotor frontLeft;
    private DcMotor frontRight;
    private DcMotor backLeft;
    private DcMotor backRight;
    private DcMotor dumper;
    private DcMotor grabber;
    private Servo leftGrab;
    private Servo rightGrab;
    private Servo grabTilt;
    private TouchSensor grabTouch;

    //variables to store motor values to prevent stuttering
    private double fl;
    private double fr;
    private double bl;
    private double br;

    //servo-related variables
    private boolean presseda = false;
    private boolean pressedb = false;
    private boolean rightGrabbed = false;
    @Override
    public void init(){
        //find hardware on HWMap
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        backRight = hardwareMap.get(DcMotor.class, "backRight");
        dumper = hardwareMap.get(DcMotor.class, "dumper");
        grabber = hardwareMap.get(DcMotor.class,"grabber");
        leftGrab = hardwareMap.get(Servo.class, "leftGrab");
        rightGrab = hardwareMap.get(Servo.class, "rightGrab");
        grabTilt = hardwareMap.get(Servo.class, "grabTilt");

        //set necessary motors to REVERSE
        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        grabber.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        grabber.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    @Override
    public void loop(){

        //this part does front, back, left and right from gamepad1.left_stick
        fl = gamepad1.left_stick_y-gamepad1.left_stick_x;
        fr = gamepad1.left_stick_y+gamepad1.left_stick_x;
        bl = gamepad1.left_stick_y+gamepad1.left_stick_x;
        br = gamepad1.left_stick_y-gamepad1.left_stick_x;

        //this part does swiveling
        if(fl-gamepad1.right_stick_x > 1){
            fl = 1;
        }else if (fl-gamepad1.right_stick_x < -1){
            fl = -1;
        }else {
            fl = fl-gamepad1.right_stick_x;
        }

        if(bl-gamepad1.right_stick_x > 1){
            bl = 1;
        }else if (bl-gamepad1.right_stick_x < -1){
            bl = -1;
        }else {
            bl = bl-gamepad1.right_stick_x;
        }

        if(fr+gamepad1.right_stick_x > 1){
            fr = 1;
        }else if (fr+gamepad1.right_stick_x < -1){
            fr = -1;
        }else {
            fr = fr+gamepad1.right_stick_x;
        }

        if(br+gamepad1.right_stick_x > 1){
            br = 1;
        }else if (br+gamepad1.right_stick_x < -1){
            br = -1;
        }else {
            br = br+gamepad1.right_stick_x;
        }

        //this sends the variables to the motors
        frontLeft.setPower(fl);
        frontRight.setPower(fr);
        backLeft.setPower(bl);
        backRight.setPower(br);

        //run grabber arm to either position using button
        if (gamepad2.y){
            grabber.setTargetPosition(90);
            grabber.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }

        if (gamepad2.x){
            grabber.setTargetPosition(0);
            grabber.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }

        //rightGrab to position change

        if (!grabTouch.isPressed()&&)



        if (gamepad2.a){
            presseda = true;
        }
        if (!gamepad2.a && presseda){
            if (!rightGrabbed){
                rightGrab.setPosition(.5);

            }else{
                rightGrab.setPosition(.7);
            }
            presseda = false;
        }
        if (gamepad2.b){
            pressedb = true;
        }
        if (!gamepad2.b && pressedb){
            if (!rightGrabbed){


            }else{
                leftGrab.setPosition(.7);
            }
            pressedb = false;
        }



    }
}
