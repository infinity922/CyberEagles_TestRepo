package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotor.RunMode;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.vuforia.HINT;
import com.vuforia.Vuforia;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

/**
 * Created by Thursday on 2/8/2018.
 */
@Autonomous
public class Bleu extends LinearOpMode {
    //init hardware, variables, and runtime, also inital heading
    HydeHardware r = new HydeHardware();
    private double extra, orbit, direction, strafe;
    private double fl,fr,bl,br;
    private ElapsedTime runtime = new ElapsedTime();
    float currentHeading = 0,heading2,heading1;
    Orientation angles,angles2;

    private int initBlue, initRed, colorMax = 128, blueaverage, redaverage;

    @Override
    public void runOpMode() throws InterruptedException{
        //r.liftnTilt.setMode(RunMode.RUN_USING_ENCODER);
        //r.liftnTilt.setMode(RunMode.STOP_AND_RESET_ENCODER);

        //get ambient light readings
        for(int i=0; i < 5; i++) {
            initBlue += r.csensor.blue();
            initRed += r.csensor.red();

            idle();
        }

        initBlue = initBlue/5;
        initRed = initRed/5;

        /*
        //vuforia init code
        VuforiaLocalizer.Parameters params = new VuforiaLocalizer.Parameters(R.id.cameraMonitorViewId);

        //set param values
        params.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        params.vuforiaLicenseKey = "AbuDM6D/////AAAAGYYH2C9DTUzXt5vADIJYK4FhcJkx8VccnquSTg67efZ/JSLjC+qx1o+QV7idL5MYhjqswZglFenXN6KjsmP5lAUbShN6M0EvhpGdIoDx1f6zzUiiQts0GAswRyGwi4VJa+m8UMm8+ZJXi8k56X4gdn0KOr216AA0O0oqcMdIXc9A6q2KW5IlzvrTId8jZy8lLKrQStrJtUHtlqe5d2RT/gY7i+wIZz+aVTAvAdisMgCFsYWmK9IJdo3dPmrMVOFlhZvvgchEwYDKIJ6axGekXX8u2MAFl/hEuAJWpuoYa0/VzZ/JeM61dj6VpHsZaxC0BJMRG0ypQWWxbrJg0hjcjCdNhcMP6JSkNLU5AmTPDdC9";
        params.cameraMonitorFeedback = VuforiaLocalizer.Parameters.CameraMonitorFeedback.AXES;

        //create VuforiaLocalizer
        VuforiaLocalizer vuforia = ClassFactory.createVuforiaLocalizer(params);
        Vuforia.setHint(HINT.HINT_MAX_SIMULTANEOUS_IMAGE_TARGETS, 1);

        VuforiaTrackables relicTrackables = vuforia.loadTrackablesFromAsset("RelicVuMark");
        VuforiaTrackable relicTrackable = relicTrackables.get(0);
        relicTrackable.setName("relicVuMarkTemplate");
        relicTrackables.activate();
        RelicRecoveryVuMark vuMark = RelicRecoveryVuMark.from(relicTrackable);

        telemetry.addData("vuMark", vuMark);
        telemetry.update();*/

        r.init(hardwareMap);
        waitForStart();

        //set to initial position of the middle
        r.flicker.setPosition(.5);

        /*currently using arbitrary numbers for 'extra'
        if (vuMark == RelicRecoveryVuMark.CENTER)extra = .2;
        if (vuMark == RelicRecoveryVuMark.LEFT)extra=0;
        if (vuMark == RelicRecoveryVuMark.RIGHT)extra=.4;
        */

        //all command code here

        doJewel();

        //note that direction is reversed
        setDrive(0,0,-1,1.4);
        setDrive(0,-1,0,1.2);
        WheelsOn();
        wiggle(2);
        WheelsOff();
        setDrive(0,0,1,.5);
        setHeading(90);
        setDrive(0,0,1,2);
        DumpGlyphs();

        //line up and repeat, not forgetting to move over, unless it is okay to fill the column.

        idle();

    }

