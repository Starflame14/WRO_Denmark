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

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;


/**
 * This file contains an minimal example of a Linear "OpMode". An OpMode is a 'program' that runs in either
 * the autonomous or the teleop period of an FTC match. The names of OpModes appear on the menu
 * of the FTC Driver Station. When an selection is made from the menu, the corresponding OpMode
 * class is instantiated on the Robot Controller and executed.
 * <p>
 * This particular OpMode just executes a basic Tank Drive Teleop for a two wheeled robot
 * It includes all the skeletal structure that all linear OpModes contain.
 * <p>
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */

@TeleOp(name = "Basic: Linear OpMode", group = "Linear Opmode")

public class MyMain extends LinearOpMode {

    private static String message = "";
    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();

    static int count = 0;

    private Arm arm;

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();


        int qos = 2;
        String broker = "tcp://192.168.49.74:1883";
        String clientId = "JavaSample";
        MemoryPersistence persistence = new MemoryPersistence();

        try {
            MqttClient sampleClient = new MqttClient(broker, clientId, persistence);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            telemetry.addLine("Connecting to broker: " + broker);

            sampleClient.connect(connOpts);

            sampleClient.subscribe("asd");
            sampleClient.publish("cntd", new MqttMessage("true".getBytes()));

            sampleClient.setCallback(new MqttCallback() {
                @Override
                public void connectionLost(Throwable cause) {
                    telemetry.addLine("connection lost");
                }

                @Override
                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    if (topic.equals("warehouse/take-in/")) {
                        String msg = message.toString();
                        int x = Integer.parseInt(msg.split(" ")[0]);
                        int y = Integer.parseInt(msg.split(" ")[1]);
                        MyMain.message = x + " " + y;
                    }
                    telemetry.addLine("topic: " + topic + " message: " + message);
                    telemetry.update();
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {

                }
            });
        } catch (MqttException me) {
            telemetry.addLine("reason " + me.getReasonCode());
            telemetry.addLine("msg " + me.getMessage());
            telemetry.addLine("loc " + me.getLocalizedMessage());
            telemetry.addLine("cause " + me.getCause());
            telemetry.addLine("excep " + me);
            telemetry.update();
            me.printStackTrace();
        }


        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        runtime.reset();
/*
        Servo armServo = hardwareMap.get(Servo.class, "arm");
        Servo remoteControlServo = hardwareMap.get(Servo.class, "remoteControlServo");
        DcMotor xMoveMotor = hardwareMap.get(DcMotor.class, "xMoveMotor");
        DcMotor yMoveMotor1 = hardwareMap.get(DcMotor.class, "yMoveMotor1");
        DcMotor yMoveMotor2 = hardwareMap.get(DcMotor.class, "yMoveMotor2");
        DcMotor elevatorMotor = hardwareMap.get(DcMotor.class, "elevatorMotor");

        arm = new Arm(armServo, remoteControlServo, xMoveMotor, yMoveMotor1, yMoveMotor2, elevatorMotor);
*/

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            telemetry.addData("mess", MyMain.message);
        }
    }
}
