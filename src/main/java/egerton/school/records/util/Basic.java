package egerton.school.records.util;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class Basic {
    private String nationalId,phone,email,address,firstName,lastName,gender,YoB;
    private byte[] image;

    public Basic() {
    }

    @Column(name = "national_id",length =8,unique = true)
    public String getNationalId() {
        return nationalId;
    }
    @Column(name = "phone",length =10,nullable =false)
    public String getPhone() {
        return phone;
    }
    @Column(name = "email",length =100,nullable =false)
    public String getEmail() {
        return email;
    }
    @Column(name = "address",length =120,nullable =false)
    public String getAddress() {
        return address;
    }
    @Column(name = "first_name",length =15,nullable =false)
    public String getFirstName() {
        return firstName;
    }
    @Column(name = "last_name",length =15,nullable =false)
    public String getLastName() {
        return lastName;
    }
    @Column(name = "gender",length =7,nullable =false)
    public String getGender() {
        return gender;
    }
    @Column(name = "YoB",length =15,nullable =false)
    public String getYoB() {
        return YoB;
    }
    @Column(name = "photo")
    public byte[] getImage() {
        return image;
    }


    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setYoB(String yoB) {
        YoB = yoB;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
