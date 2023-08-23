const int REED_PIN = D2;	// pin connected to reed switch

void setup() {
	Serial.begin(9600);
	pinMode(REED_PIN, INPUT_PULLUP);	// enable internal pull-up for the reed switch
}

void loop() {
  if (digitalRead(REED_PIN) == HIGH) 
  {
    Serial.println("Door opened.");
    Serial.println("");
    delay(100); 						// 100 ms delay
  }

	else 
	{
	Serial.println("Door closed.");
    Serial.println("");
    delay(100);
	}
}