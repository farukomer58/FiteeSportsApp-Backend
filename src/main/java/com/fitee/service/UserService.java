package com.fitee.service;


import com.fitee.exception.ResourceAlreadyExistsException;
import com.fitee.model.User;
import com.fitee.repository.UserRepository;
import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

//Encoding


@Service
@AllArgsConstructor
public class UserService {

    private static final String EMAIL_SUBJECT = "Activate Account";
    private static final String FROM_ADDRESS = "info@fitee.nl";
    private static final String HOST_ADDRESS = "http://localhost:4200";
    private static final String HEROKU_ADDRESS = "";

    // Dependencies
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
//    private final BCryptPasswordEncoder byCryptPasswordEncoder;
//    private final JavaMailSender javaMailSender;
//    private final JwtProvider jwtProvider;

    /**
     * Registers a new user to the database. New users containing an already owned username are
     * ignored and thrown with a ResourceAlreadyExistsException indicating duplication of a given
     * resource (e.g. not unique username).
     */
    public User registerUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new ResourceAlreadyExistsException("An existing resource was found");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
//        user.setLocked(false);
        return userRepository.save(user);
    }

    /**
     * Retrieves the current context user from the database. Throws a ResourceNotFoundException if
     * the user entity could not be found in the database.
     */
    public User getCurrentUser() {
//        var principal = (Principal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        return userRepository.findByIdWithSupplier(principal.getId())
//                .orElseThrow(() -> new ResourceNotFoundException("Current user not found in database."));
        return userRepository.findById(2l).get();
    }

