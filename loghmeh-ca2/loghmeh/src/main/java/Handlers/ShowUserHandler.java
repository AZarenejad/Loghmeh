package Handlers;

import Classes.Interface;
import io.javalin.http.Context;
import io.javalin.http.Handler;

public class ShowUserHandler implements Handler {

    private Interface system;

    public ShowUserHandler(Interface system) {
        this.system = system;
    }

    public void handle(Context context) throws Exception {
        String response ="";
                response +="<!DOCTYPE html>\n" +
                        "<html lang=\"en\">\n" +
                        "<head>\n" +
                        "    <meta charset=\"UTF-8\">\n" +
                        "    <title>User</title>\n" +
                        "    <style>\n" +
                        "        li {\n" +
                        "        \tpadding: 5px\n" +
                        "        }\n" +
                        "    </style>\n" +
                        "</head>\n" +
                        "<body>\n" +
                        "    <ul>\n" +
                        "        <li>id:" + system.getUser().getId() +"</li>\n" +
                        "        <li>full name: " + system.getUser().getUserFirstName() +" " + system.getUser().getUserLastName() +"</li>\n" +
                        "        <li>phone number: " + system.getUser().getPhoneNumber() +"</li>\n" +
                        "        <li>email: " + system.getUser().getEmail()+"</li>\n" +
                        "        <li>credit: "+system.getUser().getCredit()+ " Toman" + "</li>\n" +
                        "        <form action=\"increase_credit\" method=\"POST\">\n" +
                        "            <button type=\"submit\">increase</button>\n" +
                        "            <input type=\"text\" name=\"credit\" value=\"\" />\n" +
                        "        </form>\n" +
                        "    </ul>\n" +
                        "</body>\n" +
                        "</html>";
                context.html(response);
    }
}
