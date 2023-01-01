public class Member {

    //成员信息
    private Integer id;
    private String name;
    private String gender;
    private String college;
    private String major;
    private String campus;
    private String wx;
    private String phone;
    private String categories;

    public Member() {
    }

    public Member(Integer id, String name, String gender, String college, String major, String campus, String wx, String phone, String categories) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.college = college;
        this.major = major;
        this.campus = campus;
        this.wx = wx;
        this.phone = phone;
        this.categories = categories;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getCampus() {
        return campus;
    }

    public void setCampus(String campus) {
        this.campus = campus;
    }

    public String getWx() {
        return wx;
    }

    public void setWx(String wx) {
        this.wx = wx;
    }

    public String  getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", college='" + college + '\'' +
                ", major='" + major + '\'' +
                ", campus='" + campus + '\'' +
                ", wx='" + wx + '\'' +
                ", phone=" + phone +
                ", categories='" + categories + '\'' +
                '}';
    }
}
