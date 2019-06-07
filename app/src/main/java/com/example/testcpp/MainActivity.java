package com.example.testcpp;

import android.os.Bundle;
import android.widget.TextView;

import org.spongycastle.crypto.generators.SCrypt;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {


    byte[] salt;
    public static final int EXPANSION = 4;

    public native byte[] aezEncrypt(byte[] plainText, byte[] additionalData, byte[] nonce, byte[] secretKey, int expansion);

    static {
        System.loadLibrary("aez");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        salt = getRandomSalt();
        bip39Test();

    }

    byte[] getRandomSalt() {

        byte[] salt = new byte[5];
        new SecureRandom().nextBytes(salt);
        return salt;

    }

    private void bip39Test() {

        byte[] plainText = getPlainText();
        byte[] secretKey = getSecretKey();
        byte[] additionalData = getAdditionalData();

        // 23 encrypted bytes
        byte[] encryptedPlainText = aezEncrypt(plainText, additionalData,  null, secretKey, EXPANSION);

        ((TextView) findViewById(R.id.text_hello_from_lib)).setText(Arrays.toString(encryptedPlainText));

    }

    // 19 bytes = 1 byte version + 2 bytes birthday(cutted bigEndian) + 16 bytes random
    byte[] getPlainText() {

        byte[] versionAndBirthDay = new byte[3];
        versionAndBirthDay[0] = getVersion();
        versionAndBirthDay[1] = getFirstByteOfDays();
        versionAndBirthDay[2] = getSecondByteOfDays();

        byte[] random16Bytes = getRandom16bytes();

        byte[] plainText = new byte[versionAndBirthDay.length + random16Bytes.length];
        System.arraycopy(versionAndBirthDay, 0, plainText, 0, versionAndBirthDay.length);
        System.arraycopy(random16Bytes, 0, plainText, versionAndBirthDay.length, random16Bytes.length);

        return plainText;

    }

    byte getVersion() {

        return (byte) 0;

    }

    byte getFirstByteOfDays() {

        return longToBytes(getDays())[6];

    }

    byte getSecondByteOfDays() {

        return longToBytes(getDays())[7];

    }

    // 3803 days
    long getDays() {

        long bitcoinGenesisDate = 1231006505000L;

        long now = System.currentTimeMillis();

        long timeDaysMilliseconds = now - bitcoinGenesisDate;

        return TimeUnit.MILLISECONDS.toDays(timeDaysMilliseconds);

    }

    byte[] getRandom16bytes() {

        byte[] entropy = new byte[16];
        new SecureRandom().nextBytes(entropy);
        return entropy;

    }

    // key 32 bytes = scrypt(defaultPassphrase, salt, scryptN, scryptR, scryptP, keyLen) keyLen = 32, byte[] defaultPassphrase = Encoding.ASCII.GetBytes("aezeed");
    byte[] getSecretKey() {

        byte[] defaultPassphrase = "aezeed".getBytes();
        int scryptN = 32768;
        int scryptR = 8;
        int scryptP = 1;
        int keyLength = 32;

        return SCrypt.generate(defaultPassphrase, salt, scryptN, scryptR, scryptP, keyLength);

    }

    // Additional data 6 bytes = 1 byte version + 5 bytes salt(random)
    private byte[] getAdditionalData() {
        byte[] version = new byte[1];
        version[0] = getVersion();
        byte[] additionalData = new byte[version.length + salt.length];
        System.arraycopy(version, 0, additionalData, 0, version.length);
        System.arraycopy(salt, 0, additionalData, version.length, salt.length);
        return additionalData;
    }

    public byte[] longToBytes(long days) {
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        buffer.order(ByteOrder.BIG_ENDIAN);
        buffer.putLong(days);
        return buffer.array();
    }

    /*public static String unsignedBytes(byte[] signedBytes) {

        ArrayList<Integer> integers = new ArrayList<>();
        for (int byteOfSingedBytes : signedBytes) {
            int unsignedByte = byteOfSingedBytes & 0xFF;
            integers.add(unsignedByte);
        }
        return Arrays.toString(integers.toArray());

    }*/

}
