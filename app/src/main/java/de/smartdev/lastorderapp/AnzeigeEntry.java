package de.smartdev.lastorderapp;

/**
 * Created by Andi on 14.03.2016.
 */
public class AnzeigeEntry {
private String restaurantName;
private String id;
private String anzeigenText;
private String tags;
private String adresse;
private String lifetime;
private String userAnzeigeId;

@SuppressWarnings("unused")
public AnzeigeEntry() {
        //needed for firebaseUI, before changing check documentation https://github.com/firebase/firebaseui-android
        }


public AnzeigeEntry(String restaurantName, String id, String anzeigenText, String tags, String adresse, String lifetime, String userAnzeigeId) {
        this.restaurantName = restaurantName;
        this.id = id;
        this.anzeigenText = anzeigenText;
        this.tags = tags;
        this.adresse = adresse;
        this.lifetime = lifetime;
        this.userAnzeigeId = userAnzeigeId;

        }

public String getRestaurantName() {
        return restaurantName;
        }

public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
        }

public String getAnzeigenText() {
        return anzeigenText;
        }

public void setAnzeigenText(String anzeigenText) {
        this.anzeigenText = anzeigenText;
        }

public String getTags() {
        return tags;
        }

public void setTags(String tags) {
        this.tags = tags;
        }

public String getAdresse() {
        return adresse;
        }

public void setAdresse(String adresse) {
        this.adresse = adresse;
        }

public String getLifetime() {
        return lifetime;
        }

public void setLifetime(String lifetime) {
        this.lifetime = lifetime;
        }

public String getUserAnzeigeId() {
        return userAnzeigeId;
        }

public void setUserAnzeigeId(String userAnzeigeId) {
        this.userAnzeigeId = userAnzeigeId;
        }

public String getId() {
        return id;
        }

public void setId(String id) {
        this.id = id;
        }
        }