#define SensorPin A0

void setup() {
    Serial.begin(9600); // initialize serial communication
}

void loop() {
    int sensorValue = analogRead(SensorPin); 
    Serial.println("The amount of CO2 (in PPM): ");
    Serial.println(sensorValue);
    delay(2000);
} 