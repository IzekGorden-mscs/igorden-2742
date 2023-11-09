/*
 Web client

 This sketch connects to a website (http://www.google.com)
 using an Arduino WIZnet Ethernet shield.

 Circuit:
 * Ethernet shield attached to pins 10, 11, 12, 13

 created 18 Dec 2009
 by David A. Mellis
 modified 9 Apr 2012
 by Tom Igoe, based on work by Adrian McEwen

 */

#include <SPI.h>
#include <Ethernet.h>

// Enter a MAC address for your controller below.
// Newer Ethernet shields have a MAC address printed on a sticker on the shield
byte mac[] = { 0xDE, 0xAD, 0xBE, 0xEF, 0xFE, 0xED };

// if you don't want to use DNS (and reduce your sketch size)
// use the numeric IP instead of the name for the server:
//IPAddress server(74,125,232,128);  // numeric IP for Google (no DNS)
char server[] = "www.google.com";    // name address for Google (using DNS)
int serverPort = 80;
char pageName[] = "/propertymonitor/api/sensorreadings";
// Declare http Post request data
char json[41];  // Must be large enough to hold entire JSON payload
#define SENSORID 4045
//--------------------------------------------

const int pingPin = 6; // Trigger Pin of Ultrasonic Sensor
const int echoPin = 5; // Echo Pin of Ultrasonic Sensor
#define FILTER_N 7

int distances[FILTER_N];
long prevDistance = 0;


// Set the static IP address to use if the DHCP fails to assign
IPAddress ip(192, 168, 86, 177);
IPAddress myDns(192, 168, 86, 1);

IPAddress serverIp(204, 77, 50, 53);

// Initialize the Ethernet client library
// with the IP address and port of the server
// that you want to connect to (port 80 is default for HTTP):
EthernetClient client;

// Variables to measure the speed
unsigned long beginMicros, endMicros;
unsigned long byteCount = 0;
bool printWebData = true;  // set to false for better speed measurement

void setup() {
  // You can use Ethernet.init(pin) to configure the CS pin
  //Ethernet.init(10);  // Most Arduino shields
  //Ethernet.init(5);   // MKR ETH Shield
  //Ethernet.init(0);   // Teensy 2.0
  //Ethernet.init(20);  // Teensy++ 2.0
  //Ethernet.init(15);  // ESP8266 with Adafruit FeatherWing Ethernet
  //Ethernet.init(33);  // ESP32 with Adafruit FeatherWing Ethernet

  // Open serial communications and wait for port to open:
  Serial.begin(115200);
  while (!Serial) {
    ; // wait for serial port to connect. Needed for native USB port only
  }

  // start the Ethernet connection:
  Serial.println("Initialize Ethernet with DHCP:");
  if (Ethernet.begin(mac) == 0) {
    Serial.println("Failed to configure Ethernet using DHCP");
    // Check for Ethernet hardware present
    if (Ethernet.hardwareStatus() == EthernetNoHardware) {
      Serial.println("Ethernet shield was not found.  Sorry, can't run without hardware. :(");
      while (true) {
        delay(1); // do nothing, no point running without Ethernet hardware
      }
    }
    if (Ethernet.linkStatus() == LinkOFF) {
      Serial.println("Ethernet cable is not connected.");
    }
    // try to configure using IP address instead of DHCP:
    Ethernet.begin(mac, ip, myDns);
  } else {
    Serial.print("  DHCP assigned IP ");
    Serial.println(Ethernet.localIP());
  }
  // give the Ethernet shield a second to initialize:
  delay(1000);
  Serial.print("connecting to ");
  Serial.print(server);
  Serial.println("...");

  // if you get a connection, report back via serial:
  if (client.connect(server, 80)) {
    Serial.print("connected to ");
    Serial.println(client.remoteIP());
    // Make a HTTP request:
    client.println("GET /search?q=arduino HTTP/1.1");
    client.println("Host: www.google.com");
    client.println("Connection: close");
    client.println();
  } else {
    // if you didn't get a connection to the server:
    Serial.println("connection failed");
  }
  beginMicros = micros();
}

