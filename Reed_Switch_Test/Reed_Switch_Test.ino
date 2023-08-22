const int REED_PIN = D2;	// Pin connected to reed switch

void setup() {
	Serial.begin(9600); // Start serial
	pinMode(REED_PIN, INPUT_PULLUP);	// Enable internal pull-up for the reed switch
}

void loop() {
  if (digitalRead(REED_PIN) == HIGH) {
    Serial.println("Door opened.");
    Serial.println("");
    delay(100); // 100 ms delay
  }

	else {
		Serial.println("Door closed.");
    Serial.println("");
    delay(100);
	}
}