package util;


import javax.mail.MessagingException;
import service0.OTPService;

public class OTPManager {

    public static String generateOTP(String email) {
        String otp = OTPService.generateOTP();
        try {
            OTPService.sendOTPEmail(email, otp);
        } catch (MessagingException e) {
            e.printStackTrace();
            return null;
        }
        return otp;
    }

    public static boolean verifyOTP(String email, String enteredOTP) {
        return OTPService.verifyOTP(email, enteredOTP);
    }
}
