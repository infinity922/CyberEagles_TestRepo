package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by ethan on 2018-03-23.
 */




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
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

    /**
     * Created by Thursday on 2/8/2018.
     */
    @Autonomous
    public class Bleu extends LinearOpMode {
        HydeHardware r = new HydeHardware();
        private double extra, orbit, direction, strafe;
        private double fl,fr,bl,br;
        private ElapsedTime runtime = new ElapsedTime();

        private int initBlue, initRed, colorMax = 128, blueaverage, redaverage;

        @Override
        public void runOpMode() throws InterruptedException{
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
        */
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
        /*currently using arbitrary numbers for 'extra'
        if (vuMark == RelicRecoveryVuMark.CENTER)extra = .2;
        if (vuMark == RelicRecoveryVuMark.LEFT)extra=0;
        if (vuMark == RelicRecoveryVuMark.RIGHT)extra=.4;
        */
            runtime.reset();
            //all command code here

            //doJewel();
            setDrive(0,0,-1,1.4);
            setDrive(0,-1,0,1.2);
            WheelsOn();
            setDrive(0,-.25,-1,2);

            WheelsOff();
            setDrive(0,0,1,3);
            DumpGlyphs();
            idle();

        }
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

        private void WheelsOn(){
            r.rightWheel.setPower(1);
            r.leftWheel.setPower(1);
        }
        private void WheelsOff(){
            r.rightWheel.setPower(0);
            r.leftWheel.setPower(0);
        }
        private void setHeading(float targetHeading){
            //check which way to rotate will be faster
            //set orbit that way, then do while loop until heading is reached.
            //also init angles...
            while (opModeIsActive()&& Math.abs(currentHeading) < targetHeading){
                //check heading
            }
        }
        /**private void doJewel(){
         double revDirection=0;
         jewel.setPosition(0);

         //detect jewel color average
         for(int i = 0; i < 5; i++) {
         if ((csensor.blue() * colorMax ) / initBlue > (csensor.red() * colorMax) / initRed) {
         blueaverage++;
         }
         else if ((csensor.red() * colorMax ) / initRed > (csensor.blue() * colorMax) / initBlue) {
         redaverage++;
         }
         idle();
         }

         //carry out according action
         if (redaverage>blueaverage){

         revDirection = -1;
         telemetry.addData("Jewel Status: ", "red");
         }
         else if (blueaverage>redaverage) {

         revDirection = 1;
         telemetry.addData("Jewel Status: ", "blue");
         }
         else {
         telemetry.addData("Jewel Status: ", "not determined");
         }
         telemetry.update();

         jewel.setPosition(1);

         //return to initial position
         setDrive(0,0,revDirection,.5);

         }

         */
