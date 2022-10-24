package org.galaxy.utils;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.web3j.crypto.ECDSASignature;
import org.web3j.crypto.Hash;
import org.web3j.crypto.Keys;
import org.web3j.crypto.Sign;
import org.web3j.utils.Numeric;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

public class SignUtils {

    public static final String PERSONAL_MESSAGE_PREFIX = "\u0019Ethereum Signed Message:\n";
    public static final String SIGN_NODE_PREFIX = "Get latest divident for DID node: ";


    public static List<String> getRecoverAddressFromSignature(String signature, String message, String signPrefix) {
        if (StringUtils.isNotBlank(signPrefix)) {
            message = StringUtils.join(signPrefix, message);
        }
        List<String> resultList = Lists.newArrayList();
        String prefix = PERSONAL_MESSAGE_PREFIX + message.length();
        byte[] msgHash = Hash.sha3((prefix + message).getBytes());

        byte[] signatureBytes = Numeric.hexStringToByteArray(signature);
        byte v = signatureBytes[64];
        if (v < 27) {
            v += 27;
        }

        Sign.SignatureData sd =
                new Sign.SignatureData(
                        v,
                        (byte[]) Arrays.copyOfRange(signatureBytes, 0, 32),
                        (byte[]) Arrays.copyOfRange(signatureBytes, 32, 64));

        String addressRecovered = null;
        boolean match = false;

        // Iterate for each possible key to recover
        for (int i = 0; i < 4; i++) {
            BigInteger publicKey =
                    Sign.recoverFromSignature(
                            (byte) i,
                            new ECDSASignature(
                                    new BigInteger(1, sd.getR()), new BigInteger(1, sd.getS())),
                            msgHash);

            if (publicKey != null) {
                addressRecovered = "0x" + Keys.getAddress(publicKey);
                resultList.add(StringUtils.remove(addressRecovered, SIGN_NODE_PREFIX));
            }
        }
        return resultList;
    }

}
