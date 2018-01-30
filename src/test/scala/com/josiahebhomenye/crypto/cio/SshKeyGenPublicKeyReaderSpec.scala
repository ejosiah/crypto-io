package com.josiahebhomenye.crypto.cio


import org.scalatest.{OptionValues, MustMatchers, WordSpec}

/**
  * Created by jay on 26/09/2016.
  */
class SshKeyGenPublicKeyReaderSpec extends WordSpec with MustMatchers with OptionValues{

  "SshKeyGenPublicKeyReader" should {
    "successfully ready in a rsa public key in ssh-keygen format" in {
      val key = SshKeyGenPublicKeyReader("src/test/resources/id_rsa.pub")
      key.getAlgorithm mustBe "RSA"
    }

    "successfully ready in a dsa public key in ssh-keygen format" in {
      val key = SshKeyGenPublicKeyReader("src/test/resources/id_dsa.pub")
      key.getAlgorithm mustBe "DSA"
    }
  }
}
