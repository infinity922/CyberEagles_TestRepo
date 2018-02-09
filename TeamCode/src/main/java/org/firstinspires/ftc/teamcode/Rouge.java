package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.vuforia.HINT;
import com.vuforia.Vuforia;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

/**
 * Created by Thursday on 2/8/2018.
 */
@Autonomous
public class Rouge extends LinearOpMode {
    private DcMotor fL, fR, bR, bL;
    private Servo jewel;
    private double fr, fl, br, bl, extra, orbit, direction, strafe;
    private ElapsedTime runtime = new ElapsedTime();
    private ColorSensor csensor;

    @Override
    public void runOpMode() throws InterruptedException{
        //init code
        fR = hardwareMap.dcMotor.get("fR");
        fL = hardwareMap.dcMotor.get("fL");
        bR = hardwareMap.dcMotor.get("bR");
        bL = hardwareMap.dcMotor.get("bL");
        csensor = hardwareMap.colorSensor.get("csensor");

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

        //currently using arbitrary numbers for 'extra'
        if (vuMark == RelicRecoveryVuMark.CENTER)extra = .2;
        if (vuMark == RelicRecoveryVuMark.LEFT)extra=0;
        if (vuMark == RelicRecoveryVuMark.RIGHT)extra=.4;
        telemetry.addData("vuMark", vuMark);
        telemetry.update();

        waitForStart();
        runtime.reset();
        //all command code here

        //how to check if the jewel is red or blue --- then take the appropriate action.
        if (csensor.red()>csensor.blue())setDrive(0,0,1,.5);
        else if (csensor.blue()>csensor.red())setDrive(0,0,-1,.5);

    }
    private void setDrive(double strafe, double orbit, double direction, double time){
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
        while (time<runtime.seconds()) {
            bL.setPower(bl / 4);
            bR.setPower(br / 4);
            fL.setPower(fl / 4);
            fR.setPower(fr / 4);
        }
        runtime.reset();
        fR.setPower(0);
        fL.setPower(0);
        bL.setPower(0);
        bR.setPower(0);
    }
}
