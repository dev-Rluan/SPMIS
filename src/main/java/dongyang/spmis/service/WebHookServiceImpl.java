package dongyang.spmis.service;

import dongyang.spmis.mapper.DiscordMapper;
import dongyang.spmis.model.ProjectNoticeDTO;
import dongyang.spmis.model.WebhookLinkDTO;
import dongyang.spmis.properties.WHPropertie;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

@Service
public class WebHookServiceImpl implements WebHookService{

    @Autowired
    DiscordMapper discordMapper;

    @Override
    public WHPropertie callEvent(int project_id, String Message) {

        ArrayList<WebhookLinkDTO> linkSet = discordMapper.findWebhookByProjectID(project_id);

        if(linkSet.isEmpty()){
            return WHPropertie.FIND_NOT_LINK;
        }

        for (WebhookLinkDTO webhookLinkDTO : linkSet) {

            JSONObject data = new JSONObject();

            data.put("content", Message);

            send(data, webhookLinkDTO.getWebhook_link());
        }


        return WHPropertie.SUCCESS;

    }

    private void send(JSONObject object, String url){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> entity = new HttpEntity<>(object.toString(), headers);
        restTemplate.postForObject(url, entity, String.class);
    }
    @Override
    public WHPropertie addWebhookLink(int project_id, String link) {
        if(discordMapper.findOneWebhookByProjectID(project_id, link) != null){
            return WHPropertie.DUPLICATE_WEBHOOK_LINK;
        }
        discordMapper.addWebhookLink(project_id, link );
        return WHPropertie.SUCCESS;
    }

    @Override
    public boolean deleteWebhookLink(int project_id, String link) {

        return discordMapper.deleteWebhook(project_id, link );
    }

    @Override
    public ArrayList<WebhookLinkDTO> FindWebhookLinkByProjectID(int project_id) {

        return discordMapper.findWebhookByProjectID(project_id);
    }

    @Override
    public ArrayList<ProjectNoticeDTO> FindNoticeByProjectID(int project_id) {
        return discordMapper.findProjectNoticeByProject_id(project_id);
    }

    @Override
    public boolean editWebhook(int project_id, String link) {
        return discordMapper.updateWebhook(project_id, link);
    }


}
