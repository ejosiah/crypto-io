package com.josiahebhomenye.crypto.cio

import java.security.PublicKey

/**
  * Created by jay on 26/09/2016.
  */
class OpenSSLPublicKeyReader(path: String) extends PublicKeyReader(path){

  override def readKey(): PublicKey = ???
}
