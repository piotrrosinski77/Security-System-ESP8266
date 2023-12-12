#include <ESP8266WiFi.h>
#include <ESP8266HTTPClient.h>
#include <WiFiClient.h>

const char* ssid = "NETIASPOT-2.4GHz-5D9Q";
const char* password = "eszewerie95";

unsigned long lastTime = 0;
unsigned long timerDelay = 5000;

int value = 0; 					//wartość początkowa stanu czujnika

#define sensor A0       //pin, do którego dołączony jest czujnik
#define power D1 				//pin zasilający czujnik 
#define buzzer D5       //pin, do którego dołączony jest brzęczyk
#define led D8				  //pin, do którego dołączony jest LED

void setup() {
	pinMode(sensor, INPUT);		  //ustaw czujnik jako wejście
	pinMode(power, OUTPUT);   	//ustaw pin zasilający jako wyjście
	pinMode(led, OUTPUT);      	//ustaw LED jako wyjście 
	pinMode(buzzer, OUTPUT);	  //ustaw brzęczyk jako wyjście
	digitalWrite(power, LOW); 	//wyłącz czujnik
	Serial.begin(115200);

	WiFi.begin(ssid, password);
	Serial.println("Connecting");
	
	while(WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
	}
	Serial.println("");
	Serial.print("Connected to WiFi network with IP Address: ");
	Serial.println(WiFi.localIP());
}

void loop() {
	digitalWrite(power, HIGH);    //włącz czujnik
	delay(10);                    //opóźnij 10 ms
	value = analogRead(sensor);   //odczytaj analogową wartość z czujnika
	digitalWrite(power, LOW);     //wyłącz czujnik
	
	Serial.print("Ilość wody: "); //wypisz w transmisji szeregowej
	Serial.println(value);		    //wypisz wartość czujnika
	Serial.println("");			      //wypisz linijkę odstępu
	
	if (value > 400) {				    //jeżeli wartość większa niż 400 jednostek wody
		digitalWrite(led, HIGH);   	//włącz LED
		digitalWrite(buzzer, HIGH); //włącz brzęczyk
		delay(100);                 //opóźnij 100 ms
	}
	
	else {
		digitalWrite(led, LOW);   	//wyłącz LED
		digitalWrite(buzzer, LOW);  //wyłącz brzęczyk
		delay(100);           			//opóźnij 100 ms
  }

  delay(1000);
	
	if ((millis() - lastTime) > timerDelay) {
    
    if(WiFi.status()== WL_CONNECTED){

      HTTPClient http;
      WiFiClientSecure client;
	    client.setInsecure();

      http.begin(client,"https://security-system-api-260273149601.herokuapp.com/water");
  
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