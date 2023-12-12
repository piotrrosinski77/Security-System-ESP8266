#include <ESP8266WiFi.h>
#include <ESP8266HTTPClient.h>
#include <WiFiClient.h>

const char* ssid = "NETIASPOT-2.4GHz-5D9Q";
const char* password = "eszewerie95";

unsigned long lastTime = 0;
unsigned long timerDelay = 5000;

int value;					//wartość początkowa stanu czujnika

#define sensor D1			//pin, do którego dołączony jest czujnik
#define led D8			  //pin, do którego dołączony jest LED

void setup() {
	pinMode(sensor, INPUT);		  //ustaw czujnik jako wejście
	pinMode(led, OUTPUT);      	//ustaw LED jako wyjście 
	Serial.begin(115200); 		    //otwórz transmisję szeregową
	
	WiFi.begin(ssid, password);
	Serial.println("Connecting");
	
}

void loop() {
  if (digitalRead(sensor) == HIGH)
  //Outputs low level when there is sound
  {
    Serial.println("Nie wykryto hałasu.");
    Serial.println("");
	digitalWrite(led, LOW);   			  //wyłącz LED
	value = 0;
	Serial.println(value);
    delay(1000); 						          //opóźnij 1000 ms
	
  }
  
  else 
  {
	Serial.println("Wykryto hałas.");
    Serial.println("");
	digitalWrite(led, HIGH);   			  //włącz LED
	value = 1;
	Serial.println(value);
    delay(3500);							        //opóźnij 1000 ms
	
  }
  
  if ((millis() - lastTime) > timerDelay) {
    
    if(WiFi.status()== WL_CONNECTED){

      HTTPClient http;
      WiFiClientSecure client;
	    client.setInsecure();

      http.begin(client,"https://security-system-api-260273149601.herokuapp.com/sound");
  
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
