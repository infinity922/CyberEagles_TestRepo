package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.*;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by Ethan McGhan on 2018-02-17.
 */
public class Encoders extends OpMode {
    private DcMotor lift;
    @Override
    public void init() {
        lift = hardwareMap.dcMotor.get("lift");
        lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    @Override
    public void loop() {
        telemetry.addData("encoder Pos", lift.getCurrentPosition());
        telemetry.update();
    }
}
