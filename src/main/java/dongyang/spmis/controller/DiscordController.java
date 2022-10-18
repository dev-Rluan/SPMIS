package dongyang.spmis.controller;

import dongyang.spmis.model.ProjectNoticeDTO;
import dongyang.spmis.model.WebhookLinkDTO;
import dongyang.spmis.properties.WHPropertie;
import dongyang.spmis.service.WebHookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Controller
public class DiscordController {

    @Autowired
    WebHookService webHookService;

    @PostMapping("editWebhookLink")
    public void editWebhookLink(WebhookLinkDTO webhook, HttpServletResponse res) throws IOException {
        res.setContentType("text/html; charset=UTF-8");
        PrintWriter out = res.getWriter();

        if(webHookService.editWebhook(webhook.getProject_id(), webhook.getWebhook_link())){
            out.println("<script>");
            out.println("alert('웹훅 삭제 성공');");
            out.println("location.href='settings?project_id=" + webhook.getProject_id() + "';");
            out.println("</script>");
        }else{
            out.println("<script>");
            out.println("alert('웹훅 삭제 실패');");
            out.println("location.href='settings?project_id=" + webhook.getProject_id() + "';");
            out.println("</script>");
        }
    }

    @PostMapping("addWebhookLink")
    public void addWebhookLink(WebhookLinkDTO webhook, HttpServletResponse res) throws IOException {

        System.out.println(webhook.getWebhook_link());
        res.setContentType("text/html; charset=UTF-8");
        PrintWriter out = res.getWriter();
        WHPropertie result = webHookService.addWebhookLink(webhook.getProject_id(), webhook.getWebhook_link());
        switch(result){
            case DUPLICATE_WEBHOOK_LINK:
                out.println("<script>");
                out.println("alert('이미 동일한 웹훅이 존재합니다.');");
                out.println("location.href='settings?project_id=" + webhook.getProject_id() + "';");
                out.println("</script>");
                break;
            case SUCCESS:
                out.println("<script>");
                out.println("alert('웹훅 추가 성공');");
                out.println("location.href='settings?project_id=" + webhook.getProject_id() + "';");
                out.println("</script>");
                break;
        }

    }

    @PostMapping("sendNotice")
    public void sendMessage(ProjectNoticeDTO notice, HttpServletResponse res) throws IOException {
        res.setContentType("text/html; charset=UTF-8");
        PrintWriter out = res.getWriter();
        WHPropertie result = webHookService.callEvent(notice.getProject_id(), notice.getNotice());
        switch(result){
            case FIND_NOT_LINK:
                out.println("<script>");
                out.println("alert('웹훅 링크가 존재하지 않습니다');");
                out.println("location.href='settings?project_id=" + notice.getProject_id() + "';");
                out.println("</script>");
                break;
            case SUCCESS:
                out.println("<script>");
                out.println("alert('보내기 성공');");
                out.println("location.href='settings?project_id=" + notice.getProject_id() + "';");
                out.println("</script>");
                break;

        }

    }

    @PostMapping("deleteWebhookLink")
    public void deleteWebhookLink(WebhookLinkDTO webhook, HttpServletResponse res) throws IOException {
        res.setContentType("text/html; charset=UTF-8");
        PrintWriter out = res.getWriter();

        if(webHookService.deleteWebhookLink(webhook.getProject_id(), webhook.getWebhook_link())){
            out.println("<script>");
            out.println("alert('웹훅 삭제 성공');");
            out.println("location.href='settings?project_id=" + webhook.getProject_id() + "';");
            out.println("</script>");
        }else{
            out.println("<script>");
            out.println("alert('웹훅 삭제 실패');");
            out.println("location.href='settings?project_id=" + webhook.getProject_id() + "';");
            out.println("</script>");
        }


    }

}
