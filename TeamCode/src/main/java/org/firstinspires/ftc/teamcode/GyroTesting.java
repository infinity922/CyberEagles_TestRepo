package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Func;
import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

import java.util.Locale;

/**
 * Created by ethan on 2018-03-17.
 */
public class GyroTesting extends LinearOpMode {
    HydeHardware r = new HydeHardware();
    Orientation angles,angles2;
    Acceleration acceleration;
    float heading,heading1, heading2;
    double fr,br,bl,fl;
    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() throws InterruptedException {
        r.init(hardwareMap);
        int checks = 0;


        //get an average of these later

        waitForStart();
        //setDrive(0,1,0);
        while (opModeIsActive()){
            telemetry.clearAll();
            checks++;
            composeTelemetry();

            angles = r.imu1.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
            angles2 = r.imu3.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
            heading1 = AngleUnit.DEGREES.fromUnit(angles.angleUnit, angles.firstAngle);
            heading2 = AngleUnit.DEGREES.fromUnit(angles2.angleUnit, angles2.firstAngle);
            heading = (heading1+heading2) /2;
            telemetry.addData("heading1:", heading1);
            telemetry.addData("Heading2: ", heading2);
            telemetry.update();
            telemetry.addData("checks", checks);
            telemetry.update();
            //work with a <-10 amount b/c of latency**dependent on processing.
            if (heading>80 || heading<-80)setDrive(0,0,0);
        }
        telemetry.addData("exited", true);
        telemetry.update();
        idle();
    }

    void composeTelemetry() {

// At the beginning of each telemetry update, grab a bunch of data
// from the IMU that we will then display in separate lines.
        telemetry.addAction(new Runnable() { @Override public void run()
        {
// Acquiring the angles is relatively expensive; we don't want
// to do that in each of the three items that need that info, as that's
// three times the necessary expense.
            angles = r.imu1.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
            angles2 = r.imu3.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
            acceleration = r.imu1.getGravity();
        }
        });

        telemetry.addLine()
                .addData("status", new Func<String>() {
                    @Override public String value() {
                        return r.imu1.getSystemStatus().toShortString();
                    }
                })
                .addData("calib", new Func<String>() {
                    @Override public String value() {
                        return r.imu1.getCalibrationStatus().toString();
                    }
                });

        telemetry.addLine()
                .addData("heading", new Func<String>() {
                    @Override public String value() {
                        return formatAngle(angles.angleUnit, angles.firstAngle);
                    }
                })
                .addData("roll", new Func<String>() {
                    @Override public String value() {
                        return formatAngle(angles.angleUnit, angles.secondAngle);
                    }
                })
                .addData("pitch", new Func<String>() {
                    @Override public String value() {
                        return formatAngle(angles.angleUnit, angles.thirdAngle);
                    }
                });

        telemetry.addLine()
                .addData("grvty", new Func<String>() {
                    @Override public String value() {
                        return acceleration.toString();
                    }
                })
                .addData("mag", new Func<String>() {
                    @Override public String value() {
                        return String.format(Locale.getDefault(), "%.3f",
                                Math.sqrt(acceleration.xAccel*acceleration.xAccel
                                        + acceleration.yAccel*acceleration.yAccel
                                        + acceleration.zAccel*acceleration.zAccel));
                    }
                });
    }
    private void setDrive(double strafe, double orbit, double direction){
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
            r.backLeft.setPower(bl / 3);
            r.backRight.setPower(br / 3);
            r.frontLeft.setPower(fl / 3);
            r.frontRight.setPower(fr / 3);
    }

//----------------------------------------------------------------------------------------------
// Formatting
//----------------------------------------------------------------------------------------------

    String formatAngle(AngleUnit angleUnit, double angle) {
        return formatDegrees(AngleUnit.DEGREES.fromUnit(angleUnit, angle));
    }

    String formatDegrees(double degrees){
        return String.format(Locale.getDefault(), "%.1f", AngleUnit.DEGREES.normalize(degrees));
    }

}
