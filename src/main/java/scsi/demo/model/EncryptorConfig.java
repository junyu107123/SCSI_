package scsi.demo.model;

import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.jasypt.util.text.BasicTextEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile({"prod"})
public class EncryptorConfig {
	private static final String JASYPT_ENCRYPTOR_PWD = "1qaz@WSX";
	
	@Autowired
	public StringEncryptor stringEncryptor;
		
	 @Bean("jasyptStringEncryptor")
		public StringEncryptor stringEncryptor() {
			PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
			SimpleStringPBEConfig config = new SimpleStringPBEConfig();
			config.setPassword(JASYPT_ENCRYPTOR_PWD);
			config.setAlgorithm("PBEWithMD5AndDES");
			config.setKeyObtentionIterations("1000");
			config.setPoolSize("1");
			config.setProviderName("SunJCE");
			config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
			config.setStringOutputType("base64");
			encryptor.setConfig(config);
			return encryptor;
		}
		
		public String decry(String str) {
			String encData = stringEncryptor.encrypt(str);
			String decData = stringEncryptor.decrypt(encData);
			System.out.println("ENC ="+encData);
			System.out.println("DEC ="+decData);
			return decData ;
			
		}
		
	}
