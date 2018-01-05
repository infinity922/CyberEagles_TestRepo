/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

/**
 * This file contains an example of an iterative (Non-Linear) "OpMode".
 * An OpMode is a 'program' that runs in either the autonomous or the teleop period of an FTC match.
 * The names of OpModes appear on the menu of the FTC Driver Station.
 * When an selection is made from the menu, the corresponding OpMode
 * class is instantiated on the Robot Controller and executed.
 *
 * This particular OpMode just executes a basic Tank Drive Teleop for a two wheeled robot
 * It includes all the skeletal structure that all iterative OpModes contain.
 *
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */

@TeleOp(name="Basic: Iterative OpMode", group="Iterative Opmode")
public class BasicOpMode_Iterative extends OpMode
{
    // Declare OpMode members.
    private DcMotor leftDrive = null;
    private DcMotor rightDrive = null;
    private DcMotor leftArm, rightArm = null;
    private Servo left, right = null;
    private DcMotor tipper = null;
    private Servo hugger = null;
    private DcMotor slider = null;

    /*
     * Code to run ONCE when the driver hits INIT
     */
    @Override
    public void init() {

        leftDrive  = hardwareMap.get(DcMotor.class, "leftDrive");
        rightDrive = hardwareMap.get(DcMotor.class, "rightDrive");
        leftArm = hardwareMap.dcMotor.get("leftArm");
        rightArm = hardwareMap.dcMotor.get("rightArm");
        left = hardwareMap.servo.get("left");
        right = hardwareMap.servo.get("right");
        tipper = hardwareMap.dcMotor.get("tipper");
        hugger = hardwareMap.servo.get ("hugger");
        slider = hardwareMap.dcMotor.get("slider");

        // Most robots need the motor on one side to be reversed to drive forward
        // Reverse the motor that runs backwards when connected directly to the battery
        leftDrive.setDirection(DcMotor.Direction.FORWARD);
        rightDrive.setDirection(DcMotor.Direction.REVERSE);
        leftArm.setDirection(DcMotor.Direction.FORWARD);
        rightArm.setDirection(DcMotor.Direction.REVERSE);

        left.setPosition(.5);
        right.setPosition(.5);
        hugger.setPosition(.5);

    }

    @Override
    public void init_loop() {
    }


    @Override
    public void start() {    }

    @Override
    public void loop() {
        leftDrive.setPower(-gamepad1.left_stick_y+gamepad1.left_stick_x);
        rightDrive.setPower(-gamepad1.right_stick_y - gamepad1.right_stick_x);
        leftArm.setPower(-gamepad2.left_trigger);
        rightArm.setPower(gamepad2.right_trigger);
        tipper.setPower(gamepad2.left_stick_y/2);
        slider.setPower(gamepad2.right_stick_x);

        if (gamepad2.a){
            //check the position individually so each servo can be manipulated individually.
            //Can also set position limits
            if (right.getPosition() !=1){
                double rightGrabPosition = right.getPosition() +.02;
                right.setPosition(rightGrabPosition);
            }}
            if (gamepad2.x){
            if (right.getPosition() !=0){
                double rightGrabPos = right.getPosition() -.02;
                right.setPosition(rightGrabPos);
            }
        }

        if (gamepad2.b){
            //check the position individually so each servo can be manipulated individually.
            //Can also set position limits
            if (left.getPosition() !=1){
                double leftGrabPosition = left.getPosition() +.02;
                left.setPosition(leftGrabPosition);
            }}
            if (gamepad2.y){
            if (right.getPosition() !=0){
                double rightGrabPos = right.getPosition() -.02;
                right.setPosition(rightGrabPos);
            }
        }
        if (gamepad1.a){
            //check the position individually so each servo can be manipulated individually.
            //Can also set position limits
            if (hugger.getPosition() !=1){
                double huggerPosition = hugger.getPosition() +.02;
                hugger.setPosition(huggerPosition);
            }}
        if (gamepad1.b){
            if (hugger.getPosition() !=0){
                double huggerPos = hugger.getPosition() -.02;
                hugger.setPosition(huggerPos);
            }
        }
    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
        leftDrive.setPower(0);
        rightDrive.setPower(0);
        leftArm.setPower(0);
        rightArm.setPower(0);
    }

}

