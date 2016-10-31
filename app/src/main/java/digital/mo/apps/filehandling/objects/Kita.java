package digital.mo.apps.filehandling.objects;

import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Created by mohammadalirahimi
 */
public class Kita implements Serializable{
    // EinrNr
    // Name
    // Strasse
    // HausNr
    // HausNr_Alpha
    // PLZ
    // Ort
    // Stadtteil_Name
    // Bezirk_Name
    // AnsprechPartner
    // Telefon
    // Fax
    // Traeger_Name
    // URL
    // Email

    private int id;
    private String name, street, houseNo, houseNoAlpha, postal, place, district, precinct,
                   contactName, phone, fax, agencyName, url, email;
    private boolean selected;

    public Kita(int id) {
        this.id = id;
    }

    public Kita(int id, String name, String street, String houseNo, String houseNoAlpha, String postal,
                String place, String district, String precinct, String contactName, String phone,
                String fax, String agencyName, String url, String email) {
        this(id);
        this.name = name;
        this.street = street;
        this.houseNo = houseNo;
        this.houseNoAlpha = houseNoAlpha;
        this.postal = postal;
        this.place = place;
        this.district = district;
        this.precinct = precinct;
        this.contactName = contactName;
        this.phone = phone;
        this.fax = fax;
        this.agencyName = agencyName;
        this.url = url;
        this.email = email;
    }

    public static Kita createKita(String[] rawKitaValues){
        Kita created = null;
        // big try catch for errors
        // TODO: implement a better exception handling
        try{
            // raw data contains UTF8 control signals
            //TODO: find a better way to deal with UTF-X signals, maybe during parsing?
            if (rawKitaValues[0].startsWith("\ufeff")) {
                rawKitaValues[0] = rawKitaValues[0].substring(1);
            }
            int id = Integer.parseInt(rawKitaValues[0]);

            created = new Kita(id, rawKitaValues[1],
                    rawKitaValues[2], rawKitaValues[3], rawKitaValues[4], rawKitaValues[5],
                    rawKitaValues[6], rawKitaValues[7], rawKitaValues[8], rawKitaValues[9],
                    rawKitaValues[10], rawKitaValues[11], rawKitaValues[12], rawKitaValues[13],
                    rawKitaValues[14]);
        }catch (Exception e){
            Log.e("Kita creation exception","rawSize: "+ rawKitaValues.length + " "+e.getMessage());
        }
        return created;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouseNo() {
        return houseNo;
    }

    public void setHouseNo(String houseNo) {
        this.houseNo = houseNo;
    }

    public String getHouseNoAlpha() {
        return houseNoAlpha;
    }

    public void setHouseNoAlpha(String houseNoAlpha) {
        this.houseNoAlpha = houseNoAlpha;
    }

    public String getPostal() {
        return postal;
    }

    public void setPostal(String postal) {
        this.postal = postal;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getPrecinct() {
        return precinct;
    }

    public void setPrecinct(String precinct) {
        this.precinct = precinct;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getAgencyName() {
        return agencyName;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    public String toString() {
        return name;
    }

}
