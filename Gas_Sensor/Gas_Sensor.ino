#define sensor A0			//pin, do którego dołączony jest czujnik
#define buzzer D5         //pin, do którego dołączony jest brzęczyk
#define led D8			//pin, do którego dołączony jest LED

int value = 0; 					//wartość początkowa stanu czujnika

void setup() {
	pinMode(sensor, INPUT);		//ustaw czujnik jako wejście
	pinMode(led, OUTPUT);      	//ustaw LED jako wyjście 
	pinMode(buzzer, OUTPUT);	//ustaw brzęczyk jako wyjście
    Serial.begin(9600); 		//otwórz transmisję szeregową
}

void loop() {
    value = analogRead(sensor); 
    Serial.println("Ilość CO2 (w PPM): ");
    Serial.println(value);
	
	if (value > 15000) {				  //jeżeli wartość większa niż 500 PPM CO2
		digitalWrite(led, HIGH);   	  //włącz LED
		digitalWrite(buzzer, HIGH);   //włącz brzęczyk
		delay(100);                   //opóźnij 100 ms
	}
	
	else {
		digitalWrite(led, LOW);   	  	//wyłącz LED
		digitalWrite(buzzer, LOW);   	//wyłącz brzęczyk
		delay(100);           			//opóźnij 100 ms
  }

	delay(1000);				  		//opóźnij 1000 ms
} 