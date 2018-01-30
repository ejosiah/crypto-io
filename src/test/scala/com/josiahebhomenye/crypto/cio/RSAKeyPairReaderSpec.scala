package com.josiahebhomenye.crypto.cio

import org.scalatest.{MustMatchers, OptionValues, WordSpec}

/**
  * Created by jay on 26/09/2016.
  */
class RSAKeyPairReaderSpec extends WordSpec with MustMatchers with OptionValues{

  "RSAKeyPairReader" should {
    "successfully ready in a rsa  key pair in PKCS#1 format" in {
      val keyPair = RSAKeyPairReader("src/test/resources/id_rsa")
      keyPair.getPrivate.getAlgorithm mustBe "RSA"
      keyPair.getPublic.getAlgorithm mustBe "RSA"
    }
  }
}
