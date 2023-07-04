package kz.attractor.java.LabWork;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.sun.net.httpserver.HttpExchange;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import kz.attractor.java.server.BasicServer;
import kz.attractor.java.server.ContentType;
import kz.attractor.java.server.ResponseCodes;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VotingServer extends BasicServer {
    private final static Configuration freemarker = initFreeMarker();

    List<Member> members= new ArrayList<>();

    public VotingServer(String host, int port) throws IOException {
        super(host, port);
        registerGet("/", this::showMain);
        registerGet("/votes", this::showVotes);
        registerGet("/thanks", this::showThanks);
        initMembers();
    }

    private void showThanks(HttpExchange exchange) {
        DataModel dataModel = new DataModel(members);
        renderTemplate(exchange,"thankyou.html",dataModel);
    }

    private void initMembers() throws FileNotFoundException {
        Gson gson = new Gson();
        JsonReader reader = new JsonReader(new FileReader("data/candidates.json"));

       members= Arrays.asList(gson.fromJson(reader,Member[].class));

       int id = 0;

        for (Member member : members) {
            member.setId(id++);
            id +=1;
        }
    }

    private static Configuration initFreeMarker() {
        try {
            Configuration cfg = new Configuration(Configuration.VERSION_2_3_29);
            cfg.setDirectoryForTemplateLoading(new File("/data"));
            cfg.setDefaultEncoding("UTF-8");
            cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
            cfg.setLogTemplateExceptions(false);
            cfg.setWrapUncheckedExceptions(true);
            cfg.setFallbackOnNullLoopVariable(false);
            return cfg;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void showMain(HttpExchange exchange) {
        DataModel dataModel = new DataModel(members);
        renderTemplate(exchange,"candidates.ftl",dataModel);
    }

    private void showVote(HttpExchange exchange) {
        DataModel dataModel = new DataModel(members);
        renderTemplate(exchange,"candidates.ftl",dataModel);
    }

    private void showVotes(HttpExchange exchange) {
        DataModel dataModel = new DataModel(members);
        renderTemplate(exchange,"votes.ftl",dataModel);
    }

protected void renderTemplate(HttpExchange exchange, String templateFile,Object dataModel) {
    try {
        Template temp = freemarker.getTemplate(templateFile);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        try (OutputStreamWriter writer = new OutputStreamWriter(stream)) {
            temp.process(dataModel, writer);
            writer.flush();
            var data = stream.toByteArray();
            sendByteData(exchange, ResponseCodes.OK, ContentType.TEXT_HTML, data);
        }
    } catch (IOException | TemplateException e) {
        e.printStackTrace();
    }
}

}
