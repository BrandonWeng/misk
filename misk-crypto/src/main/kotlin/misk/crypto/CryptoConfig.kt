package misk.crypto

import misk.config.Config
import misk.config.Secret

/**
 * Main configuration object representing to be used in the app.
 */
data class CryptoConfig(
  val keys: List<Key>?,
  /**
   * The KMS' key URI.
   * For GCP users that may look like:
   * gcp-kms://projects/<project>/locations/<location>/keyRings/<keyRing>/cryptoKeys/<key>
   * For AWS users that the Key URI looks like:
   * aws-kms://arn:aws:kms:<region>:<account-id>:key/<key-id>
   *
   * When using [CryptoTestModule] this value can be omitted, it'll be replaces by a [FakeKmsClient]
   */
  val kms_uri: String?
) : Config

/**
 * Describes a specific key
 */
data class Key(
  /**
   * Descriptive short string for the key.
   * This name will be used when injecting the corresponding key object in the app, like:
   * ```
   * @Inject @Named("keyName") lateinit var myKey: Aead
   * ```
   */
  val key_name: String,
  /**
   * Type of Tink primitive to initialize
   */
  val key_type: KeyType,
  /**
   * Path to a file containing the encrypted key material in Tink's JSON format.
   *
   * When using [CryptoTestModule] this value can be omitted, it'll be replaced by a hardcoded keyset.
   */
  val encrypted_key: Secret<String>?
) : Config

/**
 * Supported key types
 */
enum class KeyType {
  AEAD,
  MAC,
  DIGITAL_SIGNATURE
}