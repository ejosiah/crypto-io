package com.josiahebhomenye.crypto.cio

import java.io._
import java.math.BigInteger
import java.security.spec.{DSAPublicKeySpec, RSAPublicKeySpec}
import java.security.{KeyFactory, PublicKey, Key}
import java.util.Base64


object SshKeyGenPublicKeyReader{

  def apply(path: String) = new SshKeyGenPublicKeyReader(path).readKey()
}

class SshKeyGenPublicKeyReader(path: String) extends PublicKeyReader(path){

  lazy val data = Base64.getDecoder.decode(readLine().split(" ")(1).getBytes)

  override def readKey(): PublicKey = {
    val dis = new DataInputStream(new ByteArrayInputStream(data))
    val algorithm = extractPart(dis)(new String(_))
    algorithm match {
      case "ssh-rsa" => createRSAPublicKey(dis)
      case "ssh-dss" => createDSAPublicKey(dis)
    }
  }

  def createRSAPublicKey(dis: DataInputStream): PublicKey = {
    val exponent = extractPart(dis)(new BigInteger(_))
    val modulus = extractPart(dis)(new BigInteger(_))
    val keySpec = new RSAPublicKeySpec(modulus, exponent)
    KeyFactory.getInstance("RSA").generatePublic(keySpec)
  }

  def createDSAPublicKey(dis: DataInputStream): PublicKey = {
    val y = extractPart(dis)(new BigInteger(_))
    val p = extractPart(dis)(new BigInteger(_))
    val q = extractPart(dis)(new BigInteger(_))
    val g = extractPart(dis)(new BigInteger(_))
    val keySpec = new DSAPublicKeySpec(y, p, q, g)
    KeyFactory.getInstance("DSA").generatePublic(keySpec)
  }

  private def extractPart[T](dis: DataInputStream)(f: Array[Byte] => T): T ={
    val n = dis.readInt()
    val buf = new Array[Byte](n)
    dis.read(buf)
    f(buf)
  }

}
