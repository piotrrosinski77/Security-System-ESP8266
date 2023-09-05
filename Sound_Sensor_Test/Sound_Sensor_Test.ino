#define sensorPin D0 // Digital input pin that the Sensor is attached to

void setup() {
   pinMode(sensorPin, INPUT);
   Serial.begin(9600); // Initialize serial communication

}

void loop() {
  int sensorValue = digitalRead(sensorPin);
  if (digitalRead(sensorPin) == HIGH) 
  {
    Serial.println("Sound level is not exceeded.");
    Serial.println("");
    delay(100); 						// 100 ms delay
  }

	else 
	{
	  Serial.println("Sound level is exceeded");
    Serial.println("");
    delay(100);
	}

}
