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
public class BlueLeft extends LinearOpMode {
    private DcMotor frontLeft;
    private DcMotor frontRight;
    private DcMotor backLeft;
    private DcMotor backRight;
    private DcMotor grabber, armIn, armOut = null;
    private Servo gripper, rOver, rHold, jewel = null;
    //private ColorSensor colorSensor = null;
    private double fl;
    private double fr;
    private double bl;
    private double br;
    private double orbit = 0, strafe = 0, direction = 0;
    private DriveUsingImage driver;
    private boolean hasFound = false;

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

        gripper = hardwareMap.get(Servo.class, "gripper");
        rOver = hardwareMap.servo.get("rOver");
        rHold = hardwareMap.servo.get("rHold");
        jewel = hardwareMap.servo.get("jewel");
        //colorSensor = hardwareMap.colorSensor.get("colorSensor");
        driver = new DriveUsingImage(frontLeft, frontRight, backLeft, backRight, this);

        //if (colorSensor instanceof SwitchableLight) {
        //    ((SwitchableLight) colorSensor).enableLight(true);}

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
        telemetry.addData("started", "true");
        telemetry.update();
        relicTrackables.activate();
        telemetry.addData("activated", true);
        telemetry.update();
        RelicRecoveryVuMark vuMark = RelicRecoveryVuMark.from(relicTrackable);
        telemetry.addData("1",true);
        telemetry.update();
        OpenGLMatrix pose = ((VuforiaTrackableDefaultListener) relicTrackable.getListener()).getPose();
        telemetry.addData("2", true);
        telemetry.update();
        strafe = .33;
        telemetry.addData("3",true);
        telemetry.update();
        setDrive();
        telemetry.addData("3",true);
        telemetry.update();

        while (opModeIsActive() && pose!=null){
            pose = ((VuforiaTrackableDefaultListener) relicTrackable.getListener()).getPose();

        }
        telemetry.addData("driver called", true);
        telemetry.update();
        driver.driveTo(0, 400, 27.5, vuforia,relicTrackables,relicTrackable);

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


}
