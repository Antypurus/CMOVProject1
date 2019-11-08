package Common;

import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchProviderException;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableEntryException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import android.app.Activity;
import android.icu.util.Calendar;
import android.icu.util.GregorianCalendar;
import android.security.KeyPairGeneratorSpec;
import android.util.Log;

import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;

import org.bouncycastle.util.encoders.Base64;

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
     *
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
     *
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
     *
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

}
