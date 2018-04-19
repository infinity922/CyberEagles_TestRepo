package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

/**
 * Created by ethan on 2018-04-18.
 */
@Autonomous
public class TestJewel extends LinearOpMode{
    HydeHardware r = new HydeHardware();
    private double extra, orbit, direction, strafe;
    private double fl,fr,bl,br;
    private ElapsedTime runtime = new ElapsedTime();
    float currentHeading = 0,heading2,heading1;
    boolean angleNeg = true,back=false;
    Orientation angles,angles2;
    DoJewel go;

    @Override
    public void runOpMode() throws InterruptedException {
        go = new DoJewel(r,0,this);
        r.init(hardwareMap);
        waitForStart();
        telemetry.addData("unblalnced: ", go.jewel());
        sleep(5000);
        DoJewel gi = new DoJewel(r,1,this);
        telemetry.addData("unbalanced: ", gi.jewel());
        sleep(5000);
    }
}
