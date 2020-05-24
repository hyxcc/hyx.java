import com.hyx.entity.Student;
import com.hyx.service.StudentService;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")

public class TestApp{
    private static final Logger logger = Logger.getLogger(TestApp.class.getName());

    @Resource(name = "studentService")
    private StudentService studentService;

    @Test
    public void insert100W() throws InterruptedException {
        //线程池： 提供一个线程队列，队列中保存着所有等待状态的线程。避免了创建与销毁的额外开销，提高了响应的速度。
        //引用线程池，创建固定大小的线程池
        ExecutorService executorService = Executors.newFixedThreadPool(1000);
        //通过CountDownLatch，我们可以导致线程阻塞，直到其他线程完成给定任务。
        //
        final CountDownLatch latch = new CountDownLatch(1);
        executorService.execute(new Runnable() {
            public void run() {

                long begin = System.currentTimeMillis();
                for (int i=1;i<1000;i++) {
                    List<Student> lists = new ArrayList<Student>();
                    for (int j = 1; j < 10000; j++) {
                        Student stu = new Student();
                        stu.setNumber(j);
                        stu.setName("周星星");
                        stu.setUniversity("西南财经大学");
                        stu.setBrother("周正华");
                        stu.setJob("PM工程师");
                        stu.setLink("www.jnshu.com");
                        stu.setTarget("不入IT誓不还");
                        Long s = Long.valueOf(new Date().getTime());
                        stu.setCreateTime(s);
                        stu.setUpdateTime(s);
                        stu.setQq("1131023043");
                        synchronized (stu){lists.add(stu);}
                    }
                    try{
                        studentService.insert(lists);
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                }

                System.out.println("......1");
                latch.countDown();
                long end = System.currentTimeMillis();
                logger.info("插入1000万条数据耗时：" + (end - begin));
            }
        });
        //开始等待，主线程挂起
        latch.await();
    }


    @Ignore
    public void findAll() {

        List<Student> list = studentService.findAll();
//            System.out.println(list);
            logger.info(studentService.findAll().toString());
    }
    @Test
    public void findOne() {
        List<Student> student = studentService.findOne(1);
        System.out.println(student);
        logger.info("查找到一个");
    }
    @Ignore
    public void update(){

        String university= "华东理大";
        studentService.updateById(university,1L);
        logger.info("大学信息修改成功");
    }

//    @Ignore
//    public void insert(){
//        Student stu = new Student();
//        stu.setNumber(458);
//        stu.setName("周星星");
//        stu.setQq("1131023043");
//        stu.setUniversity("西南财经大学");
//        stu.setBrother("周正华");
//        stu.setJob("PM工程师");
//        stu.setLink("www.jnshu.com");
//        stu.setTarget("不入IT誓不还");
//        Long s = Long.valueOf(new Date().getTime());
//        stu.setCreateTime(s);
//        stu.setUpdateTime(s);
//        studentService.insert(stu);
//        logger.info("新增成功" + stu.getName());
//    }
    @Ignore
    public void delete(){
        studentService.deleteById(3);
    }


}
