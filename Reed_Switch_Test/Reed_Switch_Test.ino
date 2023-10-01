#define sensor D1			//pin, do którego dołączony jest czujnik
#define buzzer D5   //pin, do którego dołączony jest brzęczyk
#define led D8			//pin, do którego dołączony jest LED

void setup() {
	Serial.begin(9600);				//otwórz transmisję szeregową
	pinMode(sensor, INPUT_PULLUP);	//włącz wewnętrzny pull-up dla kontaktronu
	pinMode(led, OUTPUT);      		//ustaw LED jako wyjście 
	pinMode(buzzer, OUTPUT);		//ustaw brzęczyk jako wyjście
	Serial.begin(9600); 			//otwórz transmisję szeregową
}

void loop() {
	if (digitalRead(sensor) == HIGH) {
    Serial.println("Otworzono drzwi/okno.");
    Serial.println("");
	digitalWrite(led, HIGH);   			//włącz LED
    digitalWrite(buzzer, HIGH); 		//włącz brzęczyk
    delay(1000); 					
  }

	else 
	{
		Serial.println("Zamknięto drzwi/okno.");
		Serial.println("");
		digitalWrite(led, LOW);   		//wyłącz LED
		digitalWrite(buzzer, LOW); 		//wyłącz brzęczyk
		delay(1000);
	}
}