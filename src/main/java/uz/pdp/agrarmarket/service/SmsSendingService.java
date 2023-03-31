package uz.pdp.agrarmarket.service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import uz.pdp.agrarmarket.exception.SmsSendingFailException;

@Service
public class SmsSendingService {
    @Value("${TWILIO_ACCOUNT_SID}")
    String TWILIO_ACCOUNT_SID;
    @Value("${TWILIO_ACCOUNT_TOKEN}")
    String AUTH_TOKEN;
    @Value("${TWILIO_ACCOUNT_NUMBER}")
    String SENDER_NUMBER;

    @PostConstruct
    private void setUp() {
        Twilio.init(TWILIO_ACCOUNT_SID, AUTH_TOKEN);
    }
    public String verificationCode(String phone ,int code) {
        try {
            Message message = Message.creator(
                            new PhoneNumber("+998906163464"), //to
                            new PhoneNumber(SENDER_NUMBER), // from
                            "Verification code " + code)
                    .create();
            return message.getStatus().toString();
        }catch (Exception e){
            e.printStackTrace();
            throw  new SmsSendingFailException("Can not send sms");
        }

    }
}
