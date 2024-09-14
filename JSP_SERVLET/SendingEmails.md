Sending emails from a JSP/Servlet application is commonly done using the **JavaMail API**, which provides classes to compose, send, and receive emails in Java applications. Below is a detailed explanation and example of how to send an email using JSP and Servlets.

### **Steps to Send an Email in JSP/Servlet**

1. **Include the JavaMail API Dependency**  
   - If you're using Maven, add the following dependency to your `pom.xml`:
     ```xml
     <dependency>
         <groupId>com.sun.mail</groupId>
         <artifactId>javax.mail</artifactId>
         <version>1.6.2</version> <!-- Check for the latest version -->
     </dependency>
     ```

   - If not using Maven, download the JavaMail API JAR files and add them to your project’s classpath.

2. **Configure SMTP Settings**  
   To send an email, you need to specify an SMTP server (e.g., Gmail SMTP, or your company's email server). This information includes:
   - SMTP host
   - SMTP port
   - Authentication details (username, password)
   - SSL/TLS security settings

### **Detailed Example: Sending Email in JSP/Servlet**

#### 1. **Servlet Code to Send Email**

The following servlet accepts parameters like email, subject, and message from a form and sends an email using the JavaMail API.

```java
import java.io.IOException;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/sendEmail")
public class EmailServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get parameters from the form
        String recipient = request.getParameter("recipient");
        String subject = request.getParameter("subject");
        String content = request.getParameter("message");

        // SMTP server information
        String host = "smtp.gmail.com"; // Using Gmail SMTP server
        final String username = "your-email@gmail.com"; // your email ID
        final String password = "your-email-password"; // your email password
        
        // Set properties for SMTP
        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "587"); // For Gmail SMTP
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        // Create a session with authentication
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            // Create a default MimeMessage object
            Message message = new MimeMessage(session);
            
            // Set From field
            message.setFrom(new InternetAddress(username));
            
            // Set To field
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
            
            // Set Subject
            message.setSubject(subject);
            
            // Set Content (email body)
            message.setText(content);
            
            // Send message
            Transport.send(message);
            
            // Send success response to client
            response.getWriter().println("Email successfully sent to " + recipient);
            
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
```

#### 2. **JSP Form to Collect User Input**

The JSP page takes user input for the recipient's email address, subject, and message, and then submits the form to the servlet (`EmailServlet`) for sending the email.

```jsp
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Send Email</title>
</head>
<body>

<h2>Send Email Form</h2>

<form action="sendEmail" method="post">
    <label for="recipient">Recipient Email:</label>
    <input type="email" id="recipient" name="recipient" required><br><br>

    <label for="subject">Subject:</label>
    <input type="text" id="subject" name="subject" required><br><br>

    <label for="message">Message:</label><br>
    <textarea id="message" name="message" rows="5" cols="30" required></textarea><br><br>

    <input type="submit" value="Send Email">
</form>

</body>
</html>
```

### **Explanation of the Servlet Code:**

1. **Extracting Form Parameters**:  
   The servlet extracts the form data using `request.getParameter()`, including:
   - `recipient`: Email address of the person who will receive the email.
   - `subject`: Subject line of the email.
   - `message`: The body or content of the email.

2. **SMTP Configuration**:  
   The servlet uses the JavaMail API to configure an SMTP server. For Gmail:
   - SMTP host: `smtp.gmail.com`
   - SMTP port: `587` (TLS) or `465` (SSL)
   - SMTP authentication: Requires email username and password for authentication.

3. **Session and Authentication**:  
   The `Session.getInstance()` method sets up an email session, including SMTP server settings and the credentials used for authentication. It uses the `Authenticator` class for this purpose.

4. **Message Composition**:  
   A `MimeMessage` object is created, which holds the details of the email:
   - **From**: The sender’s email (your email).
   - **To**: The recipient’s email (from the form input).
   - **Subject**: Subject of the email (from the form input).
   - **Body**: The actual email content (from the form input).

5. **Sending the Email**:  
   The `Transport.send()` method is responsible for sending the message to the SMTP server, which then forwards it to the recipient.

6. **Handling Errors**:  
   A `MessagingException` is thrown if there is an issue during the sending process. The exception is caught and can be logged or shown as an error message to the user.

### **Additional Configuration (Security)**

#### **For Gmail**:
If you're using Gmail as the SMTP server, you might encounter issues due to Google's security settings, which block less secure apps by default. You need to:
1. **Enable "Less secure apps"** in your Google account:  
   - Go to your [Google Account Settings](https://myaccount.google.com/lesssecureapps) and allow less secure apps.
2. **Use App Passwords** (Preferred):  
   - If you have **2-step verification** enabled, use an **App Password** instead of your Gmail password.

---

### **Output**

1. When the user fills out the form and clicks **Send Email**, the servlet sends an email using the JavaMail API.
2. The user receives a success message on the web page, confirming that the email was sent.
3. The recipient will receive the email with the subject and message provided.

---

### **Conclusion**

Sending an email from a JSP/Servlet application is easy with the JavaMail API. This example demonstrates how to configure an SMTP server, set up the email session, and send an email using user-provided input. The key is to ensure the SMTP server settings (e.g., Gmail) are correct and that your email account allows the Java application to send emails.