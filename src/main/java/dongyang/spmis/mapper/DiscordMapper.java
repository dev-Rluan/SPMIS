package dongyang.spmis.mapper;

import dongyang.spmis.model.DiscordLinkDTO;
import dongyang.spmis.model.ProjectNoticeDTO;
import dongyang.spmis.model.WebhookLinkDTO;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;

public interface DiscordMapper extends DefaultDBInfo {

    @Select("Select * FROM " + PROJECT_NOTICE + " WHERE project_id=#{project_id}")
    ProjectNoticeDTO discordNoticeFindByProjectID(String project_id);

    @Select("Select * FROM " + DISCORD_LINK + " WHERE project_id=#{project_id}")
    DiscordLinkDTO discordLinkFindByProjectID(int project_id);

    @Select("Select dl.project_id, link FROM " + DISCORD_LINK + " as dl, "+ PROJECTJOIN +" as pj " +
            " WHERE dl.project_id = pj.project_id" +
            " AND pj.user_email=#{user_email}")
    ArrayList<DiscordLinkDTO> discordLinkFindByUserEmail(String user_email);

    @Insert("INSERT INTO " + DISCORD_LINK + " VALUES (#{project_id}, #{link} )")
    boolean saveDiscordLink(DiscordLinkDTO discordLinkDTO);

    @Delete("DELETE FROM " + DISCORD_LINK + " WHERE project_id=#{project_id} AND link=#{link}")
    boolean deleteDiscordLink(DiscordLinkDTO discordLinkDTO);

    @Insert("INSERT INTO " + WEBHOOK_LINK + " VALUES (#{project_id}, #{webhook_link})")
    boolean addWebhookLink(@Param("project_id") int project_id,@Param("webhook_link") String webhook_link);

    @Select("Select * from " + WEBHOOK_LINK + " WHERE project_id=#{project_id} ")
    ArrayList<WebhookLinkDTO> findWebhookByProjectID(int project_id);

    @Select("Select * from " + WEBHOOK_LINK + " WHERE project_id=#{project_id} AND webhook_link=#{webhook_link}")
    WebhookLinkDTO findOneWebhookByProjectID(@Param("project_id") int project_id,@Param("webhook_link") String webhook_link);

    @Select("Select  from " + WEBHOOK_LINK + " WHERE (#{project_id})")
    ArrayList<WebhookLinkDTO> findWebhookByProjectIdAndUser(int project_id, String user_email);

    @Update("UPDATE" + DISCORD_LINK + " SET webhook_link=#{webhook_link}  WHERE project_id=#{project_id}")
    boolean updateWebhook(int project_id, String webhook_link);

    @Delete("DELETE FROM " + WEBHOOK_LINK + " WHERE project_id=#{project_id} AND webhook_link=#{webhook_link}")
    boolean deleteWebhook(int project_id, String webhook_link);

    @Select("SELECT * FROM " + PROJECT_NOTICE + " WHERE project_id=#{project_id}")
    ArrayList<ProjectNoticeDTO> findProjectNoticeByProject_id(int Project_id);


}
