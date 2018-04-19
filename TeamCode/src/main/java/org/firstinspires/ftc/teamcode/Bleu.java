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

/**
 * Created by Thursday on 2/8/2018.
 */

public class Bleu extends LinearOpMode {
    //init hardware, variables, and runtime, also inital heading
    HydeHardware r = new HydeHardware();
    private double extra, orbit, direction, strafe;
    private double fl,fr,bl,br;
    private ElapsedTime runtime = new ElapsedTime();
    float currentHeading = 0,heading2,heading1;
    boolean angleNeg = true,back=false;
    Orientation angles,angles2;
    DoJewel go = new DoJewel();

    private int initBlue, initRed, blueaverage, redaverage;

    @Override
    public void runOpMode() throws InterruptedException{
        r.init(hardwareMap);
        //r.liftnTilt.setMode(RunMode.RUN_USING_ENCODER);
        //r.liftnTilt.setMode(RunMode.STOP_AND_RESET_ENCODER);

        //get ambient light readings
        /*for(int i=0; i < 5; i++) {
            initBlue += r.csensor.blue();
            initRed += r.csensor.red();

            idle();
        }

        initBlue = initBlue/5;
        initRed = initRed/5;
        //so if the csensor doesn't work then we won't be dividing by zero.
        if (initBlue ==0)initBlue=1;
        if (initRed==0)initRed=1;*/

        waitForStart();
        //set to initial position of the middle
        //r.flicker.setPosition(.5);

        //doJewel();
        //while (opModeIsActive())idle();

        //note that direction is reversed

        //mock up the position after jewel

        go.jewel(r,1, this);

        setDrive(0,0,-1,.4);
        idle();

        setDrive(0,0,-1,1);
        setDrive(0,1,0,1.45);
        setDrive(0,0,1,.4);
        DumpGlyphs();
        setDrive(0,0,-1,.4);

        WheelsOn();
        wiggle(2);
        WheelsOff();
        setDrive(0,0,1,1);
        setHeading(90);

                /**
                 * (-180,180)
                 */
        setDrive(0,0,1,1.5);
        setDrive(0,0,-1,.4);
        DumpGlyphs();
        setDrive(0,0,-1,.5);
        //line up and repeat, not forgetting to move over, unless it is okay to fill the column.

        idle();

    }

    //dump the glyphs and lower the lift back down
    //based on time as no encoder currently
    private void DumpGlyphs(){
        runtime.reset();
        while (runtime.seconds()<4.5&&opModeIsActive()){
            r.liftnTilt.setPower(-.75);
        }
        r.liftnTilt.setPower(0);
        setDrive(0,0,1,.5);
        sleep(1000);
        runtime.reset();

        while(runtime.seconds()<3.5&& opModeIsActive()){
            r.liftnTilt.setPower(1);
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

    private void setFullDrive(double strafe, double orbit, double direction, double time){
        setDriveVars(strafe,orbit,direction);
        runtime.reset();
        while (time>runtime.seconds()&&opModeIsActive()) {
            r.backLeft.setPower(bl);
            r.backRight.setPower(br);
            r.frontLeft.setPower(fl);
            r.frontRight.setPower(fr);
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

    //sets  a heading for the robot
    private void setHeading(float targetHeading) {
        //get an average of the current heading
        angles = r.imu1.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        angles2 = r.imu3.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        heading1 = AngleUnit.DEGREES.fromUnit(angles.angleUnit, angles.firstAngle);
        heading2 = AngleUnit.DEGREES.fromUnit(angles2.angleUnit, angles2.firstAngle);
        currentHeading = (heading1 + heading2) / 2;
        //check which way to rotate will be faster, accounting for: (-Math.PI,Math.PI)
        int orb = 0;
        if (currentHeading + 180 <= 180 && (currentHeading < targetHeading && targetHeading < currentHeading + 180)) {
            orb = -1;
            angleNeg = false;
        } else if (currentHeading + 180 <= 180) {
            orb = 1;
        }
        if (currentHeading - 180 >= -180 && currentHeading > targetHeading && targetHeading > currentHeading - 180) {
            orb = 1;
            angleNeg=false;
        } else if (currentHeading - 180 >= -180) orb = -1;
        //set orbit that way, then do while loop until heading is reached.
        drive(0, orb, 0);
        //-5 because of latency when the code is occuring, to make it more accurate without adding complexit
        // or slowing down the robot
        if (angleNeg && opModeIsActive()) {
            while (opModeIsActive() && Math.abs(currentHeading) > Math.abs(targetHeading + 8)) {
                //check heading
                angles = r.imu1.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
                angles2 = r.imu3.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
                heading1 = AngleUnit.DEGREES.fromUnit(angles.angleUnit, angles.firstAngle);
                heading2 = AngleUnit.DEGREES.fromUnit(angles2.angleUnit, angles2.firstAngle);
                currentHeading = (heading1 + heading2) / 2;
            }
        } else {
            while (opModeIsActive() && Math.abs(currentHeading) < Math.abs(targetHeading - 8)){
                //check heading
                angles = r.imu1.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
                angles2 = r.imu3.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
                heading1 = AngleUnit.DEGREES.fromUnit(angles.angleUnit, angles.firstAngle);
                heading2 = AngleUnit.DEGREES.fromUnit(angles2.angleUnit, angles2.firstAngle);
                currentHeading = (heading1 + heading2) / 2;
            }
        }
        drive(0, 0, 0);
    }

    private void doJewel(){
        double revDirection=0;
        //
        // r.jewel.setPosition(0);

        //detect jewel color average
        for(int i = 0; i < 5; i++) {
            if (r.csensor.blue() / initBlue > r.csensor.red() / initRed) {
                blueaverage++;
            }
            else if (r.csensor.red() / initRed > (r.csensor.blue() / initBlue)) {
                redaverage++;
            }
            idle();
        }

        //determine if blue or red has a greater value.
        // then carry out according action.
        if (redaverage>blueaverage){

            setDrive(0,0,1,.4);
            back=true;
            telemetry.addData("Jewel Status: ", "red");

        }
        else if (blueaverage>redaverage) {

            setDrive(0,0,-1,.4);
            telemetry.addData("Jewel Status: ", "blue");
        }
        else {
            telemetry.addData("Jewel Status: ", "not determined");
        }
        telemetry.update();
        if (back)setDrive(0,0,-1,.8);
        r.jewel.setPosition(1);

        //return to initial position
    }

    //To create a wiggling motion to help pick up blocks.
    private void wiggle(double time){
        setFullDrive(0,.7,-1,time/6);
        setFullDrive(0,-.7,-1,time/6);
        setFullDrive(0,.7,-1,time/6);
        setFullDrive(0,-.7,-1,time/6);
        setFullDrive(0,.7,-1,time/6);
        setFullDrive(0,-.7,-1,time/6);
    }
}
