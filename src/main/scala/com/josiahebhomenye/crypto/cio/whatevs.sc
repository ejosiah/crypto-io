import java.io.{FileInputStream, File}
import java.security.{Signature, KeyPairGenerator}
import java.security.cert.CertificateFactory

import com.josiahebhomenye.crypto.cio.RSAKeyPairReader

val domain = "crypto-utility.com"
val sslHome = s"${System.getProperty("user.home")}/$domain.ssl"
require(new File(sslHome).exists(), "ssl home does not exist")
val keyPath = s"$sslHome/$domain.key"
require(new File(keyPath).exists(), "key path does not exists")
val crtPath = s"$sslHome/$domain.crt"
require(new File(crtPath).exists(), "cert path does not exists")

val keyPair = RSAKeyPairReader(keyPath)
println(keyPair.getPublic)
val key = keyPair.getPrivate
val crt = {
  val factory = CertificateFactory.getInstance("X509")
  val cert = factory.generateCertificate(new FileInputStream(crtPath))
  cert
}
val signer = Signature.getInstance("MD5WITHRSA")
signer.initSign(key)
signer.update("Hello World!".getBytes)
val signature = signer.sign()
val verifier = Signature.getInstance("MD5WITHRSA")
verifier.initVerify(crt)
verifier.update("Hello World!".getBytes)
verifier.verify(signature)

