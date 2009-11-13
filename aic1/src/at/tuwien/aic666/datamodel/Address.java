/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package at.tuwien.aic666.datamodel;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author peter
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "address")
public class Address {

    @XmlAttribute(name = "id")
    private String id;
    private String street;
    private String city;
    private String zipCode;
    private int house;
    private int door;

    public Address() {
    }

    //Help Constructor
    public Address(String id, String street, String city, String zipCode, int house, int door) {
        this.id = id;
        this.street = street;
        this.city = city;
        this.zipCode = zipCode;
        this.house = house;
        this.door = door;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getDoor() {
        return this.door;
    }

    public void setDoor(int door) {
        this.door = door;
    }

    public int getHouse() {
        return this.house;
    }

    public void setHouse(int house) {
        this.house = house;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStreet() {
        return this.street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZipCode() {
        return this.zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
}
