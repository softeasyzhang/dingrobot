package com.github.softeasyzhang.dingrobot.scheduled;

import com.dingtalk.chatbot.DingtalkChatbotClient;
import com.dingtalk.chatbot.message.TextMessage;
import com.github.softeasyzhang.dingrobot.entity.Employee;
import com.github.softeasyzhang.dingrobot.service.IWhoIsTurn;
import com.github.softeasyzhang.dingrobot.util.DefaultValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author EasyZhang
 * @date 2018/11/23 -  14:13
 */
@Component
public class EverydayTask {

    @Autowired
    private IWhoIsTurn whoIsTurn;

    private static final Logger logger = LoggerFactory.getLogger(EverydayTask.class);
    //@Scheduled(cron="*/30 * * * * ?")
    @Scheduled(cron="0 26 9 ? * *")
    private void process(){
        send();
        logger.info("执行每天发送.....");
    }

    private void send(){
        Employee employee = whoIsTurn.getWhoIsTurn();

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("爸爸们:").append("\n ------------------")
                .append("\n今日值班人:").append(employee.getName())
                .append("\n链接:").append("http://confluence.daojia-inc.com/pages/viewpage.action?pageId=79030052 ")
                .append("\n--------------------").append("\n");

        logger.info("发送信息{}",stringBuilder.toString());

        TextMessage textMessage = new TextMessage(stringBuilder.toString());
        textMessage.setIsAtAll(true);

        DingtalkChatbotClient dingtalkChatbotClient = new DingtalkChatbotClient();
        try {
            dingtalkChatbotClient.send(DefaultValue.ypyfbRobot, textMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
