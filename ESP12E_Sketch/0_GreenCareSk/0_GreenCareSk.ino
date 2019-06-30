#include <ArduinoJson.h>
#include <EEPROM.h>
#include <ESP8266WiFi.h>
#include <ESP8266HTTPClient.h>
#include <ESP8266WiFi.h>
#include <WiFiUdp.h>
#include <Adafruit_Sensor.h>
#include <DHT.h>
#include <DHT_U.h>

#define DHTPIN            9
#define DHTTYPE           DHT11
#define ARDUINOJSON_DEFAULT_NESTING_LIMIT 50
DHT_Unified dht(DHTPIN, DHTTYPE);
uint32_t delayMS;



int request_timeout = 30;
int sleep_time = 30;

struct info{
  String ssid;
  String pass;
  int sleep;
  int _set;
};

struct measure{
  int temp;
  int hum;
  int soilm;
};

struct cmd{
  double vol;
  String date;
};

info restore();
void setConf(bool b);
bool isConf();
void w_connect(String ssid, String pass);
void request();
void DHT_INIT();
measure read_s();
info parseb(char* b, int l);
void store(info p);
void send_server(measure m);
int receive_server();
cmd parseCMD();

