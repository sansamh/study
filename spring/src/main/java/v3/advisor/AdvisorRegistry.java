package v3.advisor;

import java.util.List;

/**
 * 注册advisors
 */
public interface AdvisorRegistry {

    void registAdvisor(Advisor advisor);

    List<Advisor> getAdvisor();
}
