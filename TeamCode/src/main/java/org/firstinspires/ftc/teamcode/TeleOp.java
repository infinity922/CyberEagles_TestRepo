package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
/**
 * Created by Thursday on 10/20/2017.
 */
@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name="TeleOp", group="Iterative Opmode")
public class TeleOp extends OpMode{

    private DcMotor lift = null;

    @Override
    public void init(){
        lift = hardwareMap.get(DcMotor.class, "lift");

    }
    @Override
    public void init_loop(){}
    @Override
    public void start(){}
    @Override
    public void loop(){

    }
    @Override
    public void stop(){
        lift.setPower(0);
    }
}
