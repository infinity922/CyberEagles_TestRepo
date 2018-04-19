package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by ethan on 2018-04-18.
 */

public class DoJewel {
    HydeHardware r;
    int team;
    final int RED = 0;
    final int BLUE = 1;
    int blueaverage;
    int redaverage;
    LinearOpMode mode;
    private ElapsedTime runtime = new ElapsedTime();
    double bl,br,fr,fl;

    public DoJewel(){

    }


    public boolean jewel(HydeHardware hydeHardware, int colour, LinearOpMode opMode){
        r = hydeHardware;
        team = colour;
        mode = opMode;
        r.jewel.setPosition(.75);
        mode.sleep(500);
        if (colour == RED){
            if (check() == 0){
                setDrive(0,0,-1,.2);
                r.jewel.setPosition(.2);
                setDrive(0,0,1,.2);
                return false;
            }else if (check() == 1){
                setDrive(0,0,1,.2);
                r.jewel.setPosition(.2);
                return true;
            }
        }else if (colour == BLUE){
            if (check() == 1){
                setDrive(0,0,-1,.2);
                r.jewel.setPosition(.2);

                return true;
            }else if (check() == 0){
                setDrive(0,0,1,.2);
                r.jewel.setPosition(.2);
                setDrive(0,0,-1,.2);
                return false;
            }
        }
        return false;
    }

    int check(){
        for(int i = 0; i < 5 && mode.opModeIsActive(); i++) {
            if (r.csensor.blue()>r.csensor.red()) {
                blueaverage++;
            }
            else if (r.csensor.red() > (r.csensor.blue())) {
                redaverage++;
            }
            mode.idle();
        }
        if (redaverage>blueaverage){
            return 0;
        } else if (blueaverage>redaverage) {
            return 1;
        }else{
            return 2;
        }
    }
    private void setDrive(double strafe, double orbit, double direction, double time){
        setDriveVars(strafe,orbit,direction);
        runtime.reset();
        while (time>runtime.seconds()&&mode.opModeIsActive()) {
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
        while (time>runtime.seconds()&&mode.opModeIsActive()) {
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


}
