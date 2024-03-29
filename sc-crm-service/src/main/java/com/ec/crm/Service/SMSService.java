package com.ec.crm.Service;

import com.ec.crm.Data.SMSGatewayResponse;
import com.ec.crm.ReusableClasses.ProjectConstants;
import com.ec.crm.ReusableClasses.ReusableMethods;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.util.HashMap;

@Service
public class SMSService {

    @Value("${sendsms}")
    private Boolean sendSms;

    @Autowired
    InstanceService instanceService;
    Logger log = LoggerFactory.getLogger(SMSService.class);

    public SMSGatewayResponse sendSMStoGateway(HashMap<String,String> payloadData) throws UnirestException {

        log.info("Body - "+ReusableMethods.convertObjectToJson(payloadData));
        if (sendSms && instanceService.getEnvironment().equals("prod")) {
            HttpResponse<JsonNode> response = Unirest.post(ProjectConstants.smsGatewayURL)
                    .header("authkey", ProjectConstants.authKey)
                    .header("content-type", "application/JSON")
                    .body(ReusableMethods.convertObjectToJson(payloadData))
                    .asJson();
            JSONObject myObj = response.getBody().getObject();
            SMSGatewayResponse res = new SMSGatewayResponse(myObj.getString("message"), myObj.getString("type"));
            log.info("SMS Gateway Response - " + res.toString());
            return res;
        } else {
            SMSGatewayResponse res = new SMSGatewayResponse("message", "SMS Service Disabled");
            log.info("SMS Gateway Response - " + res.toString());
            return res;
        }
    }
}
