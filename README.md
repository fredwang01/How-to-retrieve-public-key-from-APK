"# How-to-retrieve-public-key-from-APK" 

the signature file of APK package contains public key to be used to verify APK's correctness  when installed into mobile device. 

signature file is encode as DER format usually, so i manage to collect the corresponding decode implementation of pkcs7 and write the utility class `PublicKeyDump` to retrieve the public key of APK.
