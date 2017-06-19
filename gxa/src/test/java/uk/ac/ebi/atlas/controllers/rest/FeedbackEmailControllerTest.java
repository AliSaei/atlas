package uk.ac.ebi.atlas.controllers.rest;

import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.util.MultiValueMap;
import uk.ac.ebi.atlas.commons.mail.EmailMessage;
import uk.ac.ebi.atlas.commons.mail.MailService;
import uk.ac.ebi.atlas.web.ApplicationProperties;

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FeedbackEmailControllerTest {

    public static final String FEEDBACK_MESSAGE = "feedbackMessage";
    public static final String FEEDBACK_EMAIL_ADDRESS = "feedbackEmailAddress";
    public static final String SUBJECT = "Atlas Feedback";
    @Mock
    private MailService mailServiceMock;

    @Mock
    private EmailMessage emailMessageMock;

    @Mock
    private ApplicationProperties applicationPropertiesMock;

    private FeedbackEmailController subject;

    @Before
    public void setUp() throws Exception {
        subject = new FeedbackEmailController(mailServiceMock, emailMessageMock, applicationPropertiesMock);

        when(applicationPropertiesMock.getFeedbackEmailAddress()).thenReturn(FEEDBACK_EMAIL_ADDRESS);
    }

    @Test
    public void testSendFeedbackMail() throws Exception {
        subject.sendFeedbackMail(FEEDBACK_MESSAGE, FEEDBACK_EMAIL_ADDRESS);

        verify(emailMessageMock).setBody(FEEDBACK_MESSAGE);
        verify(emailMessageMock).setSender(FEEDBACK_EMAIL_ADDRESS);
        verify(emailMessageMock).setSubject(SUBJECT);
        verify(emailMessageMock).setRecipient(FEEDBACK_EMAIL_ADDRESS);
        verify(mailServiceMock).send(emailMessageMock);

    }

}