#include <ESP8266WiFi.h>
#include <ESP8266HTTPClient.h>
#include <WiFiClient.h>

const char* ssid = "NETIASPOT-2.4GHz-5D9Q";
const char* password = "eszewerie95";

unsigned long lastTime = 0;
unsigned long timerDelay = 5000;

int value; 					//wartość początkowa stanu czujnika

#define sensor D1			//pin, do którego dołączony jest czujnik
#define buzzer D5   //pin, do którego dołączony jest brzęczyk
#define led D8			//pin, do którego dołączony jest LED

void setup() {
	pinMode(sensor, INPUT_PULLUP);	//włącz wewnętrzny pull-up dla kontaktronu
	pinMode(led, OUTPUT);      		//ustaw LED jako wyjście 
	pinMode(buzzer, OUTPUT);		//ustaw brzęczyk jako wyjście
	Serial.begin(115200); 			//otwórz transmisję szeregową
	
	WiFi.begin(ssid, password);
	Serial.println("Connecting");
}

void loop() {

	if (digitalRead(sensor) == HIGH) {
    Serial.println("Otworzono drzwi/okno.");
    Serial.println("");
	  digitalWrite(led, HIGH);   			//włącz LED
    digitalWrite(buzzer, HIGH); 		//włącz brzęczyk
	  value = 1;
    Serial.println(value);
    delay(1000); 					
  }

  else 
  {
		Serial.println("Zamknięto drzwi/okno.");
		Serial.println("");
		digitalWrite(led, LOW);   		//wyłącz LED
		digitalWrite(buzzer, LOW); 		//wyłącz brzęczyk
		value = 0;
		delay(1000);
	}

	if ((millis() - lastTime) > timerDelay) {
    
    if(WiFi.status()== WL_CONNECTED){

      HTTPClient http;
      WiFiClientSecure client;
	    client.setInsecure();

      http.begin(client,"https://security-system-api-260273149601.herokuapp.com/reedswitch");
  
      http.addHeader("Content-Type", "application/x-www-form-urlencoded");
      
      String httpRequestData = "value=" + String(value);           
      int httpResponseCode = http.POST(httpRequestData);
      String payload = http.getString();
      Serial.print("HTTP Response code: ");
      Serial.println(httpResponseCode);
      Serial.println(payload);
    
      http.end();
    }
    else {
      Serial.println("WiFi Disconnected");
    }
    lastTime = millis();
  }
}