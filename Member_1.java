public class Member_1 {
    private Integer id;
    private String name;
    private String gender;
    private String college;
    private String categories;

    public Member_1() {
    }

    public Member_1(Integer id, String name, String gender, String college, String categories) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.college = college;
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

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    @Override
    public String toString() {
        return "member_1{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", college='" + college + '\'' +
                ", categories='" + categories + '\'' +
                '}';
    }
}
