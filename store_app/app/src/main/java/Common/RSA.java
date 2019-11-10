package Common;

import java.io.FileReader;
import java.io.IOException;
import java.io.SequenceInputStream;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchProviderException;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;

import android.app.Activity;
import android.icu.util.Calendar;
import android.icu.util.GregorianCalendar;
import android.security.KeyPairGeneratorSpec;
import android.util.Log;

import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.RSAPrivateKeySpec;
import java.util.Enumeration;

import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERInteger;
import org.bouncycastle.util.encoders.Base64;
import org.bouncycastle.util.io.pem.*;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.security.auth.x500.X500Principal;

public class RSA {

    /**
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static void GeneratePublicPrivateKeyPair(Activity activity) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException, KeyStoreException {
        KeyStore ks = KeyStore.getInstance("AndroidKeyStore");
        Calendar start = new GregorianCalendar();
        Calendar end = new GregorianCalendar();
        end.add(Calendar.YEAR, 20);
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA", "AndroidKeyStore");
        AlgorithmParameterSpec spec = new KeyPairGeneratorSpec.Builder(activity)
                .setAlias("AuthKey")
                .setKeySize(512)
                .setSubject(new X500Principal("CN=AuthKey"))
                .setSerialNumber(BigInteger.valueOf(12121212))
                .setStartDate(start.getTime())
                .setEndDate(end.getTime())
                .build();
        generator.initialize(spec);
        KeyPair kp = generator.generateKeyPair();
    }

    /**
     * @return
     * @throws KeyStoreException
     * @throws NoSuchAlgorithmException
     * @throws CertificateException
     * @throws IOException
     */
    public static String getPublicKey() throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException {
        KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
        keyStore.load(null);
        PublicKey pubKey = keyStore.getCertificate("AuthKey").getPublicKey();
        return new String(Base64.encode(pubKey.getEncoded()));
    }

    /**
     * @param data
     * @return
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     * @throws IOException
     * @throws KeyStoreException
     * @throws CertificateException
     * @throws InvalidKeyException
     * @throws UnrecoverableEntryException
     */
    public static String Encrypt(String data) throws NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, IllegalBlockSizeException, IOException, KeyStoreException, CertificateException, InvalidKeyException, UnrecoverableEntryException {
        KeyStore ks = KeyStore.getInstance("AndroidKeyStore");
        ks.load(null);
        KeyStore.Entry entry = ks.getEntry("AuthKey", null);
        PublicKey pub = ((KeyStore.PrivateKeyEntry) entry).getCertificate().getPublicKey();
        final Cipher cipher = Cipher.getInstance("RSA/NONE/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, pub);
        byte[] encryptedData = cipher.doFinal(data.getBytes());
        return Base64.toBase64String(encryptedData);
    }

    /**
     * @param data
     * @return
     * @throws KeyStoreException
     * @throws CertificateException
     * @throws NoSuchAlgorithmException
     * @throws IOException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     * @throws UnrecoverableEntryException
     */
    public static String Decrypt(String data) throws KeyStoreException, CertificateException, NoSuchAlgorithmException, IOException, NoSuchPaddingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, UnrecoverableEntryException {
        KeyStore ks = KeyStore.getInstance("AndroidKeyStore");
        ks.load(null);
        KeyStore.Entry entry = ks.getEntry("AuthKey", null);
        PrivateKey pri = ((KeyStore.PrivateKeyEntry) entry).getPrivateKey();
        final Cipher cipher = Cipher.getInstance("RSA/NONE/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, pri);
        byte[] decryptedData = cipher.doFinal(Base64.decode(data));
        return new String(decryptedData);
    }

    public static String Decrypt(String data, String key) throws Exception {
        key = key.replaceAll("\\n", "").replace("-----BEGIN RSA PRIVATE KEY-----", "")
                .replace("-----END RSA PRIVATE KEY-----", "");
        byte[] encodedPrivateKey  = Base64.decode(key);

        try {
            ASN1Sequence primitive = (ASN1Sequence) ASN1Sequence
                    .fromByteArray(encodedPrivateKey);
            Enumeration<?> e = primitive.getObjects();
            BigInteger v = ((ASN1Integer) e.nextElement()).getValue();

            int version = v.intValue();
            if (version != 0 && version != 1) {
                throw new IllegalArgumentException("wrong version for RSA private key");
            }
            /**
             * In fact only modulus and private exponent are in use.
             */
            BigInteger modulus = ((ASN1Integer) e.nextElement()).getValue();
            BigInteger publicExponent = ((ASN1Integer) e.nextElement()).getValue();
            BigInteger privateExponent = ((ASN1Integer) e.nextElement()).getValue();
            BigInteger prime1 = ((ASN1Integer) e.nextElement()).getValue();
            BigInteger prime2 = ((ASN1Integer) e.nextElement()).getValue();
            BigInteger exponent1 = ((ASN1Integer) e.nextElement()).getValue();
            BigInteger exponent2 = ((ASN1Integer) e.nextElement()).getValue();
            BigInteger coefficient = ((ASN1Integer) e.nextElement()).getValue();

            RSAPrivateKeySpec spec = new RSAPrivateKeySpec(modulus, privateExponent);
            KeyFactory kf = KeyFactory.getInstance("RSA");
            PrivateKey pk = kf.generatePrivate(spec);

            final Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.DECRYPT_MODE, pk);
            byte[] decryptedData = cipher.doFinal(Base64.decode(data));
            return new String(decryptedData, StandardCharsets.UTF_16LE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
