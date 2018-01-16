package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.SwitchableLight;
import com.vuforia.HINT;
import com.vuforia.Vuforia;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

/**
 * Created by Ethan McGhan on 2018-01-13.
 */
@Autonomous
public class BlueLeft extends LinearOpMode {
    private DcMotor frontLeft;
    private DcMotor frontRight;
    private DcMotor backLeft;
    private DcMotor backRight;
    private DcMotor grabber, armIn, armOut = null;
    private Servo gripper, rOver, rHold, jewel = null;
    private ColorSensor colorSensor = null;
    private double fl;
    private double fr;
    private double bl;
    private double br;
    private DriveUsingImage driver;

    @Override
    public void runOpMode() throws InterruptedException {

        armOut = hardwareMap.get(DcMotor.class, "armOut");
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        frontRight = hardwareMap.dcMotor.get("frontRight");
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        backRight = hardwareMap.get(DcMotor.class, "backRight");
        armIn = hardwareMap.get(DcMotor.class, "armIn");
        grabber = hardwareMap.get(DcMotor.class, "grabber");

        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        gripper= hardwareMap.get(Servo.class, "gripper");
        rOver = hardwareMap.servo.get("rOver");
        rHold = hardwareMap.servo.get("rHold");
        jewel = hardwareMap.servo.get("jewel");
        colorSensor = hardwareMap.colorSensor.get("colorSensor");
        driver = new DriveUsingImage(frontLeft, frontRight, backLeft, backRight);

        if (colorSensor instanceof SwitchableLight) {
            ((SwitchableLight)colorSensor).enableLight(true);
        }

        //create vuforia params
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

        waitForStart();
        relicTrackables.activate();
        RelicRecoveryVuMark vuMark = RelicRecoveryVuMark.from(relicTrackable);
        driver.driveTo(100,40000, 27.5, relicTrackable);


        /* = .2;
        fl = .2;
        bl = .2;
        br = .2;
        updateDrive();
        sleep(2000);
        stopDrive();

        boolean found = false;
        fr = -.2;
        fl = .2;
        br = -.2;
        bl = .2;
        updateDrive();*/

        boolean hasSeen = false;
        boolean step1 = false;

        float xTrans;
        float yTrans;
        float heading = 99999;

        while (opModeIsActive()) {

            OpenGLMatrix pose = ((VuforiaTrackableDefaultListener) relicTrackable.getListener()).getPose();
            boolean isVisible = pose != null;

            if (!hasSeen && isVisible) {
                hasSeen = true;
            }
            else if (hasSeen && !isVisible) {
                break;
            }

            /*if (hasSeen && !step1){
                sleep(200);
                stopDrive();
                step1 = true;
            }*/

            if (isVisible) {
                heading = getEuler(pose).get(1);

            }
            telemetry.addData("trackable", vuMark);telemetry.update();
            telemetry.addData("trans", pose != null ? pose.getTranslation() : "INVISIBLE");
            telemetry.addData("angle", heading);

        }



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
