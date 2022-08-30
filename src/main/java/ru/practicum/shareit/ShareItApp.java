package ru.practicum.shareit;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class ShareItApp {

    public static void main(String[] args) {

        new SpringApplicationBuilder()
                .banner((environment, sourceClass, out) -> out.print("\n" +
                        " _____ _   _   ___  ______ _____   _____ _____ \n" +
                        "/  ___| | | | / _ \\ | ___ \\  ___| |_   _|_   _|\n" +
                        "\\ `--.| |_| |/ /_\\ \\| |_/ / |__     | |   | |  \n" +
                        " `--. \\  _  ||  _  ||    /|  __|    | |   | |  \n" +
                        "/\\__/ / | | || | | || |\\ \\| |___   _| |_  | |  \n" +
                        "\\____/\\_| |_/\\_| |_/\\_| \\_\\____/   \\___/  \\_/  \n" +
                        "                                               \n"))
                .sources(ShareItApp.class)
                .run(args);
    }
}
