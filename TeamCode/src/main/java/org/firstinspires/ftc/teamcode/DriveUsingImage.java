package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.vuforia.HINT;
import com.vuforia.Vuforia;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

class DriveUsingImage{
    private DcMotor backLeft, backRight, frontLeft, frontRight;

    private double fl, fr, bl, br;
    private double orbit = 0;
    private double strafe = 0;
    private double direction = 0;
    private LinearOpMode opMode;




    public DriveUsingImage(DcMotor frontLeft, DcMotor frontRight, DcMotor backLeft, DcMotor backRight, LinearOpMode opMode) {
        this.frontLeft = frontLeft;
        this.backLeft = backLeft;
        this.backRight = backRight;
        this.frontRight = frontRight;
        this.opMode = opMode;
        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public void driveTo(double x, double z, double rot, VuforiaLocalizer vuforia, VuforiaTrackables relicTrackables, VuforiaTrackable relicTrackable){
        opMode.telemetry.addData("or Here", true);
        opMode.telemetry.update();









        OpenGLMatrix pose;
        double heading = 0;
        VectorF currentTrans;
        double currentx, lastx;
        double currentz, lastz;
        double cameraAngle = 10000;
        boolean locationReached = false;
        boolean rotNotReached = true;
        boolean xNotMet = true;
        boolean zNotMet = true;
        pose = ((VuforiaTrackableDefaultListener) relicTrackable.getListener()).getPose();
        opMode.telemetry.addData("4", true);
        opMode.telemetry.update();
        while (pose == null && opMode.opModeIsActive()) {

            pose = ((VuforiaTrackableDefaultListener) relicTrackable.getListener()).getPose();
        }
        currentTrans = pose.getTranslation();
        currentx = currentTrans.get(0);
        currentz = currentTrans.get(2);
        lastz = currentz;
        lastx = currentx;
        while (!locationReached && opMode.opModeIsActive()){






            while(xNotMet && opMode.opModeIsActive()){


                pose = ((VuforiaTrackableDefaultListener) relicTrackable.getListener()).getPose();
                if (pose != null) {
                    currentTrans = pose.getTranslation();
                    currentx = currentTrans.get(0);
                    currentz = currentTrans.get(2);
                    heading = getEuler(pose).get(1);
                    cameraAngle = Math.toDegrees(Math.atan2(currentz,currentx)-Math.toRadians(heading));

                    if (currentx < x-1){
                        if (direction < .33){
                            direction = direction + .03;
                        }
                        if (direction > .33) {
                            direction = .33;
                        }
                    }else if (currentx > x+1) {
                        if (direction > -.33){
                            direction = direction - .03;
                        }
                        if (direction < -.33) {
                            direction = -.33;
                        }

                    }else if (direction < 0){
                        direction = direction +.03;
                    }else if(direction > 0){
                        direction = direction -.03;
                    }


                    if (cameraAngle<-90){
                        if (orbit > -.33){
                            orbit = orbit-0.03;
                        }

                    }else if (cameraAngle>-90){
                        if (orbit < .33){
                            orbit = orbit+.03;
                        }
                    }

                    if (lastz>currentz){
                        if (strafe> -.33){
                            strafe = strafe-.03;
                        }
                    }else if (lastz<currentz){
                        if (strafe< .33){
                            strafe = strafe+.03;
                        }
                    }

                    if(currentx<x+1 && currentx> x-1)xNotMet = false;
                    lastz = currentz;
                    lastx = currentx;


                }
                direction = 0;
                strafe = 0;
                setDrive();





                opMode.telemetry.addData("pose",(pose != null ? pose.getTranslation() : "INVISIBLE"));
                opMode.telemetry.addData("cameraAngle",cameraAngle);
                opMode.telemetry.addData("direction", direction);
                opMode.telemetry.addData("strafe", strafe);
                opMode.telemetry.addData("orbit", orbit);
                opMode.telemetry.addData("inx", true);
                opMode.telemetry.update();


            }
            while (!xNotMet && zNotMet&& opMode.opModeIsActive()){


                pose = ((VuforiaTrackableDefaultListener) relicTrackable.getListener()).getPose();
                if (pose != null) {
                    currentTrans = pose.getTranslation();
                    currentx = currentTrans.get(0);
                    currentz = currentTrans.get(2);
                    heading = getEuler(pose).get(1);
                    cameraAngle = Math.toDegrees(Math.atan2(currentz,currentx)-Math.toRadians(heading));

                    if (currentx < lastx){
                        if (direction < .33){
                            direction = direction + .03;
                        }
                        if (direction > .33) {
                            direction = .33;
                        }
                    }else if (currentx > lastx) {
                        if (direction > -.33){
                            direction = direction - .03;
                        }
                        if (direction < -.33) {
                            direction = -.33;
                        }

                    }else if (direction < 0){
                        direction = direction +.03;
                    }else if(direction > 0){
                        direction = direction -.03;
                    }


                    if (cameraAngle>-90){
                        if (orbit > -.33){
                            orbit = orbit-0.03;
                        }

                    }else if (cameraAngle<-90){
                        if (orbit < .33){
                            orbit = orbit+.03;
                        }
                    }

                    if (z+1>currentz){
                        if (strafe> -.33){
                            strafe = strafe-.03;
                        }
                    }else if (z-1<currentz){
                        if (strafe< .33){
                            strafe = strafe+.03;
                        }
                    }

                    if(currentz<z+1 && currentz> z-1)zNotMet = false;
                    lastz = currentz;
                    lastx = currentx;
                    setDrive();
                }





                opMode.telemetry.addData("pose",(pose != null ? pose.getTranslation() : "INVISIBLE"));
                opMode.telemetry.addData("cameraAngle",cameraAngle);
                opMode.telemetry.addData("direction", direction);
                opMode.telemetry.addData("strafe", strafe);
                opMode.telemetry.addData("orbit", orbit);
                opMode.telemetry.addData("inz", true);
                opMode.telemetry.update();


            }


            strafe = 0;
            direction = 0;
            orbit = 0;
            setDrive();
            locationReached = true;


        }

        while (rotNotReached && opMode.opModeIsActive()) {
            pose = ((VuforiaTrackableDefaultListener) relicTrackable.getListener()).getPose();
            if(pose != null){
                heading = getEuler(pose).get(1);
                if (heading<rot+.005) {
                    if (orbit<.33){
                        orbit = orbit+.03;
                    }
                }
                if (heading>rot-.005){
                    if (orbit>-.33){
                        orbit = orbit-.33;
                    }
                }

                if (heading<rot+.005&&heading>rot-.005){
                    rotNotReached = false;
                }
            }
        }
        stopDrive();



    }





    private void updateDrive() {
        backLeft.setPower(bl);
        backRight.setPower(br);
        frontLeft.setPower(fl);
        frontRight.setPower(fr);


    }





    private void setDrive(){
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
        updateDrive();
    }



    private void stopDrive() {
        backLeft.setPower(0);
        backRight.setPower(0);
        frontLeft.setPower(0);
        frontRight.setPower(0);
    }
    private static VectorF getEuler(OpenGLMatrix pose) {
        double heading, pitch, roll;

        // Assuming the angles are in radians.
        if (pose.get(1, 0) > 0.998) { // singularity at north pole
            heading = Math.atan2(pose.get(0, 2), pose.get(2, 2));
            pitch = Math.PI/2;
            roll = 0;
        }
        else if (pose.get(1, 0) < -0.998) { // singularity at south pole
            heading = Math.atan2(pose.get(0, 2), pose.get(2, 2));
            pitch = -Math.PI/2;
            roll = 0;
        }
        else {
            heading = Math.atan2(pose.get(2, 0), pose.get(0, 0));
            pitch = Math.atan2(-pose.get(1, 2), pose.get(1, 1));
            roll = Math.asin(pose.get(1, 0));
        }

        return new VectorF((float) Math.toDegrees(roll), (float) Math.toDegrees(heading), (float) Math.toDegrees(pitch));
    }

}
