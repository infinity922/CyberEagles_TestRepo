package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.vuforia.HINT;
import com.vuforia.Vuforia;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

import static java.lang.Thread.sleep;

class DriveUsingImage{
    private DcMotor backLeft, backRight, frontLeft, frontRight;

    private double fl, fr, bl, br;
    double orbit = 0;
    double strafe = 0;
    double direction = 0;



    public DriveUsingImage(DcMotor frontLeft, DcMotor frontRight, DcMotor backLeft, DcMotor backRight) {
        this.frontLeft = frontLeft;
        this.backLeft = backLeft;
        this.backRight = backRight;
        this.frontRight = frontRight;

    }

    void driveTo(double x, double y, double rot, VuforiaTrackable relicTrackable){

        double phoneMax = 34; // this is a made-up value, will have to test to get the real one
        double phoneMin = -34;
        OpenGLMatrix pose = ((VuforiaTrackableDefaultListener) relicTrackable.getListener()).getPose();
        double heading = getEuler(pose).get(1);
        VectorF currentTrans = pose.getTranslation();
        double currentx = currentTrans.get(0);
        double currenty = currentTrans.get(1);
        double cameraAngle = Math.toDegrees(Math.atan2(currenty,currentx)-Math.toRadians(heading));

    }



    private void updateDrive() {
        backLeft.setPower(bl);
        backRight.setPower(br);
        frontLeft.setPower(fl);
        frontRight.setPower(fr);
    }



    void setOrbit(){
        fl = fl + orbit;
        fr = fr - orbit;
        bl = bl + orbit;
        br = br - orbit;
        updateDrive();
    }

    void setStrafe(){
        fl = fl + strafe;
        fr = fr - strafe;
        bl = bl - strafe;
        br = br + strafe;
        updateDrive();
    }

    void setDirection(){
        fr = fr + direction;
        fl = fl + direction;
        bl = bl + direction;
        br = br + direction;
        updateDrive();
    }

    void stopDrive() {
        backLeft.setPower(0);
        backRight.setPower(0);
        frontLeft.setPower(0);
        frontRight.setPower(0);
    }
    static VectorF getEuler(OpenGLMatrix pose) {
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
