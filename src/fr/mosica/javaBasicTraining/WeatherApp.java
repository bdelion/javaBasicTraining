/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.mosica.javaBasicTraining;

import fr.mosica.javaBasicTraining.owm.OwmClient;
import fr.mosica.javaBasicTraining.owm.TechnicalException;
import fr.mosica.javaBasicTraining.owm.WeatherResult;
        

import java.util.Scanner;

/**
 *
 * @author bertrand
 */
public class WeatherApp {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // To run this application from the command line without Ant, try:
        // java -jar "C:\Users\MOSICA\Documents\NetBeansProjects\javaBasicTraining\dist\javaBasicTraining.jar"
        System.out.println("Veuillez saisir le code postal de la ville dont vous souhaitez avoir la météo : ");
        // utilisation de Scanner (lecture des entrées clavier)
        Scanner sc = new Scanner(System.in);
        String codePostal = sc.nextLine();
        //[TODEL] System.out.println("Code postal de la ville recherchée : " + codePostal);

        // appel au service de météo
        OwmClient owmc = new OwmClient(null);
        try {
            //[TODEL] owmc.getWeather();
            //[TODEL] System.out.println(MessageTemplateReader.read());
            WeatherResult cpWeather = owmc.getWeather(codePostal);
//            System.out.println(MessageTemplateReader.read().replace("{city}", cpWeather.getName())
//                                        .replace("{cp}", codePostal)
//                                        .replace("{temp}", String.valueOf(cpWeather.getMain().getTemp() - 273.15))
//                                        .replace("{wind}", String.valueOf(cpWeather.getWind().getSpeed())));
//            System.out.println("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
//            System.out.println(MessageTemplateReader.read().replace("{city}", cpWeather.getName())
//                                        .replace("{cp}", codePostal)
//                                        .replace("{temp}", String.format(Locale.FRANCE, "%.2f °C", cpWeather.getMain().getTemp() - 273.15))
//                                        .replace("{wind}", String.valueOf(cpWeather.getWind().getSpeed())));
            String msgtmpl = MessageTemplateReader.read();

            if (!"".equals(msgtmpl)) {
                System.out.println("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
                System.out.println(MessageTemplateReader.read().replace("{city}", cpWeather.getName())
                        .replace("{cp}", codePostal)
                        .replace("{temp}", String.format("%.2f °C", cpWeather.getMain().getTemp() - 273.15))
                        .replace("{wind}", String.format("%.2f m/sec", cpWeather.getWind().getSpeed())));
                System.out.println("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
            } else {
                System.out.println("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
                System.out.println("Template vide !");
                System.out.println("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
            }
        } catch (TechnicalException te) {
            te.printStackTrace(System.err);
        }
    }

}
