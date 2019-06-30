void store(info p){
  int i = 0;
  int j = 0;
  int lssid = p.ssid.length();//SSID 32 chars
  int lpass = p.pass.length();//pass 64 chars
  
  //Clear
  EEPROM.begin(512);
  for(i = 0;i<512;i++)
    EEPROM.write(i,0);
  EEPROM.commit();
  
  //Write
  i = 0;
  if((lssid < 33) && (lpass < 65)){
    while(i < lssid){
      EEPROM.write(i,p.ssid.charAt(i));
      i++;
    }
    EEPROM.write(i,0);
    i++;
    while(j < lpass){
      EEPROM.write(j+i,p.pass.charAt(j));
      j++;
    }
    EEPROM.write(i+j,0);
  i++;
  EEPROM.write(i+j,p.sleep);
  i++;
  EEPROM.write(i+j,0);
  i++;
  EEPROM.write(i+j,p._set);
  }
  EEPROM.end();
}



info restore(){
  EEPROM.begin(512);
  info p;
  p.ssid = "";
  p.pass = "";
  int a = 0;
  char v = EEPROM.read(0);
  while(v != 0){
    p.ssid += v;
    a++;
    v = EEPROM.read(a);
    yield();
  }
  a++;
  v = EEPROM.read(a);
  while(v != 0){
    p.pass += v;
    a++;
    v = EEPROM.read(a);
    yield();
  }
  a++;
  p.sleep = EEPROM.read(a);
  a += 2;
  p._set = EEPROM.read(a);
  if(p.sleep < 10)
    p.sleep = 10;
  if(p._set < 10)
    p._set = 10;
  return p;
}


