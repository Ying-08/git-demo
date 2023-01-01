import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

public class memSys_02 {
    public memSys_02() {
    }

    public static void main(String[] args) throws Exception {
        Connection connection=null;

        connection=JDBCUtils.getConnection();

        //创建对象
        JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDatasource());

        Scanner sc = new Scanner(System.in);

        while (true){
            System.out.println("管理员登录输入A，用户登录输入B");
            String choose1=sc.next();
            switch (choose1){
                case "A":
                    loginAdm(connection,template);
                    break;
                case "B":
                    loginUser(connection,template);
                    break;
                default:
                    System.out.println("请重新输入");
            }
        }

    }

    //用户登录
    private static void loginUser(Connection connection,JdbcTemplate template) throws Exception {
        Scanner sc = new Scanner(System.in);
        while (true){
            System.out.println("请输入你的验证码");
            boolean flag=code();
            if(flag){
                System.out.println("请输入你的账号和密码");
                Integer id=sc.nextInt();
                String password=sc.next();

                //判断是否登录成功
                String sql="select count(id) from student where id=? and password=?;";
                Long judge = template.queryForObject(sql, Long.class, id, password);
                if(judge==1){
                    System.out.println("----------------登录成功------------------");
                    System.out.println("需要修改密码输入1，否则输入0");
                    int choose=sc.nextInt();
                    if(choose==1){
                        updatePass(id,template);
                    } else if(choose==0){
                        userPrivilege(connection,template);
                        break ;
                    }else {
                        System.out.println("输入的数字无效");
                    }
                }else {
                    System.out.println("登录失败");
                    System.out.println("找回密码请输入1，重新登录请随机输入数字");
                    int choose1=sc.nextInt();
                    if (choose1 == 1) {
                        findPass(template);
                    }
                }
            }
        }
    }

    //修改密码
    public static void updatePass(Integer id,JdbcTemplate template){
        Scanner sc=new Scanner(System.in);
        System.out.println("请输入你修改后密码");
        String password = sc.next();

        int count= 0;
        try {
            String sql="update student  set password=? where id=?";
            count = template.update(sql,password,id);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }

        judgeMent(count);
    }

    //找回密码
    public static void findPass(JdbcTemplate template){

            Scanner sc=new Scanner(System.in);

            System.out.println("请输入你的账号");
            Integer id=sc.nextInt();
        String sql= null;
        Long judge = null;
        try {
            sql = "select count(id) from student where id=?;";
            judge = template.queryForObject(sql, Long.class, id);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        //账号正确
            if(judge==1){
                sql="select password from student where id=?;";

                User user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), id);
                System.out.println("你的密码是：");
                System.out.println(user.getPassword());
            }else {
                System.out.println("该账号不存在");
            }
    }

    //验证码
    public static boolean code(){
        Random r=new Random();
        int number=r.nextInt(9999)+1000;
        Scanner sc=new Scanner(System.in);
        System.out.println("你的验证码是：");
        System.out.println(number);
        System.out.println("请输入你的验证码");
        int code=sc.nextInt();
        if(code==number){
            return true;
        }else {
            System.out.println("你的验证码错误，请重新输入");
            return false;
        }
    }

    //用户权限
    public static void userPrivilege(Connection connection,JdbcTemplate template) throws Exception {
        Scanner sc=new Scanner(System.in);
        loop1:while (true){
            System.out.println("---------------欢迎来到用户端-----------------");
            System.out.println("1：显示团队成员列表");
            System.out.println("2：按组别展示该组别内所有成员列表");
            System.out.println("3:根据某一信息进行模糊搜索");
            System.out.println("4:退出登录");
            System.out.println("请输入你的选择：");
            int choose=sc.nextInt();
            switch (choose){
                case 1:
                    memQuery(connection,template);
                    break;
                case 2:
                    cateMember(template);
                    break;
                case 3:
                    search(connection,template);
                    break;
                case 4:
                    break loop1;
                default:
                    System.out.println("没有这个选项");
                    break ;
            }
        }
    }

    //管理员登录
    private static void loginAdm(Connection connection,JdbcTemplate template) throws Exception {
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入账号和密码");
        Integer id=sc.nextInt();
        String password=sc.next();

        //判断是否登录成功
        String sql="select count(id) from administrator where id=? and password=?;";
        Long judge = template.queryForObject(sql, Long.class, id, password);
        if(judge==1){
            adminPrivilege(connection,template);
        }else {
            System.out.println("登录失败");
        }
    }

    //管理员权限
    public static void adminPrivilege(Connection connection,JdbcTemplate template) throws Exception {
        Scanner sc=new Scanner(System.in);
        //选择
        loop:while (true) {
            System.out.println("--------欢迎来到成员系统管理员端------------");
            System.out.println("1：添加成员信息");
            System.out.println("2：修改成员信息");
            System.out.println("3：删除成员信息");
            System.out.println("4：查看成员信息");
            System.out.println("5：按组别展示所有成员信息");
            System.out.println("6:模糊搜索");
            System.out.println("7：退出");
            System.out.println("请输入你的选择：");
            int choose = sc.nextInt();
            switch (choose) {
                case 1:
                    memAdd(connection, template);
                    break;
                case 2:
                    memUpdate(connection, template);
                    break;
                case 3:
                    memDelete(connection, template);
                    break;
                case 4:
                    memQuery(connection, template);
                    break;
                case 5:
                    cateMember(template);
                    break ;
                case 6:
                    search(connection,template);
                    break ;
                case 7:
                    break loop;
                default:
                    System.out.println("没有这个选项，请重新输入");
                    break ;
            }
        }
    }

    //查询用户数据
    public static void memQuery(Connection connection,JdbcTemplate template) throws Exception{

        Scanner sc2=new Scanner(System.in);

        //定义SQL
        String sql="select  id,name,gender,college,categories from student ;";

        List<Member_1> list=template.query(sql,new BeanPropertyRowMapper<Member_1>(Member_1.class));
        for(Member_1 member_1:list){
            System.out.println(member_1);
        }

        while (true){
            System.out.println("如需查看成员的详细信息，请输入1，否则输入0");
            int choose1=sc2.nextInt();
            if(choose1==1){
                System.out.println("请输入成员的id：");
                Integer id = sc2.nextInt();
                try {
                    sql="select * from student where id=?;";

                    Map<String,Object> map=template.queryForMap(sql,id);

                    System.out.println(map);
                } catch (DataAccessException e) {
                    e.printStackTrace();
                }
            }else {
                break;
            }
     }
 }

    //添加成员
    public static void memAdd(Connection connection,JdbcTemplate template) throws Exception{

        Scanner sc1=new Scanner(System.in);
        System.out.println("请输入成员学号id");
        int id=sc1.nextInt();
        System.out.println("请输入成员姓名");
        String name = sc1.next();
        System.out.println("请输入成员性别");
        String gender= sc1.next();
        System.out.println("请输入成员学院");
        String college= sc1.next();
        System.out.println("请输入成员校区");
        String campus= sc1.next();
        System.out.println("请输入成员专业");
        String major= sc1.next();
        System.out.println("请输入成员微信");
        String wx= sc1.next();
        System.out.println("请输入成员电话号码");
        String phone= sc1.next();
        System.out.println("请输入成员组别");
        String categories= sc1.next();

        String password=String.valueOf(id);

        //定义SQL
        int count= 0;
        try {
            String sql="insert into student (name,gender,college,campus,major,wx,phone,categories,id,password)" +
                    "values (?,?,?,?,?,?,?,?,?,?);";

            //执行SQL
            count = template.update(sql,name,gender,college,campus,major,wx,phone,categories,id,password);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }

        judgeMent(count);
    }

    //修改成员信息
    public static void memUpdate(Connection connection,JdbcTemplate template) throws Exception {
        Scanner sc=new Scanner(System.in);
        System.out.println("请输入需要修改的成员学号id：");
        Integer id = sc.nextInt();
        Member member1 = null;
        int choose1=1;
        try {
            String sql1="select * from student where id=?;";

            member1 = template.queryForObject(sql1, new BeanPropertyRowMapper<Member>(Member.class), id);
        } catch (DataAccessException e) {
            choose1=0;
            e.printStackTrace();

        }
        if (choose1==1) {
            String gender=member1.getGender(),college=member1.getCollege(),
                    campus=member1.getCampus(),major=member1.getMajor(),wx=member1.getWx(),categories=member1.getCategories();
            String  phone=member1.getPhone();

            System.out.println("请输入你需要进行修改的类别");
            System.out.println("性别输入1，学院输入2，校区输入3,专业输入4，微信输入5，电话输入6，部门输入7");

            int choose=sc.nextInt();
            switch (choose){
                case 1:
                    System.out.println("请输入修改内容");
                     gender=sc.next();
                    break;
                case 2:
                    System.out.println("请输入修改内容");
                     college=sc.next();
                    break;
                case 3:
                    System.out.println("请输入修改内容");
                     campus=sc.next();
                    break;
                case 4:
                    System.out.println("请输入修改内容");
                     major=sc.next();
                    break;
                case 5:
                    System.out.println("请输入修改内容");
                     wx=sc.next();
                    break;
                case 6:
                    System.out.println("请输入修改内容");
                     phone=sc.next();
                    break;
                case 7:
                    System.out.println("请输入修改内容");
                     categories=sc.next();
                    break;

            }

            //定义SQL
            String sql = "update student \n set gender=?,\n college=?,\n" +
                    "campus=?,\n major=?,\n wx=?,\n phone=?,\n categories=?\n where id=?;";
            int count=template.update(sql,gender,college,campus,major,wx,phone,categories,id);
            judgeMent(count);
        }
    }

    //删除用户数据
    public static void memDelete(Connection connection,JdbcTemplate template) throws SQLException {
        System.out.println("请输入要删除成员的id");
        Scanner sc1=new Scanner(System.in);
        Integer id = sc1.nextInt();

        String sql="delete from student where id=?";

        int count=template.update(sql,id);

        judgeMent(count);

    }

    //根据组别查找成员
    public static void cateMember(JdbcTemplate template){
        Scanner sc=new Scanner(System.in);
        System.out.println("请输入你要查找的组别");
        String cate=sc.next();
        String sql="select * from student where categories=?;";
        List<Member> list=template.query(sql,new BeanPropertyRowMapper<Member>(Member.class),cate);
        for(Member member:list){
            System.out.println(member);
        }
    }

    //模糊搜索
    public static void search(Connection connection,JdbcTemplate template){
        String name=null,gender=null,college=null,campus=null,major=null,wx=null,categories=null;
        Integer id=null;
        String phone=null;

        Scanner sc=new Scanner(System.in);
        System.out.println("请输入你需要进行模糊查找的类别");
        System.out.println("姓名输入1，性别输入2，学院输入3，校区输入4,专业输入5，微信输入6，电话输入7，部门输入8,id输入9");
        int choose=sc.nextInt();
        switch (choose){
            case 1:
                System.out.println("请输入查询内容");
                name=sc.next();
                break;
            case 2:
                System.out.println("请输入查询内容");
                gender=sc.next();
                break;
            case 3:
                System.out.println("请输入查询内容");
                college=sc.next();
                break;
            case 4:
                System.out.println("请输入查询内容");
                campus=sc.next();
                break;
            case 5:
                System.out.println("请输入查询内容");
                major=sc.next();
                break;
            case 6:
                System.out.println("请输入查询内容");
                wx=sc.next();
                break;
            case 7:
                System.out.println("请输入查询内容");
                phone=sc.next();
                break;
            case 8:
                System.out.println("请输入查询内容");
                categories=sc.next();
                break;
            case 9:
                System.out.println("请输入查询内容");
                id=sc.nextInt();
                break;
        }

        String sql = "select * from student where name like ? or gender like ? or college like ? or campus like ?" +
                "or major like ? or wx like ? or phone like ? or categories like ? or id like ?";
        List<Member> list=template.query(sql,new BeanPropertyRowMapper<Member>(Member.class),"%" + name + "%",
                "%" + gender + "%","%" + college + "%","%" + campus + "%","%" + major + "%","%" + wx + "%",
                "%" + phone + "%","%" + categories + "%","%" + id + "%");

        for(Member member:list){
            System.out.println(member);
        }
    }

    //判断
    public static  void judgeMent(int count){
        if(count>0){
            System.out.println("成功");
        }else{
            System.out.println("失败");
        }
    }

}

