
/*
const SerialPort=require('serialport');
        const Readline=require('@serialport/parser-readline');


public class SensorArray {
    final int midPoint = 320;

    public SensorArray(String portName, int sensorLength) {

    }

    public sensorData:Observable<number[]>;

    static lastSensorData:number[];

    constructor(public readonly portName:string, public readonly sensorLength:number) {

        const USBSerialPort = new Readline();
        const port = new SerialPort(portName, {baudRate:115200});

        port.pipe(USBSerialPort);

        this.sensorData = fromEvent(USBSerialPort, "data")
                .pipe(
                        map((line:string) =>{
            return line.slice(0, line.length - 1).split(",").map((data) = > Number(data.trim()));
        }),
        filter((sensorData:number[]) =>sensorData.length == = sensorLength)
            );

        this.sensorData.subscribe(value = > SensorArray.lastSensorData = value);
    }

    static isBlack(number:number) {
        return number > midPoint;
    }
}
}

 */
