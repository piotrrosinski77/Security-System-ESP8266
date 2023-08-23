int sensor = D1;             	// the pin that the sensor is atteched to
// int buzzer = D5;             // the pin that the buzzer is attached to 
int state = LOW;             	// by default, no motion detected
int val = 0;                	// variable to store the sensor status (value)

void setup() {
  pinMode(led, OUTPUT);      	// initalize LED as an output
  pinMode(sensor, INPUT);    	// initialize sensor as an input
  // pinMode(buzzer, OUTPUT);	// initialize buzzer as an output
  Serial.begin(9600);        	// initialize serial
}

void loop(){
  val = digitalRead(sensor);   		// read sensor value
  if (val == HIGH) {           		// check if the sensor is HIGH
    digitalWrite(led, HIGH);   		// turn LED ON
    // digitalWrite(buzzer, HIGH); 	//turn buzzer ON
    delay(100);                		// delay 100 milliseconds 
    
    if (state == LOW) {
      Serial.println("Motion detected!"); 
      state = HIGH;       			// update variable state to HIGH
    }
  } 
  else {
      digitalWrite(led, LOW); 			// turn LED OFF
      // digitalWrite(buzzer, LOW); 	//turn buzzer OFF
      delay(200);             			// delay 200 milliseconds 
      
      if (state == HIGH){
        Serial.println("Motion stopped!");
        Serial.println("");
        state = LOW;       				// update variable state to LOW
    }
  }
}