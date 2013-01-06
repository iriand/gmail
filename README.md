GMail
=====

Module for sending email using GMail account.

```java
    public static void main(String[] args) {
        String username = "your_gmail_account_name@gmail.com";
        String password = "your_gmail_account_password";

        String[] recipients = new String[2];
        recipients[0] = "recipient_1@gmail.com";
        recipients[1] = "recipient_2@gmail.com";

        String[] files = new String[2];
        files[0] = "build.xml ";
        files[1] = "build.properties ";

        new GMail(username, password).send(username, null, null, recipients, "Test subject", "Test <strong>HTML</strong> <i>body</i>", files);
    }
```