/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.mosica.javaBasicTraining.owm;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author bertrand
 */
// Open weather map api key : 
// appid=8c05dfed7d5d0d8ba3a2bc70b83b227f
public class OwmClient {

    private static final Logger LOG = LogManager.getLogger(OwmClient.class);

    /**
     * URL du serveur
     */
    private final URL ownUrl;

    private ObjectMapper jsonMapper;

    public OwmClient(URL ownUrl) {
        this.ownUrl = ownUrl;
        this.jsonMapper = new ObjectMapper();
        // attention à la configuration du mapper
        this.jsonMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    /**
     *
     * @return
     */
    public WeatherResult getWeather(String CP) {
        // String url = "http://api.openweathermap.org/data/2.5/weather?zip=79430,fr&APPID=8c05dfed7d5d0d8ba3a2bc70b83b227f";
        // pour avoir une erreur 401 qui remonte sur le main => String url = "http://api.openweathermap.org/data/2.5/weather?zip=79430,fr";

        String url = "http://api.openweathermap.org/data/2.5/weather?zip=" + CP + ",fr&APPID=8c05dfed7d5d0d8ba3a2bc70b83b227f";
        //[TODO] By name String url = "http://api.openweathermap.org/data/2.5/weather?q=" + CP + "&APPID=8c05dfed7d5d0d8ba3a2bc70b83b227f";

        // déclarations de variables locales
        WeatherResult weatherResult = null;
        HttpURLConnection owmConnection = null;

        //lire le flux et le convertir en objet
        try {
            URL owmUrl = new URL(url);
            owmConnection = (HttpURLConnection) owmUrl.openConnection();
            // sortie en erreur si le code retour est KO <>200
            if (owmConnection.getResponseCode() != 200) {
                throw new TechnicalException("Statut de la réponse invalide (code retour = '" + owmConnection.getResponseCode() + "' / message = '" + owmConnection.getResponseMessage() + "')");
            }
            // pour avoir une sortie structurée du flux : http://json.parser.online.fr/
            weatherResult = this.jsonMapper.readValue(owmConnection.getInputStream(), WeatherResult.class);
        } catch (MalformedURLException ex) {
            throw new TechnicalException("Oups ! Pb sur l'URL", ex);
        } catch (IOException ex) {
            throw new TechnicalException("Oups ! I/O erreur", ex);
        } finally {
            if (owmConnection != null) {
                owmConnection.disconnect();
            }
        }

        return weatherResult;
    }

    public URL getOwnUrl() {
        return ownUrl;
    }

    public ObjectMapper getJsonMapper() {
        return jsonMapper;
    }

    public void setJsonMapper(ObjectMapper jsonMapper) {
        this.jsonMapper = jsonMapper;
    }
}
