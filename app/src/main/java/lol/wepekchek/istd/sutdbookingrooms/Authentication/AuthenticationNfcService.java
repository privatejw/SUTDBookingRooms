package lol.wepekchek.istd.sutdbookingrooms.Authentication;

import android.nfc.cardemulation.HostApduService;
import android.os.Bundle;

import java.util.Arrays;

/**
 * Created by 1001827 on 13/12/16.
 */

//public class AuthenticationNfcService extends HostApduService {
//    @Override
//    public void onDeactivated(int code){
//
//    }
//
//    @Override
//    public byte[] processCommandApdu(byte[] commandApdu, Bundle extras){
//        if (Arrays.equals(SELECT_AID_APDU,commandApdu)){
//            String authorKey = "test";
//            byte[] authorKeyBytes = authorKey.getBytes();
//            return ConcatBytes(authorKeyBytes,SELECT_OK);
//        } else {
//            return UNKNOWN_CMD;
//        }
//    }
//}
