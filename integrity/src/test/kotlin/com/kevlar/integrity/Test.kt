/**
 * Designed and developed by Kevlar Contributors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.kevlar.integrity

import org.junit.Test
import java.util.Base64

public class Test {

    /*
    @Test
    public fun `given md5 base64 hash then matches fingerprint`() {
        val hashType = FingerprintHashType.MD5
        // given google play store fingerprint
        val md5Fingerprint = "E9:9C:7B:44:7E:50:F9:D9:0C:FF:CA:D7:77:A3:F6:2A"
        // user encodes the output as base64
        val fingerprintHash = "RTk6OUM6N0I6NDQ6N0U6NTA6Rjk6RDk6MEM6RkY6Q0E6RDc6Nzc6QTM6RjY6MkE="
        val hardcodedFingerprint = HardcodedBase64EncodedFingerprint(
            base64EncodedFingerprints = listOf(fingerprintHash),
            type = hashType
        )

        // a real signature, obtained from debug build's toCharsString() (matches the md5 above)
        val mockSignature =
            """308203753082025da00302010202086277089a0252e35c300d06092a864886f70d01010b050030693
                |10b3009060355040613024259310e300c060355040813054d696e736b310e300c06035504071305
                |4d696e736b3110300e060355040a13075265737061776e3110300e060355040b130752657370617
                |76e311630140603550403130d4e696b697461205661697a696e301e170d32323035323331393232
                |35395a170d3439313030383139323235395a3069310b3009060355040613024259310e300c06035
                |5040813054d696e736b310e300c060355040713054d696e736b3110300e060355040a1307526573
                |7061776e3110300e060355040b13075265737061776e311630140603550403130d4e696b6974612
                |05661697a696e30820122300d06092a864886f70d01010105000382010f003082010a0282010100
                |a465b4ab11d66b8a609a40b40d72c4a9c492a083f58841ad0814f66c3a23b30df8d1fbd40db52f8
                |6773b230471354137f2fd3606d793c4008eced27d41c94b8c186f002db195c812e7a438b5ad76a9
                |642ed0ab49f4d9fc8dff3462342a0b43b7d2bf23b376005c736442e3cd078a9deabf36e13bc5826
                |8c7555690db8b84d8de6b001b94200ecd48ed899ac76095f14f7435da41b468af44e0de097c3638
                |b4658c42dbad565c5cffc3953989dbf56ba7a84e515a26a53051758b7b2e682c8fda2005c4e25a9
                |e72786b32249e008c296a13f13625f771e674f4b8cca2394e487b65698b7de91d62a0ad414321c3
                |bdfa29b84b00f5f36917b412d947a31eb133c10203010001a321301f301d0603551d0e041604142
                |6eb75a288547549b59024de0c15d0ce211a8a1f300d06092a864886f70d01010b05000382010100
                |2d0e60425d75e3534a659bf457078a05d397337deb9d0dbe6b6b730c08f4df1ce0acd8764666f8b
                |d425a3d8793225e2f4ed5eb9bb1dd4cc6f1086d3bd4a9362be7853011937eeae4da8bf76595f13d
                |faff370bf0ba2af186e4e5269464d565931930aec49c98de7e96dbd68159a0595c6490dbee9522f
                |88af913bcc61305a31c1abacb4123e26faed3353f991bf0005b733715e80bf01bd1e7997c042f73
                |7b893ff66bc2ba306ae0cb1b59fc4aa899133b5b004d06a135a70ee1dc82c28dc9072c9b5cab276
                |fc72a1c03aa37ef916711a58f1c74487eeed06aedda90489e173fbc1dac33e9c465337c7d51896b
                |46eaa39c1f4469668f04d12700bb5dcf42d8c3""".trimMargin()
                .replace("\n", "")
        val runtimeFingerprint = mockSignature.signatureFromChars()
            .hash(hashType)!!
            .toHex(":")
        println("runtimeFingerprint: $runtimeFingerprint")
        val runtimeHash = runtimeFingerprint.toByteArray().let {
            Base64.getEncoder().encodeToString(it)
        }

        assert(runtimeHash == fingerprintHash)
    }
}

private fun parseHexDigit(nibble: Int): Int {
    return if ('0'.code <= nibble && nibble <= '9'.code) {
        nibble - '0'.code
    } else if ('a'.code <= nibble && nibble <= 'f'.code) {
        nibble - 'a'.code + 10
    } else if ('A'.code <= nibble && nibble <= 'F'.code) {
        nibble - 'A'.code + 10
    } else {
        throw IllegalArgumentException("Invalid character $nibble in hex string")
    }
}

// copied from Android package manager source code
private fun String.signatureFromChars(): ByteArray {
    val input = toByteArray()
    val N = input.size
    require(N % 2 == 0) { "text size $N is not even" }

    val sig = ByteArray(N / 2)
    var sigIndex = 0

    var i = 0
    while (i < N) {
        val hi: Int = parseHexDigit(input[i++].toInt())
        val lo: Int = parseHexDigit(input[i++].toInt())
        sig[sigIndex++] = (hi shl 4 or lo).toByte()
    }
    return sig
}
*/
}