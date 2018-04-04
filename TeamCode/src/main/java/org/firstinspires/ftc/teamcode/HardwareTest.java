package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

/**
 * Created by ethan on 2018-03-31.
 */
@Autonomous
public class HardwareTest extends LinearOpMode {

    private HydeHardware r;


    @Override
    public void runOpMode() throws InterruptedException {
        r.init(hardwareMap);

        waitForStart();

        r.jewel.setPosition(1);
        sleep(3000);
        r.jewel.setPosition(0);
        sleep(3000);
        r.flicker.setPosition(1);
        sleep(3000);
        r.flicker.setPosition(0);
        sleep(3000);

    }


}
