package scsi.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import scsi.demo.model.User;
import scsi.demo.repository.UserRepository;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

@Service
@Transactional
public class UserService {
    @Autowired
    private static UserRepository userRepository;

    public static final int MAX_FAILED_ATTEMPTS = 3;
    
    private static final long LOCK_TIME_DURATION =  15 * 60 * 1000; // 15 min
    
    public User findUserByUser_id(String userid) {
        return userRepository.findByUser_id(userid);
    }
    
    public void increaseFailedAttempts(User user) {
        int newFailAttempts = user.getFail_times() + 1;
        userRepository.updateFailedAttempts(user.getUserid(),newFailAttempts);
    }
    
    public void lock(User user) {
    	userRepository.setlockflag(user.getUserid(),1);
//    	userRepository.setlocktime(user.getUserid(), new Date());
    }
    
    public boolean unlockWhenTimeExpired(User user) {
        long lockTimeInMillis = getTime(user.getLock_st());
        long currentTimeInMillis = System.currentTimeMillis();
         
        if (lockTimeInMillis + LOCK_TIME_DURATION < currentTimeInMillis) {
            userRepository.setlockflag(user.getUserid(), 0);;
            userRepository.setlocktime(user.getUserid(), null);
            userRepository.updateFailedAttempts(user.getUserid(),0);
            return true;
        }
        return false;
    }
    
    public static long getTime(String s) {
        return parseMysql(s).getTime();
    }

    public static Date parseMysql(String s) {
        try {
            DateFormat fmtTemp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return fmtTemp.parse(s);
        } catch (ParseException e) {
            return null;
        }
    }

}