package dongyang.spmis.group;

import dongyang.spmis.mapper.ProjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class GroupCheck {

    @Autowired
    ProjectMapper projectMapper;

//    @Test
//    void projectGroupCheck(){
//
//        projectMapper.selectGroupCheck(2, "test@gmail.com");
//    }
}
