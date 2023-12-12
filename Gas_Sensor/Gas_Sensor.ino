#include <ESP8266WiFi.h>
#include <ESP8266HTTPClient.h>
#include <WiFiClient.h>

const char* ssid = "NETIASPOT-2.4GHz-5D9Q";
const char* password = "eszewerie95";

unsigned long lastTime = 0;
unsigned long timerDelay = 5000;

#define sensor A0			//pin, do którego dołączony jest czujnik
#define buzzer D5         //pin, do którego dołączony jest brzęczyk
#define led D8			//pin, do którego dołączony jest LED

int value; 					//wartość początkowa stanu czujnika

void setup() {
	pinMode(sensor, INPUT);		//ustaw czujnik jako wejście
	pinMode(led, OUTPUT);      	//ustaw LED jako wyjście 
	pinMode(buzzer, OUTPUT);	//ustaw brzęczyk jako wyjście
    Serial.begin(115200);        	//otwórz transmisję szeregową

    WiFi.begin(ssid, password);
	Serial.println("Connecting");
}

void loop() {
    value = analogRead(sensor); 
    Serial.println("Ilość CO2 (w PPM): ");
    Serial.println(value);
	
	if (value > 1000) {				  //jeżeli wartość większa niż 1000 PPM CO2
	//Górny próg w pomieszczeniach stałego przebywania ludzi według WHO
    Serial.println("Przekroczono próg 1000 PPM CO2");
		digitalWrite(led, HIGH);   	  //włącz LED
		digitalWrite(buzzer, HIGH);   //włącz brzęczyk
		delay(3500);                   //opóźnij 100 ms
	}
	
	else {
    Serial.println("Stężenie CO2 OK");
		digitalWrite(led, LOW);   	  	//wyłącz LED
		digitalWrite(buzzer, LOW);   	//wyłącz brzęczyk
		delay(1000);           			//opóźnij 100 ms
  }

	if ((millis() - lastTime) > timerDelay) {
    
    if(WiFi.status()== WL_CONNECTED){

      HTTPClient http;
      WiFiClientSecure client;
	    client.setInsecure();

      http.begin(client,"https://security-system-api-260273149601.herokuapp.com/gas");
  
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