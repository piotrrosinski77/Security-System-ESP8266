#define sensor A0             //pin, do którego dołączony jest czujnik
#define power D1 				//pin zasilający czujnik 
#define buzzer D5             //pin, do którego dołączony jest brzęczyk
#define led D8				//pin, do którego dołączony jest LED

int value = 0; 					//wartość początkowa stanu czujnika

void setup() {
	pinMode(sensor, INPUT);		//ustaw czujnik jako wejście
	pinMode(power, OUTPUT);   	//ustaw pin zasilający jako wyjście
	pinMode(led, OUTPUT);      	//ustaw LED jako wyjście 
	pinMode(buzzer, OUTPUT);	//ustaw brzęczyk jako wyjście
	digitalWrite(power, LOW); 	//wyłącz czujnik
	Serial.begin(9600);			//otwórz transmisję szeregową
}

void loop() {
	digitalWrite(power, HIGH);    //włącz czujnik
	delay(10);                    //opóźnij 10 ms
	value = analogRead(sensor);   //odczytaj analogową wartość z czujnika
	digitalWrite(power, LOW);     //wyłącz czujnik
	
	Serial.print("Ilość wody: "); //wypisz w transmisji szeregowej
	Serial.println(value);		  //wypisz wartość czujnika
	Serial.println("");			  //wypisz linijkę odstępu
	
	if (value > 400) {				  //jeżeli wartość większa niż 400 jednostek wody
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