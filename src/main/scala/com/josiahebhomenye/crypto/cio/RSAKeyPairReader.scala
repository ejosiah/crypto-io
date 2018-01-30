package com.josiahebhomenye.crypto.cio

import java.nio.file.{Paths, Files}
import java.security.spec.{RSAPublicKeySpec, RSAPrivateKeySpec}
import java.security.{KeyFactory, KeyPair}
import java.util.Base64

import sun.security.util.DerValue

import scala.collection.JavaConverters._

object RSAKeyPairReader{
  def apply(path: String) = new RSAKeyPairReader(path).readKey()
  val PKCS1 = "-----BEGIN RSA PRIVATE KEY-----"
}

import RSAKeyPairReader._

class RSAKeyPairReader(path: String) extends KeyPairReader{

  val body = {
    val content = Files.readAllLines(Paths.get(path)).asScala
    require(content.slice(0, 1).mkString == PKCS1 , "Not in PKCS#1 format")
    new DerValue(Base64.getDecoder.decode(content.slice(1, content.length - 1).mkString)).data
  }

  override def readKey(): KeyPair = {
    val (_, m, e, d) = (
        body.getBigInteger,
        body.getBigInteger,
        body.getBigInteger,
        body.getBigInteger
      )
    val privKey = KeyFactory.getInstance("RSA").generatePrivate(new RSAPrivateKeySpec(m, d))
    val pubKey = KeyFactory.getInstance("RSA").generatePublic(new RSAPublicKeySpec(m, e))

    new KeyPair(pubKey, privKey)
  }

  override def close(): Unit = {}

  override def read(cbuf: Array[Char], off: Int, len: Int): Int = throw new UnsupportedOperationException
}
