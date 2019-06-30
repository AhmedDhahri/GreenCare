void send_server(measure m){
  if(WiFi.status() == WL_CONNECTED){
    String t(m.temp);
    String h(m.hum);
    String s(m.soilm);
    String ch ="temp="  + t + "&hum=" + h + "&soilm=" + s;
    Serial.println("temp=");
    Serial.print(t);
    Serial.print("&hum=");
    Serial.print(h);
    Serial.print("&soilm=");
    Serial.print(s);
    HTTPClient http;
    http.begin("http://greencare10.000webhostapp.com/write.php?" + ch);
    http.setTimeout(5000);
    http.GET();
    http.end();
  }
}
int receive_server(){
  int n = 0;

  return n;
}

cmd parseCMD(){
  cmd x;
  HTTPClient http;
  String url = "http://greencare10.000webhostapp.com/store1.json";
  http.begin(url);
  http.addHeader("Content-Type","text/plain");
  int g = http.GET();
  String input = http.getString();
  http.end();
  url = "http://greencare10.000webhostapp.com/write1.php?V=0.0";
  http.begin(url);
  int t = http.GET();
  http.end();
  DynamicJsonBuffer jsonBuffer;
  JsonObject& root = jsonBuffer.parseObject(input);
  double V = root["V"];
  String D= root["D"];
  Serial.println("");
  Serial.println("");
  Serial.println(D);
  Serial.println(V);
  cmd c;
  c.vol = V;
  c.date = D;
  return c;
}


