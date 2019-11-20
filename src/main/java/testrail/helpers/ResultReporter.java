package testrail.helpers;

import helpers.PropertiesResourceManager;
import testrail.api.APIClient;

public class ResultReporter {

    private static PropertiesResourceManager manager = new PropertiesResourceManager("testrail.properties");

    private APIClient apiClient;
    private String projectId;

    private static ResultReporter resultReporter;

    private ResultReporter() {
        this.apiClient = new APIClient(manager.getProperty("testrail.url"));
        this.apiClient.setUser(manager.getProperty("testrail.email"));
        this.apiClient.setPassword(manager.getProperty("testrail.password"));
        this.projectId = manager.getProperty("testrail.projectid");
    }

    public static ResultReporter getResultReporter() {
        if(resultReporter == null) {
            resultReporter = new ResultReporter();
        }
        return resultReporter;
    }

    public APIClient getApiClient() {
        return apiClient;
    }

    public String getProjectId() {
        return projectId;
    }
}
