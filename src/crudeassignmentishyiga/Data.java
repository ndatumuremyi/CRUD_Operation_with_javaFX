package crudeassignmentishyiga;

public class Data {
    String dataId;
    String sex;
    String department;
    String favouriteLanguage;
    String fullName;

    public Data() {
    }

    public Data(String sex, String department, String favouriteLanguage, String fullName) {
        this.sex = sex;
        this.department = department;
        this.favouriteLanguage = favouriteLanguage;
        this.fullName = fullName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getFavouriteLanguage() {
        return favouriteLanguage;
    }

    public void setFavouriteLanguage(String favouriteLanguage) {
        this.favouriteLanguage = favouriteLanguage;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }
}