void loop() 
{
  //Serial.println("entering loop");
  //------------------- ex2b -------------------
  Ethernet.maintain();
  //--------------------------------------------
  long duration, cm, avgDistance, tolerance;
   for (int i = 0; i < FILTER_N; i++)
  {
    pinMode(pingPin, OUTPUT);
    digitalWrite(pingPin, LOW);
    delayMicroseconds(2);
    digitalWrite(pingPin, HIGH);
    delayMicroseconds(10);
    digitalWrite(pingPin, LOW);
    pinMode(echoPin, INPUT);
    duration = pulseIn(echoPin, HIGH);
    cm = microsecondsToCentimeters(duration);
    if(cm >= 400 || cm <= 0){
      //Serial.println("Out of Range");
    i--;
   }
    else{
      distances[i] = cm;
    }
  }
  bubbleSort(distances, FILTER_N);
  avgDistance = medianAvg(distances, FILTER_N);
  tolerance = prevDistance * 0.07;
  if((avgDistance > prevDistance + tolerance || avgDistance < prevDistance - tolerance) && prevDistance > 0){
    Serial.print("MOTION DETECTED");
    sprintf(json, "{'SensorId':%d,'Value':%d}", SENSORID, avgDistance); // Cast currTempReading to int
    Serial.println(json);
    postPage(json);
    delay(1000);
  }

  //Serial.print(avgDistance);
  prevDistance = avgDistance;
  //Serial.print("cm");
  //Serial.println();
  delay(5);
}

//...

//------------------- ex2b -------------------
// --------------------------------- postPage()  ---------------------------------
// ---------------------- Performs http Post of json payload ---------------------
byte postPage(char* json)
{
  int inChar;
  Serial.println("postPage() connecting...");

  if (client.connect(serverIp, serverPort)) {
    Serial.println("postPage() connected");

    // send http header
//    client.println("POST /propertymonitor/api/sensorreadings HTTP/1.1");
    client.print("POST ");
    client.print(pageName);
    client.println(" HTTP/1.1");
    client.println("Host: 192.168.86.120"); // or generate from your server variable to not hardwire
    client.println("User-Agent: Arduino/uno ethernet");
    client.println("Connection: close");
    client.println("Content-Type: application/json");
    client.print("Content-Length: ");
    client.println(strlen(json));// number of bytes in the payload
    client.println();// important: need an empty line here
    // send payload
    client.println(json);
  }
  else
  {
    Serial.println(F("postPage() connect failed"));
    return 0;
  }

  int connectLoop = 0;

  while(client.connected())
  {
    while(client.available())
    {
      inChar = client.read();
//      Serial.write(inChar);
      connectLoop = 0;
    }

    delay(1);
    connectLoop++;
    if(connectLoop > 10000)
    {
      Serial.println();
      Serial.println(F("Timeout"));
      client.stop();
    }
  }

  Serial.println(F("disconnecting."));
  Serial.println();
  
  client.stop();
  return 1;
}

int medianAvg(int arr[], int n){
  int tot = 0;
  for(int i = 1; i < n-3; i++){
    tot = tot + arr[i];
  }
  return tot/(n-4);
}

void bubbleSort(int arr[], int n)
{
    int i, j;
    bool swapped;
    for (i = 0; i < n - 1; i++) {
        swapped = false;
        for (j = 0; j < n - i - 1; j++) {
            if (arr[j] > arr[j + 1]) {
                int temp = arr[j];
                arr[j] = arr[j+1];
                arr[j+1] = temp;
                swapped = true;
            }
        }
 
        // If no two elements were swapped
        // by inner loop, then break
        if (swapped == false)
            break;
    }
}

long microsecondsToCentimeters(long microseconds) {
   return microseconds / 29 / 2;
}
