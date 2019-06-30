void setup(){
  Serial.begin(74880);
  pinMode(12, OUTPUT);
  pinMode(14, INPUT);
  digitalWrite(12, LOW);
  if(digitalRead(14))
    request();
  info o = restore();
  Serial.println(o.ssid);
  Serial.println(o.pass);
  Serial.println(o.sleep);
  Serial.println(o._set);
  w_connect(o.ssid, o.pass);
  digitalWrite(12, HIGH);
  delay(2000);
  DHT_INIT();
  sleep_time = o.sleep;
  request_timeout = o._set;
  measure m = read_s();
  send_server(m);
  cmd c = parseCMD();
  digitalWrite(12, LOW);
  Serial.println("sleeping");
  ESP.deepSleep(sleep_time*60000000);
}


