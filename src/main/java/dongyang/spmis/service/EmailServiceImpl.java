package dongyang.spmis.service;

import dongyang.spmis.mapper.CheckMapper;
import dongyang.spmis.model.UserActivatedDTO;
import dongyang.spmis.model.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService{

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private CheckMapper checkMapper;

    @Override
    public void mailSend(UserDTO user) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(user.getUser_email());
        simpleMailMessage.setSubject("SPMIS 이메일 인증번호");
        simpleMailMessage.setText(user.getChecknum());
        System.out.println(simpleMailMessage);

        javaMailSender.send(simpleMailMessage);
    }

    @Override
    public boolean selectActivated(UserDTO user) {
        return checkMapper.selectActivated(new UserActivatedDTO(user.getUser_email(), true));
    }

    @Override
    public String selectCheckNum(UserDTO user) {
        return checkMapper.selectchecknum(user);
    }

    @Override
    public boolean updateCheckNum(UserDTO user) {
        checkMapper.updatechecknum(user);
        return true;
    }

    @Override
    public boolean updateActivated(UserDTO user) {
        checkMapper.updateactivated(user);
        return true;
    }
}
