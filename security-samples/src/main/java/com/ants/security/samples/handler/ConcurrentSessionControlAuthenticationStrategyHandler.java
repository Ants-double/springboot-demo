package com.ants.security.samples.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.authentication.session.ConcurrentSessionControlAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;

/**
 * @author lyy
 * @describe
 * @date 2021/10/28
 */
// @Slf4j
// @Component
public class ConcurrentSessionControlAuthenticationStrategyHandler extends ConcurrentSessionControlAuthenticationStrategy {

    protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();
    /**
     * @param sessionRegistry the session registry which should be updated when the
     *                        authenticated session is changed.
     */
    public ConcurrentSessionControlAuthenticationStrategyHandler(SessionRegistry sessionRegistry) {
        super(sessionRegistry);
    }

    @Override
    protected void allowableSessionsExceeded(List<SessionInformation> sessions, int allowableSessions, SessionRegistry registry) throws SessionAuthenticationException {

        {
            if ((sessions != null)) {
                throw new SessionAuthenticationException(messages.getMessage(
                        "ConcurrentSessionControlAuthenticationStrategy.exceededAllowed",
                        new Object[]{allowableSessions},
                        "当前账号登录人数超出最大数{0}"));
            }

            // Determine least recently used sessions, and mark them for invalidation
            sessions.sort(Comparator.comparing(SessionInformation::getLastRequest));
            int maximumSessionsExceededBy = sessions.size() - allowableSessions + 1;
            List<SessionInformation> sessionsToBeExpired = sessions.subList(0, maximumSessionsExceededBy);
            for (SessionInformation session : sessionsToBeExpired) {
                session.expireNow();
            }

        }
    }
}
