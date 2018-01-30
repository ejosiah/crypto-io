package com.josiahebhomenye.crypto.cio

import java.io._
import java.security.{KeyPair, PublicKey, Key}


/**
  * Created by jay on 26/09/2016.
  */
trait KeyWriter[-K] extends Writer{

  def write(key: K)

}

trait KeyReader[K] extends Reader{

  def readKey(): K

}

abstract class PublicKeyReader(path: String) extends
  BufferedReader(new InputStreamReader(new FileInputStream(path))) with KeyReader[PublicKey]

abstract class KeyPairReader extends KeyReader[KeyPair]