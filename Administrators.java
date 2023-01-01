public class Administrators {
    private Integer id;
    private String password;

    public Administrators() {
    }

    public Administrators(Integer id, String password) {
        this.id = id;
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Administrators{" +
                "id=" + id +
                ", password='" + password + '\'' +
                '}';
    }
}
