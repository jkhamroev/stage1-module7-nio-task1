package com.epam.mjc.nio;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;


public class FileReader {

    public Profile getDataFromFile(File file) {
        try (RandomAccessFile access = new RandomAccessFile(file, "r")) {
            FileChannel channel = access.getChannel();
            ByteBuffer buffer = ByteBuffer.allocate(256);
            int bytesRead = channel.read(buffer);
            StringBuilder content = new StringBuilder();
            while (bytesRead != -1) {
                buffer.flip();
                while (buffer.hasRemaining()) {
                    content.append((char) buffer.get());
                }
                buffer.clear();
                bytesRead = channel.read(buffer);
            }
            String[] lines = content.toString().split(System.lineSeparator());
            String name = getValue(lines[0]);
            int age = Integer.parseInt(getValue(lines[1]));
            String email = getValue(lines[2]);
            long phone = Long.parseLong(getValue(lines[3]));
            return new Profile(name, age, email, phone);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Profile();
    }

    private String getValue(String line) {
        return line.substring(line.indexOf(' ') + 1);
    }
}