    //dump the glyphs and lower the lift back down
    //based on time as no encoder currently
    private void DumpGlyphs(){
        runtime.reset();
        while (runtime.seconds()<1.35&&opModeIsActive()){
            r.liftnTilt.setPower(.75);
        }
        r.liftnTilt.setPower(0);
        setDrive(0,0,1,.5);
        sleep(1000);
        runtime.reset();
        while(runtime.seconds()<1.35&&opModeIsActive()){
            r.liftnTilt.setPower(-.75);
        }
        r.liftnTilt.setPower(0);
    }
    private void setDrive(double strafe, double orbit, double direction, double time){
        setDriveVars(strafe,orbit,direction);
        runtime.reset();
        while (time>runtime.seconds()&&opModeIsActive()) {
            r.backLeft.setPower(bl / 3);
            r.backRight.setPower(br / 3);
            r.frontLeft.setPower(fl / 3);
            r.frontRight.setPower(fr / 3);
        }
        r.frontRight.setPower(0);
        r.frontLeft.setPower(0);
        r.backLeft.setPower(0);
        r.backRight.setPower(0);
    }

    //turn on block collecting wheels
    private void WheelsOn(){
        r.rightWheel.setPower(1);
        r.leftWheel.setPower(1);
    }
    //turn off block collecting wheels
    private void WheelsOff(){
        r.rightWheel.setPower(0);
        r.leftWheel.setPower(0);
    }

    //sets drive variables
    void setDriveVars(double strafe,double orbit, double direction){
        fl = 0;
        fr = 0;
        bl = 0;
        br = 0;
        fl = fl + strafe;
        fr = fr - strafe;
        bl = bl - strafe;
        br = br + strafe;
        fl = fl + orbit;
        fr = fr - orbit;
        bl = bl + orbit;
        br = br - orbit;
        fr = fr + direction;
        fl = fl + direction;
        bl = bl + direction;
        br = br + direction;
    }
    //sets motor power to drive variables.
    void drive(double strafe,double orbit, double direction){
        setDriveVars(strafe,orbit,direction);
        r.backLeft.setPower(bl / 3);
        r.backRight.setPower(br / 3);
        r.frontLeft.setPower(fl / 3);
        r.frontRight.setPower(fr / 3);
    }

    private void setHeading(float targetHeading){
        //get an average of the current heading
        angles = r.imu1.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        angles2 = r.imu3.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        heading1 = AngleUnit.DEGREES.fromUnit(angles.angleUnit, angles.firstAngle);
        heading2 = AngleUnit.DEGREES.fromUnit(angles2.angleUnit, angles2.firstAngle);
        currentHeading = (heading1+heading2) /2;
        //check which way to rotate will be faster
        int orb=0;
        if (currentHeading - targetHeading < -180)orb=1;
        if (currentHeading + targetHeading > 180)orb=-1;
        //set orbit that way, then do while loop until heading is reached.
        drive(0,orb,0);
        //-5 because of latency when the code is occuring, to make it more accurate without adding complexity
        // or slowing down the robot
        while (opModeIsActive()&& Math.abs(currentHeading) < Math.abs(targetHeading-5)){
            //check heading
            angles = r.imu1.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
            angles2 = r.imu3.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
            heading1 = AngleUnit.DEGREES.fromUnit(angles.angleUnit, angles.firstAngle);
            heading2 = AngleUnit.DEGREES.fromUnit(angles2.angleUnit, angles2.firstAngle);
            currentHeading = (heading1+heading2) /2;
        }
        drive(0,0,0);
    }
    private void doJewel(){
        double revDirection=0;
        r.jewel.setPosition(0);

        //detect jewel color average
        for(int i = 0; i < 5; i++) {
            if ((r.csensor.blue() * colorMax ) / initBlue > (r.csensor.red() * colorMax) / initRed) {
                blueaverage++;
            }
            else if ((r.csensor.red() * colorMax ) / initRed > (r.csensor.blue() * colorMax) / initBlue) {
                redaverage++;
            }
            idle();
        }

        //determine if blue or red has a greater value.
        // then carry out according action.
        if (redaverage>blueaverage){

            r.flicker.setPosition(0);
            telemetry.addData("Jewel Status: ", "red");
        }
        else if (blueaverage>redaverage) {

            r.flicker.setPosition(1);
            telemetry.addData("Jewel Status: ", "blue");
        }
        else {
            telemetry.addData("Jewel Status: ", "not determined");
        }
        telemetry.update();

        r.jewel.setPosition(1);

        //return to initial position
        setDrive(0,0,revDirection,.5);
    }

    //To create a wiggling motion to help pick up blocks.
    private void wiggle(double time){
        setDrive(0,.7,1,time/6);
        setDrive(0,-.7,1,time/6);
        setDrive(0,.7,1,time/6);
        setDrive(0,-.7,1,time/6);
        setDrive(0,.7,1,time/6);
        setDrive(0,-.7,1,time/6);
        //right Heading??
        setHeading(90);
    }

}
