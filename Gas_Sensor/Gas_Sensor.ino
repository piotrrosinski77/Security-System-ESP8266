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
	
	if (value > 15000) {				  //jeżeli wartość większa niż 15000 PPM CO2
  //Maksymalne stężenie w specyficznych warunkach pracy np. łodzie podwodne
    Serial.println("Przekroczono próg 15000 PPM CO2");
		digitalWrite(led, HIGH);   	  //włącz LED
		digitalWrite(buzzer, HIGH);   //włącz brzęczyk
		delay(100);                   //opóźnij 100 ms
	}

  else if (value > 1000) {				  //jeżeli wartość większa niż 1000 PPM CO2
  //Górny próg w pomieszczeniach stałego przebywania ludzi według WHO
    Serial.println("Przekroczono próg 1000 PPM CO2");
		digitalWrite(led, HIGH);   	  //włącz LED
		digitalWrite(buzzer, HIGH);   //włącz brzęczyk
		delay(100);                   //opóźnij 100 ms
	}
	
	else {
    Serial.println("Stężenie CO2 OK");
		digitalWrite(led, LOW);   	  	//wyłącz LED
		digitalWrite(buzzer, LOW);   	//wyłącz brzęczyk
		delay(100);           			//opóźnij 100 ms
  }

	delay(1000);				  		//opóźnij 1000 ms
} 