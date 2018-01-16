package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;

class DriveUsingImage{
    private DcMotor backLeft, backRight, frontLeft, frontRight;

    private double fl, fr, bl, br;
    private double orbit = 0;
    private double strafe = 0;
    private double direction = 0;



    public DriveUsingImage(DcMotor frontLeft, DcMotor frontRight, DcMotor backLeft, DcMotor backRight) {
        this.frontLeft = frontLeft;
        this.backLeft = backLeft;
        this.backRight = backRight;
        this.frontRight = frontRight;

    }

    public void driveTo(double x, double z, double rot, VuforiaTrackable relicTrackable){


        OpenGLMatrix pose;
        double heading;
        VectorF currentTrans;
        double currentx, lastx;
        double currentz, lastz;
        double cameraAngle;
        boolean locationReached = false;
        boolean xNotMet = true;
        boolean yNotMet = true;

        while (!locationReached){
            pose = ((VuforiaTrackableDefaultListener) relicTrackable.getListener()).getPose();
            heading = getEuler(pose).get(1);
            currentTrans = pose.getTranslation();
            currentx = currentTrans.get(0);
            currentz = currentTrans.get(2);
            lastz = currentz;
            lastx = currentx;
            cameraAngle = Math.toDegrees(Math.atan2(currentz,currentx)-Math.toRadians(heading));

            while(xNotMet){
                if (currentx>x){
                    if (currentx > x){
                        if(direction<.33){
                            direction = direction+.03;
                        }
                    }else if (currentx < x) {
                        if (direction > -0.33) {
                            direction = direction - .03;
                        }
                    }
                }

                if (cameraAngle<0){
                    if (orbit > -.33){
                        orbit = orbit-0.03;
                    }

                }else if (cameraAngle>0){
                    if (orbit < .33){
                        orbit = orbit+.33;
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

                if(currentx<x+5 && currentx> x-5)xNotMet = false;
                lastz = currentz;
                lastx = currentx;
                setDrive();
            }

            strafe = 0;
            direction = 0;
            setDrive();
        }

    }



    private void updateDrive() {
        backLeft.setPower(bl);
        backRight.setPower(br);
        frontLeft.setPower(fl);
        frontRight.setPower(fr);
    }





    private void setDrive(){
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
