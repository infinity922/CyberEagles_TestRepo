package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp
public class TeleOp extends OpMode {

    private DcMotor frontLeft;
    private DcMotor frontRight;
    private DcMotor backLeft;
    private DcMotor backRight;
    private double fl;
    private double fr;
    private double bl;
    private double br;

    @Override
    public void init(){
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        backRight = hardwareMap.get(DcMotor.class, "backRight");
    }

    @Override
    public void loop(){

        fl = gamepad1.left_stick_y-gamepad1.left_stick_x;
        fr = gamepad1.left_stick_y+gamepad1.left_stick_x;
        bl = gamepad1.left_stick_y+gamepad1.left_stick_x;
        br = gamepad1.left_stick_y-gamepad1.left_stick_x;

        if(fl+gamepad1.right_stick_x > 1){
            fl = 1;
        }else if (fl+gamepad1.right_stick_x < -1){
            fl = -1;
        }else {
            fl = fl+gamepad1.right_stick_x;
        }

        if(bl+gamepad1.right_stick_x > 1){
            bl = 1;
        }else if (bl+gamepad1.right_stick_x < -1){
            bl = -1;
        }else {
            bl = bl+gamepad1.right_stick_x;
        }

        if(fr-gamepad1.right_stick_x > 1){
            fr = 1;
        }else if (fr-gamepad1.right_stick_x < -1){
            fr = -1;
        }else {
            fr = fr-gamepad1.right_stick_x;
        }

        if(br-gamepad1.right_stick_x > 1){
            br = 1;
        }else if (br-gamepad1.right_stick_x < -1){
            br = -1;
        }else {
            br = br-gamepad1.right_stick_x;
        }

        frontLeft.setPower(fl);
        frontRight.setPower(fr);
        backLeft.setPower(bl);
        backRight.setPower(br);


    }
}
