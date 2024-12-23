1. Ensure MailDev is Running
   MailDev runs a local SMTP server on port `1025` and a web UI for viewing emails on port `1080`.
    - **To start MailDev locally**:
    - Run the following command:
      ```bash
      docker run -d -p 1025:1025 -p 1080:1080 maildev/maildev
      ```
    - **To access the MailDev web UI**:
    - Open your browser and navigate to `http://localhost:1080`.
    - You can view the emails sent by the application in the MailDev web UI.
    - **To stop MailDev**:
    - Run the following command:
      ```bash
      docker stop $(docker ps -q --filter ancestor=maildev/maildev)
      ```
2. on windows, you can use the following command to stop MailDev
   ```bash
   npm install -g maildev
    ```
3. Start MailDev: To start MailDev, use the following command in your terminal:
   ```bash
   maildev
   ```
4. Access the MailDev Web Interface: Once MailDev is running, you can access the web interface by navigating to
   `http://localhost:1080` in your browser.
5. Send Test Emails: You can send test emails from your application to the MailDev SMTP server. The emails will be
   displayed in the MailDev web interface.
6. Stop MailDev: To stop MailDev, press `Ctrl + C` in the terminal where MailDev is running.

- **Configure the Application to Use MailDev**:
  To configure the application to use MailDev, you need to set the SMTP server configuration in the application
  properties.
    - **For Spring Boot Applications**:
        - Open the `application.properties` file in the `src/main/resources` directory.
        - Add the following configuration:
          ```properties
          spring.mail.host=localhost
          spring.mail.port=1025
          spring.mail.properties.mail.smtp.auth=false
          spring.mail.properties.mail.smtp.starttls.enable=false
          ```
        - Save the file.
- **Send Emails from the Application**:
    1. Once you’ve configured the application to use MailDev, you can send emails from the application.
        - **For Spring Boot Applications**:
            - Use the `JavaMailSender` interface to send emails.
            - Inject the `JavaMailSender` bean into your service or controller.
            - Use the `send()` method to send emails.
            - Here's an example of sending an email using `JavaMailSender`:
   ```java
             import org.springframework.beans.factory.annotation.Autowired;

         import org.springframework.mail.SimpleMailMessage;
         import org.springframework.mail.javamail.JavaMailSender;
         import org.springframework.web.bind.annotation.GetMapping;
         import org.springframework.web.bind.annotation.RestController;
      
         @RestController
         public class EmailTestController {
      
             @Autowired
             private JavaMailSender emailSender;
      
             @GetMapping("/sendTestEmail")
             public String sendTestEmail() {
                 try {
                     SimpleMailMessage message = new SimpleMailMessage();
                     message.setTo("recipient@example.com");  // You can use your own email or a test email
                     message.setSubject("Test Email");
                     message.setText("This is a test email sent from Spring Boot.");
                     emailSender.send(message);
                     return "Test email sent successfully!";
                 } catch (Exception e) {
                     return "Error sending test email: " + e.getMessage();
                 }
             }
      
         }
   ```
- **By default, MailDev will run**:

  SMTP server on port 1025
  Web UI for viewing emails on port 1080

- **Verify that MailDev is running**:

  Access the MailDev web UI by navigating to http://localhost:1080 in your browser.
  You should see the MailDev interface where emails are displayed.