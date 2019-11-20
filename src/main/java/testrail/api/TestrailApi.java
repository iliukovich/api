package testrail.api;

import org.json.simple.JSONObject;
import org.testng.ITestResult;
import testrail.helpers.ResultReporter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class TestrailApi {

    private ResultReporter resultReporter = ResultReporter.getResultReporter();

    public Long createRun() {
        Map<String, Object> data = new HashMap<>();
        data.put("name", String.format("Run%s", new Random().nextInt(1000)));
        data.put("include_all", true);
        long runId = 0;
        try {
            JSONObject response = (JSONObject) resultReporter.getApiClient().sendPost(String.format("add_run/%s", resultReporter.getProjectId()), data);
            runId = (long) response.get("id");
        } catch (IOException | APIException e) {
            e.printStackTrace();
        }
        return runId;
    }

    public void postTestResults(long runId, int caseId, ITestResult testResult, String attachmentUrl) {
        Map<String, Object> data = new HashMap<>();
        data.put("status_id", getTestResultStatusCode(testResult).getId());
        data.put("comment", attachmentUrl);
        try {
            resultReporter.getApiClient().sendPost(String.format("add_result_for_case/%s/%s", runId, caseId), data);
        } catch (IOException | APIException e) {
            e.printStackTrace();
        }
    }

    private TestrailTestStatus getTestResultStatusCode(ITestResult testResult) {
        switch (testResult.getStatus()) {
            case ITestResult.SUCCESS:
                return TestrailTestStatus.PASSED;
            case ITestResult.FAILURE:
                return TestrailTestStatus.FAILED;
            default:
                throw new AssertionError("Unsupported test result");
        }
    }
}
