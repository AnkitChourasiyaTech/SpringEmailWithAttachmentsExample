package com.websystique.spring.service;

import java.io.File;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import com.websystique.spring.model.ProductOrder;

@Service("mailService")
public class MailServiceImpl implements MailService {

	@Autowired
	JavaMailSender mailSender;

	@Override
	public void sendEmail(Object object) {

		ProductOrder order = (ProductOrder) object;

		MimeMessagePreparator preparator = getContentWtihAttachementMessagePreparator(order);

		try {
			/*mailSender.send(preparator);
			System.out.println("Message With Attachement has been sent.............................");
			*/
			preparator = getContentAsInlineResourceMessagePreparator(order);
			
			mailSender.send(preparator);
			System.out.println("Message With Inline Resource has been sent.........................");
		} catch (MailException ex) {
			System.err.println(ex.getMessage());
		}
	}

	private MimeMessagePreparator getContentWtihAttachementMessagePreparator(final ProductOrder order) {

		MimeMessagePreparator preparator = new MimeMessagePreparator() {

			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

				helper.setSubject("Your order on Demoapp with attachement");
				helper.setFrom("test1@wlmobiles.in.th");
				helper.setTo(order.getCustomerInfo().getEmail());
				String content = "Dear " + order.getCustomerInfo().getName()
						+ ", thank you for placing order. Your order id is " + order.getOrderId() + ".";

				helper.setText(content);

				// Add a resource as an attachment
				helper.addAttachment("cutie.png", new ClassPathResource("linux-icon.png"));

			}
		};
		return preparator;
	}

	private MimeMessagePreparator getContentAsInlineResourceMessagePreparator(final ProductOrder order) {

		MimeMessagePreparator preparator = new MimeMessagePreparator() {

			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

				helper.setSubject("Your order on Demoapp with Inline resource");
				helper.setFrom("test1@wlmobiles.in.th");
				helper.setTo(order.getCustomerInfo().getEmail());

				String content = "Dear <strong>" + order.getCustomerInfo().getName()
						+ "</strong>, thank you for placing order. Your order id is " + order.getOrderId() + ".";

				// Add an inline resource.
				// use the true flag to indicate you need a multipart message
				helper.setText("<html><body><p>" + content + "</p><p><span style='background-color: #999999;'>This is a table you can experiment with.</span></p><img src='cid:company-logo'></body></html>", true);
				helper.addInline("company-logo", new ClassPathResource("linux-icon.png"));
				
				//FileSystemResource file = new FileSystemResource("C:\\temp\\test.zip");
				//helper.addAttachment(file.getFilename(), file);
				
				File _file = new File("C:\\temp\\p01\\");
				File[] files =  _file.listFiles();
				for(File f: files){
					System.out.println("list file>>"+f.getName());
					
					FileSystemResource file = new FileSystemResource("C:\\temp\\p01\\"+f.getName());
					helper.addAttachment(file.getFilename(), file);
				}
			}
		};
		return preparator;
	}

}
