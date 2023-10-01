#define sensor D1			//pin, do którego dołączony jest czujnik
#define buzzer D5     //pin, do którego dołączony jest brzęczyk
#define led D8			  //pin, do którego dołączony jest LED

void setup() {
	pinMode(sensor, INPUT);		  //ustaw czujnik jako wejście
	pinMode(led, OUTPUT);      	//ustaw LED jako wyjście 
	pinMode(buzzer, OUTPUT);	  //ustaw brzęczyk jako wyjście
	Serial.begin(9600); 		    //otwórz transmisję szeregową
}

void loop() {
  if (digitalRead(sensor) == HIGH)
  //Outputs low level when there is sound
  {
    Serial.println("Nie wykryto hałasu.");
    Serial.println("");
	  digitalWrite(led, LOW);   			  //wyłącz LED
    digitalWrite(buzzer, LOW); 			  //wyłącz brzęczyk
    delay(100); 						          //opóźnij 1000 ms
  }
  
  else 
  {
	  Serial.println("Wykryto hałas.");
    Serial.println("");
	  digitalWrite(led, HIGH);   			  //włącz LED
    digitalWrite(buzzer, HIGH); 		  //włącz brzęczyk
    delay(1000);							        //opóźnij 1000 ms
  }
  
}
