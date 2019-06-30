void DHT_INIT(){
  dht.begin();
  sensor_t sensor;
  delayMS = sensor.min_delay / 1000;
}
measure read_s(){
  measure m;
  delay(delayMS);
  sensors_event_t event; 
  dht.temperature().getEvent(&event);
  m.temp = event.temperature;
  Serial.println(m.temp);
  dht.humidity().getEvent(&event);
  m.hum = event.relative_humidity;
  
  Serial.println(m.hum);
  
  m.soilm = 100-((analogRead(0)*100)/1024);
  Serial.println(m.soilm);

  if(m.temp > 80)
    m.temp = 80;
  if(m.temp < -20)
    m.temp = -20;
  if(m.hum > 100)
    m.hum = 100;
  if(m.hum < 0)
    m.hum = 0;
  if(m.soilm > 100)
    m.soilm = 100;
  if(m.soilm < 0)
    m.soilm = 0;
  return m;
}

