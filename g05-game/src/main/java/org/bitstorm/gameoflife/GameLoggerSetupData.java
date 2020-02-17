package org.bitstorm.gameoflife;

import ch.hslu.vsk.logger.api.LogLevel;
import ch.hslu.vsk.logger.api.Logger;
import ch.hslu.vsk.logger.api.LoggerSetup;
import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static ch.hslu.vsk.logger.api.LoggerSetupFactory.*;

/**
 *
 * @author MatthiasKüng
 */
public class GameLoggerSetupData {

    LoggerSetup setup;
    private String jarPath = "./target/dependency/g06-loggercomponent-2.0.0.jar";
    private String className = "ch.hslu.vsk.logger.component.LoggerSetup";
    private String loggerID = "GameLogger";
    private int portNumber = 3200;
    private String addr = "localhost";
    private String logLevel = "LogLevel.INFO";
    File file;

    private BufferedWriter writer;
    private BufferedReader reader;

    public GameLoggerSetupData() throws FileNotFoundException {
        this.file = new File("./target/config.txt");
        this.writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8));
        this.reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));
    }

    public GameLoggerSetupData(File file) throws FileNotFoundException {
        this.file = file;
        this.writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8));
        this.reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));
    }



    public Logger getLogger() throws NoSuchMethodException, UnknownHostException {
        this.setup();
        this.createAndConfigureLoggerSetup();
        return setup.getLogger();
    }

    public void setup() {
        try{
        if(!file.exists()){
            file.createNewFile();
        }
        if(file.length() == 0) {
            this.writeDefaultParamsToFile();
        } else {
            this.setAttributesFromConfigFile();
        }
        }catch(IOException e){
           e.printStackTrace();
        }

    }




    private void writeDefaultParamsToFile() throws IOException {
        writer.write(jarPath + "\n");
        writer.write(className+ "\n");
        writer.write(loggerID+ "\n");
        writer.write(addr+ "\n");
        writer.write(portNumber+ "\n");
        writer.write(logLevel+ "\n");
        writer.flush();

        this.setAttributesFromConfigFile();

    }

    public void createAndConfigureLoggerSetup() throws UnknownHostException {
        System.out.println("1");
        setup = createLoggerSetup(jarPath, className);
        System.out.println("2");
            setup.setID(loggerID);
            setup.setServerHost(InetAddress.getByName(addr));
            setup.setServerPort(portNumber);
            setup.setLogLevel(LogLevel.INFO);
 

    }
    private void setAttributesFromConfigFile() throws UnknownHostException, FileNotFoundException {
        ArrayList<String> list = null;
        try{
             list = this.readFromConfigFile();
             this.jarPath = list.get(0);
             this.className = list.get(1);
             this.loggerID = list.get(2);
             this.addr = list.get(3);
             this.portNumber = Integer.valueOf(list.get(4));
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    private ArrayList<String> readFromConfigFile() throws IOException {
        ArrayList<String> tempList = new ArrayList <>();
        String line = null;
        while((line = reader.readLine()) != null){
            System.out.println("reading");
            tempList.add(line);
        }
        return tempList;
    }

    public ch.hslu.vsk.logger.api.Logger getStaticLoggerWithoutFactory() throws UnknownHostException {
        this.setup();
        LoggerSetup setup = new ch.hslu.vsk.logger.component.LoggerSetupComponent(loggerID, LogLevel.INFO, InetAddress.getByName(addr), portNumber);
        Logger logger = setup.getLogger();
        return logger;
    }




}
