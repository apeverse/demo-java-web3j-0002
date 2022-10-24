package org.galaxy;

import java.util.List;

import static org.galaxy.utils.SignUtils.SIGN_NODE_PREFIX;
import static org.galaxy.utils.SignUtils.getRecoverAddressFromSignature;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        String signature =
                "0x82c4f4d353f81ae1ea12a7a423d5cf14359444d5015b84fe5b422e3e3ca79bf236923e8ef2ecdca5d743a8eede89f446c086f1ea04eb8b90b73df1ce4ea39f601c";
        String message = "0x3d5d2e21162745e4df4f56471fd7f651f441adaaca25deb70e4738c6f63d1224";

        String address = "0xe26f015ba6b8c400cE327CeEBE34B717e6897e69";
        List<String> ret = getRecoverAddressFromSignature(signature, message, SIGN_NODE_PREFIX);
        System.out.println(ret);
        System.out.println(ret.contains(address.toLowerCase()));
    }
}
