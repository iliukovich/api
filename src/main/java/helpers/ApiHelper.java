package helpers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.HttpClients;

import java.io.*;
import java.net.URISyntaxException;
import java.util.List;

public class ApiHelper {

    private HttpClient httpClient = HttpClients.custom().build();

    public HttpResponse sendGetRequest(String url) {
        try {
            return sendRequest(new HttpGet(new URIBuilder(url).build()));
        } catch (URISyntaxException e) {
            throw new Error(e.getMessage());
        }
    }

    public HttpResponse sendPostRequest(String url, HttpEntity entity) {
        HttpPost postRequest = new HttpPost(url);
        postRequest.setEntity(entity);
        return sendRequest(postRequest);
    }

    public HttpResponse sendPostRequest(String url, List<NameValuePair> data) {
        try {
            return sendPostRequest(url, new UrlEncodedFormEntity(data));
        } catch (UnsupportedEncodingException e) {
            throw new Error(e.getMessage());
        }
    }

    public HttpResponse postUploadPhoto(String uriForUpload, String filePath) {
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.addTextBody("photo", "uploaded photo", ContentType.TEXT_PLAIN);

        File photo = new File(filePath);
        try {
            builder.addBinaryBody(
                    "file",
                    new FileInputStream(photo),
                    ContentType.APPLICATION_OCTET_STREAM,
                    photo.getName()
            );
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        HttpEntity multipart = builder.build();
        return sendPostRequest(uriForUpload, multipart);
    }

    protected <T> T mapToObject(HttpResponse response, Class<T> className) {
        ObjectMapper objectMapper = new ObjectMapper();
        T object = null;
        String responseBody = null;
        try {
            responseBody = getContent(response);
            object = objectMapper.readValue(responseBody, className);
        } catch (IOException e) {
            e.getStackTrace();
        }
        return object;
    }

    private String getContent(HttpResponse response) {
        try (InputStream stream = response.getEntity().getContent()) {
            return new String(IOUtils.toByteArray(stream));
        } catch (IOException e) {
            throw new Error(e.getMessage());
        }
    }

    private HttpResponse sendRequest(HttpRequestBase request) {
        try {
            return httpClient.execute(request);
        } catch (IOException e) {
            throw new Error(e.getMessage());
        }
    }
}
