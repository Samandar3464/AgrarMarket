package uz.pdp.agrarmarket.exception;

public class VerificationCodeLiveTimeEnd extends RuntimeException {
    public VerificationCodeLiveTimeEnd(String verificationCodeLiveTimeEnd) {
    super(verificationCodeLiveTimeEnd);
    }
}
