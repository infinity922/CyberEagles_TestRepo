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

    public void driveTo(double x, double z, double rot, VuforiaLocalizer vuforia){

        Vuforia.setHint(HINT.HINT_MAX_SIMULTANEOUS_IMAGE_TARGETS, 4);

        VuforiaTrackables relicTrackables = vuforia.loadTrackablesFromAsset("RelicVuMark");
        VuforiaTrackable relicTrackable = relicTrackables.get(0);
        relicTrackables.activate();







        OpenGLMatrix pose;
        double heading;
        VectorF currentTrans;
        double currentx, lastx;
        double currentz, lastz;
        double cameraAngle = 10000;
        boolean locationReached = false;
        boolean rotNotReached = true;
        boolean xNotMet = true;
        boolean zNotMet = true;
        pose = null;
        while (pose == null) {

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

                    if (currentx < x-.005){
                        if (direction < .33){
                            direction = direction + .03;
                        }
                        if (direction > .33) {
                            direction = .33;
                        }
                    }else if (currentx > x+.005) {
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


                    if (cameraAngle<0){
                        if (orbit > -.33){
                            orbit = orbit-0.03;
                        }

                    }else if (cameraAngle>0){
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

                    if(currentx<x+0.005 && currentx> x-0.005)xNotMet = false;
                    lastz = currentz;
                    lastx = currentx;
                    setDrive();
                }





                opMode.telemetry.addData("pose",(pose != null ? pose.getTranslation() : "INVISIBLE"));
                opMode.telemetry.addData("cameraAngle",cameraAngle);
                opMode.telemetry.addData("direction", direction);
                opMode.telemetry.addData("strafe", strafe);
                opMode.telemetry.addData("orbit", orbit);
                opMode.telemetry.update();


            }
            while (!xNotMet && zNotMet){


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


                    if (cameraAngle<0){
                        if (orbit > -.33){
                            orbit = orbit-0.03;
                        }

                    }else if (cameraAngle>0){
                        if (orbit < .33){
                            orbit = orbit+.03;
                        }
                    }

                    if (z+.005>currentz){
                        if (strafe> -.33){
                            strafe = strafe-.03;
                        }
                    }else if (z-.005<currentz){
                        if (strafe< .33){
                            strafe = strafe+.03;
                        }
                    }

                    if(currentx<x+0.005 && currentx> x-0.005)xNotMet = false;
                    lastz = currentz;
                    lastx = currentx;
                    setDrive();
                }





                opMode.telemetry.addData("pose",(pose != null ? pose.getTranslation() : "INVISIBLE"));
                opMode.telemetry.addData("cameraAngle",cameraAngle);
                opMode.telemetry.addData("direction", direction);
                opMode.telemetry.addData("strafe", strafe);
                opMode.telemetry.addData("orbit", orbit);
                opMode.telemetry.update();

            }


            //strafe = 0;
            //direction = 0;
            //setDrive();
            //locationReached = true;


        }

        while (rotNotReached) {

        }

    }



    private void updateDrive() {
        backLeft.setPower(bl/4);
        backRight.setPower(br/4);
        frontLeft.setPower(fl/4);
        frontRight.setPower(fr/4);


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
