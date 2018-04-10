package com.application.controller.rest;

import com.application.controller.client.Client;

import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import java.util.Date;

@Stateless
@Path("restService")
public class RestService {

    @GET
    public void service(@Suspended AsyncResponse asyncResponse) {
        Client client = new Client();
        client.setAsyncResponse(asyncResponse);
        client.connect();
        client.send("Message from client " + new Date().getTime());
        client.close();
    }
}
