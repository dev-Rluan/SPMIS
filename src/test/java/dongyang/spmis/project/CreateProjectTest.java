package dongyang.spmis.project;

import dongyang.spmis.mapper.ProjectMapper;
import dongyang.spmis.model.KanbanDTO;
import dongyang.spmis.model.ProjectDTO;
import dongyang.spmis.model.ProjectJoinDTO;
import dongyang.spmis.model.UserDTO;
import dongyang.spmis.service.ProjectService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CreateProjectTest {

    @Autowired
    private ProjectService projectService;

//    @Test
//    public void CreateTest(){
//        UserDTO user = new UserDTO();
//        user.setUser_email("test@gmail.com");
//
//        ProjectDTO project = new ProjectDTO();
//        project.setProject_name("test");
//        project.setProject_des("testproject");
//        project.setPrivacy_scope("public");
//
//        ProjectJoinDTO join = new ProjectJoinDTO();
//        projectService.createProject(project);
//
//        // test
//        Assertions.assertThat(projectService.createProject(project)).isTrue();
//
//        project.setProject_id(projectService.selectLatestProject().getProject_id());
//
//        join.setUser_email(user.getUser_email());
//        join.setProject_id(project.getProject_id());
//        join.setRole("관리자");
//        join.setJoin_status("admin");
//
//        //test
//        Assertions.assertThat(projectService.insertGroup(join)).isTrue();
//
//        // test
//        Assertions.assertThat(projectService.createDefaultKanban(project)).isTrue();
//
//
//
//
//
//
//    }
}