//    /**
//     * Retrieves the current context user from the database. Throws a ResourceNotFoundException if
//     * the user entity could not be found in the database.
//     */
//    public UserEntity getCurrentUser() {
//        var principal = (Principal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        return userRepository.findByIdWithSupplier(principal.getId())
//                .orElseThrow(() -> new ResourceNotFoundException("Current user not found in database."));
//    }
//
//    /**
//     * Verifies the user. Updates the current status of the locked to false so the user can access their account.
//     */
//    public void verifyUser(String token) {
//        Object validateEmail = jwtProvider.verify(token);
//        if (validateEmail != null) {
//            Optional<UserEntity> data = userRepository.findByUsername(validateEmail.toString());
//            if (data.isPresent()) {
//                data.get().setLocked(false);
//                userRepository.save(data.get());
//            }
//        }
//    }
//
//    /**
//     * Sends email to user with a link to verify the e-mailaddress.
//     */
//    public void sendVerifyingEmail(UserEntity user, String token) throws MailException, MessagingException {
//        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
//
//        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
//
//        helper.setFrom(FROM_ADDRESS);
//        helper.setTo(user.getUsername());
//        helper.setSubject(EMAIL_SUBJECT);
//        helper.setText("<html lang=\"en\"><body style=\"background-color: #fafbfc; padding-top:40px; " +
//                        "font-family:'Open+Sans', 'Open Sans', Helvetica, Arial, sans-serif;\"><table border=\"0\" " +
//                        "cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n <tbody><tr><td><div
//                        style=\"width:100%;" +
//                        "background-color:#fafbfc;-webkit-text-size-adjust:100%;-ms-text-size-adjust:100%;\">" +
//                        "<div id=\"maincontent\" style=\"max-width:620px; font-size:0;margin:0 auto;\">\n" +
//                        "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"width:100%;\">" +
//                        "<tbody><tr><td><table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"width:100%;
//                        \">\n" +
//                        "<tbody><tr><td align=\"center\" style=\"padding-bottom:20px;\">\n <table border=\"0\" " +
//                        "cellpadding=\"0\" cellspacing=\"0\" style=\"font-size:13px; line-height:18px;
//                        color:#255239; " +
//                        "text-align:center; width:152px;\">\n <tbody><tr><td style=\"padding:20px 0 10px 0;\">" +
//                        "<a href=\"#tba\" style=\"text-decoration:none;\" target=\"_blank\">" +
//                        "<img border=\"0\" height=\"50\"\n " +
//                        "src=\"https://cdn.shopify
//                        .com/s/files/1/0021/1966/3675/files/Van-streek-logo-liggend-zwart_482x.png\"\n" +
//                        "style=\"display:block; width:152px !important; font-size:22px; line-height:26px; " +
//                        "color:#000000; text-transform:uppercase; text-align:center; letter-spacing:1px;\"\n
//                        width=\"152\">" +
//                        "</a></td></tr></tbody></table></td></tr></tbody></table></td></tr>\n <tr><td><table
//                        border=\"0\" " +
//                        "cellpadding=\"0\" cellspacing=\"0\" style=\"width:100%;\"><tbody><tr>\n <td><table
//                        border=\"0\" " +
//                        "cellpadding=\"0\" cellspacing=\"0\" style=\"width:100%;\"><tbody>\n <tr><td
//                        style=\"text-align:center;" +
//                        "padding:40px 40px 40px 40px; border-top:3px solid #255239; background-color: white;\">\n" +
//                        "<div style=\"display:inline-block; width:100%; max-width:520px;\">\n" +
//                        "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\"\n" +
//                        "style=\"font-size:14px; line-height:24px; color:#525C65; text-align:left; width:100%;\">\n" +
//                        "<tbody>\n" +
//                        "<tr><td><p style=\"margin:0; font-size:18px; line-height:23px; color:#102231;
//                        font-weight:bold;\">\n" +
//                        "       <strong>Welkom " + user.getFullname() + ",</strong><br><br></p></td></tr>\n
//                        <tr><td>\n" +
//                        "       Bedankt voor het registreren. Je ben nog maar één stap verwijderd van het gebruiken
//                        van je\n" +
//                        "       account. Om je registratie te voltooien dien je je email te verifiëren: <br><br>\n" +
//                        "</td></tr>\n <tr><td align=\"center\" style=\"padding:15px 0 40px 0; border-bottom:1px
//                        solid #f3f6f9;\">\n" +
//                        "<table border=\"0\" cellpadding=\"0\"\n" +
//                        "cellspacing=\"0\"\n style=\"border-collapse:separate !important; border-radius:15px;
//                        width:210px;\">\n" +
//                        "<tbody><tr><a href=\"" + HOST_ADDRESS + "/verify?token=" + token + "\"\n
//                        target=\"_blank\"\n style=\"background-color:#255239; " +
//                        "border-collapse:separate !important; border-top:10px solid #255239;\n" +
//                        "border-bottom:10px solid #255239; border-right:45px solid #255239; border-left:45px solid
//                        #255239;\n" +
//                        "border-radius:4px; color:#ffffff; display:inline-block; font-size:13px; font-weight:bold;
//                        \n" +
//                        "text-align:center; text-decoration:none; letter-spacing:1px;\">VERIFIEER
//                        EMAIL</a></tr></tbody>\n" +
//                        "</table>\n</td>\n</tr>\n<tr><td style=\"padding-top:25px;\">\n" +
//                        "       <p style=\"margin: 20px 0 20px 0;color: black;\">Of kopieer en plak deze link in je
//                        web browser</p>\n" +
//                        "       <p style=\"margin:20px 0; font-size:12px; line-height:17px; word-wrap:break-word;
//                        word-break:break-all;\">\n" +
//                        "       <a href=\"" + HOST_ADDRESS + "/verify?token=" + token + "\"\nstyle=\"color:#5885ff;
//                        text-decoration:underline;\"\n" +
//                        "       target=\"_blank\"> " + HOST_ADDRESS + "/verify?token=" + token +
//                        "</a>\n</p>\n</td>\n</tr>\n</tbody>\n" +
//                        "</table>\n</div>\n</td>\n</tr>\n<tr><td style=\"background-color:#255239; height:1px; " +
//                        "width:100%; line-height:1px; font-size:0;\">&nbsp;</td>\n </tr><tr> <td
//                        style=\"background-color:#255239; height:1px; width:100%; line-height:1px; font-size:0;
//                        \">&nbsp;</td></tr>\n" +
//                        "<tr></tr><td style=\"background-color:#255239; height:1px; width:100%; line-height:1px;
//                        font-size:0;\">&nbsp;</td></tr>\n" +
//                        "<tr><td style=\"background-color:#255239; height:1px; width:100%; line-height:1px;
//                        font-size:0;\">&nbsp;</td></tr></tbody>\n" +
//                        "</table></td>\n<td style=\"background-color: #edeef1; width:1px; font-size:1px;\">&nbsp;
//                        </td>\n" +
//                        "<td style=\"background-color: #edeef1; width:1px; font-size:1px;\">&nbsp;</td>\n" +
//                        "<td style=\"background-color: #edeef1; width:1px; font-size:1px;\">&nbsp;</td>\n" +
//                        "<td style=\"background-color: #edeef1; width:1px; font-size:1px;\">&nbsp;</td>\n" +
//                        "</tr>\n</tbody>\n</table>\n</td>\n" +
//                        "</tr><tr><td style=\"padding-bottom:40px; padding-top:40px\">\n" +
//                        "<table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"\n" +
//                        "style=\"font-size:12px; line-height:18px; width:auto;\">\n" +
//                        "<tbody><tr><td style=\"color:#b7bdc1;\">\n<p style=\"margin:0;\">" +
//                        "           <span class=\"appleLinksGrey\">Bezoek rechtstreeks onze website op </span>\n" +
//                        "           <a href=\"" + HOST_ADDRESS + "\"\n" +
//                        "           style=\"color:#5885ff; text-decoration:underline;\"\n" +
//                        "           target=\"_blank\">vanstreek.nl</a></p></td></tr></tbody>\n</table>\n" +
//                        "</td>\n</tr>\n</tbody>\n</table>\n</div>\n</div>\n</td>\n</tr>\n</tbody>\n</table>\n</body
//                        >\n</html>",
//                true);
//        javaMailSender.send(mimeMessage);
//    }
}
