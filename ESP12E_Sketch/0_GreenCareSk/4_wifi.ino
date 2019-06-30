void w_connect(String ssid, String pass){
  int timer = 0;
  bool operation = true;
  char _ssid[32];
  char _pass[64];

  ssid.toCharArray(_ssid,ssid.length()+1);
  pass.toCharArray(_pass,pass.length()+1);
  
  //wifi connect
  WiFi.begin(_ssid, _pass);
  Serial.print("(*o*)");
  while(WiFi.status() != WL_CONNECTED){
    delay(1000);
    Serial.print("_(*o*)");
    timer++;
    if(timer == request_timeout){
      operation = false;
      break;
    }
  }
  if(operation)
    Serial.println("\nConnected");
  else
    Serial.println("\nNot Connected");
}
void request(){
  IPAddress local_IP(192,168,1,22);
  IPAddress gateway(192,168,4,9);
  IPAddress subnet(255,255,255,0);
  char ToSend[255];
  char buf[255];
  info wp;
  WiFiUDP Udp;
  IPAddress ip;
  unsigned int port;
  unsigned int localPort = 4000;
  WiFi.softAPConfig(local_IP, gateway, subnet);
  WiFi.softAP("GreenCare");
  Udp.begin(localPort);
  bool c_OK = false;
  bool first_paket = true;
  long m = millis() + 120 * 1000;
  Serial.println("Ready");
  while(millis() < m) {
    if(WiFi.softAPgetStationNum() > 0){
      int n = Udp.parsePacket();
      if(n){
        Serial.println("Received");
        int len = Udp.read(buf, 255);
      if(len)
        buf[len] = 0;
      info n = parseb(buf,len);
      store(n);
      Udp.beginPacket(Udp.remoteIP(),Udp.remotePort());
      char* s = "OK";
      Udp.write(s,2);
      Serial.println("Sent");
      Udp.endPacket();
      }
    }
    yield();
  }
  WiFi.softAPdisconnect(true);
  //store(n);
}

info parseb(char* b, int l){
  int i =0;
  info n = {"","",0,0};
  while(b[i]!=((char)7)){
    n.ssid += b[i];
    i++;
  }
  i++;
  while(b[i]!=((char)7)){
    n.pass += b[i];
    i++;
  }
  i++;
  n.sleep = b[i];
  i += 2;
  n._set = b[i];
  Serial.println(n.ssid);
  Serial.println(n.pass);
  Serial.println(n.sleep);
  Serial.println(n._set);
  return n;
}


