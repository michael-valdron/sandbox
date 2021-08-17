package annotations;

import java.util.Locale;

@JsonSerializable
public class Person {
    @JsonElement
    private String personName;

    @JsonElement(key = "personAge")
    private short age;

    @JsonElement(key = "personAddress")
    private String address;

    public Person(String personName, int age, String address) {
        this.personName = personName;
        this.age = (short) age;
        this.address = address;
    }

    @Init
    private void initName() {
        var sep = this.personName.indexOf(' ');

        this.personName = this.personName.substring(0, 1).toUpperCase()
                + this.personName.substring(1);

        if (sep != -1)
            this.personName = this.personName.substring(0, sep+1)
                    + this.personName.substring(sep+1, sep+2).toUpperCase()
                    + this.personName.substring(sep+2);
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = (short) age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
